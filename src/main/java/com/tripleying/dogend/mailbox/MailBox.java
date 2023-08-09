package com.tripleying.dogend.mailbox;

import com.tripleying.dogend.bukkit.item.ItemMeta;
import com.tripleying.dogend.mailbox.api.mail.attach.AttachCommand;
import com.tripleying.dogend.mailbox.api.mail.attach.AttachFile;
import com.tripleying.dogend.mailbox.data.MySQLData;
import com.tripleying.dogend.mailbox.data.SQLiteData;
import com.tripleying.dogend.mailbox.manager.CommandManager;
import com.tripleying.dogend.mailbox.manager.DataManager;
import com.tripleying.dogend.mailbox.manager.ListenerManager;
import com.tripleying.dogend.mailbox.manager.MailManager;
import com.tripleying.dogend.mailbox.manager.ModuleManager;
import com.tripleying.dogend.mailbox.manager.MoneyManager;
import com.tripleying.dogend.mailbox.util.FileUtil;
import com.tripleying.dogend.mailbox.util.MessageUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import com.tripleying.dogend.bukkit.item.ItemStack;

public class MailBox {
    
    private static MailBox mailbox;
    /**
     * MC版本
     */
    private static double mc_version = 1.00;
    // 管理器
    private DataManager datamgr;
    private MailManager mailmgr;
    private MoneyManager moneymgr;
    private CommandManager cmdmgr;
    private ListenerManager listenermgr;
    private ModuleManager modulemgr;
    
    public MailBox(){
        mailbox = this;
        System.out.println(
            "  _____   ____   _____ ______ _   _ _____  \n" +
            " |  __ \\ / __ \\ / ____|  ____| \\ | |  __ \\ \n" +
            " | |  | | |  | | |  __| |__  |  \\| | |  | |\n" +
            " | |  | | |  | | | |_ |  __| | . ` | |  | |\n" +
            " | |__| | |__| | |__| | |____| |\\  | |__| |\n" +
            " |_____/ \\____/ \\_____|______|_| \\_|_____/ "
        );
        ConfigurationSerialization.registerClass(AttachCommand.class);
        ConfigurationSerialization.registerClass(AttachFile.class);
        ConfigurationSerialization.unregisterClass("org.bukkit.inventory.ItemStack");
        ConfigurationSerialization.unregisterClass("ItemMeta");
        ConfigurationSerialization.registerClass(ItemStack.class, "org.bukkit.inventory.ItemStack");
        ConfigurationSerialization.registerClass(ItemMeta.class, "ItemMeta");
        load();
    }
    
    public void load(){
        this.datamgr = new DataManager();
        this.mailmgr = new MailManager();
        this.moneymgr = new MoneyManager();
        this.cmdmgr = new CommandManager();
        this.listenermgr = new ListenerManager();
        this.modulemgr = new ModuleManager();
        YamlConfiguration msg = FileUtil.getConfig("message.yml");
        YamlConfiguration db = FileUtil.getConfig("database.yml");
        YamlConfiguration config = FileUtil.getConfig("config.yml");
        MessageUtil.init(msg);
        this.loadDataBase(db);
        try{
                // 加载本地模块
                this.modulemgr.loadLocalModule();
            }catch(Exception ex){
            }finally{
                // 选择使用的数据源
                String database = config.getString("database", "sqlite");
                if(this.datamgr.selectData(database)){
                }else{
                    // 启动数据源失败, 卸载插件
                    MessageUtil.error(MessageUtil.data_enable_error.replaceAll("%data%", database));
                }
            }
    }
    
    public void loadDataBase(YamlConfiguration database){
        // 加载SQLite配置
        if(database.getBoolean("sqlite.enable", false)){
            this.datamgr.addData(null, new SQLiteData(database));
        }
        // 加载MySQL配置
        if(database.getBoolean("mysql.enable", false)){
            this.datamgr.addData(null, new MySQLData(database));
        }
    }
    
    public void unload(){
        // 关闭数据源
        if(this.datamgr!=null) this.datamgr.closeData();
        // 卸载全部模块
        if(this.modulemgr!=null) this.modulemgr.unloadAllModule();
        this.datamgr = null;
        this.mailmgr = null;
        this.moneymgr = null;
        this.cmdmgr = null;
        this.listenermgr = null;
        this.modulemgr = null;
    }
    
    public void reload(){
        MessageUtil.error(MessageUtil.reload_unload);
        unload();
        MessageUtil.log(MessageUtil.reload_load);
        load();
        MessageUtil.log(MessageUtil.reload_finish);
    }
    
    public void close(){
        unload();
        ConfigurationSerialization.unregisterClass(AttachFile.class);
        ConfigurationSerialization.unregisterClass(AttachCommand.class);
    }
    
    public DataManager getDataManager(){
        return this.datamgr;
    }
    
    public MailManager getMailManager(){
        return this.mailmgr;
    }
    
    public MoneyManager getMoneyManager(){
        return this.moneymgr;
    }
    
    public CommandManager getCommandManager(){
        return this.cmdmgr;
    }
    
    public ListenerManager getListenerManager(){
        return this.listenermgr;
    }
    
    public ModuleManager getModuleManager(){
        return this.modulemgr;
    }
    
    public static MailBox getMailBox(){
        return mailbox;
    }
    
    public static double getMCVersion(){
        return mc_version;
    }
    
}
