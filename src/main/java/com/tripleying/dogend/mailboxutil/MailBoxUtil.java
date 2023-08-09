package com.tripleying.dogend.mailboxutil;

import com.github.yitter.idgen.YitIdHelper;
import com.tripleying.dogend.mailbox.MailBox;
import com.tripleying.dogend.mailbox.api.mail.PersonMail;
import com.tripleying.dogend.mailbox.api.mail.SystemMail;
import com.tripleying.dogend.mailbox.api.money.DoubleMoney;
import com.tripleying.dogend.mailbox.api.money.IntegerMoney;
import com.tripleying.dogend.mailbox.manager.MoneyManager;
import com.tripleying.dogend.mailbox.util.TimeUtil;
import com.tripleying.dogend.mailboxutil.data.PersonMailData;
import com.tripleying.dogend.mailboxutil.data.UUIDData;
import java.util.LinkedHashMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MailBoxUtil {
    
    public static MailBox getMailBox(){
        return MailBox.getMailBox();
    }
    
    public static boolean registerIntegerMoney(String name, String display){
        MailBox mb = getMailBox();
        if(mb!=null){
            MoneyManager mm = mb.getMoneyManager();
            if(mm!=null){
                MoneyManager.getMoneyManager().registerMoney(null, new IntegerMoney(name, display) {
                    @Override
                    protected boolean givePlayerBalance(Player p, int i) {
                        return true;
                    }

                    @Override
                    protected boolean removePlayerBalance(Player p, int i) {
                        return true;
                    }

                    @Override
                    protected boolean hasPlayerBalance(Player p, int i) {
                        return true;
                    }

                    @Override
                    public Object getPlayerBalance(Player p) {
                        return true;
                    }
                });
                return true;
            }
        }
        return false;
    }
    
    public static boolean registerDoubleMoney(String name, String display){
        MailBox mb = getMailBox();
        if(mb!=null){
            MoneyManager mm = mb.getMoneyManager();
            if(mm!=null){
                MoneyManager.getMoneyManager().registerMoney(null, new DoubleMoney(name, display) {
                    @Override
                    protected boolean givePlayerBalance(Player p, double i) {
                        return true;
                    }

                    @Override
                    protected boolean removePlayerBalance(Player p, double i) {
                        return true;
                    }

                    @Override
                    protected boolean hasPlayerBalance(Player p, double i) {
                        return true;
                    }

                    @Override
                    public Object getPlayerBalance(Player p) {
                        return true;
                    }
                });
                return true;
            }
        }
        return false;
    }
    
    public static PersonMail generatePersonMail(SystemMail sm){
        PersonMail pm = new PersonMail(sm);
        pm.setType("web");
        pm.setId(YitIdHelper.nextId());
        pm.setSendtime(TimeUtil.currentTimeString());
        return pm;
    }
    
    public static boolean send2OfflinePlayerByName(PersonMail pm, String name){
        String uuid = UUIDData.getUUIDFromName(name);
        if(uuid==null){
            return false;
        }else{
            return send2OfflinePlayerByUUID(pm, uuid);
        }
    }

    public static boolean send2OfflinePlayerByUUID(PersonMail pm, String uuid){
        PersonMailData pmd = new PersonMailData();
        LinkedHashMap<String, Object> selid = new LinkedHashMap();
        selid.put("uuid", uuid);
        selid.put("type", pm.getType());
        selid.put("id", pm.getId());
        YamlConfiguration yml = pm.toYamlConfiguration();
        yml.set("uuid", uuid);
        yml.set("received", false);
        pm = new PersonMail(yml);
        yml = pm.toYamlConfiguration();
        pmd = (PersonMailData) pmd.loadFromYamlConfiguration(yml);
        return pmd.insertCustomData();
    }
    
}
