package com.definitelyubi.textrpg.slash;

import com.definitelyubi.textrpg.slash_handler.Slash;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class Witg implements Slash {
    @Override
    public void onSlashCommandEvent(SlashCommandInteractionEvent event) {
        ChannelType channelType = event.getChannelType();

        if (channelType != ChannelType.PRIVATE || event.getChannel().getType().isThread()) {
            event.reply("can't use this bot in guilds or group chats, go in DMs").setEphemeral(true).queue();
        }


        event.reply("hello").queue();
    }

    @Override
    public String getName() {
        return "witg";
    }

    @Override
    public String getDescription() {
        return "What is this game?";
    }

    @Override
    public boolean isSpecificGuild() {
        return false;
    }
}
