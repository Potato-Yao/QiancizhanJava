package com.potato.OCRUtil

import com.alibaba.fastjson2.JSONObject
import com.baidu.aip.ocr.AipOcr
import com.potato.Config
import com.potato.Word.Word
import com.potato.Word.Word.WordBuilder
import com.potato.Word.WordHelper
import java.io.File

class OCRReader
{
//    private var APP_ID: String? = null
//    private var APP_KEY: String? = null
//    private var APP_SECRET_KEY: String? = null
//    private val client: AipOcr
//    private val options: HashMap<String, String>
//
//    init
//    {
//        if (Config.ocrAppId == "" || Config.ocrAppKey == "" || Config.ocrSecretKey == "")
//        {
//            APP_ID = "35339593"
//            APP_KEY = "Ad5C3C8bxLBeVn7YiDLy1o4e"
//            APP_SECRET_KEY = "lqvCae8jX5QfYGpaavlo6NVIXDGbNF9x"
//        }
//        else
//        {
//            APP_ID = Config.ocrAppId
//            APP_KEY = Config.ocrAppKey
//            APP_SECRET_KEY = Config.ocrSecretKey
//        }
//        client = AipOcr(APP_ID, APP_KEY, APP_SECRET_KEY)
//        options = HashMap()
//        options["recognize_granularity"] = "big"
//        options["language_type"] = "ENG"
//        options["detect_direction"] = "false"
//        options["detect_language"] = "false"
//        options["vertexes_location"] = "false"
//        options["probability"] = "true"
//        options["paragraph"] = "false"
//    }
//
//    fun recognizeWordList(image: File): List<Word>
//    {
//        val wordList = recognizeImage(image)
//        val meaningList = WordHelper.transWords(wordList)
//        for (i in wordList.indices)
//        {
//            wordList[i].meaning = meaningList[i]
//        }
//        return wordList
//    }
//
//    private fun recognizeImage(image: File): List<Word>
//    {
//        val wordList: MutableList<Word> = ArrayList()
//        val res = client.general(image.absolutePath, options)
//        val result = res.toString(2)
//        val jsonObject = JSONObject.parse(result)
//        val words = jsonObject.getJSONArray("words_result")
//        for (i in words.indices)
//        {
//            val wordResult = words.getJSONObject(i)
//            var wordName = wordResult.getString("words")
//            val j = wordName.indexOf("/")
//            if (j != -1)
//            {
//                wordName = wordName.substring(0, j)
//            }
//            wordName = wordName.replace("[^a-zA-Z]".toRegex(), "")
//            val word = WordBuilder().name(wordName).build()
//            wordList.add(word)
//        }
//        return wordList
//    }
}
