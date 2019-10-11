package net.splodgebox.spookymobs;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.splodgebox.spookymobs.boss.CustomSpawn;
import net.splodgebox.spookymobs.utils.Chat;
import org.bukkit.command.CommandSender;

@CommandAlias("spookymobs")
public class SpookyMobsCMD extends BaseCommand {
    @Default
    public void defaultMessage(CommandSender commandSender){
        Chat.msg(commandSender,
                "",
                "&6&lSpookyMobs",
                "&6/spookymobs give <player> &7- &eGive a player a spawn egg",
                "&6/spookymobs reload &7- &eReload the configuration files",
                ""
        );
    }

    @Subcommand("give")
    @CommandPermission("Spookymobs.give")
    @CommandCompletion("@players")
    public void giveSpawnEgg(CommandSender commandSender, OnlinePlayer onlinePlayer){
        CustomSpawn customSpawn = new CustomSpawn(SpookyMobs.getInstance());
        onlinePlayer.getPlayer().getInventory().addItem(customSpawn.getEgg());
        Chat.msg(commandSender, "&6&l(!) &eYou have given " + onlinePlayer.getPlayer().getName() + " a halloween spawn!");
    }

    @Subcommand("reload")
    @CommandPermission("Spookymobs.reload")
    public void reloadFiles(CommandSender commandSender){
        SpookyMobs.getInstance().reloadConfig();
        SpookyMobs.getInstance().bossConfig.reload();
        Chat.msg(commandSender, "&c&l(!) &cConfiguration files have been reloaded!");
    }
}
