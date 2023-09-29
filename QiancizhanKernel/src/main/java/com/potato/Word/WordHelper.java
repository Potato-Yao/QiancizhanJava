package com.potato.Word;

import com.potato.Translate.Translate;

import java.time.LocalDate;
import java.util.*;

/**
 * WordHelper是{@link Word}的工具类
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
     * 将代表词性的字符串转化为{@link WordClass}的数组
     * <p> 代表词性的字符串应当使用;分隔每个词性
     * <p> 如字符串n.;adj.;将会被解析为{@code {WordClass.n, WordClass.adj}}的数组
     *
     * @param text 需要解析的词性字符串
     * @return 解析出的词性数组
     */
    public static WordClass[] parserWordClass(String text)
    {
        String[] wordClass = text.split(";");  // 词性以;分隔，这里获取了所有词性的字符串
        WordClass[] classes = new WordClass[wordClass.length];

        for (int i = 0; i < wordClass.length; i++)
        {
            classes[i] = WordClass.valueOf(wordClass[i]);
        }

        return classes;
    }

    /**
     * 将{@link WordClass}的数组转换为代表词性的字符串
     * <p> 字符串中的每个词性使用;分割
     * <p> 如词性数组{@code {WordClass.n, WordClass.adj}}将会被解析为字符串n.;adj.;
     *
     * @param wordClasses 需要转换的词性数组
     * @return 转换后的字符串
     */
    public static String wordClassToString(WordClass[] wordClasses)
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
     * <p> 它可以自增单词的复习次数以及正确和错误的次数，并且将最后修改日期改为今天
     *
     * @param word    需要更新的单词
     * @param correct 此次更新是否将正确次数自增一
     * @param killed  此次更新是否将单词设为已斩的
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
     * 这个工具类用于给出{@link Word}列表的所有单词的汉义
     *
     * @param words 需要翻译的单词列表
     * @return 传入单词列表对应的汉义的字符串数组
     */
    public static String[] transWords(List<Word> words)
    {
        StringBuilder sourceText = new StringBuilder();
        Translate translate = new Translate();

        /*
        这个方法的算法是将单词列表转化为一个特殊格式的字符串
        将其翻译后再得到汉义的字符串
        再使用特殊格式就可以将其还原为原来单词列表的顺序了
        这里使用的是回车作为分割的特殊格式，比如传入单词列表{apple, banana, watermelon}
        则会被转换为
        apple
        banana
        watermelon
        这个字符串会被翻译为
        苹果
        香蕉
        西瓜
        这个字符串就可以还原为{苹果, 香蕉, 苹果}，对应给单词赋汉义即可
        最开始使用的是*分割，后来换成了+++分割，但是它们都有概率被百度翻译替换掉，因此被废弃了
        但是回车不会被替换，所以使用回车
         */
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
     * 通过分析单词{@link Word}的后缀名，自动给单词设置词性{@link WordClass}
     *
     * @param word 需要设置的词性的单词
     */
    public static void setWordClass(Word word)
    {
        String space = " ";

        // 如果包含空格，那么就视为词组
        if (word.getWordName().contains(space))
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
