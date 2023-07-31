package com.potato.ToolKit;

import com.potato.Word.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizMaker
{
    private Random random = new Random();
    private List<Word> words;

    public QuizMaker(List<Word> wordList)
    {
        this.words = wordList;
    }

    public List<Word> randomQuizMaker()
    {
        List<Word> quizList = new ArrayList<>();
        List<Word> wordList = new ArrayList<>(words);

        while (wordList.size() > 1)
        {
            int index = random.nextInt(0, wordList.size());
            quizList.add(wordList.get(index));
            wordList.remove(index);
        }
        quizList.add(random.nextInt(0, quizList.size()), wordList.get(0));

        return quizList;
    }
}
