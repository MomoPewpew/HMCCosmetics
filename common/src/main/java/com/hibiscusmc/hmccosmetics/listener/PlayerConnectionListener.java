package com.hibiscusmc.hmccosmetics.listener;

import com.hibiscusmc.hmccosmetics.HMCCosmeticsPlugin;
import com.hibiscusmc.hmccosmetics.database.Database;
import com.hibiscusmc.hmccosmetics.user.CosmeticUser;
import com.hibiscusmc.hmccosmetics.user.CosmeticUsers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        CosmeticUser user = Database.get(event.getPlayer().getUniqueId());
        CosmeticUsers.addUser(user);
        Bukkit.getScheduler().runTaskLater(HMCCosmeticsPlugin.getInstance(), () -> {
            user.updateCosmetic();
        }, 2);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        CosmeticUser user = CosmeticUsers.getUser(event.getPlayer());
        if (user == null) { // Remove any passengers if a user failed to initialize. Bugs can cause this to happen
            if (!event.getPlayer().getPassengers().isEmpty()) {
                for (Entity entity : event.getPlayer().getPassengers()) {
                    if (entity.getType() == EntityType.ARMOR_STAND) {
                        entity.remove();
                        entity.remove();
                    }
                }
            }
        }
        if (user.isInWardrobe()) user.leaveWardrobe();
        Database.save(user);
        user.despawnBackpack();
        user.despawnBalloon();
        CosmeticUsers.removeUser(user.getUniqueId());
    }
}