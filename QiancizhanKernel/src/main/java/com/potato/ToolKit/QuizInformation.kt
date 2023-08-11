package com.potato.ToolKit

/**
 * QuizInformation是一个统计测试数据的工具类
 */
class QuizInformation
{
    private var correctCount = 0
    private var wrongCount = 0

    /**
     * 正确计数使用此方法
     */
    fun onCorrect()
    {
        correctCount++
    }

    /**
     * 错误计数使用此方法
     */
    fun onWrong()
    {
        wrongCount++
    }

    /**
     * 返回计数的统计结果
     *
     * @return 返回的ArrayList中第一项是测试的总数，第二项是测试的正确个数，第三项是测试的错误个数，最后一项是正确率（百分比）
     */
    fun getStatistic(): ArrayList<Int>
    {
        val infoList: ArrayList<Int> = ArrayList()
        val rate: Double = (correctCount.toDouble() / (correctCount + wrongCount)) * 100

        infoList.add(correctCount + wrongCount)
        infoList.add(correctCount)
        infoList.add(wrongCount)
        infoList.add(rate.toInt())

        return infoList
    }
}