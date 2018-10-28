package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        String prepStatement = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        sqlHelper.execute(prepStatement, ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.executeUpdate();
        });
    }

    @Override
    public void update(Resume resume) {
        String prepStatement = "UPDATE resume SET full_name=? WHERE uuid=?";
        sqlHelper.executeAndReturn(prepStatement, ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String prepStatement = "SELECT * FROM resume r WHERE r.uuid=?";
        return sqlHelper.executeAndReturn(prepStatement, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        String prepStatement = "DELETE FROM resume WHERE uuid=?";
        sqlHelper.execute(prepStatement, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String prepStatement = "SELECT * FROM resume ORDER BY uuid, full_name";
        return sqlHelper.executeAndReturn(prepStatement, ps -> {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public int size() {
        String prepStatement = "SELECT COUNT(*) FROM resume";
        return sqlHelper.executeAndReturn(prepStatement, ps -> {
            ResultSet rs = ps.executeQuery();
            int counter = 0;
            if (rs.next()) {
                counter = rs.getInt(1);
            }
            return counter;
        });
    }
}