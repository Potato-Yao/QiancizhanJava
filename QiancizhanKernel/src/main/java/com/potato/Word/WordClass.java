package com.potato.Word;

/**
 * 英语单词的词性
 * 若该软件以后扩展到其它语言（如俄语没有介词，且有一个叫前置词的词性），则会写更一般化的WordClass
 */
public enum WordClass
{
    n,  // 名词
    v,  // 动词
    adj,  // 形容词
    adv,  // 副词
    pron,  // 代词
    num,  // 数词
    art,  // 冠词
    prep,   // 介词
    conj,  // 连词
    interj,  // 感叹词
}
