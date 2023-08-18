import com.potato.ToolKit.FileToolKit;
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
}
