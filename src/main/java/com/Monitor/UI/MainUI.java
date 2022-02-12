package com.Monitor.UI;

import com.Monitor.DisplayData;
import com.Monitor.RedisUnit;
import java.util.LinkedList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainUI extends Application {
    static volatile LinkedList<DisplayData> displayData = new LinkedList();

    public MainUI() {
    }

    public void startApplication() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("产品监控应用");
        primaryStage.setWidth(1500.0D);
        primaryStage.setHeight(800.0D);
        TableView tableView = com.Monitor.UI.TableView.buildTable();
        Separator separator1 = new Separator(Orientation.VERTICAL);
        separator1.setStyle("-fx-background-color: black;");
        Label label1 = new Label("全局设置");
        Label label2 = new Label("线程数量");
        TextField text = TextUI.getText();
        Label label3 = new Label("单位：个");
        HBox hBox1 = new HBox(20.0D, new Node[]{label2, text, label3});
        VBox vBox1 = new VBox(new Node[]{label1, ButtonUI.getButton(), hBox1});
        vBox1.setSpacing(100.0D);
        Button button2 = ButtonUI.getButton2();
        Button button3 = ButtonUI.getButton3();
        Button button4 = ButtonUI.getButton4();
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setStyle("-fx-background-color: black;");
        HBox hBox2 = new HBox(30.0D, new Node[]{button2, button3, button4});
        VBox vBox2 = new VBox(30.0D, new Node[]{vBox1, hBox2, separator2});
        Label label4 = new Label("添加编号");
        TextField text1 = TextUI.getText1();
        Button button5 = ButtonUI.getButton5();
        HBox hBox3 = new HBox(20.0D, new Node[]{label4, text1, button5});
        Label label5 = new Label("删除编号");
        TextField text2 = TextUI.getText2();
        Button button6 = ButtonUI.getButton6();
        HBox hBox4 = new HBox(20.0D, new Node[]{label5, text2, button6});
        VBox vBox3 = new VBox(30.0D, new Node[]{hBox3, hBox4});
        VBox vBox4 = new VBox(100.0D, new Node[]{vBox2, vBox3, ButtonUI.getButton7(primaryStage)});
        Separator separator3 = new Separator(Orientation.VERTICAL);
        separator3.setStyle("-fx-background-color: black;");
        ListView view = TextUI.getListView();
        HBox box = new HBox(new Node[]{tableView, separator1, vBox4, separator3, view});
        box.setSpacing(10.0D);
        box.setMinHeight(800.0D);
        box.setMaxWidth(1500.0D);
        Scene scene = new Scene(box);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] argv) {
        Application.launch(argv);
    }

    public void start(final Stage primaryStage) {
        Label accountLabel = new Label("账号：");
        Label passLabel = new Label("密码：");
        final TextField acountText = new TextField();
        final TextField passText = new TextField();
        Button login = new Button("登录");
        Boolean isLoin = false;
        login.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String account = acountText.getText();
                String password = passText.getText();
                if (account != null && password != null) {
                    String reAccount = RedisUnit.get("account");
                    String rePassword = RedisUnit.get("password");
                    if (reAccount.equals(account) && rePassword.equals(password)) {
                        MainUI.this.startApplication();
                        primaryStage.close();
                    } else {
                        PopWindows.alert("账号密码错误！");
                    }

                } else {
                    PopWindows.alert("账号密码错误");
                }
            }
        });
        HBox hBox1 = new HBox(new Node[]{accountLabel, acountText});
        HBox hBox2 = new HBox(new Node[]{passLabel, passText});
        VBox vBox = new VBox(50.0D, new Node[]{hBox1, hBox2, login});
        Scene scene = new Scene(vBox);
        primaryStage.setMinWidth(600.0D);
        primaryStage.setMinHeight(350.0D);
        primaryStage.setScene(scene);
        primaryStage.show();
        (new RedisUnit()).start();
    }
}
