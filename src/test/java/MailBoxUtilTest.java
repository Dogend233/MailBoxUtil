
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import com.tripleying.dogend.bukkit.command.SystemCommandSender;
import com.tripleying.dogend.mailbox.MailBox;
import com.tripleying.dogend.mailbox.api.mail.PersonMail;
import com.tripleying.dogend.mailbox.api.mail.SystemMail;
import com.tripleying.dogend.mailbox.util.MessageUtil;
import com.tripleying.dogend.mailboxutil.MailBoxUtil;
import static com.tripleying.dogend.mailboxutil.MailBoxUtil.generatePersonMail;
import static com.tripleying.dogend.mailboxutil.MailBoxUtil.send2OfflinePlayerByName;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * 邮件读取、发送示例
 * @author Administrator
 */
public class MailBoxUtilTest {
    
    public static void main(String[] args){
        // 雪花ID生成，用于向玩家发送邮件
        IdGeneratorOptions op = new IdGeneratorOptions(new Integer(46526).shortValue());
        op.WorkerIdBitLength = 6;
        YitIdHelper.setIdGenerator(op);
        // 创建虚拟指令发送人
        SystemCommandSender cs = new SystemCommandSender();
        // 创建MailBox对象，创建时自动执行启动方法
        MailBox mb = new MailBox();
        // 注册虚拟金钱, 因为无法装前置插件所以部分金钱模块无法加载
        MailBoxUtil.registerDoubleMoney("coin", "金币");
        MailBoxUtil.registerIntegerMoney("point", "点券");
        // 测试邮箱指令
        mb.getCommandManager().onCommand(cs, null, null, new String[]{"cdkey", "list"});
        // 读取邮件测试
        SystemMail sm = mb.getMailManager().getSystemMailById("template", 1);
        // 打印附件内容
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
        // 打印附件yml
        YamlConfiguration yml = new YamlConfiguration();
        yml.set("attach", sm.getAttachFile());
        System.out.println(yml.saveToString());
        // 生成个人邮件，自动生成id必须在
        PersonMail pm = generatePersonMail(sm);
        // 发送邮件测试，玩家必须在装完MailBox之后上线过
        System.out.println(send2OfflinePlayerByName(pm, "Dogend233")?"发送成功":"发送失败");
        // 关闭MailBox对象
        mb.close();
    }
    
}
