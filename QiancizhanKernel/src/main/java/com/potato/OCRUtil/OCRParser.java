package com.potato.OCRUtil;

import com.alibaba.fastjson2.JSONArray;

import java.io.File;
import java.util.List;

public abstract class OCRParser
{
    public abstract List<String> recognizeImage(File image);
}
