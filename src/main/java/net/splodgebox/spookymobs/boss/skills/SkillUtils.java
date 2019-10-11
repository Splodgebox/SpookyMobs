package net.splodgebox.spookymobs.boss.skills;

import net.splodgebox.spookymobs.SpookyMobs;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SkillUtils {
    public static boolean containsEffect(String effect) {
        AtomicBoolean containsEffect = new AtomicBoolean(false);
        SpookyMobs.getInstance().bossConfig.getConfig().getStringList("Boss.skills").forEach(s -> {
            String[] index = s.split(":");
            if (index[0].equalsIgnoreCase(effect)){
                containsEffect.set(true);
            }
        });
        return containsEffect.get();
    }
    public static double getChance(String effect) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        SpookyMobs.getInstance().bossConfig.getConfig().getStringList("Boss.skills").forEach(s -> {
            String[] index = s.split(":");
            if (index[0].equalsIgnoreCase(effect)){
                atomicInteger.set(Integer.parseInt(index[1]));
            }
        });
        return atomicInteger.get();
    }

}
