package com.taorusb.consolecrundusesjdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        String dbURL = "jdbc:mysql://localhost/writers_info";
        String username = "root";
        String password = "root";

        Facade facade = Facade.getInstance();

        facade.setDBConnectionInfo(dbURL, username, password);
        facade.assembleApplication();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                facade.startApp(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println("I/O error.");
        }
    }
}