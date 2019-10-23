package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Contact;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        Contact value = new Contact(rs.getString("value"));
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        r.addContact(type, value);
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    duplication(conn, "UPDATE resume SET full_name =? WHERE uuid =?", "UPDATE contact SET value =? WHERE type =? AND resume_uuid =?", r,
                            1, 3);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    duplication(conn, "INSERT INTO resume (full_name, uuid) VALUES (?,?)", "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", r,
                            3, 1);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid =?")) {
                        ps.setString(1, uuid);
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(uuid);
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
                        ps.setString(1, uuid);
                    }
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        " ORDER BY full_name,uuid ",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    boolean next = false;
                    do {
                        if (!next) {
                            rs.next();
                        }
                        next = true;
                        Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                        while (rs.getString("uuid").equals(r.getUuid())) {
                            Contact value = new Contact(rs.getString("value"));
                            ContactType type = ContactType.valueOf(rs.getString("type"));
                            r.addContact(type, value);
                            if (!rs.next()) {
                                next = false;
                                break;
                            }
                        }
                        resumes.add(r);
                    } while (next);
                    return resumes;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void duplication(Connection conn, String query1, String query2, Resume r, int one, int three) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query1)) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (one == 1 && ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            ps.execute();
        }
        try (PreparedStatement ps = conn.prepareStatement(query2)) {
            for (Map.Entry<ContactType, Contact> e : r.getContacts().entrySet()) {
                ps.setString(one, e.getValue().getContact());
                ps.setString(2, e.getKey().name());
                ps.setString(three, r.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

}