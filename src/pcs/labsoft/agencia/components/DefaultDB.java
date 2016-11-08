package pcs.labsoft.agencia.components;

import com.typesafe.config.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import pcs.labsoft.agencia.components.interfaces.IDB;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/4/16.
 */
public class DefaultDB implements IDB {

    private final HikariDataSource dataSource;

    public DefaultDB(Config configuration) {
        Logger.getLogger().info("Initializing DB");
        this.dataSource = new HikariDataSource(getHikariConfig(configuration));
        Logger.getLogger().info("DB Initialized successfully");
    }

    private HikariConfig getHikariConfig(Config configuration) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(configuration.getString("db.default.url"));
        hikariConfig.setUsername(configuration.getString("db.default.username"));
        hikariConfig.setPassword(configuration.getString("db.default.password"));
        hikariConfig.setMaximumPoolSize(configuration.getInt("db.default.maximumPoolSize"));
        return hikariConfig;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
