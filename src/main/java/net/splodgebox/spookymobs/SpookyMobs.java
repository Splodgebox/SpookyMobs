package net.splodgebox.spookymobs;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.splodgebox.spookymobs.boss.events.BossEvents;
import net.splodgebox.spookymobs.boss.skills.Blind;
import net.splodgebox.spookymobs.boss.skills.Smite;
import net.splodgebox.spookymobs.utils.Chat;
import net.splodgebox.spookymobs.utils.FileManager;
import org.bukkit.entity.Boss;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpookyMobs extends JavaPlugin {

    @Getter
    private static SpookyMobs instance;
    public FileManager bossConfig;

    @Override
    public void onEnable() {
        instance = this;
        bossConfig = new FileManager(this, "boss", getDataFolder().getAbsolutePath());
        saveDefaultConfig();
        new PaperCommandManager(this).registerCommand(new SpookyMobsCMD());

        getServer().getPluginManager().registerEvents(new BossEvents(), this);
        getServer().getPluginManager().registerEvents(new Smite(), this);
        getServer().getPluginManager().registerEvents(new Blind(), this);

        Chat.log("&6[!] Spooky Mobs has loaded!");
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
