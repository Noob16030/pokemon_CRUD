package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariDBSource {
    private HikariDBSource(){}

    private static final HikariDataSource ds;

    static {
        final Properties prop = new Properties();
        try {
            prop.load(HikariDBSource.class.getResourceAsStream("/application.properties"));
        } catch (IOException e){
            throw new RuntimeException();
        }

        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/" + prop.getProperty("db.name"));
        config.setUsername(prop.getProperty("db.username"));
        config.setPassword(prop.getProperty("db.password"));
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection () throws SQLException {
        return ds.getConnection();
    }
}
