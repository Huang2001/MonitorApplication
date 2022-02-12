package com.Monitor;

import com.Monitor.Connection.AbstractHttpConnect;
import com.Monitor.Connection.XiaoXiangProxy;
import com.Monitor.UI.TableView;
import com.alibaba.fastjson.JSONObject;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class GetMessageThread extends Thread {
    private static String url1 = "https://www.ti.com/storeservices/cart/opninventory?opn=";
    private volatile boolean isDelete = false;
    private volatile boolean isWait = true;
    private volatile DisplayData data;
    private volatile boolean isOrder = true;
    private int sleepInt = 1;
    private int threadNum;
    private AbstractHttpConnect connect = new XiaoXiangProxy();

    public GetMessageThread(int n, DisplayData data) {
        this.threadNum = n;
        this.data = data;
    }

    public DisplayData getData() {
        return this.data;
    }

    public void setWait(boolean isWait) {
        this.isWait = isWait;
    }

    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getNumber() {
        return this.data.getNumber();
    }

    private void function() {
        this.setPriority(5);
        String url = url1 + this.data.getNumber();
        String content = this.connect.getContent(url);
        Calendar calendar = Calendar.getInstance();
        if (content.contains("maintenance")) {
            this.data.setTime(calendar.get(2) + 1 + "/" + calendar.get(5) + "-" + calendar.get(11) + ":" + calendar.get(12) + ":" + calendar.get(13));
            TableView.refresh();
        } else {
            JSONObject object = JSONObject.parseObject(content);
            String number = object.getString("orderable_number");
            if (number.equals(this.data.getNumber())) {
                String store = object.getString("inventory");
                if (!store.equals("0") && this.data.getStore().equals("0") && Verify.verfiy(this.data.getNumber(), this.connect)) {
                    String string = this.data.getNumber() + "&" + store + "&" + calendar.get(11) + ":" + calendar.get(12) + ":" + calendar.get(13);
                    GeneralLog.addNumber(string, this.data.getNumber(), store, calendar);
                }

                this.data.setStore(store);
                this.data.setTime(calendar.get(2) + 1 + "/" + calendar.get(5) + "-" + calendar.get(11) + ":" + calendar.get(12) + ":" + calendar.get(13));
                TableView.refresh();
                this.setPriority(2);
            }

        }
    }

    public void run() {
        while(this.data != null && !this.isDelete) {
            if (this.isWait) {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException var2) {
                    var2.printStackTrace();
                }
            } else {
                try {
                    this.function();
                } catch (Exception var3) {
                    System.out.println("cxcw!");
                }
            }
        }

    }
}
