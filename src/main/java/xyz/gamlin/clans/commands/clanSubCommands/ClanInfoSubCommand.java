package xyz.gamlin.clans.commands.clanSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.gamlin.clans.Clans;
import xyz.gamlin.clans.models.Clan;
import xyz.gamlin.clans.utils.ClansStorageUtil;
import xyz.gamlin.clans.utils.ColorUtils;

import java.util.*;

public class ClanInfoSubCommand {

    FileConfiguration messagesConfig = Clans.getPlugin().messagesFileManager.getMessagesConfig();
    private static final String CLAN_PLACEHOLDER = "%CLAN%";
    private static final String OWNER = "%OWNER%";
    private static final String CLAN_MEMBER = "%MEMBER%";
    private static final String ALLY_CLAN = "%ALLYCLAN%";
    private static final String ENEMY_CLAN = "%ENEMYCLAN%";

    public boolean clanInfoSubCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            Clan clanByOwner = ClansStorageUtil.findClanByOwner(player);
            Clan clanByPlayer = ClansStorageUtil.findClanByPlayer(player);
            if (clanByOwner != null) {
                ArrayList<String> clanMembers = clanByOwner.getClanMembers();
                ArrayList<String> clanAllies = clanByOwner.getClanAllies();
                ArrayList<String> clanEnemies = clanByOwner.getClanEnemies();
                StringBuilder clanInfo = new StringBuilder(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-header"))
                        .replace(CLAN_PLACEHOLDER, ColorUtils.translateColorCodes(clanByOwner.getClanFinalName()))
                        .replace("%CLANPREFIX%", ColorUtils.translateColorCodes(clanByOwner.getClanPrefix())));
                UUID clanOwnerUUID = UUID.fromString(clanByOwner.getClanOwner());
                Player clanOwner = Bukkit.getPlayer(clanOwnerUUID);
                if (clanOwner != null) {
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-owner-online")).replace(OWNER, clanOwner.getName()));
                }else {
                    UUID uuid = UUID.fromString(clanByOwner.getClanOwner());
                    String offlineOwner = Bukkit.getOfflinePlayer(uuid).getName();
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-owner-offline")).replace(OWNER, offlineOwner));
                }
                if (clanMembers.size() > 0) {
                    int clanMembersSize = clanMembers.size();
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-members-header")
                            .replace("%NUMBER%", ColorUtils.translateColorCodes(String.valueOf(clanMembersSize)))));
                    for (String clanMember : clanMembers) {
                        if (clanMember != null) {
                            UUID memberUUID = UUID.fromString(clanMember);
                            Player clanPlayer = Bukkit.getPlayer(memberUUID);
                            if (clanPlayer != null) {
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-members-online") + "\n").replace(CLAN_MEMBER, clanPlayer.getName()));
                            } else {
                                UUID uuid = UUID.fromString(clanMember);
                                String offlinePlayer = Bukkit.getOfflinePlayer(uuid).getName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-members-offline") + "\n").replace(CLAN_MEMBER, offlinePlayer));
                            }
                        }

                    }
                }
                if (clanAllies.size() > 0){
                    clanInfo.append(" ");
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-allies-header")));
                    for (String clanAlly : clanAllies){
                        if (clanAlly != null){
                            Player allyOwner = Bukkit.getPlayer(clanAlly);
                            if (allyOwner != null){
                                Clan allyClan = ClansStorageUtil.findClanByOwner(allyOwner);
                                String clanAllyName = allyClan.getClanFinalName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-ally-members").replace(ALLY_CLAN, clanAllyName)));
                            }else {
                                UUID uuid = UUID.fromString(clanAlly);
                                OfflinePlayer offlineOwnerPlayer = Bukkit.getOfflinePlayer(uuid);
                                Clan offlineAllyClan = ClansStorageUtil.findClanByOfflineOwner(offlineOwnerPlayer);
                                String offlineAllyName = offlineAllyClan.getClanFinalName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-ally-members").replace(ALLY_CLAN, offlineAllyName)));
                            }
                        }
                    }
                }
                if (clanEnemies.size() > 0){
                    clanInfo.append(" ");
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-enemies-header")));
                    for (String clanEnemy : clanEnemies){
                        if (clanEnemy != null){
                            Player enemyOwner = Bukkit.getPlayer(clanEnemy);
                            if (enemyOwner != null){
                                Clan enemyClan = ClansStorageUtil.findClanByOwner(enemyOwner);
                                String clanEnemyName = enemyClan.getClanFinalName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-enemy-members").replace(ENEMY_CLAN, clanEnemyName)));
                            }else {
                                UUID uuid = UUID.fromString(clanEnemy);
                                OfflinePlayer offlineOwnerPlayer = Bukkit.getOfflinePlayer(uuid);
                                Clan offlineEnemyClan = ClansStorageUtil.findClanByOfflineOwner(offlineOwnerPlayer);
                                String offlineEnemyName = offlineEnemyClan.getClanFinalName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-enemy-members").replace(ENEMY_CLAN, offlineEnemyName)));
                            }
                        }
                    }
                }
                clanInfo.append(" ");
                if (clanByOwner.isFriendlyFireAllowed()){
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-pvp-status-enabled")));
                }else {
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-pvp-status-disabled")));
                }
                if (ClansStorageUtil.isHomeSet(clanByOwner)){
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-home-set-true")));
                }else {
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-home-set-false")));
                }
                clanInfo.append(" ");
                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-footer")));
                player.sendMessage(clanInfo.toString());

            }else if (clanByPlayer != null){
                ArrayList<String> clanMembers = clanByPlayer.getClanMembers();
                ArrayList<String> clanAllies = clanByPlayer.getClanAllies();
                ArrayList<String> clanEnemies = clanByPlayer.getClanEnemies();
                StringBuilder clanInfo = new StringBuilder(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-header"))
                        .replace(CLAN_PLACEHOLDER, ColorUtils.translateColorCodes(clanByPlayer.getClanFinalName()))
                        .replace("%CLANPREFIX%", ColorUtils.translateColorCodes(clanByPlayer.getClanPrefix())));
                UUID clanOwnerUUID = UUID.fromString(clanByPlayer.getClanOwner());
                Player clanOwner = Bukkit.getPlayer(clanOwnerUUID);
                if (clanOwner != null) {
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-owner-online")).replace(OWNER, clanOwner.getName()));
                } else {
                    UUID uuid = UUID.fromString(clanByPlayer.getClanOwner());
                    String offlineOwner = Bukkit.getOfflinePlayer(uuid).getName();
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-owner-offline")).replace(OWNER, offlineOwner));
                }
                if (clanMembers.size() > 0) {
                    int clanMembersSize = clanMembers.size();
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-members-header")
                            .replace("%NUMBER%", ColorUtils.translateColorCodes(String.valueOf(clanMembersSize)))));
                    for (String clanMember : clanMembers) {
                        if (clanMember != null) {
                            UUID memberUUID = UUID.fromString(clanMember);
                            Player clanPlayer = Bukkit.getPlayer(memberUUID);
                            if (clanPlayer != null) {
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-members-online") + "\n").replace(CLAN_MEMBER, clanPlayer.getName()));
                            } else {
                                UUID uuid = UUID.fromString(clanMember);
                                String offlinePlayer = Bukkit.getOfflinePlayer(uuid).getName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-members-offline") + "\n").replace(CLAN_MEMBER, offlinePlayer));
                            }
                        }

                    }
                }
                if (clanAllies.size() > 0){
                    clanInfo.append(" ");
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-allies-header")));
                    for (String clanAlly : clanAllies){
                        if (clanAlly != null){
                            Player allyOwner = Bukkit.getPlayer(clanAlly);
                            if (allyOwner != null){
                                Clan allyClan = ClansStorageUtil.findClanByOwner(allyOwner);
                                String clanAllyName = allyClan.getClanFinalName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-ally-members").replace(ALLY_CLAN, clanAllyName)));
                            }else {
                                UUID uuid = UUID.fromString(clanAlly);
                                OfflinePlayer offlineOwnerPlayer = Bukkit.getOfflinePlayer(uuid);
                                Clan offlineAllyClan = ClansStorageUtil.findClanByOfflineOwner(offlineOwnerPlayer);
                                String offlineAllyName = offlineAllyClan.getClanFinalName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-ally-members").replace(ALLY_CLAN, offlineAllyName)));
                            }
                        }
                    }
                }
                if (clanEnemies.size() > 0){
                    clanInfo.append(" ");
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-enemies-header")));
                    for (String clanEnemy : clanEnemies){
                        if (clanEnemy != null){
                            Player enemyOwner = Bukkit.getPlayer(clanEnemy);
                            if (enemyOwner != null){
                                Clan enemyClan = ClansStorageUtil.findClanByOwner(enemyOwner);
                                String clanEnemyName = enemyClan.getClanFinalName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-enemy-members").replace(ENEMY_CLAN, clanEnemyName)));
                            }else {
                                UUID uuid = UUID.fromString(clanEnemy);
                                OfflinePlayer offlineOwnerPlayer = Bukkit.getOfflinePlayer(uuid);
                                Clan offlineEnemyClan = ClansStorageUtil.findClanByOfflineOwner(offlineOwnerPlayer);
                                String offlineEnemyName = offlineEnemyClan.getClanFinalName();
                                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-enemy-members").replace(ENEMY_CLAN, offlineEnemyName)));
                            }
                        }
                    }
                }
                clanInfo.append(" ");
                if (clanByPlayer.isFriendlyFireAllowed()){
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-pvp-status-enabled")));
                }else {
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-pvp-status-disabled")));
                }
                if (ClansStorageUtil.isHomeSet(clanByPlayer)){
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-home-set-true")));
                }else {
                    clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-home-set-false")));
                }
                clanInfo.append(" ");
                clanInfo.append(ColorUtils.translateColorCodes(messagesConfig.getString("clan-info-footer")));
                player.sendMessage(clanInfo.toString());
            }else {
                player.sendMessage(ColorUtils.translateColorCodes(messagesConfig.getString("not-in-clan")));
            }
            return true;

        }
        return false;
    }
}
