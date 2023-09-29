package com.potato.ToolKit;

import com.potato.Word.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * QuizMaker用于根据一定算法改变单词列表中元素的顺序
 */
public class QuizMaker
{
    private Random random = new Random();
    private List<Word> words;

    /**
     * QuizMaker用于根据一定算法改变单词列表中元素的顺序
     * <p> 所有用于改变顺序的方法都是没有副作用的方法，不会改变原单词列表中元素的顺序
     *
     * @param wordList 需要改变顺序的单词列表
     */
    public QuizMaker(List<Word> wordList)
    {
        this.words = wordList;
    }

    /**
     * 随机测试，该方法将会随机打乱单词列表中元素的顺序
     *
     * @return 随机测试的单词列表
     */
    public List<Word> randomQuizMaker()
    {
        List<Word> quizList = new ArrayList<>();  // 测试的列表，即打乱后的列表
        List<Word> wordList = new ArrayList<>(words);  // 未打乱的初始列表

        while (wordList.size() > 1)
        {
            int index = random.nextInt(0, wordList.size());
            quizList.add(wordList.get(index));
            wordList.remove(index);
        }
        // 上面的遍历后会剩一个元素，将其插入到随机索引
        quizList.add(random.nextInt(0, quizList.size()), wordList.get(0));

        return quizList;
    }
}
