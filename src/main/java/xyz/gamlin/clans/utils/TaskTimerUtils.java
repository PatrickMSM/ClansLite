package xyz.gamlin.clans.utils;

import org.bukkit.Bukkit;
import xyz.gamlin.clans.Clans;

import java.io.IOException;
import java.util.logging.Logger;

public class TaskTimerUtils {

    static Logger logger = Clans.getPlugin().getLogger();

    public static int taskID1;
    public static int taskID2;
    public static int taskID3;
    public static int taskID4;

    public static void runClansAutoSaveOne(){
        taskID1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Clans.getPlugin(Clans.class), new Runnable() {
            int time = 900;
            @Override
            public void run() {
                if (time == 1){
                    try {
                        ClansStorageUtil.saveClans();
                        if (Clans.getPlugin().getConfig().getBoolean("general.show-auto-save-task-message.enabled")){
                            logger.info(ColorUtils.translateColorCodes(Clans.getPlugin().messagesFileManager.getMessagesConfig().getString("auto-save-complete")));
                        }
                    } catch (IOException e) {
                        logger.severe(ColorUtils.translateColorCodes(Clans.getPlugin().messagesFileManager.getMessagesConfig().getString("auto-save-failed")));
                        e.printStackTrace();
                    }
                    runClansAutoSaveTwo();
                    Bukkit.getScheduler().cancelTask(taskID1);
                    return;
                }
                else {
                    time --;
                }
            }
        },0, 20);
    }

    public static void runClansAutoSaveTwo(){
        taskID2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Clans.getPlugin(Clans.class), new Runnable() {
            int time = 900;
            @Override
            public void run() {
                if (time == 1){
                    try {
                        ClansStorageUtil.saveClans();
                        if (Clans.getPlugin().getConfig().getBoolean("general.show-auto-save-task-message.enabled")){
                            logger.info(ColorUtils.translateColorCodes(Clans.getPlugin().messagesFileManager.getMessagesConfig().getString("auto-save-complete")));
                        }
                    } catch (IOException e) {
                        logger.severe(ColorUtils.translateColorCodes(Clans.getPlugin().messagesFileManager.getMessagesConfig().getString("auto-save-failed")));
                        e.printStackTrace();
                    }
                    runClansAutoSaveOne();
                    Bukkit.getScheduler().cancelTask(taskID2);
                    return;
                }
                else {
                    time --;
                }
            }
        },0, 20);
    }

    public static void runClanInviteClearOne(){
        taskID3 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Clans.getPlugin(Clans.class), new Runnable() {
            int time = 900;
            @Override
            public void run() {
                if (time == 1){
                    try {
                        ClanInviteUtil.emptyInviteList();
                        if (Clans.getPlugin().getConfig().getBoolean("general.show-auto-invite-wipe-message.enabled")){
                            logger.info(ColorUtils.translateColorCodes(Clans.getPlugin().messagesFileManager.getMessagesConfig().getString("auto-invite-wipe-complete")));
                        }
                    }catch (UnsupportedOperationException exception){
                        logger.info(ColorUtils.translateColorCodes(Clans.getPlugin().messagesFileManager.getMessagesConfig().getString("invite-wipe-failed")));
                    }
                    runClanInviteClearTwo();
                    Bukkit.getScheduler().cancelTask(taskID3);
                    return;
                }else {
                    time --;
                }
            }
        }, 0, 20);
    }

    public static void runClanInviteClearTwo(){
        taskID4 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Clans.getPlugin(Clans.class), new Runnable() {
            int time = 900;
            @Override
            public void run() {
                if (time == 1){
                    try {
                        ClanInviteUtil.emptyInviteList();
                        if (Clans.getPlugin().getConfig().getBoolean("general.show-auto-invite-wipe-message.enabled")){
                            logger.info(ColorUtils.translateColorCodes(Clans.getPlugin().messagesFileManager.getMessagesConfig().getString("auto-invite-wipe-complete")));
                        }
                    }catch (UnsupportedOperationException exception){
                        logger.info(ColorUtils.translateColorCodes(Clans.getPlugin().messagesFileManager.getMessagesConfig().getString("invite-wipe-failed")));
                    }
                    runClanInviteClearOne();
                    Bukkit.getScheduler().cancelTask(taskID4);
                    return;
                }else {
                    time --;
                }
            }
        }, 0, 20);
    }
}
