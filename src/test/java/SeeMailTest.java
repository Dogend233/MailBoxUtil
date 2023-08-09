
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import com.tripleying.dogend.bukkit.command.SystemCommandSender;
import com.tripleying.dogend.mailbox.MailBox;
import com.tripleying.dogend.mailbox.api.mail.SystemMail;
import com.tripleying.dogend.mailbox.util.MessageUtil;
import com.tripleying.dogend.mailboxutil.MailBoxUtil;
import static com.tripleying.dogend.mailboxutil.MailBoxUtil.generatePersonMail;
import static com.tripleying.dogend.mailboxutil.MailBoxUtil.send2OfflinePlayerByName;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * 查看邮件示例
 * @author Administrator
 */
public class SeeMailTest {
    
    public static void main(String[] args){
        IdGeneratorOptions op = new IdGeneratorOptions(new Integer(46526).shortValue());
        op.WorkerIdBitLength = 6;
        YitIdHelper.setIdGenerator(op);
        SystemCommandSender cs = new SystemCommandSender();
        MailBox mb = new MailBox();
        MailBoxUtil.registerDoubleMoney("coin", "金币");
        MailBoxUtil.registerIntegerMoney("point", "点券");
        mb.getCommandManager().onCommand(cs, null, null, new String[]{"cdkey", "list"});
        SystemMail sm = mb.getMailManager().getSystemMailById("template", 1);
        sm.getAttachFile().getItemStacks().forEach(is -> {
            MessageUtil.log("========ItemStack========");
            MessageUtil.log(is.getType()+" x "+is.getAmount());
            is.getArgs().forEach((k,v) -> MessageUtil.log(k));
            if(is.hasItemMeta()){
                MessageUtil.log("========ItemMeta=========");
                is.getItemMeta().getArgs().forEach((k,v) -> MessageUtil.log(k));
            }
            MessageUtil.log("=========================");
        });
        YamlConfiguration yml = new YamlConfiguration();
        yml.set("attach", sm.getAttachFile());
        System.out.println(yml.saveToString());
        System.out.println(send2OfflinePlayerByName(generatePersonMail(sm), "Dogend233")?"发送成功":"发送失败");
        mb.close();
    }
    
}
