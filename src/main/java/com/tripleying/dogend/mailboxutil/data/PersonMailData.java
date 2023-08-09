package com.tripleying.dogend.mailboxutil.data;

import com.tripleying.dogend.mailbox.api.data.Data;
import com.tripleying.dogend.mailbox.api.data.DataType;
import com.tripleying.dogend.mailbox.api.mail.CustomData;
import com.tripleying.dogend.mailbox.api.mail.attach.AttachFile;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;

public class PersonMailData extends CustomData {

    @Data(type = DataType.Long)
    protected long id;
    @Data(type = DataType.String)
    protected String type;
    @Data(type = DataType.String)
    protected String title;
    @Data(type = DataType.YamlString)
    protected List<String> body;
    @Data(type = DataType.String)
    protected String sender;
    @Data(type = DataType.String)
    protected String sendtime;
    @Data(type = DataType.YamlString)
    protected AttachFile attach;
    @Data(type = DataType.Boolean)
    protected boolean received;
    @Data(type = DataType.String)
    protected String uuid;

    public PersonMailData() {
        super("mailbox_person_mail");
    }

    @Override
    public CustomData loadFromYamlConfiguration(YamlConfiguration yml) {
        this.id = yml.getLong("id");
        this.title = yml.getString("title");
        this.type = yml.getString("type");
        this.body = yml.getStringList("body");
        this.sender = yml.getString("sender");
        this.sendtime = yml.getString("sendtime");
        this.attach = (AttachFile)yml.get("attach");
        this.received = yml.getBoolean("received");
        this.uuid = yml.getString("uuid");
        return this;
    }

}
