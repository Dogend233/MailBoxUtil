package com.tripleying.dogend.bukkit.item;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class ItemStack implements ConfigurationSerializable {
    
    private String type = "AIR";
    private int amount = 0;
    private short durability = 0;
    private ItemMeta meta;
    private Map<String, Object> args = new LinkedHashMap();
    
    public ItemStack(String type, int amount, short damage){
        this.type = type;
        this.amount = amount;
        this.durability = damage;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String setType(String type) {
        return this.type = type;
    }
    
    public short getDurability() {
        return durability;
    }
    
    public void setDurability(final short durability) {
        this.durability = durability;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public boolean hasItemMeta(){
        return this.meta!=null;
    }
    
    public ItemMeta getItemMeta() {
        return this.meta;
    }
    
    public ItemMeta setItemMeta(ItemMeta meta) {
        return this.meta = meta;
    }
    
    public Map<String, Object> getArgs(){
        return this.args;
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap();

        result.put("type", getType());

        if (getDurability() != 0) {
            result.put("damage", getDurability());
        }

        if (getAmount() != 1) {
            result.put("amount", getAmount());
        }

        ItemMeta meta = getItemMeta();
        if (meta!=null) {
            result.put("meta", meta);
        }
        
        result.putAll(args);

        return result;
    }
    
    public static ItemStack deserialize(Map<String, Object> args){
        
        String type = (String) args.remove("type");
        short damage = 0;
        int amount = 1;

        if (args.containsKey("damage")) {
            damage = ((Number) args.remove("damage")).shortValue();
        }

        if (args.containsKey("amount")) {
            amount = ((Number) args.remove("amount")).intValue();
        }

        ItemStack result = new ItemStack(type, amount, damage);

        if (args.containsKey("enchantments")) { // Backward compatiblity, @deprecated
        } else if (args.containsKey("meta")) { // We cannot and will not have meta when enchantments (pre-ItemMeta) exist
            Object raw = args.remove("meta");
            if (raw instanceof ItemMeta) {
                result.setItemMeta((ItemMeta) raw);
            }
        }
        
        result.args.putAll(args);
        
        return result;
    }
    
}
