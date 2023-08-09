package com.tripleying.dogend.bukkit.item;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class ItemMeta implements ConfigurationSerializable {
    
    private Map<String, Object> args = new LinkedHashMap();
    
    public Map<String, Object> getArgs(){
        return this.args;
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap();
        
        result.putAll(args);

        return result;
    }
    
    public static ItemMeta deserialize(Map<String, Object> args){
        ItemMeta result = new ItemMeta();
        result.args.putAll(args);
        return result;
    }
    
}