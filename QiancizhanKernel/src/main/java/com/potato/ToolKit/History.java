package com.potato.ToolKit;

import java.time.LocalDate;

public record History(LocalDate date, int sumCount, int correctCount, int wrongCount, int timeCost)
{
}
