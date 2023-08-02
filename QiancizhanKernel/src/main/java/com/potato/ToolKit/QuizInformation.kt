package com.potato.ToolKit

class QuizInformation
{
    private var correctCount = 0
    private var wrongCount = 0

    fun onCorrect()
    {
        correctCount++
    }

    fun onWrong()
    {
        wrongCount++
    }

    fun getStatistic(): ArrayList<Int>
    {
        val infoList: ArrayList<Int> = ArrayList()
        val rate: Double = (correctCount / (correctCount + wrongCount)).toDouble() * 100

        infoList.add(correctCount + wrongCount)
        infoList.add(correctCount)
        infoList.add(wrongCount)
        infoList.add(rate.toInt())

        return infoList
    }
}