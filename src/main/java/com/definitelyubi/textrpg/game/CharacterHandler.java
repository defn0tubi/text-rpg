package com.definitelyubi.textrpg.game;

import com.definitelyubi.textrpg.TextRPG;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharacterHandler {

    private static final Connection databaseConnection = TextRPG.getDatabaseConnection().getConnection();

    // Xp
    // Equipment
    // Origin
    // Location

    public static void addCharacter(long playerId, String name) throws SQLException {
        String insertQuery = "INSERT INTO player_data (PLAYER_ID, CHARACTER_NAME, ORIGIN, EQUIPMENT_STRING, LVL, LOCATION) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = databaseConnection.prepareStatement(insertQuery);
        statement.setBigDecimal(1, BigDecimal.valueOf(playerId));
        statement.setString(2, name);
        statement.setString(3, "origin");
        statement.setString(4, "equipmentString");
        statement.setInt(5, 1);
        statement.setString(6, "location");
        statement.executeUpdate();
        statement.close();

    }

    public static boolean playerExists(long playerId) throws SQLException {
        String sqlQuery = "SELECT PLAYER_ID FROM player_data WHERE PLAYER_ID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(sqlQuery);
        statement.setBigDecimal(1, BigDecimal.valueOf(playerId));
        ResultSet resultSet = statement.executeQuery();
        boolean exists = resultSet.next();
        resultSet.close();
        statement.close();

        return exists;
    }

    public static Map<String, Object> getCharacterData(long playerId) throws SQLException {
        Map<String, Object> characterData = new HashMap<>();
        String sqlQuery = "SELECT * FROM player_data WHERE PLAYER_ID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(sqlQuery);
        statement.setBigDecimal(1, BigDecimal.valueOf(playerId));
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);

                characterData.put(columnName, columnValue);
            }
        }

        return characterData;
    }

}
