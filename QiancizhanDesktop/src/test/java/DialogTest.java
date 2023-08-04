import com.potato.Config;
import com.potato.GUI.Dialog.QuizPassedDialog;
import com.potato.GUI.Dialog.SettingDialog;
import com.potato.OptionType;
import org.junit.jupiter.api.Test;

public class DialogTest
{
    @Test
    public void QuizDialogTest()
    {
        QuizPassedDialog dialog = new QuizPassedDialog(null, null);
        dialog.setVisible(true);
    }

    @Test
    public void SettingsDialogTest()
    {
        Config.initial();
        SettingDialog dialog = new SettingDialog(null, OptionType.ADVANCE);
        dialog.setVisible(true);
    }
}
