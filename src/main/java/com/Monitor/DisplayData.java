package com.Monitor;

import javafx.scene.control.CheckBox;

public class DisplayData {
    private String number;
    private volatile String store = "0";
    private String time = "暂无";
    private String preNumber = "null";
    private CheckBox checkBox;

    public DisplayData(String number) {
        this.number = number;
        this.checkBox = new CheckBox();
    }

    public DisplayData(String number, String store, String time) {
        this.number = number;
        this.store = store;
        this.time = time;
        this.checkBox = new CheckBox();
    }

    public String getPreNumber() {
        return this.preNumber;
    }

    public void setPreNumber(String preNumber) {
        this.preNumber = preNumber;
    }

    public boolean isMonitor() {
        return this.checkBox.isSelected();
    }

    public CheckBox getCheckBox() {
        return this.checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStore() {
        return this.store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
