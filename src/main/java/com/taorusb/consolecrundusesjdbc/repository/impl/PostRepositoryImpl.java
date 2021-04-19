package com.taorusb.consolecrundusesjdbc.repository.impl;

import com.taorusb.consolecrundusesjdbc.model.Post;
import com.taorusb.consolecrundusesjdbc.model.Writer;
import com.taorusb.consolecrundusesjdbc.repository.PostRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

public class PostRepositoryImpl implements PostRepository {

    private Supplier<Connection> connectionSupplier;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private final static SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd/MM/yyyy");
    private static PostRepositoryImpl instance;

    private PostRepositoryImpl() {
    }

    public static PostRepositoryImpl getInstance() {

        if(instance == null) {
            instance = new PostRepositoryImpl();
        }
        return instance;
    }

    public void setConnectionSupplier(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public Post getById(Long id) {

        Connection connection = connectionSupplier.get();
        Post post = null;

        try {
            preparedStatement = connection
                    .prepareStatement("select * from posts where id = ?",
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, id.intValue());
            resultSet = preparedStatement.executeQuery();

            if (getResultSize() != 1) {
                throw new NoSuchElementException("Entity not found.");
            }

            resultSet.next();
            post = getPostFromRow();

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return post;
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
        return post;
    }

    @Override
    public void deleteById(Long id) {

        Connection connection = connectionSupplier.get();

        try {
            preparedStatement = connection.prepareStatement("delete from posts where id = ?");
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
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public List<Post> findAll() {

        Connection connection = connectionSupplier.get();
        List<Post> posts = new LinkedList<>();

        try {
            preparedStatement = connection.prepareStatement("select * from posts");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                posts.add(getPostFromRow());
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return posts;
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

        return posts;
    }

    @Override
    public Post save(Post entity) {

        Connection connection = connectionSupplier.get();

        try {
            preparedStatement = connection.prepareStatement("insert into posts(content, created, updated, writer_id) values(?, ?, ?, ?)");
            preparedStatement.setString(1, entity.getContent());
            preparedStatement.setString(2, dateFormat.format(new Date()));
            preparedStatement.setString(3, "-");
            preparedStatement.setLong(4, entity.getWriter().getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return entity;
    }

    @Override
    public Post update(Post entity) {

        Connection connection = connectionSupplier.get();
        Post updatableEntity;
        String updatedDate = dateFormat.format(new Date());

        try {
            updatableEntity = getById(entity.getId());
            preparedStatement = connection.prepareStatement(
                    "update posts set content = ?, updated = ? where id = ?");

            preparedStatement.setString(1, entity.getContent());
            preparedStatement.setString(2, updatedDate);
            preparedStatement.setLong(3, entity.getId());

            entity.setWriter(updatableEntity.getWriter());
            entity.setCreated(updatableEntity.getCreated());
            entity.setUpdated(updatedDate);

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

    private Post getPostFromRow() {

        Post post;
        long id = 0L;
        String content = null;
        String created = null;
        String updated = null;
        long writerId = 0;

        try {
            id = resultSet.getLong("id");
            content = resultSet.getString("content");
            created = resultSet.getString("created");
            updated = resultSet.getString("updated");
            writerId = resultSet.getLong("writer_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        post = new Post(id, content, created, updated, new Writer(writerId));

        return post;
    }
}