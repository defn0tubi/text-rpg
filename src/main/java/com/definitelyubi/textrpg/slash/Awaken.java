package com.definitelyubi.textrpg.slash;

import com.definitelyubi.textrpg.dbtools.DatabaseConnection;
import com.definitelyubi.textrpg.game.CharacterHandler;
import com.definitelyubi.textrpg.slash_handler.Slash;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Awaken implements Slash {

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) throws InterruptedException, ExecutionException, TimeoutException {
        event.deferReply().queue();

        long userId = event.getUser().getIdLong();
        EmbedBuilder embedBuilder = new EmbedBuilder();

        try {
            if (CharacterHandler.playerExists(userId)) {
                Map<String, Object> characterData = CharacterHandler.getCharacterData(userId);
                // data
                String characterName = (String) characterData.get("CHARACTER_NAME");

                embedBuilder.setTitle(characterName);
                embedBuilder.setDescription("Text RPG desc");
                embedBuilder.setColor(Color.GRAY);
                embedBuilder.addField("Text RPG field", "Text RPG", false);
                embedBuilder.setFooter("Text RPG footer");
                MessageEmbed embed = embedBuilder.build();

                event.getHook().sendMessageEmbeds(embed).queue();
            } else {
                CharacterHandler.addCharacter(userId, "test name");

                embedBuilder.setTitle("Character Sheet add");
                embedBuilder.setDescription("Text RPG desc");
                embedBuilder.setColor(Color.GRAY);
                embedBuilder.addField("Text RPG field", "Text RPG", false);
                embedBuilder.setFooter("Text RPG footer");
                MessageEmbed embed = embedBuilder.build();

                event.getHook().sendMessageEmbeds(embed).queue();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Error during /awaken command: ", e);
        }
    }

    @Override
    public String getName() {
        return "awaken";
    }

    @Override
    public String getDescription() {
        return "Awaken yourself to start your adventure";
    }

    @Override
    public boolean isSpecificGuild() {
        return false;
    }
}
