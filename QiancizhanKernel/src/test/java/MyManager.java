import com.potato.Manager.Manager;
import com.potato.ToolKit.WordFileType;
import com.potato.Word.Word;

import java.io.File;
import java.util.Map;

public class MyManager extends Manager
{
    public MyManager(File file)
    {
        super(file, WordFileType.DATABASE.type());
    }

    @Override
    public void push()
    {
        for (Word w : getInsertWords())
        {
            // 将w加入单词本文件
        }

        for (Word w : getDeleteWords())
        {
            // 将w从单词本文件删除
        }

        for (Map.Entry<Word, Word> w : getModifyWords().entrySet())
        {
            Word from = w.getKey();
            Word to = w.getValue();
            // 将from在单词本中改为to
        }

        // 处理History的代码与上面类似
        // ...

        // 处理Info
        // ...

        // close()
    }
}
