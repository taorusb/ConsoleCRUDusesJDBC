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

    private PostRepository postRepository;
    private RegionRepository regionRepository;
    private Supplier<Connection> connectionSupplier;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private static WriterRepositoryImpl instance;

    private WriterRepositoryImpl() {
    }

    public static WriterRepositoryImpl getInstance() {

        if(instance == null) {
            instance = new WriterRepositoryImpl();
        }
        return instance;
    }



    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void setRegionRepository(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
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
                    .prepareStatement("select * from writers where id = ?"
                            , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setInt(1, id.intValue());
            resultSet = preparedStatement.executeQuery();

            if (getResultSize() != 1) {
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
            preparedStatement = connection.prepareStatement("select * from writers");
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
            preparedStatement.setInt(3, entity.getRegion().getId().intValue());
            preparedStatement.setInt(4, entity.getId().intValue());

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

        Writer writer;
        long id = 0;
        String firstName = null;
        String lastName = null;
        String role = null;
        List<Post> posts;
        Region region;
        long regionId = 0;

        try {
            id = resultSet.getLong("id");
            firstName = resultSet.getString("first_name");
            lastName = resultSet.getString("last_name");
            role = resultSet.getString("role");
            regionId = resultSet.getLong("region_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        region = regionRepository.getById(regionId);

        writer = new Writer(id, firstName, lastName, region);
        try {
            posts = postRepository.getByWriterId(id);
        } catch (NoSuchElementException e) {
            posts = new LinkedList<>();
        }
        writer.setPosts(posts);
        writer.setRole(Role.valueOf(role));

        return writer;
    }
}