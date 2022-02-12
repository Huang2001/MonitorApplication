package com.Monitor.UI;


import com.Monitor.DisplayData;
import com.Monitor.GetMessageThread;
import com.Monitor.ExcelInput.ExcelLinstener;
import com.Monitor.ExcelInput.ExcelUnit;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ButtonUI {
    private static Button button1 = null;
    private static Button button2 = null;
    private static Button button3 = null;
    private static Button button4 = null;
    private static Button button5 = null;
    private static Button button6 = null;
    private static Button button7 = null;
    private static Button button = null;
    private static volatile Integer threadSum = 0;
    private static LinkedList<GetMessageThread> threads = new LinkedList();

    public ButtonUI() {
    }

    public static Integer getThreadSum() {
        return threadSum;
    }

    private static GetMessageThread getThreadByNum(String number) {
        Iterator var1 = threads.iterator();

        GetMessageThread thread;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            thread = (GetMessageThread)var1.next();
        } while(!thread.getNumber().equals(number));

        return thread;
    }

    public static Button getButton() {
        button = new Button("全选");
        button.setOnAction(new EventHandler() {
            public void handle(Event event) {
                Iterator var2 = ButtonUI.threads.iterator();

                while(var2.hasNext()) {
                    GetMessageThread thread = (GetMessageThread)var2.next();
                    thread.getData().getCheckBox().setSelected(true);
                }

            }
        });
        return button;
    }

    public static Button getButton2() {
        button2 = new Button("单次监控");
        button2.setOnAction(new EventHandler() {
            public void handle(Event event) {
                PopWindows.alert("执行单次监控！");
            }
        });
        return button2;
    }

    public static Button getButton3() {
        button3 = new Button("连续监控");
        button3.setOnAction(new EventHandler() {
            public void handle(Event event) {
                Iterator var2 = ButtonUI.threads.iterator();

                while(var2.hasNext()) {
                    GetMessageThread thread = (GetMessageThread)var2.next();
                    thread.setWait(false);
                }

                PopWindows.alert("执行连续监控");
            }
        });
        return button3;
    }

    public static Button getButton4() {
        button4 = new Button("停止监控");
        button4.setOnAction(new EventHandler() {
            public void handle(Event event) {
                Iterator var2 = ButtonUI.threads.iterator();

                while(var2.hasNext()) {
                    GetMessageThread thread = (GetMessageThread)var2.next();
                    thread.getData().getCheckBox().setSelected(false);
                }

                PopWindows.alert("执行停止监控！");
            }
        });
        return button4;
    }

    public static Button getButton5() {
        button5 = new Button("提交");
        button5.setOnAction(new EventHandler() {
            private volatile DisplayData data;

            public void handle(Event event) {
                this.data = new DisplayData(TextUI.getAddNumber());
                TableView.addItem(this.data);
                Integer var3 = ButtonUI.threadSum;
                Integer var4 = ButtonUI.threadSum = ButtonUI.threadSum + 1;
                GetMessageThread thread = new GetMessageThread(var3, this.data);
                ButtonUI.threads.addLast(thread);
                thread.start();
                PopWindows.alert("提交编号：" + TextUI.getAddNumber());
            }
        });
        return button5;
    }

    public static Button getButton6() {
        button6 = new Button("提交");
        button6.setOnAction(new EventHandler() {
            public void handle(Event event) {
                String number = TextUI.getRemNumber();
                GetMessageThread thread = ButtonUI.getThreadByNum(number);
                if (thread != null) {
                    TableView.removeItem(thread.getData());
                    thread.setDelete(true);
                    ButtonUI.threads.remove(thread);
                    Integer var4 = ButtonUI.threadSum;
                    Integer var5 = ButtonUI.threadSum = ButtonUI.threadSum - 1;
                } else {
                    PopWindows.alert("不存在" + number + "编号");
                }

            }
        });
        return button6;
    }

    public static Button getButton7(final Stage stage) {
        button7 = new Button("Excel文件批量导入");
        button7.setOnAction(new EventHandler() {
            public void handle(Event event) {
                File f = ChooseFil.getFileChoose().showOpenDialog(stage);
                if (f != null) {
                    ExcelUnit.read(f);
                    LinkedList numberList = ExcelLinstener.getList();

                    while(numberList.size() != 0) {
                        String number = (String)numberList.pollLast();
                        DisplayData data = new DisplayData(number);
                        TableView.addItem(data);
                        Integer var7 = ButtonUI.threadSum;
                        Integer var8 = ButtonUI.threadSum = ButtonUI.threadSum + 1;
                        GetMessageThread thread = new GetMessageThread(var7, data);
                        ButtonUI.threads.addLast(thread);
                        thread.start();
                    }
                } else {
                    PopWindows.alert("所选文件为空！");
                }

            }
        });
        return button7;
    }
}
