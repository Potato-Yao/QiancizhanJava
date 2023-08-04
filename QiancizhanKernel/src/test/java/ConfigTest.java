import com.potato.Config;
import org.junit.jupiter.api.Test;

public class ConfigTest
{
    @Test
    public void test()
    {
        Config.initial();
        System.out.println(Config.author);
        Config.author = "aba";
        Config.write();
        System.out.println(Config.author);
    }
}
