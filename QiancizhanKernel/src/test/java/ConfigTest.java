import com.potato.Config;
import com.potato.OptionType;
import org.junit.jupiter.api.Test;

public class ConfigTest
{
    @Test
    public void test()
    {
        Config.initial();
        Config.update("内核版本", "111");
    }
}
