package com.definitelyubi.textrpg.discord_utilities;

import com.definitelyubi.textrpg.TextRPG;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;

public class DiscordUtils {
    private final JDA jda = TextRPG.getJDA();

    public void sendPrivateMessage(long userId, String message) {
        User user = jda.getUserById(userId);
        if (user != null ) {
            user.openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessage(message).queue();
                    }, Throwable::printStackTrace);
        } else {
            System.out.println("User not found: " + userId);
        }
    }

}
