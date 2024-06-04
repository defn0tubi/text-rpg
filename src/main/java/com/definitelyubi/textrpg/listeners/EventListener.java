package com.definitelyubi.textrpg.listeners;

import com.definitelyubi.textrpg.dbtools.DatabaseConnection;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventListener extends ListenerAdapter {

    private static final Logger logger = Logger.getLogger(EventListener.class.getName());
    private final DatabaseConnection databaseConnection;

    public EventListener(String databasePath, String databaseUsername, String databasePassword, DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        // later
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        logger.info("onShutdown method called");
        try {
            databaseConnection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing database connection in onShutdown", e);
        }
    }
}
