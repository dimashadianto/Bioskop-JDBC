package com.enigma.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db_Connector {
    private String url = "jdbc:postgresql://localhost:5432/db_bioskop";
    private String username = "postgres";
    private String password = "adm1234";

    private static Connection con;
    public Connection startConnect() {
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }
}
