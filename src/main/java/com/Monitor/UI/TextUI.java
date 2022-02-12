package com.Monitor.UI;


import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TextUI {
    private static TextField text = null;
    private static TextField text1 = null;
    private static TextField text2 = null;
    private static ListView listView = null;

    public TextUI() {
    }

    public static TextField getText() {
        text = new TextField();
        text.setStyle("-fx-max-height: 8;-fx-max-width: 50");
        return text;
    }

    public static TextField getText1() {
        text1 = new TextField();
        text1.setPrefSize(160.0D, 20.0D);
        return text1;
    }

    public static TextField getText2() {
        text2 = new TextField();
        text2.setPrefSize(160.0D, 20.0D);
        return text2;
    }

    public static ListView getListView() {
        listView = new ListView();
        listView.setPrefWidth(500.0D);
        listView.setEditable(true);
        return listView;
    }

    public static String getInterval() {
        return text.getText();
    }

    public static String getAddNumber() {
        String content = text1.getText();
        if (content.length() == 0) {
            PopWindows.alert("编号不能为空！");
            return null;
        } else {
            return content;
        }
    }

    public static String getRemNumber() {
        String content = text2.getText();
        if (content.length() == 0) {
            PopWindows.alert("编号不能为空！");
            return null;
        } else {
            return content;
        }
    }

    public static void removeLog(final String string) {
        Platform.runLater(new Runnable() {
            public void run() {
                TextUI.listView.getItems().remove(string);
            }
        });
    }

    public static void addLog(final String log, final int index) {
        Platform.runLater(new Runnable() {
            public void run() {
                if (index != -1) {
                    TextUI.listView.getItems().add(index, log);
                } else {
                    TextUI.listView.getItems().add(log);
                }

                if (TextUI.listView.getItems().size() >= 20) {
                    for(int i = 0; i < TextUI.listView.getItems().size(); ++i) {
                        String s = (String)TextUI.listView.getItems().get(i);
                        if (s.contains("已复制")) {
                            TextUI.listView.getItems().remove(s);
                        }
                    }
                }

                TextUI.listView.refresh();
            }
        });
    }
}
