package com.potato.ToolKit;

import java.util.ArrayList;
import java.util.List;

public class QuizInformation
{
    private int correctCount = 0;
    private int wrongCount = 0;

    public void onCorrect()
    {
        correctCount++;
    }

    public void onWrong()
    {
        wrongCount++;
    }

    public ArrayList<Integer> getStatistic()
    {
        ArrayList<Integer> result = new ArrayList<>();

        double correctRate = (double) correctCount /
            (double) (correctCount + wrongCount) * 100;
        result.add(correctCount + wrongCount);
        result.add(correctCount);
        result.add(wrongCount);
        result.add((int) correctRate);

        return result;
    }
}
