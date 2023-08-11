package com.potato.Word;

import com.potato.Translate.Translate;

import java.time.LocalDate;
import java.util.*;

/**
 * WordHelper是Word的工具类
 */
public class WordHelper
{
    private static Map<WordClass, String[]> wordClasses = new HashMap<>();  // 单词后缀，key是词性，value是词性对应的后缀
    private static final String[] ADJ = new String[]{"al", "able", "ible", "ed", "ful", "ish",
        "ive", "ous", "some"};  // 常见的形容词后缀
    private static final String[] V = new String[]{"fy", "ize"};  // 常见的动词后缀
    private static final String[] N = new String[]{"ance", "ee", "er", "ence", "ery", "hood",
        "ion", "ism", "ite", "ity", "logy", "ment", "ness", "or", "ship", "th", "ure"};  // 常见的名词后缀
    private static final String[] ADV = new String[]{"ly", "wise"};  // 常见的副词后缀

    static
    {
        wordClasses.put(WordClass.adj, ADJ);
        wordClasses.put(WordClass.v, V);
        wordClasses.put(WordClass.adv, ADV);
        // 因为默认词性就是名词，所以为了性能就不加名词了
        // 如果以后有了比遍历更好的判断词性的算法，则可以把它加回来
//        wordClasses.put(WordClass.n, N);
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
            sourceText.append("\n");
        }

        String resultText = Translate.filter(translate
            .translate(sourceText.toString(), "en", "zh"));

        return resultText.split("\n");
    }

    /**
     * 自动给单词设置词性
     *
     * @param word 需要设置的词性的单词
     */
    public static void autoWordClass(Word word)
    {
        // 如果包含空格，那么就视为词组
        if (word.getWordName().contains(" "))
        {
            word.setWordClass(WordClass.phrase);
            return;
        }

        // 遍历后缀的哈希表，若匹配则设置对应词性
        for (WordClass wordClass : wordClasses.keySet())
        {
            if (checkSuffix(word, wordClasses.get(wordClass)))
            {
                word.setWordClass(wordClass);
                return;
            }
        }

    }

    /**
     * 检查单词是否有匹配的后缀
     *
     * @param word 需要检查的单词
     * @param suffix 后缀
     * @return 若有匹配的后缀，则返回true
     */
    private static boolean checkSuffix(Word word, String[] suffix)
    {
        String wordText = word.getWordName();

        for (String s : suffix)
        {
            String wordSuffix = wordText;
            try
            {
                // 截取单词最后与后缀等长的部分
                wordSuffix = wordText.substring(wordText.length() - s.length());
            }
            catch (StringIndexOutOfBoundsException e)
            {
                // 出现这个错误是因为有可能后缀比单词长，因此它无关紧要，跳过即可
            }

            // 如果后缀与单词最后等长的部分一致，就说明该单词匹配的后缀
            if (wordSuffix.equals(s))
            {
                return true;
            }
        }

        return false;
    }
}
