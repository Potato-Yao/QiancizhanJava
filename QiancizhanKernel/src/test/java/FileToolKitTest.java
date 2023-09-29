import com.potato.Config;
import com.potato.ToolKit.FileToolKit;
import com.potato.ToolKit.WordFileType;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FileToolKitTest
{
    @Test
    public void FTest()
    {
        File file = new File("aaa.db");
        System.out.println(FileToolKit.getNameWithoutExtension(file));
    }

    @Test
    public void test()
    {
        try
        {
            Config.setParser(WordFileType.DATABASE, MyParser.class.getConstructor(File.class));
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }
    }
}
