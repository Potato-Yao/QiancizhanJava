package com.potato.ToolKit;

import com.potato.Word.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * QuizMaker用于生成提问的单词列表
 */
public class QuizMaker
{
    private Random random = new Random();
    private List<Word> words;

    /**
     * QuizMaker用于根据单词本生成测试
     *
     * @param wordList 需要测试的单词本
     */
    public QuizMaker(List<Word> wordList)
    {
        this.words = wordList;
    }

    /**
     * 随机测试
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
        // 上述遍历后会剩一个元素
        quizList.add(random.nextInt(0, quizList.size()), wordList.get(0));

        return quizList;
    }
}
