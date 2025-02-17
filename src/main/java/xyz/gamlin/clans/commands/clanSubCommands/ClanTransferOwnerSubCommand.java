package xyz.gamlin.clans.commands.clanSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.gamlin.clans.Clans;
import xyz.gamlin.clans.models.Clan;
import xyz.gamlin.clans.utils.ClansStorageUtil;
import xyz.gamlin.clans.utils.ColorUtils;

import java.io.IOException;

public class ClanTransferOwnerSubCommand {

    FileConfiguration messagesConfig = Clans.getPlugin().messagesFileManager.getMessagesConfig();

    public boolean transferClanOwnerSubCommand(CommandSender sender, String[] args){
        if (sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if (player != null){
                if (args.length > 2){
                    String PLAYER_PLACEHOLDER = "%PLAYER%";
                    Player newClanOwner = Bukkit.getPlayerExact(args[2]);
                    if (newClanOwner != null){
                        if (ClansStorageUtil.isClanOwner(player)){
                            Clan originalClan = ClansStorageUtil.findClanByOwner(player);
                            if (originalClan != null){
                                try {
                                    Clan newClan = ClansStorageUtil.transferClanOwner(originalClan, player, newClanOwner);
                                    if (newClan != null){
                                        String OLD_OWNER_PLACEHOLDER = "%OLDOWNER%";
                                        String NEW_CLAN_NAME = "%CLAN%";
                                        player.sendMessage(ColorUtils.translateColorCodes(messagesConfig.getString("clan-ownership-transfer-successful")
                                                .replace(PLAYER_PLACEHOLDER, newClanOwner.getName())));
                                        newClanOwner.sendMessage(ColorUtils.translateColorCodes(messagesConfig.getString("clan-ownership-transfer-new-owner")
                                                .replace(OLD_OWNER_PLACEHOLDER, player.getName()).replace(NEW_CLAN_NAME, newClan.getClanFinalName())));
                                        return true;
                                    }
                                } catch (IOException e) {
                                    sender.sendMessage(ColorUtils.translateColorCodes(messagesConfig.getString("clans-update-error-1")));
                                    sender.sendMessage(ColorUtils.translateColorCodes(messagesConfig.getString("clans-update-error-2")));
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            player.sendMessage(ColorUtils.translateColorCodes(messagesConfig.getString("clan-must-be-owner")));
                        }
                    }else {
                        player.sendMessage(ColorUtils.translateColorCodes(messagesConfig.getString("clan-ownership-transfer-failure-owner-offline")
                                .replace(PLAYER_PLACEHOLDER, args[2])));
                    }
                }else {
                    player.sendMessage(ColorUtils.translateColorCodes(messagesConfig.getString("incorrect-clan-transfer-ownership-command-usage")));
                }
            }
        }
        return true;
    }
}
