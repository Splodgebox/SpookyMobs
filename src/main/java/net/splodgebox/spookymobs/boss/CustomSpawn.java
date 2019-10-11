package net.splodgebox.spookymobs.boss;

import lombok.RequiredArgsConstructor;
import net.splodgebox.spookymobs.SpookyMobs;
import net.splodgebox.spookymobs.utils.Chat;
import net.splodgebox.spookymobs.utils.ItemStackBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CustomSpawn {

    private final SpookyMobs plugin;

    public void spawn(Location location) {
        EntityType entityType = EntityType.valueOf(plugin.bossConfig.getConfig().getString("Boss.mob"));
        String name = plugin.bossConfig.getConfig().getString("Boss.name");
        int health = plugin.bossConfig.getConfig().getInt("Boss.health");

        Creature creature = (Creature) location.getWorld().spawnEntity(location, entityType);
        creature.setCustomName(Chat.color(name));
        creature.setCustomNameVisible(true);
        creature.setMaxHealth(health);
        creature.setHealth(health);
        creature.getEquipment().setHelmet(getArmor("helmet"));
        creature.getEquipment().setChestplate(getArmor("chestplate"));
        creature.getEquipment().setLeggings(getArmor("leggings"));
        creature.getEquipment().setBoots(getArmor("boots"));
        creature.getEquipment().setItemInHand(getArmor("weapon"));


        new BukkitRunnable() {
            @Override
            public void run() {
                if (creature.isDead()){
                    cancel();
                    return;
                }
                creature.getNearbyEntities(10, 10, 10).forEach(entity -> {
                    if (entity instanceof Player){
                        creature.setTarget((LivingEntity) entity);
                    }
                });
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    public ItemStack getEgg() {
        String path = "Spawn_Egg.";
        return new ItemStackBuilder(Material.valueOf(SpookyMobs.getInstance().getConfig().getString(path + "material")))
                .setName(SpookyMobs.getInstance().getConfig().getString(path + "name"))
                .setLore(SpookyMobs.getInstance().getConfig().getStringList(path + "lore"))
                .setData(SpookyMobs.getInstance().getConfig().getInt(path + "data"))
                .nbt().set("SpookyMob", String.valueOf(UUID.randomUUID()))
                .build();
    }

    public ItemStack getArmor(String type) {
        Material material = Material.getMaterial(plugin.bossConfig.getConfig().getString("Boss.armor." + type + ".material"));
        if (material == null) return new ItemStack(Material.AIR);
        List<String> enchantmentList = plugin.bossConfig.getConfig().getStringList("Boss.armor." + type + ".enchants");
        ItemStackBuilder itemStackBuilder = new ItemStackBuilder(material);
        enchantmentList.forEach(s -> {
            String[] index = s.split(":");
            itemStackBuilder.addEnchant(Enchantment.getByName(index[0]), Integer.parseInt(index[1]));
        });
        return itemStackBuilder.build();
    }
}
