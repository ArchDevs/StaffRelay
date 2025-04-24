package me.archdev.staffrelay.util;

import org.bukkit.ChatColor;

public class ChatUtil {
    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    public static String formatLegacy(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String formatHexColor(String text){

        String[] texts = text.split(String.format(WITH_DELIMITER, "&"));

        StringBuilder finalText = new StringBuilder();

        for (int i = 0; i < texts.length; i++){
            if (texts[i].equalsIgnoreCase("&")){
                //get the next string
                i++;
                if (texts[i].charAt(0) == '#'){
                    finalText.append(net.md_5.bungee.api.ChatColor.valueOf(texts[i].substring(0, 7)) + texts[i].substring(7));
                }else{
                    finalText.append(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&" + texts[i]));
                }
            }else{
                finalText.append(texts[i]);
            }
        }

        return finalText.toString();
    }
}
