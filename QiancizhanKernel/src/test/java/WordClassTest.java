import com.potato.Word.Word;
import com.potato.Word.WordHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class WordClassTest
{
    @Test
    public void test()
    {
        Word word = new Word.WordBuilder().name("unstoppable").build();
        System.out.println(Arrays.toString(word.getWordClass()));
        WordHelper.autoWordClass(word);
        System.out.println(Arrays.toString(word.getWordClass()));

        word.setWordName("happily");
        WordHelper.autoWordClass(word);
        System.out.println(Arrays.toString(word.getWordClass()));

        word.setWordName("classify");
        WordHelper.autoWordClass(word);
        System.out.println(Arrays.toString(word.getWordClass()));
    }
}
