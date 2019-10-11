package net.splodgebox.spookymobs.boss.events;

import net.splodgebox.spookymobs.SpookyMobs;
import net.splodgebox.spookymobs.boss.CustomSpawn;
import net.splodgebox.spookymobs.utils.Chat;
import net.splodgebox.spookymobs.utils.RandomCollection;
import net.splodgebox.spookymobs.utils.nbt.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BossEvents implements Listener {

    @EventHandler
    public void onSpawn(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (itemStack == null || itemStack.getType() == Material.AIR) return;
            NBTItem nbtItem = new NBTItem(itemStack);
            if (nbtItem.hasNBTData() && nbtItem.hasKey("SpookyMob")) {
                event.setCancelled(true);
                Location location = event.getClickedBlock().getLocation();
                int xCord = (int) location.getX();
                int yCord = (int) location.getY();
                int zCord = (int) location.getZ();
                List<String> spawnMessage = SpookyMobs.getInstance().getConfig().getStringList("Message.spawn-message");
                player.setItemInHand(new ItemStack(Material.AIR));
                new CustomSpawn(SpookyMobs.getInstance()).spawn(event.getClickedBlock().getLocation().add(0, 1,0));
                spawnMessage.forEach(s -> Bukkit.broadcastMessage(Chat.color(s
                        .replace("%player%", player.getName())
                        .replace("%x%", String.valueOf(xCord))
                        .replace("%y%", String.valueOf(yCord))
                        .replace("%z%", String.valueOf(zCord))
                )));
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if (event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            String name = SpookyMobs.getInstance().bossConfig.getConfig().getString("Boss.name");
            if (event.getEntity().isCustomNameVisible() && event.getEntity().getCustomName().equals(Chat.color(name))) {
                event.getDrops().clear();
                List<String> deathMessage = SpookyMobs.getInstance().getConfig().getStringList("Message.death-message");
                deathMessage.forEach(s -> Bukkit.broadcastMessage(Chat.color(s.replace("%player%", player.getName()))));
                RandomCollection<String> chanceCollection = new RandomCollection<>(); // Temp use, I will use a better system
                chanceCollection.add(SpookyMobs.getInstance().getConfig().getDouble("Rewards.success-chance"), "success");
                chanceCollection.add(SpookyMobs.getInstance().getConfig().getDouble("Rewards.failed-chance"), "failed");

                executeRewards(player, chanceCollection.next());
            }
        }
    }

    public void executeRewards(Player player, String type){
        List<String> commandList = SpookyMobs.getInstance().getConfig().getStringList("Rewards." + type);
        commandList.forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", player.getName())));
    }
}
