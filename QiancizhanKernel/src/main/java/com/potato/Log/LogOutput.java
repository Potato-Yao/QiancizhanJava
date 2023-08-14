package com.potato.Log;

/**
 * 日志输出
 */
public interface LogOutput
{
    /**
     * 输出冗余信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    void v(String tag, String message);

    /**
     * 输出冗余信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    void v(String tag, String message, Throwable throwable);

    /**
     * 输出debug信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    void d(String tag, String message);

    /**
     * 输出debug信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    void d(String tag, String message, Throwable throwable);

    /**
     * 输出一般的信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    void i(String tag, String message);

    /**
     * 输出一般的信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    void i(String tag, String message, Throwable throwable);

    /**
     * 输出警告信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    void w(String tag, String message);

    /**
     * 输出警告信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    void w(String tag, String message, Throwable throwable);

    /**
     * 输出错误信息
     *
     * @param tag     用于分辨日志来源，可以设置为类名
     * @param message 日志信息
     */
    void e(String tag, String message);

    /**
     * 输出错误信息
     *
     * @param tag       用于分辨日志来源，可以设置为类名
     * @param message   日志信息
     * @param throwable 提供给日志的报错
     */
    void e(String tag, String message, Throwable throwable);
}
