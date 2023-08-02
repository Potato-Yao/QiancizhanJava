import com.potato.GUI.Dialog.QuizPassedDialog;
import org.junit.jupiter.api.Test;

public class DialogTest
{
    @Test
    public void QuizDialogTest()
    {
        QuizPassedDialog dialog = new QuizPassedDialog(null, null);
        dialog.setVisible(true);
    }
}
