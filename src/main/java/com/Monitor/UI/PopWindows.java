package com.Monitor.UI;


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PopWindows {
    public PopWindows() {
    }

    private static void setClipboardString(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable trans = new StringSelection(text);
        clipboard.setContents(trans, (ClipboardOwner)null);
    }

    public static void newWindow(final String number, final String store) {
        Platform.runLater(new Runnable() {
            public void run() {
                Label label1 = new Label(number);
                label1.setMinWidth(200.0D);
                Button button1 = new Button("复制");
                button1.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        PopWindows.setClipboardString(number);
                    }
                });
                button1.setAlignment(Pos.TOP_RIGHT);
                HBox hBox1 = new HBox(new Node[]{label1, button1});
                Label label2 = new Label(store);
                label2.setMinWidth(200.0D);
                Button button2 = new Button("复制");
                button2.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        PopWindows.setClipboardString(store);
                    }
                });
                button2.setAlignment(Pos.BOTTOM_RIGHT);
                HBox hBox2 = new HBox(new Node[]{label2, button2});
                VBox vBox = new VBox(new Node[]{hBox1, hBox2});
                Scene scene = new Scene(vBox);
                Stage stage = new Stage();
                stage.setMinWidth(300.0D);
                stage.setHeight(150.0D);
                stage.setScene(scene);
                stage.setTitle("提示！");
                stage.show();
            }
        });
    }

    public static void alert(final String message) {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert alert = new Alert(AlertType.CONFIRMATION, message, new ButtonType[0]);
                alert.show();
            }
        });
    }
}
