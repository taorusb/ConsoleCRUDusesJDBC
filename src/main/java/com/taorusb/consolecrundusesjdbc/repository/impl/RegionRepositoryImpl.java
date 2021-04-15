package com.taorusb.consolecrundusesjdbc.repository.impl;

import com.taorusb.consolecrundusesjdbc.model.Region;
import com.taorusb.consolecrundusesjdbc.repository.RegionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class RegionRepositoryImpl implements RegionRepository {

    private Supplier<Connection> connectionSupplier;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private static RegionRepositoryImpl instance;

    private RegionRepositoryImpl() {
    }

    public static RegionRepositoryImpl getInstance() {

        if(instance == null) {
            instance = new RegionRepositoryImpl();
        }
        return instance;
    }

    public void setConnectionSupplier(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public Region getById(Long id) {

        Connection connection = connectionSupplier.get();
        Region region = null;

        try {
            preparedStatement = connection
                    .prepareStatement("select * from regions where id = ?"
                            , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (getResultSize() != 1) {
                throw new NoSuchElementException("Entity not found.");
            }

            resultSet.next();

            long regId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            region = new Region(regId, name);

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return region;
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
        return region;
    }

    @Override
    public void deleteById(Long id) {

        Connection connection = connectionSupplier.get();

        try {
            preparedStatement = connection.prepareStatement("delete from regions where id = ?");
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
    public List<Region> findAll() {

        Connection connection = connectionSupplier.get();
        List<Region> regions = new LinkedList<>();
        Region region;

        try {
            preparedStatement = connection.prepareStatement("select * from regions");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long regId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                region = new Region(regId, name);
                regions.add(region);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
            return regions;
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
        return regions;
    }

    @Override
    public Region save(Region entity) {

        Connection connection = connectionSupplier.get();

        try {
            preparedStatement = connection.prepareStatement("insert into regions(name) values(?)");
            preparedStatement.setString(1, entity.getName());
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return entity;
    }

    @Override
    public Region update(Region entity) {

        Connection connection = connectionSupplier.get();

        try {
            preparedStatement = connection.prepareStatement(
                    "update regions set name = ? where id = ?");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 0) {
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
}