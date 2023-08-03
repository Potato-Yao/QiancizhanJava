package com.potato.Word;

import com.potato.Translate.Translate;

import java.time.LocalDate;
import java.util.*;

/**
 * Word的工具类
 */
public class WordHelper
{
    private static Map<WordClass, String[]> wordClasses = new HashMap<>();
    private static final String[] ADJ = new String[]{"al", "able", "ible", "ed", "ful", "ish",
        "ive", "ous", "some"};
    private static final String[] V = new String[]{"fy", "ize"};
    private static final String[] N = new String[]{"ance", "ee", "er", "ence", "ery", "hood",
        "ion", "ism", "ite", "ity", "logy", "ment", "ness", "or", "ship", "th", "ure"};
    private static final String[] ADV = new String[]{"ly", "wise"};

    static
    {
        wordClasses.put(WordClass.adj, ADJ);
        wordClasses.put(WordClass.v, V);
        wordClasses.put(WordClass.adv, ADV);
        wordClasses.put(WordClass.n, N);
    }

    /**
     * 从字符串解析单词的词性
     * 每个词性以;分割
     *
     * @param text 需要解析的字符串
     * @return 词性
     */
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

    /**
     * 将词性转换为字符串
     * 每个词性使用;分割
     *
     * @param wordClasses 需要转换的词性
     * @return 转换后的字符串
     */
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

    /**
     * 一个用于快速更新单词复习次数的工具类
     * 它可以自增单词的复习次数以及正确和错误的次数
     *
     * @param word    需要更新的单词
     * @param correct 此次更新是否是单词输入正确
     * @param killed  此次更新是否是斩单词
     * @return 更新后的单词
     */
    public static Word updateWord(Word word, boolean correct, boolean killed)
    {
        word.setLastReviewDate(LocalDate.now());
        word.setKilled(killed);
        word.countPlus(correct);

        return word;
    }

    /**
     * 将单词组翻译为汉语的字符串组
     *
     * @param words 需要转换的单词组
     * @return 翻译后的字符串组
     */
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

    public static void autoWordClass(Word word)
    {
        if (word.getWordName().contains(" "))
        {
            word.setWordClass(WordClass.phrase);
            return;
        }

        for (WordClass wordClass : wordClasses.keySet())
        {
            if (checkSuffix(word, wordClasses.get(wordClass)))
            {
                word.setWordClass(wordClass);
                return;
            }
        }

    }

    private static boolean checkSuffix(Word word, String[] suffix)
    {
        String wordText = word.getWordName();

        for (String s : suffix)
        {
            String wordSuffix = wordText;
            try
            {
                wordSuffix = wordText.substring(wordText.length() - s.length());
            }
            catch (StringIndexOutOfBoundsException e)
            {
                // 无关紧要
            }

            if (wordSuffix.equals(s))
            {
                return true;
            }
        }


        return false;
    }
}
