package net.splodgebox.spookymobs.utils;

import com.google.common.collect.Lists;
import net.splodgebox.spookymobs.utils.nbt.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


public class ItemStackBuilder {
    private ItemStack itemStack;

    public ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setName(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(Chat.color(name));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        List<String> temp = Lists.newArrayList();
        lore.stream().forEach((s) -> {
            temp.add(Chat.color(s));
        });
        meta.setLore(temp);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addLore(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = Lists.newArrayList();
        }

        ((List) lore).add(Chat.color(name));
        meta.setLore((List) lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addItemFlags(ItemFlag... flags) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(flags);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setData(int data) {
        this.itemStack.setDurability((short) data);
        return this;
    }

    public ItemStackBuilder addEnchant(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }


    public ItemStackBuilder withGlow(boolean glow) {
        if (glow) {
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            this.itemStack.setItemMeta(meta);
            this.itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        }
        return this;
    }

    public ItemStackBuilder.Nbt nbt() {
        return new ItemStackBuilder.Nbt(this);
    }

    public ItemStack build() {
        return this.itemStack;
    }

    public class Nbt {
        protected final ItemStackBuilder builder;
        protected NBTItem nbtItem;

        public Nbt(ItemStackBuilder builder) {
            this.builder = builder;
            this.nbtItem = new NBTItem(builder.itemStack);
        }

        public ItemStackBuilder.Nbt set(String key, String value) {
            this.nbtItem.setString(key, value);
            return this;
        }

        public ItemStackBuilder.Nbt set(String key, Integer intVal) {
            this.nbtItem.setInteger(key, intVal);
            return this;
        }

        public ItemStackBuilder.Nbt set(String key, Double intVal) {
            this.nbtItem.setDouble(key, intVal);
            return this;
        }

        public ItemStackBuilder.Nbt set(String key, Boolean bool) {
            this.nbtItem.setBoolean(key, bool);
            return this;
        }

        public ItemStack build() {
            return this.nbtItem.getItem();
        }

        public ItemStackBuilder builder() {
            return this.builder;
        }
    }
}