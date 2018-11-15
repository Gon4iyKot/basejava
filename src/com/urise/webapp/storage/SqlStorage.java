package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
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
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        String sqlQuery1 = "UPDATE resume SET full_name=? WHERE uuid=?";
        String sqlQuery2 = "DELETE FROM contact WHERE resume_uuid=?";
        sqlHelper.transactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery1)) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(sqlQuery2)) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            insertContacts(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String sqlQuery = "SELECT * FROM resume AS r" +
                " LEFT JOIN contact AS c" +
                " ON r.uuid = c.resume_uuid" +
                " WHERE r.uuid=? ";
        return sqlHelper.execute(sqlQuery, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                if (rs.getString("type") != null) {
                    resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }
            } while (rs.next());
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
        String sqlQuery = "SELECT * FROM resume AS r" +
                " LEFT JOIN contact AS c" +
                " ON r.uuid = c.resume_uuid" +
                " ORDER BY full_name, uuid";
        return sqlHelper.execute(sqlQuery, ps -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String full_name = rs.getString("full_name");
                String type = rs.getString("type");
                String value = rs.getString("value");
                resumeMap.computeIfAbsent(uuid, a -> new Resume(uuid, full_name));
                resumeMap.computeIfPresent(uuid, (a, resume) -> {
                    resume.addContact(ContactType.valueOf(type), value);
                    return resume;
                });
            }
            return new ArrayList<>(resumeMap.values());
        });
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
}