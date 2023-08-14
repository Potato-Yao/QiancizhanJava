package com.potato;

/**
 * OptionType配置类型的枚举类
 */
public enum OptionType
{
    /* 一般选项，主要是偏好设置，适合用户进行自定义 */
    NORMAL,
    /* 高级选项，不建议用户自定义 */
    ADVANCE,
    /* 指向目录（如一般单词本存放位置），不应该被程序外修改 */
    DIR,
}
