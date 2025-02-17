package xyz.gamlin.clans.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.gamlin.clans.Clans;
import xyz.gamlin.clans.utils.ColorUtils;

import java.util.logging.Logger;

public class PlayerDisconnectEvent implements Listener {

    FileConfiguration clansConfig = Clans.getPlugin().getConfig();
    Logger logger = Clans.getPlugin().getLogger();

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Clans.connectedPlayers.remove(player);
        if (clansConfig.getBoolean("general.developer-debug-mode.enabled")){
            logger.info(ColorUtils.translateColorCodes("&6ClansLite-Debug: &aPlayer removed from connected players list"));
        }
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onBedrockPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (Clans.getFloodgateApi() != null){
            if (Clans.bedrockPlayers.containsKey(player)){
                Clans.bedrockPlayers.remove(player);
                if (clansConfig.getBoolean("general.developer-debug-mode.enabled")){
                    logger.info(ColorUtils.translateColorCodes("&6ClansLite-Debug: &aBedrock player removed from bedrock players list"));
                }
            }
        }
    }
}
