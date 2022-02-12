package com.Monitor.ExcelInput;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ExcelLinstener extends AnalysisEventListener<LinkedHashMap> {
    private static LinkedList<String> numberList = new LinkedList<>();

    public static LinkedList<String> getList() {
        return numberList;
    }

    public void invoke(LinkedHashMap map, AnalysisContext var2) {
        numberList.addFirst((String)map.get(Integer.valueOf(0)));
    }

    public void invokeHeadMap(Map<Integer, String> map, AnalysisContext context) {
        numberList.addFirst(map.get(Integer.valueOf(0)));
    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext) {}
}

