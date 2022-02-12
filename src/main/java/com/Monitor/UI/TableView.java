package com.Monitor.UI;


import com.Monitor.DisplayData;
import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class TableView {
    private static javafx.scene.control.TableView tableView = new javafx.scene.control.TableView();

    public TableView() {
    }

    public static javafx.scene.control.TableView buildTable() {
        tableView.setEditable(true);
        TableColumn<DisplayData, String> column1 = new TableColumn("产品编号");
        column1.setCellValueFactory(new PropertyValueFactory("number"));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setMinWidth(200.0D);
        column1.setEditable(true);
        TableColumn<Object, Object> column2 = new TableColumn("库存数量");
        column2.setCellValueFactory(new PropertyValueFactory("store"));
        column2.setMinWidth(50.0D);
        TableColumn<Object, Object> column3 = new TableColumn("监控时间");
        column3.setCellValueFactory(new PropertyValueFactory("time"));
        column3.setMinWidth(200.0D);
        TableColumn<Object, Object> column4 = new TableColumn("选择监控");
        column4.setCellValueFactory(new PropertyValueFactory("checkBox"));
        column4.setMinWidth(50.0D);
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.setMinHeight(750.0D);
        tableView.setMinWidth(470.0D);
        return tableView;
    }

    public static void addItems(ArrayList<DisplayData> displayData) {
        for(int i = 0; i < displayData.size(); ++i) {
            tableView.getItems().addAll(new Object[]{displayData.get(i)});
            tableView.refresh();
        }

    }

    public static void addItem(DisplayData data) {
        tableView.getItems().add(data);
        tableView.refresh();
    }

    public static void removeItem(DisplayData data) {
        tableView.getItems().remove(data);
        tableView.refresh();
    }

    public static void refresh() {
        tableView.refresh();
    }
}
