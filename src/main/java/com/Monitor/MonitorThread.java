package com.Monitor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class MonitorThread extends Thread {
    private static volatile ArrayList<DisplayData> dataList = new ArrayList();
    private static volatile LinkedList<DisplayData> minitorDataList;
    private static volatile boolean isMonitorOnce;
    private static volatile boolean isStop = true;
    private static volatile int sleepTime = 2;

    public MonitorThread() {
    }

    public static void clearMinitor() {
        minitorDataList.clear();
    }

    public void setMinitorDataList(LinkedList<DisplayData> list) {
        minitorDataList = list;
    }

    public static void setIsMonitorOnce(boolean is) {
        isStop = false;
        isMonitorOnce = is;
    }

    public static void setAllMonitor() {
        for(int i = 0; i < dataList.size(); ++i) {
            ((DisplayData)dataList.get(i)).getCheckBox().setSelected(true);
        }

    }

    public static void setIsStop(boolean is) {
        isStop = is;
    }

    public static void setSleepTime(int second) {
        sleepTime = second;
    }

    public static void addData(DisplayData data) {
        synchronized(dataList) {
            dataList.add(data);
        }
    }

    public static DisplayData getData(String number) {
        for(int i = 0; i < dataList.size(); ++i) {
            if (((DisplayData)dataList.get(i)).getNumber().equals(number)) {
                return (DisplayData)dataList.get(i);
            }
        }

        return null;
    }

    public static void removeData(DisplayData data) {
        synchronized(dataList) {
            dataList.remove(data);
        }
    }

    private void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep((long)second);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

    }

    public void run() {
    }
}
