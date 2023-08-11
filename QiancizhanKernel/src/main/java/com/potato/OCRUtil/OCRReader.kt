package com.potato.OCRUtil

import com.alibaba.fastjson2.JSONObject
import com.baidu.aip.ocr.AipOcr
import com.potato.Config
import com.potato.Word.Word
import com.potato.Word.Word.WordBuilder
import java.io.File

/**
 * OCRReader用于识别图像中的单词
 */
class OCRReader
{
    private var APP_ID: String? = null  // 百度OCR的APP_ID，下二者同
    private var APP_KEY: String? = null
    private var APP_SECRET_KEY: String? = null
    private val client: AipOcr
    private val options: HashMap<String, String>

    init
    {
        // 设置APP_ID、APP_KEY和APP_SECRET_KEY，如果用户没有自定义那就用我的
        if (Config.ocrAppId == "" || Config.ocrAppKey == "" || Config.ocrSecretKey == "")
        {
            APP_ID = "35339593"
            APP_KEY = "Ad5C3C8bxLBeVn7YiDLy1o4e"
            APP_SECRET_KEY = "lqvCae8jX5QfYGpaavlo6NVIXDGbNF9x"
        }
        else
        {
            APP_ID = Config.ocrAppId
            APP_KEY = Config.ocrAppKey
            APP_SECRET_KEY = Config.ocrSecretKey
        }

        client = AipOcr(APP_ID, APP_KEY, APP_SECRET_KEY)
        options = HashMap()  // 设置
        options["recognize_granularity"] = "big"  // 识别粒度
        options["language_type"] = "ENG"  // 识别语言
        options["detect_direction"] = "false"  // 是否给出文字方向
        options["detect_language"] = "false"  // 是否给出文字语言
        options["vertexes_location"] = "false"  // 是否给出顶点位置
        options["probability"] = "true"  // 是否给出置信度
        options["paragraph"] = "false"  // 是否按照段落分开
    }

    /**
     * 将图像识别为单词列表
     * 这里专门提取出一个方法是便于后期添加功能
     *
     * @param image 传入的图像
     */
    fun recognizeWordList(image: File) = recognizeImage(image)

    /**
     * 将图像识别为单词列表
     *
     * @param image 传入的图像
     * @return 识别出的单词列表
     */
    private fun recognizeImage(image: File): List<Word>
    {
        val wordList: MutableList<Word> = ArrayList()
        val res = client.general(image.absolutePath, options)  // 获取image的识别结果
        val result = res.toString(2)  // JSONObject -> String
        val jsonObject = JSONObject.parse(result)  // String -> FastJSONObject
        val words = jsonObject.getJSONArray("words_result")

        for (i in words.indices)
        {
            val wordResult = words.getJSONObject(i)
            var wordName = wordResult.getString("words")  // 获取单词

            wordName = filterString(wordName)  // 过滤识别内容

            val word = WordBuilder().name(wordName).build()
            wordList.add(word)
        }
        return wordList
    }

    /**
     * 过滤文本
     * 识别出的结构可能包含一些奇怪的字符，该函数可以将其全部过滤掉
     *
     * @param text 需要过滤的文本
     * @return 过滤后的文本
     */
    private fun filterString(text: String): String
    {
        var result = text

        // 对于英语书，识别结果会包括单词后的音标
        // 因此识别后就会有/音标/的部分
        // 这里将其全部去掉
        val j = text.indexOf("/")
        if (j != -1)
        {
            result = text.substring(0, j)
        }

        // 这里去掉其它不是英语字母的文本
        // 之所以不在这里去除音标，是因为音标也被识别为了英语字母
        result = result.replace("[^a-zA-Z]".toRegex(), "")

        return result
    }
}
