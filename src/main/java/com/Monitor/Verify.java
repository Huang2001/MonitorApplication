package com.Monitor;

import com.Monitor.Connection.AbstractHttpConnect;
import com.alibaba.fastjson.JSONObject;

public class Verify {
    private static String url1 = "https://www.ti.com.cn/search/opn?searchTerm=";

    private static String url2 = "&locale=zh-CN";

    private static String url3 = "https://www.ti.com.cn/productmodel/";

    private static String url4 = "/orderables?locale=zh-CN&orderable=";

    public static boolean verfiy(String number, AbstractHttpConnect connect) {
        return step1(number, connect);
    }

    private static boolean step1(String number, AbstractHttpConnect connect) {
        String content = connect.getContent(url1 + number + url2);
        JSONObject json = JSONObject.parseObject(content);
        String generalNumber = json.getString("genericPartNumber");
        return step2(generalNumber, number, connect);
    }

    private static boolean step2(String number1, String number2, AbstractHttpConnect connect) {
        String content = connect.getContent(url3 + number1 + url4 + number2);
        JSONObject json = JSONObject.parseObject(content);
        String flag = json.getString("availableForPurchaseFlag");
        if (flag.equals("Y"))
            return true;
        return false;
    }
}

