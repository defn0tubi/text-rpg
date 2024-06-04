package com.definitelyubi.textrpg;

import com.definitelyubi.textrpg.dbtools.DatabaseConnection;
import com.definitelyubi.textrpg.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextRPG {

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    private final Dotenv config;
    private final ShardManager shardManager;
    public DatabaseConnection databaseConnection;

    public TextRPG() throws InvalidTokenException, SQLException {

        config = Dotenv.configure().load();

        String token = config.get("TOKEN");
        String databasePath = config.get("DB_PATH");
        String databaseUsername = config.get("DB_USER");
        String databasePassword = config.get("DB_PASSWORD");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.IDLE);

        shardManager = builder.build();

        databaseConnection = new DatabaseConnection(databasePath, databaseUsername, databasePassword);
        logger.info("Database connection created for TextRPG");

        shardManager.addEventListener(new EventListener(databasePath, databaseUsername, databasePassword, databaseConnection));

        // Option 2 to close connection in case of unexpected death of bot
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            boolean closed = false;
            logger.info("Shutdown hook triggered");
            try {
                if (databaseConnection != null) {
                    databaseConnection.close();
                    closed = true;
                    logger.info("Database connection closed in Shutdown hook");
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing database connection in shutdown hook", e);
            }
            // Sometimes the loggers above don't work
            System.out.println("Ran through the hook, connection closed: " + closed);
        }));

    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) throws SQLException {
        TextRPG bot = new TextRPG();
    }

}