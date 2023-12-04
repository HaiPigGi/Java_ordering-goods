package gui.user;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserOrderGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Order");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(18);
        grid.setHgap(70);

        // Table to display items
        TableView<Item> itemTable = createItemTable();
        GridPane.setConstraints(itemTable, 0, 0, 2, 1);

        // Button for placing an order
        Button orderButton = new Button("Place Order");
        GridPane.setConstraints(orderButton, 1, 1);
        orderButton.setOnAction(e -> placeOrder(itemTable.getSelectionModel().getSelectedItem()));

        grid.getChildren().addAll(itemTable, orderButton);

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private TableView<Item> createItemTable() {
        TableView<Item> table = new TableView<>();
        ObservableList<Item> items = FXCollections.observableArrayList(
                new Item("Laptop", 1200),
                new Item("Smartphone", 800),
                new Item("Headphones", 150),
                new Item("Camera", 1000)
                // Add more items as needed
        );

        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.setItems(items);
        table.getColumns().addAll(nameColumn, priceColumn);

        // Set a row factory to enable click events on the table rows
        table.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    // Double-click action (you can customize this)
                    placeOrder(row.getItem());
                }
            });
            return row;
        });
        return table;
    }

    private void placeOrder(Item selectedItem) {
        if (selectedItem != null) {
            // Perform the order processing logic here
            System.out.println("Item ordered: " + selectedItem.getName() + ", Price: $" + selectedItem.getPrice());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Item Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to order.");
            alert.showAndWait();
        }
    }

    // Simple Item class for demonstration purposes
    public static class Item {
        private final String name;
        private final double price;

        public Item(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }
}
