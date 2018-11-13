package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        String prepStatement1 = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        String prepStatement2 = "INSERT INTO contact (type, value, resume_uuid) VALUES (?,?,?)";
        sqlHelper.transactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(prepStatement1)) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = connection.prepareStatement(prepStatement2)) {
                for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                    ps.setString(1, contact.getKey().name());
                    ps.setString(2, contact.getValue());
                    ps.setString(3, resume.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        String prepStatement1 = "UPDATE resume SET full_name=? WHERE uuid=?";
        String prepStatement2 = "UPDATE contact SET value=? WHERE resume_uuid=? AND type=?";
        sqlHelper.transactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(prepStatement1)) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            if (resume.getContacts().size() != 0) {
                try (PreparedStatement ps = connection.prepareStatement(prepStatement2)) {
                    for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                        ps.setString(3, entry.getKey().name());
                        ps.setString(1, entry.getValue());
                        ps.setString(2, resume.getUuid());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            } else {
                sqlHelper.execute("DELETE FROM contact where resume_uuid=?", ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.execute();
                    return null;
                });
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String prepStatement = "SELECT * FROM resume AS r" +
                " LEFT JOIN contact AS c" +
                " ON r.uuid = c.resume_uuid" +
                " WHERE r.uuid=? ";
        return sqlHelper.execute(prepStatement, ps -> {
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
        String prepStatement = "DELETE FROM resume r WHERE r.uuid=?";
        sqlHelper.execute(prepStatement, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String prepStatement = "SELECT * FROM resume AS r" +
                " LEFT JOIN contact AS c" +
                " ON r.uuid = c.resume_uuid" +
                " ORDER BY uuid, full_name";
        return sqlHelper.execute(prepStatement, ps -> {
            Map<String, Resume> treeMap = new TreeMap<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                if (treeMap.get(uuid) == null) {
                    treeMap.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
                treeMap.get(uuid).addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
            }
            return new ArrayList<>(treeMap.values());
        });
    }

    @Override
    public int size() {
        String prepStatement = "SELECT COUNT(*) FROM resume";
        return sqlHelper.execute(prepStatement, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}