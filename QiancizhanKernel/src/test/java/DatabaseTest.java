import com.potato.Config;
import com.potato.Manager.AutoManager;
import com.potato.Parser.AutoParser;
import com.potato.ToolKit.History;
import com.potato.ToolKit.Info;
import com.potato.Word.Word;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

public class DatabaseTest
{
    @Test
    public void parserTest()
    {
        Config.initial();
        AutoParser parser = new AutoParser(new File(Config.normalWordListPath, "选八U3.db"));
        System.out.println(parser.getHistoryList());
        System.out.println(parser.getInfo());
    }

    @Test
    public void managerTest()
    {
        Config.initial();
        AutoManager manager = new AutoManager(new File(Config.normalWordListPath, "选八U3.db"));
//        manager.insert(new History(LocalDate.now(), 3, 1, 2, 1000));
        manager.modify(new Info("ru"));
        manager.push();
    }
}
