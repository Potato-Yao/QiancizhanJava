package com.potato.OCRUtil;

import com.alibaba.fastjson2.JSONArray;

import java.io.File;

public abstract class OCRParser
{
    public abstract JSONArray recognizeImage(File image);
}
