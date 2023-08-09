package com.tripleying.dogend.mailboxutil.builder;

import com.tripleying.dogend.bukkit.item.ItemStack;
import com.tripleying.dogend.mailbox.api.mail.PersonMail;
import com.tripleying.dogend.mailbox.api.mail.attach.AttachFile;
import com.tripleying.dogend.mailbox.util.TimeUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerMailBuilder {

    protected long id;
    protected String type;
    protected String title;
    protected List<String> body;
    protected String sender;
    protected String sendtime;
    protected AttachFile attach;
    protected boolean received;
    protected String uuid;

    public PlayerMailBuilder(){
        this.id = 0;
        this.type = "";
        this.title = "";
        this.body = new ArrayList<>();
        this.sender = "";
        this.sendtime = "2021-01-01 08:00:00";
        this.attach = new AttachFile();
        this.received = false;
        this.uuid = "";
    }

    public PlayerMailBuilder setId(long id){
        this.id = id;
        return this;
    }

    public PlayerMailBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public PlayerMailBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public PlayerMailBuilder setBody(List<String> body) {
        this.body = body;
        return this;
    }

    public PlayerMailBuilder addBody(String body) {
        this.body.add(body);
        return this;
    }


    public PlayerMailBuilder setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public PlayerMailBuilder setSendTime(String sendtime) {
        this.sendtime = sendtime;
        return this;
    }

    public PlayerMailBuilder setSendTime(Date date) {
        this.sendtime = TimeUtil.date2String(date);
        return this;
    }

    public PlayerMailBuilder setSendTimeNow() {
        this.sendtime = TimeUtil.currentTimeString();
        return this;
    }

    public PlayerMailBuilder setAttach(AttachFile attach) {
        this.attach = attach;
        return this;
    }

    public PlayerMailBuilder addMoney(String name, Object count) {
        attach.addMoney(name, count);
        return this;
    }

    public PlayerMailBuilder addPlayerCommand(String cmd) {
        attach.getCommands().addPlayerCommand(cmd);
        return this;
    }

    public PlayerMailBuilder addConsoleCommand(String cmd) {
        attach.getCommands().addConsoleCommand(cmd);
        return this;
    }

    public PlayerMailBuilder addOPCommand(String cmd) {
        attach.getCommands().addOPCommand(cmd);
        return this;
    }

    public PlayerMailBuilder addItemStack(ItemStack... iss) {
        attach.addItemStack(iss);
        return this;
    }

    public PlayerMailBuilder setReceived(boolean received) {
        this.received = received;
        return this;
    }

    public PlayerMailBuilder setReceiver(Player p) {
        this.uuid = p.getUniqueId().toString();
        return this;
    }

    public PlayerMailBuilder setReceiverUUID(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public YamlConfiguration toYamlConfiguration(){
        YamlConfiguration yml = new YamlConfiguration();
        yml.set("id", this.id);
        yml.set("type", this.type);
        yml.set("title", this.title);
        yml.set("body", this.body);
        yml.set("sender", this.sender);
        yml.set("sendtime", this.sendtime);
        yml.set("attach", this.attach);
        yml.set("received", this.received);
        yml.set("uuid", this.uuid);
        return yml;
    }

    public PersonMail toPersonMail(){
        YamlConfiguration yml = this.toYamlConfiguration();
        PersonMail pm = new PersonMail(yml);
        return pm;
    }

}
