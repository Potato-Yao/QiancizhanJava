import com.potato.Parser.Parser;
import com.potato.ToolKit.History;
import com.potato.ToolKit.Info;
import com.potato.ToolKit.WordFileType;
import com.potato.Word.Word;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyParser extends Parser
{
    public MyParser(File file)
    {
        super(file, WordFileType.DATABASE.type());
    }

    @Override
    protected void parser()
    {
        List<Word> wordList = new ArrayList<>();
        List<History> historyList = new ArrayList<>();
        Info info = null;

        // 对以上三者赋值
        // ...

        setWordList(wordList);
        setHistoryList(historyList);
        setInfo(info);
    }
}
