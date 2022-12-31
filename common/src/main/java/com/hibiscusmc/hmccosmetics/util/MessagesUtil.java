package com.hibiscusmc.hmccosmetics.util;

import com.hibiscusmc.hmccosmetics.HMCCosmeticsPlugin;
import com.hibiscusmc.hmccosmetics.config.Settings;
import com.hibiscusmc.hmccosmetics.user.CosmeticUser;
import com.hibiscusmc.hmccosmetics.util.misc.Adventure;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.HashMap;
import java.util.logging.Level;

public class MessagesUtil {

    private static String prefix;
    private static HashMap<String, String> messages = new HashMap<>();

    public static void setup(ConfigurationNode config) {
        prefix = config.node("prefix").getString("");
        for (ConfigurationNode node : config.childrenMap().values()) {
            if (node.virtual()) continue;
            if (node.empty()) continue;
            messages.put(node.key().toString(), node.getString());
        }
     }

    public static void sendMessage(CosmeticUser user, String key) {
        sendMessage(user.getPlayer(), key);
    }

    public static void sendMessage(Player player, String key) {
        Component finalMessage = processString(key);
        Audience target = BukkitAudiences.create(HMCCosmeticsPlugin.getInstance()).player(player);

        target.sendMessage(finalMessage);
    }

    public static void sendMessage(CommandSender sender, String key) {
        Component finalMessage = processString(key);
        Audience target = BukkitAudiences.create(HMCCosmeticsPlugin.getInstance()).sender(sender);

        target.sendMessage(finalMessage);
    }

    public static void sendMessage(Player player, String key, TagResolver placeholder) {
        if (!messages.containsKey(key)) return;
        if (messages.get(key) == null) return;
        String message = messages.get(key);
        message = message.replaceAll("%prefix%", prefix);
        Component finalMessage = Adventure.MINI_MESSAGE.deserialize(message, placeholder);
        Audience target = BukkitAudiences.create(HMCCosmeticsPlugin.getInstance()).player(player);

        target.sendMessage(finalMessage);
    }

    public static void sendActionBar(Player player, String key) {
        Component finalMessage = processString(key);
        Audience target = BukkitAudiences.create(HMCCosmeticsPlugin.getInstance()).player(player);

        target.sendActionBar(finalMessage);
    }

    public static Component processString(String key) {
        if (!messages.containsKey(key)) return null;
        if (messages.get(key) == null) return null;
        String message = messages.get(key);
        message = message.replaceAll("%prefix%", prefix);
        return Adventure.MINI_MESSAGE.deserialize(message);
    }

    public static Component processStringNoKey(String message) {
        message = message.replaceAll("%prefix%", prefix);
        return Adventure.MINI_MESSAGE.deserialize(message);
    }



    public static void sendDebugMessages(String message) {
        sendDebugMessages(message, Level.INFO);
    }

    public static void sendDebugMessages(String message, Level level) {
        if (!Settings.isDebugEnabled() && level != Level.SEVERE) return;
        HMCCosmeticsPlugin.getInstance().getLogger().log(level, message);
    }
}