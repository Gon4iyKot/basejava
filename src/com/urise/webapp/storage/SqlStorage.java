package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
        try {
            Class.forName((new org.postgresql.Driver()).getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        String sqlQuery = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        sqlHelper.transactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        String sqlQuery = "UPDATE resume SET full_name=? WHERE uuid=?";
        sqlHelper.transactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContact(resume, connection);
            deleteSection(resume, connection);
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String sqlQuery1 = "SELECT * FROM resume AS r WHERE r.uuid=?";
        String sqlQuery2 = "SELECT * FROM contact AS c WHERE c.resume_uuid=?";
        String sqlQuery3 = "SELECT * FROM section AS s WHERE s.resume_uuid=?";
        return sqlHelper.transactionExecute(connection -> {
            Resume resume;
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery1)) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
            }
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery2)) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    addContact(resultSet, resume);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery3)) {
                ps.setString(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    addSection(resume, resultSet);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        String sqlQuery = "DELETE FROM resume r WHERE r.uuid=?";
        sqlHelper.execute(sqlQuery, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String sqlQuery1 = "SELECT * FROM resume ORDER BY full_name, uuid";
        String sqlQuery2 = "SELECT * FROM contact";
        String sqlQuery3 = "SELECT * FROM section";
        return sqlHelper.transactionExecute(connection -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery1)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    String fullname = resultSet.getString("full_name");
                    resumeMap.put(uuid, new Resume(uuid, fullname));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery2)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String resume_uuid = resultSet.getString("resume_uuid");
                    Resume resume = resumeMap.get(resume_uuid);
                    addContact(resultSet, resume);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery3)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String resume_uuid = resultSet.getString("resume_uuid");
                    Resume resume = resumeMap.get(resume_uuid);
                    addSection(resume, resultSet);
                }
            }
            return new ArrayList<>(resumeMap.values());
        });
    }

    private void addContact(ResultSet resultSet, Resume resume) throws SQLException {
        String value = resultSet.getString("value");
        ContactType type = ContactType.valueOf(resultSet.getString("type"));
        if (value != null) {
            resume.addContact(type, value);
        }
    }

    private void addSection(Resume resume, ResultSet resultSet) throws SQLException {
        SectionType type = SectionType.valueOf(resultSet.getString("section_type"));
        String content = resultSet.getString("content");
        resume.addSection(type, JsonParser.read(content, Section.class));
    }

    @Override
    public int size() {
        String sqlQuery = "SELECT COUNT(*) FROM resume";
        return sqlHelper.execute(sqlQuery, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                ps.setString(1, contact.getKey().name());
                ps.setString(2, contact.getValue());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (section_type, content, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> section : resume.getSections().entrySet()) {
                String sectionType = section.getKey().name();
                String content = JsonParser.write(section.getValue(), Section.class);
                ps.setString(1, sectionType);
                ps.setString(2, content);
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSection(Resume resume, Connection connection) throws SQLException {
        deleteAttributes(resume, connection, "DELETE FROM section WHERE resume_uuid=?");
    }

    private void deleteContact(Resume resume, Connection connection) throws SQLException {
        deleteAttributes(resume, connection, "DELETE FROM contact WHERE resume_uuid=?");
    }

    private void deleteAttributes(Resume resume, Connection connection, String sqlQuery) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}