package xyz.gamlin.clans.commands.commandTabCompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ClanCommandTabCompleter implements TabCompleter {

    List<String> arguments = new ArrayList<>();

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (arguments.isEmpty()){
            arguments.add("create");
            arguments.add("disband");
            arguments.add("invite");
            arguments.add("join");
            arguments.add("leave");
            arguments.add("kick");
            arguments.add("info");
            arguments.add("list");
            arguments.add("prefix");
            arguments.add("transfer");
            arguments.add("ally add");
            arguments.add("ally remove");
            arguments.add("enemy add");
            arguments.add("enemy remove");
            arguments.add("pvp");
            arguments.add("sethome");
            arguments.add("delhome");
            arguments.add("home");
        }

        List<String> result = new ArrayList<>();
        if (args.length == 1){
            for (String a : arguments){
                if (a.toLowerCase().startsWith(args[0].toLowerCase())){
                    result.add(a);
                }
            }
            return result;
        }
        return null;
    }
}
