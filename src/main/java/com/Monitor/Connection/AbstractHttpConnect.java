package com.Monitor.Connection;


import java.util.HashMap;

public abstract class AbstractHttpConnect {
    private static int successNum = 0;
    private static int falseNum = 0;
    protected HashMap<String, String> map = new HashMap();

    public AbstractHttpConnect() {
    }

    protected abstract String doGet(String var1);

    public String getContent(String url) {
        int errorNum = 0;
        int allErrorNum = 0;
        String content = null;

        do {
            if (errorNum >= 6) {
                this.map.clear();
                System.out.println("重新创建会话");
                errorNum = 0;
            }

            long time1 = System.currentTimeMillis();
            content = this.doGet(url);
            if (content.equals("no")) {
                System.out.println("url:  " + url + "请求实体为空！");
                return "no";
            }

            if (content.contains("permission") || content.contains("false")) {
                ++errorNum;
                ++falseNum;
            }

            long time2 = System.currentTimeMillis();
            long consumerTime = (time2 - time1) / 1000L;
            System.out.println("content:" + content + "  url  :" + url + "错误次数：" + allErrorNum + "  请求时间：" + consumerTime);
            ++allErrorNum;
        } while(!content.contains("orderable_number") && !content.contains("orderablePartNumber") && !content.contains("availableForPurchaseFlag"));

        ++successNum;
        System.out.println("成功=" + successNum + "  失败=" + falseNum);
        return content;
    }
}
