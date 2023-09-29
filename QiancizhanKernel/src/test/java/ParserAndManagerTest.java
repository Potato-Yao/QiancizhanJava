import com.potato.Config;
import com.potato.Log.Log;
import com.potato.Manager.AutoManager;
import com.potato.Parser.AutoParser;
import com.potato.ToolKit.WordFileType;
import com.potato.Word.Word;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class ParserAndManagerTest
{
    @Test
    public void parserTest()
    {
        // 首先要在Config中配置好Parser
        /*
        try
        {
            Config.setParser(WordFileType.DATABASE, MyParser.class.getConstructor(File.class));
        }
        catch (NoSuchMethodException e)
        {
            Log.e(getClass().toString(),
                String.format("将文件类型%s的解析器替换为%s时出现错误", WordFileType.DATABASE.type(), MyParser.class), e);
            throw new RuntimeException(e);
        }
         */
        File file = new File("a.db");
        // AutoParser会根据文件类型自动获取对应的解析器
        // 数据库文件的默认解析器是内核中提供的DatabaseParser
        // 在Config中修改后则会使用我们提供的修改器
        AutoParser parser = new AutoParser(file);
        List<Word> wordList = parser.getWordList();
    }

    @Test
    public void managerTest()
    {
        // 首先要在Config中配置好Manager
        /*
        try
        {
            Config.setManager(WordFileType.DATABASE, MyManager.class.getConstructor(File.class));
        }
        catch (NoSuchMethodException e)
        {
            Log.e(getClass().toString(),
                String.format("将文件类型%s的管理器替换为%s时出现错误", WordFileType.DATABASE.type(), MyManager.class), e);
            throw new RuntimeException(e);
        }
         */
        File file = new File("a.db");
        // AutoManager会根据文件类型自动获取对应的管理器
        // 数据库文件的默认管理器是内核中提供的DatabaseManager
        // 在Config中修改后则会使用我们提供的管理器
        AutoManager manager = new AutoManager(file);

        // Word构造方法请见API指南
        Word insertWord = new Word.WordBuilder().name("test").build();
        manager.insert(insertWord);  // 插入单词

        manager.push();  // 将修改写入单词本文件
    }
}
