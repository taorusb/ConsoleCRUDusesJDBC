package com.taorusb.consolecrundusesjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Supplier;

public class JDBCConnectionSupplier implements Supplier<Connection> {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String dbURL;
    private String user;
    private String password;

    public JDBCConnectionSupplier(String dbURL, String user, String password) {
        this.dbURL = dbURL;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection get() {
        Connection connection = null;
        try {
            return DriverManager.getConnection(dbURL, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}