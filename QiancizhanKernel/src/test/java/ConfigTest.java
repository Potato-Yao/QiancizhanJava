import com.potato.Config;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ConfigTest
{
    @Test
    public void initialTest()
    {
        File configFile = new File(".", "config.json");
        Config.initial(configFile, null, null, null, new MyLogger());
    }
}
