package com.taorusb.consolecrundusesjdbc.repository.impl;


import com.taorusb.consolecrundusesjdbc.model.Post;
import com.taorusb.consolecrundusesjdbc.model.Region;
import com.taorusb.consolecrundusesjdbc.model.Role;
import com.taorusb.consolecrundusesjdbc.model.Writer;
import com.taorusb.consolecrundusesjdbc.repository.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

public class WriterRepositoryImpl implements WriterRepository {

    private Supplier<Connection> connectionSupplier;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private static WriterRepositoryImpl instance;

    private WriterRepositoryImpl() {
    }

    public static WriterRepositoryImpl getInstance() {

        if (instance == null) {
            instance = new WriterRepositoryImpl();
        }
        return instance;
    }

    public void setConnectionSupplier(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public Writer getById(Long id) {

        Connection connection = connectionSupplier.get();
        Writer writer = null;

        try {
            preparedStatement = connection
                    .prepareStatement("select * from writers w left join posts p on w.id = p.writer_id join regions r on w.region_id = r.id where w.id = ?"
                            , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, id.intValue());
            resultSet = preparedStatement.executeQuery();

            if (getResultSize() == 0) {
                throw new NoSuchElementException("Entity not found.");
            }

            resultSet.next();
            writer = getWriterFromRow();

            resultSet.close();
            preparedStatement.close();
            connection.close();
            return writer;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return writer;
    }

    @Override
    public void deleteById(Long id) {

        Connection connection = connectionSupplier.get();

        try {
            preparedStatement = connection.prepareStatement("delete from writers where id = ?");
            preparedStatement.setInt(1, id.intValue());

            int result = preparedStatement.executeUpdate();
            if (result != 1) {
                throw new NoSuchElementException("Entity not found.");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Writer> findAll() {

        Connection connection = connectionSupplier.get();
        List<Writer> writers = new LinkedList<>();

        try {
            preparedStatement = connection.prepareStatement("select * from writers w left join posts p on w.id = p.writer_id join regions r on w.region_id = r.id  order by w.id");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                writers.add(getWriterFromRow());
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
            return writers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return writers;
    }

    @Override
    public Writer save(Writer entity) {

        Connection connection = connectionSupplier.get();

        try {
            preparedStatement = connection.prepareStatement("insert into writers(first_name, last_name, region_id, role) values(?, ?, ?, ?)");
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setLong(3, entity.getRegion().getId());
            preparedStatement.setString(4, Role.USER.name());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    @Override
    public Writer update(Writer entity) {

        Connection connection = connectionSupplier.get();

        try {
            preparedStatement = connection.prepareStatement(
                    "update writers set first_name = ?, last_name = ?, region_id = ? where id = ?");
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setLong(3, entity.getRegion().getId());
            preparedStatement.setLong(4, entity.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new NoSuchElementException("Entity not found.");
            }

            preparedStatement.close();
            connection.close();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    private int getResultSize() {

        int size;
        try {
            resultSet.last();
            size = resultSet.getRow();
            resultSet.beforeFirst();
        } catch (SQLException e) {
            return 0;
        }
        return size;
    }

    private Writer getWriterFromRow() {

        Writer writer = new Writer();
        long writerId;
        String firstName;
        String lastName;
        String role;

        Region region;
        long regionId;
        String regionName;

        Post post;
        List<Post> posts = new LinkedList<>();
        long postId;
        String content;
        String created;
        String updated;

        try {
            regionId = resultSet.getLong("region_id");
            regionName = resultSet.getString("name");
            region = new Region(regionId, regionName);

            writerId = resultSet.getLong("w.id");
            firstName = resultSet.getString("first_name");
            lastName = resultSet.getString("last_name");
            role = resultSet.getString("role");
            writer = new Writer(writerId, firstName, lastName, region);
            writer.setRole(Role.valueOf(role));

            if (resultSet.getLong("p.id") > 0) {
                postId = resultSet.getLong("p.id");
                content = resultSet.getString("content");
                created = resultSet.getString("created");
                updated = resultSet.getString("updated");
                post = new Post(postId, content, created, updated, writer);
                posts.add(post);

                while (resultSet.next() && resultSet.getLong("w.id") == writerId) {
                    postId = resultSet.getLong("p.id");
                    content = resultSet.getString("content");
                    created = resultSet.getString("created");
                    updated = resultSet.getString("updated");
                    post = new Post(postId, content, created, updated, writer);
                    posts.add(post);
                }
                resultSet.previous();
            }

            writer.setPosts(posts);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return writer;
    }
}