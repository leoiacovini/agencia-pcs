package pcs.labsoft.agencia.components;

import com.typesafe.config.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import pcs.labsoft.agencia.components.interfaces.IDB;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by leoiacovini on 11/4/16.
 */
public class DefaultDB implements IDB {

    private final HikariDataSource dataSource;
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final Flyway migrationManager;
    private final Config config;
    private int poolSize;

    public DefaultDB(Config configuration) {
        this.config = configuration;
        Logger.getLogger().info("Initializing DB");
        jdbcUrl = configuration.getString("db.default.url");
        username = configuration.getString("db.default.username");
        password = configuration.getString("db.default.password");
        poolSize = configuration.getInt("db.default.maximumPoolSize");
        this.dataSource = new HikariDataSource(getHikariConfig());
        migrationManager = new Flyway();
        migrationManager.setDataSource(this.dataSource);
        Logger.getLogger().info("DB Initialized successfully");
    }

    private HikariConfig getHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(poolSize);
        return hikariConfig;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void runMigrations() {
        if (config.getString("env").equals("test")) {
            migrationManager.setLocations("db/migration", "db/seed/test");
        } else {
            migrationManager.setLocations("db/migration", "db/seed/prod");
        }
        migrationManager.migrate();
    }

    public void clean() {
        migrationManager.clean();
    }

    public void stop() {
        dataSource.close();
    }

}
