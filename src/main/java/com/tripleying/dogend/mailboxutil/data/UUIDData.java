package com.tripleying.dogend.mailboxutil.data;

import com.tripleying.dogend.mailbox.api.data.Data;
import com.tripleying.dogend.mailbox.api.data.DataType;
import com.tripleying.dogend.mailbox.api.mail.CustomData;
import java.util.LinkedHashMap;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;

public class UUIDData extends CustomData {

    @Data(type = DataType.String)
    public String name;
    @Data(type = DataType.String)
    public String uuid;

    public UUIDData() {
        super("mailbox_player_data");
    }

    @Override
    public CustomData loadFromYamlConfiguration(YamlConfiguration yml) {
        name = yml.getString("name");
        uuid = yml.getString("uuid");
        return this;
    }

    public static String getUUIDFromName(String name){
        LinkedHashMap<String, Object> sel = new LinkedHashMap();
        sel.put("name", name);
        List<CustomData> list = new UUIDData().selectCustomData(sel);
        if(list.isEmpty()){
            return null;
        }else{
            return ((UUIDData)list.get(0)).uuid;
        }
    }

    public static String getNameFromUUID(String uuid){
        LinkedHashMap<String, Object> sel = new LinkedHashMap();
        sel.put("uuid", uuid);
        List<CustomData> list = new UUIDData().selectCustomData(sel);
        if(list.isEmpty()){
            return null;
        }else{
            return ((UUIDData)list.get(0)).name;
        }
    }

}