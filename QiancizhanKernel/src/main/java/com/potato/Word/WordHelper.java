package com.potato.Word;

import com.potato.Translate.Translate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class WordHelper
{
    public static WordClass[] parserWordClass(String text)
    {
        String[] wordClass = text.split(";");
        WordClass[] classes = new WordClass[wordClass.length];

        for (int i = 0; i < wordClass.length; i++)
        {
            classes[i] = WordClass.valueOf(wordClass[i]);
        }

        return classes;
    }

    public static String writeToString(WordClass[] wordClasses)
    {
        StringBuilder classes = new StringBuilder();
        for (WordClass wc : wordClasses)
        {
            classes.append(wc.toString());
            classes.append(";");
        }

        return classes.toString();
    }

    public static Word updateWord(Word word, boolean correct, boolean killed)
    {
        word.setLastReviewDate(LocalDate.now());
        word.setKilled(killed);
        word.countPlus(correct);

        return word;
    }

    public static String[] transWords(List<Word> words)
    {
        StringBuilder sourceText = new StringBuilder();
        Translate translate = new Translate();

        for (Word w : words)
        {
            sourceText.append(w.getWordName());
            sourceText.append("+++");
        }

        String resultText = Translate.filter(translate
                .translate(sourceText.toString(), "en", "zh"));
        String[] textList = resultText.split("\\+\\+\\+");
        textList = Arrays.copyOfRange(textList, 0, textList.length - 1);  // 百度翻译结果的最后一项是\n，要把它截取掉

        return textList;
    }
}
