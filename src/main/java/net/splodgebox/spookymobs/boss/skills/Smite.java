package net.splodgebox.spookymobs.boss.skills;

import net.splodgebox.spookymobs.SpookyMobs;
import net.splodgebox.spookymobs.utils.Chat;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.ThreadLocalRandom;

public class Smite implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        String name = SpookyMobs.getInstance().bossConfig.getConfig().getString("Boss.name");
        if (event.getDamager().isCustomNameVisible() && event.getDamager().getCustomName().equals(Chat.color(name))) {
            if (SkillUtils.containsEffect("Smite")){
                if (ThreadLocalRandom.current().nextInt(101) <= SkillUtils.getChance("Smite")){
                    LivingEntity livingEntity = (LivingEntity) event.getEntity();
                    livingEntity.getLocation().getWorld().strikeLightningEffect(livingEntity.getLocation());
                    livingEntity.damage(ThreadLocalRandom.current().nextInt(2,5));
                }
            }
        }
    }
}
