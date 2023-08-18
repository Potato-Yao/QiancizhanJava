package com.potato.Word;

import com.potato.Log.Log;
import lombok.Data;

import java.time.LocalDate;

/**
 * Word是单词数据类
 */
@Data
public class Word
{
    /* 单词的拼写 */
    private String wordName;

    /*
     * 单词的词性
     * 词性可以有多个
     */
    private WordClass[] wordClass;

    /*
     * 单词的意思
     * 对于有多个意思或者不同词性有不同意思的，使用英文分号;将其分隔开
     */
    private String meaning;

    /* 单词的复习次数 */
    private int reviewCount;

    /* 最后一次复习时间，格式yyyy-MM-dd */
    private LocalDate lastReviewDate;

    /* 复习次数中答对的次数 */
    private int correctCount;

    /* 复习次数中答错的次数，即sum = correct + wrong */
    private int wrongCount;

    /* 是否已斩 */
    private boolean isKilled;

    /**
     * 使用Builder的构造器
     *
     * @param builder 传入的Builder
     */
    private Word(WordBuilder builder)
    {
        this.wordName = builder.wordName;
        this.wordClass = builder.wordClass;
        this.meaning = builder.meaning;
        this.reviewCount = builder.reviewCount;
        this.lastReviewDate = builder.lastReviewDate;
        this.correctCount = builder.correctCount;
        this.wrongCount = builder.wrongCount;
        this.isKilled = builder.isKilled;
    }

    /**
     * 若单词有多个词性，使用这个方法设置词性
     *
     * @param wordClass 词性
     */
    public void setWordClass(WordClass[] wordClass)
    {
        this.wordClass = wordClass;
    }

    /**
     * 若单词只有一个词性，可以使用传入数组的方法，也可以使用该方法
     *
     * @param wordClass 词性
     */
    public void setWordClass(WordClass wordClass)
    {
        this.wordClass = new WordClass[]{wordClass};
    }

    /**
     * 获取是否已斩信息
     *
     * @return 若已斩则返回1，反之返回0
     */
    public int getIntIsKilled()
    {
        return isKilled ? 1 : 0;
    }

    /**
     * 单词增添复习次数
     *
     * @param isCorrect 是否正确
     *                  若正确则给reviewCount和correctCount自增
     *                  反之给wrongCount自增
     */
    public void countPlus(boolean isCorrect)
    {
        reviewCount++;
        if (isCorrect)
        {
            correctCount++;
        }
        else
        {
            wrongCount++;
        }
    }

    /**
     * 构造Word的Builder
     */
    public static class WordBuilder
    {
        private String wordName = "";  // 单词的拼写
        private WordClass[] wordClass = new WordClass[]{WordClass.n};  // 单词的词性
        private String meaning = "";  // 单词的意思
        private int reviewCount = 0;  // 单词的复习次数
        private LocalDate lastReviewDate = LocalDate.now();  // 最后一次复习时间
        private int correctCount = 0;  // 复习次数中答对的次数
        private int wrongCount = 0;  // 复习次数中答错的次数
        private boolean isKilled = false;  // 是否已斩

        /**
         * 构造Word的Builder
         */
        public WordBuilder()
        {
        }

        /**
         * 设置单词拼写
         *
         * @param name 单词
         * @return 设置了拼写的Builder
         */
        public WordBuilder name(String name)
        {
            this.wordName = name;
            return this;
        }

        /**
         * 设置单词单个词性
         *
         * @param wordClass 词性
         * @return 设置了词性的Builder
         */
        public WordBuilder wordClass(WordClass wordClass)
        {
            this.wordClass = new WordClass[]{wordClass};
            return this;
        }

        /**
         * 设置单词多个词性
         *
         * @param wordClass 词性
         * @return 设置了词性的Builder
         */
        public WordBuilder wordClass(WordClass[] wordClass)
        {
            this.wordClass = wordClass;
            return this;
        }

        /**
         * 设置单词的汉义
         *
         * @param meaning 汉义
         * @return 设置了汉义的Builder
         */
        public WordBuilder meaning(String meaning)
        {
            this.meaning = meaning;
            return this;
        }

        /**
         * 设置单词的复习次数
         *
         * @param reviewCount 复习次数
         * @return 设置了复习次数的Builder
         */
        public WordBuilder reviewCount(int reviewCount)
        {
            this.reviewCount = reviewCount;
            return this;
        }

        /**
         * 设置单词的最后复习日期
         *
         * @param date 最后复习日期
         * @return 设置了最后复习日期的Builder
         */
        public WordBuilder lastReviewDate(LocalDate date)
        {
            this.lastReviewDate = date;
            return this;
        }

        /**
         * 设置单词的正确次数
         *
         * @param correctCount 正确次数
         * @return 设置了正确次数的Builder
         */
        public WordBuilder correctCount(int correctCount)
        {
            this.correctCount = correctCount;
            this.wrongCount = this.reviewCount - correctCount;
            return this;
        }

        /**
         * 设置单词是否已斩
         *
         * @param isKilled 是否已斩
         * @return 设置了是否已斩的Builder
         */
        public WordBuilder isKilled(boolean isKilled)
        {
            this.isKilled = isKilled;
            return this;
        }

        /**
         * 构造单词
         *
         * @return 构造好的Word
         */
        public Word build()
        {
            Word word = new Word(this);

            Log.v(getClass().toString(), String.format("单词%s已创建", word));

            return word;
        }
    }
}
