package com.potato.Log;

/**
 * ConsoleLogOutput是将日志输出至终端的一个实现
 */
public class ConsoleLogOutput implements LogOutput
{
    /**
     * 一般日志输出至标准输出流
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    private void normalPrintln(String tag, String message)
    {
        normalPrintln(tag, message, null);
    }

    /**
     * 一般日志输出至标准输出流
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    private void normalPrintln(String tag, String message, Throwable throwable)
    {
        if (throwable != null)
        {
            System.out.printf("TAG:%s\tINFO:%s\tTHROWABLE:%s%n", tag, message, throwable.toString());
        }
        else
        {
            System.out.printf("TAG:%s\tINFO:%s%n", tag, message);
        }
    }

    /**
     * 错误日志输出至错误输出流
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    private void errorPrintln(String tag, String message)
    {
        errorPrintln(tag, message, null);
    }

    /**
     * 错误日志输出至错误输出流
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    private void errorPrintln(String tag, String message, Throwable throwable)
    {
        if (throwable != null)
        {
            System.err.printf("TAG:%s\tINFO:%s\tTHROWABLE:%s%n", tag, message, throwable.toString());
        }
        else
        {
            System.err.printf("TAG:%s\tINFO:%s%n", tag, message);
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
        normalPrintln(tag, message);
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
        normalPrintln(tag, message, throwable);
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
        normalPrintln(tag, message);
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
        normalPrintln(tag, message, throwable);
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
        normalPrintln(tag, message);
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
        normalPrintln(tag, message, throwable);
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
        errorPrintln(tag, message);
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
        errorPrintln(tag, message, throwable);
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
        errorPrintln(tag, message);
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
        errorPrintln(tag, message, throwable);
    }
}
