package com.potato.Log;

import com.potato.Config;
import lombok.Setter;

import java.util.Objects;

/**
 * Log用于输出日志
 */
public class Log
{
    @Setter
    private static LogOutput logOutput;

    /**
     * 输出冗余信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    public static void v(String tag, String message)
    {
        logOutput.v(tag, message);
    }

    /**
     * 输出冗余信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    public static void v(String tag, String message, Throwable throwable)
    {
        logOutput.v(tag, message, throwable);
    }

    /**
     * 输出debug信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    public static void d(String tag, String message)
    {
        logOutput.d(tag, message);
    }

    /**
     * 输出debug信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    public static void d(String tag, String message, Throwable throwable)
    {
        logOutput.d(tag, message, throwable);
    }

    /**
     * 输出一般的信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    public static void i(String tag, String message)
    {
        logOutput.i(tag, message);
    }

    /**
     * 输出一般的信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    public static void i(String tag, String message, Throwable throwable)
    {
        logOutput.i(tag, message, throwable);
    }

    /**
     * 输出警告信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    public static void w(String tag, String message)
    {
        logOutput.w(tag, message);
    }

    /**
     * 输出警告信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    public static void w(String tag, String message, Throwable throwable)
    {
        logOutput.w(tag, message, throwable);
    }

    /**
     * 输出错误信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    public static void e(String tag, String message)
    {
        logOutput.e(tag, message);
    }

    /**
     * 输出错误信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    public static void e(String tag, String message, Throwable throwable)
    {
        logOutput.e(tag, message, throwable);
    }
}
