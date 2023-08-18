package com.potato.Log;

/**
 * ConsoleLogOutput是将日志输出至终端的一个实现
 */
public class ConsoleLogger implements Logger
{
    /**
     * 一般日志输出至标准输出流
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    private void normalPrintln(String tag, String message, LogLevel logLevel)
    {
        normalPrintln(tag, message, null, logLevel);
    }

    /**
     * 一般日志输出至标准输出流
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    private void normalPrintln(String tag, String message, Throwable throwable, LogLevel logLevel)
    {
        if (throwable != null)
        {
            System.out.printf("TAG:%s\t%s:%s\tTHROWABLE:%s%n", tag, logLevel, message, throwable);
        }
        else
        {
            System.out.printf("TAG:%s\t%s:%s%n", tag, logLevel, message);
        }
    }

    /**
     * 错误日志输出至错误输出流
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    private void errorPrintln(String tag, String message, LogLevel logLevel)
    {
        errorPrintln(tag, message, null, logLevel);
    }

    /**
     * 错误日志输出至错误输出流
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    private void errorPrintln(String tag, String message, Throwable throwable, LogLevel logLevel)
    {
        if (throwable != null)
        {
            System.err.printf("TAG:%s\t%s:%s\tTHROWABLE:%s%n", tag, logLevel, message, throwable);
        }
        else
        {
            System.err.printf("TAG:%s\t%s:%s%n", tag, logLevel, message);
        }
    }

    /**
     * 输出冗余信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    @Override
    public void v(String tag, String message)
    {
        normalPrintln(tag, message, LogLevel.VERBOSE);
    }

    /**
     * 输出冗余信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    @Override
    public void v(String tag, String message, Throwable throwable)
    {
        normalPrintln(tag, message, throwable, LogLevel.VERBOSE);
    }

    /**
     * 输出debug信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    @Override
    public void d(String tag, String message)
    {
        normalPrintln(tag, message, LogLevel.DEBUG);
    }

    /**
     * 输出debug信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    @Override
    public void d(String tag, String message, Throwable throwable)
    {
        normalPrintln(tag, message, throwable, LogLevel.DEBUG);
    }

    /**
     * 输出一般的信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    @Override
    public void i(String tag, String message)
    {
        normalPrintln(tag, message, LogLevel.INFO);
    }

    /**
     * 输出一般的信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    @Override
    public void i(String tag, String message, Throwable throwable)
    {
        normalPrintln(tag, message, throwable, LogLevel.INFO);
    }

    /**
     * 输出警告信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    @Override
    public void w(String tag, String message)
    {
        errorPrintln(tag, message, LogLevel.WARN);
    }

    /**
     * 输出警告信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    @Override
    public void w(String tag, String message, Throwable throwable)
    {
        errorPrintln(tag, message, throwable, LogLevel.WARN);
    }

    /**
     * 输出错误信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    @Override
    public void e(String tag, String message)
    {
        errorPrintln(tag, message, LogLevel.ERROR);
    }

    /**
     * 输出错误信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    @Override
    public void e(String tag, String message, Throwable throwable)
    {
        errorPrintln(tag, message, throwable, LogLevel.ERROR);
    }
}
