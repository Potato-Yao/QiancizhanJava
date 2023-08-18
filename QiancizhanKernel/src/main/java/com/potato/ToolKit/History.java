package com.potato.ToolKit;

import java.time.LocalDate;

/**
 * History是单词测试记录
 * @param date 测试日期
 * @param sumCount 测试的单词总数
 * @param correctCount 测试单词中正确的个数
 * @param wrongCount 测试单词中错误的个数
 * @param timeCost 测试用时，单位ms
 */
public record History(LocalDate date, int sumCount, int correctCount, int wrongCount, int timeCost)
{
}
