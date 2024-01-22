package bookstore;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javax.swing.*;
import java.io.IOException;

public class Main extends Application {

    private final Owner admin = new Owner();
    private Customer currentCustomer;
    private static final Files files = new Files();

    Button loginButton = new Button("Login");
    Button booksButton = new Button("Books");
    Button customersButton = new Button("Customers");
    Button logoutButton = new Button("Logout");
    Button backButton = new Button("\uD83E\uDC60");
    Button buyButton = new Button("Buy");
    Button pointsBuyButton = new Button("Buy using Points");
    TextField userTextField = new TextField();
    PasswordField passTextField = new PasswordField();
    HBox hb = new HBox();

    TableView<Book> booksTable = new TableView<>();
    final TableView.TableViewFocusModel<Book> defaultFocusModel = booksTable.getFocusModel();
    ObservableList<Book> books = FXCollections.observableArrayList();

    public ObservableList<Book> addBooks(){
        books.addAll(Owner.books);
            return books;
    }

    TableView<Customer> customersTable = new TableView<>();
    ObservableList<Customer> customers = FXCollections.observableArrayList();

    public ObservableList<Customer> addCustomers(){
        customers.addAll(admin.getCustomers());
            return customers;
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Welcome to Bookstore");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(loginScreen(false), 620, 600));
        primaryStage.show();
        System.out.println("Bookstore App was opened");

        try{
            admin.restockArrays();
            System.out.println("Arrays restocked from files");
                }
            catch (IOException e){
                System.out.println("File Importing Error");
                        }

        loginButton.setOnAction(e -> {
            boolean logged_in = false;

            if(userTextField.getText().equals(admin.getUsername()) && passTextField.getText().equals(admin.getUsername())) {
                primaryStage.setScene(new Scene(adminStartScreen(), 620, 600));
                    logged_in = true;
                            }
            for(Customer c: admin.getCustomers()) {
                if (userTextField.getText().equals(c.getUsername()) && passTextField.getText().equals(c.getPassword())) {
                    currentCustomer = c;
                    primaryStage.setScene(new Scene(customerHomeScreen(0), 620, 600));
                        logged_in = true;
                }
            }
            if(!logged_in) {
                primaryStage.setScene(new Scene(loginScreen(true),620, 600));
                }
            }
        );

        logoutButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(loginScreen(false), 620, 600));
            for(Book b: Owner.books){
                b.setSelect(new CheckBox());
                }
            userTextField.clear();
            passTextField.clear();
            }
        );

        booksButton.setOnAction(e -> primaryStage.setScene(new Scene(booksTableScreen(), 620, 600)));
        customersButton.setOnAction(e -> primaryStage.setScene(new Scene(customerTableScreen(), 620, 600)));
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(adminStartScreen(), 620, 600)));

        pointsBuyButton.setOnAction(e -> {
            boolean bookSelected = false;
            for(Book b: Owner.books) {
                if (b.getSelect().isSelected()) {
                    bookSelected = true;
                }
            }
            if(!bookSelected){
                primaryStage.setScene(new Scene(customerHomeScreen(1),620, 600));
                        }
                else if(currentCustomer.getPoints() == 0){
                    primaryStage.setScene(new Scene(customerHomeScreen(2), 620, 600));
                            }
                        else if(currentCustomer.getPoints() != 0){
                            primaryStage.setScene(new Scene(checkoutScreen(true), 620, 500));
                            }
                }
        );

        buyButton.setOnAction(e -> {
            boolean bookSelected = false;
            for(Book b: Owner.books) {
                if (b.getSelect().isSelected()) {
                    bookSelected = true;
                }
            }
            if(bookSelected){
                primaryStage.setScene(new Scene(checkoutScreen(false),620, 600));
                    }
                else
                    primaryStage.setScene(new Scene(customerHomeScreen(1), 620, 600));
            }
        );

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Exited the book store");
            try {
                files.bookFileReset();
                files.customerFileReset();

                System.out.println("Files reset");

                files.bookFileWrite(Owner.books);
                files.customerFileWrite(admin.getCustomers());

                System.out.println("Files updated with current array data");
                        }
                    catch (IOException exception) {
                        exception.printStackTrace();
                                     }
            }
        );
    }

    public Group loginScreen(boolean loginError) {
        Group lis = new Group();
        HBox header = new HBox();

        Label brand = new Label("Bookstore Login");
            brand.setFont(new Font("Century", 45));
            brand.setAlignment(Pos.CENTER);

        header.getChildren().addAll(brand);
            header.setSpacing(5);
            header.setAlignment(Pos.CENTER);

        Image rawLogo = new Image("file:src/kidReading.png");
            ImageView image = new ImageView(rawLogo);
            image.setFitHeight(175);
            image.setFitWidth(175);

        VBox loginBox = new VBox();
            loginBox.setPadding(new Insets(0, 65, 0, 65));
            loginBox.setSpacing(15);
            loginBox.setAlignment(Pos.CENTER);

        userTextField.setMinSize(200, 30);
        passTextField.setMinSize(200, 30);

        Text user = new Text("Username:");
            user.textAlignmentProperty();

        Text pass = new Text("Password:");
            pass.textAlignmentProperty();

        loginButton.setMinWidth(210);

        loginBox.getChildren().addAll(image ,user, userTextField, pass, passTextField, loginButton);

        if(loginError){
            Text errorMsg = new Text("Incorrect username or password.");
            errorMsg.setFill(Color.RED);
            loginBox.getChildren().add(errorMsg);
                        }

        VBox bg = new VBox();
            bg.getChildren().addAll(header, loginBox);
            bg.setStyle("-fx-background-color: #8A87EB");
            bg.setPadding(new Insets(50, 150, 200, 150));
            bg.setSpacing(45);

        lis.getChildren().addAll(bg);

        return lis;
    }

    public Group customerHomeScreen(int type) {
        Group bookstore = new Group();
            booksTable.getItems().clear();
            booksTable.getColumns().clear();
            booksTable.setFocusModel(null);

        Font font = new Font(15);

        Text welcomeMsg = new Text("Welcome, " + currentCustomer.getUsername() + ".");
            welcomeMsg.setFont(font);

        Text status1 = new Text(" Member: ");
            status1.setFont(font);

        Text status2 = new Text(currentCustomer.getStatus());
            status2.setFont(font);

        if (currentCustomer.getStatus().equals("GOLD")) {
            status2.setFill(Color.GOLD);
            }
                else {
                    status2.setFill(Color.SILVER);
                }

        Text points = new Text(" | Points: " + currentCustomer.getPoints());
            points.setFont(font);

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
            titleColumn.setMinWidth(200);
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
            priceColumn.setMinWidth(100);
            priceColumn.setStyle("-fx-alignment: CENTER;");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Book, String> selectColumn = new TableColumn<>("Select");
            selectColumn.setMinWidth(100);
            selectColumn.setStyle("-fx-alignment: CENTER;");
            selectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));

        booksTable.setItems(addBooks());
            booksTable.getColumns().addAll(titleColumn, priceColumn, selectColumn);

        HBox info = new HBox();
            info.getChildren().addAll(status1, status2, points);
            BorderPane header = new BorderPane();
            header.setLeft(welcomeMsg);
            header.setRight(info);

        HBox bottom = new HBox();
            bottom.setAlignment(Pos.BOTTOM_CENTER);
            bottom.setSpacing(10);
            buyButton.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white;");
            pointsBuyButton.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white;");
            logoutButton.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white;");
            bottom.getChildren().addAll(buyButton, pointsBuyButton, logoutButton);

        VBox vbox = new VBox();

        String errorMessage = "";
            if (type == 1) {
                errorMessage = "Please select at least one book.";
                    }
                else if (type == 2) {
                            errorMessage = "You don't have any points.";
                        }

        Text warning = new Text(errorMessage);
            warning.setFill(Color.RED);

        vbox.setStyle("-fx-background-color: #8A87EB;");
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40, 200, 70, 100));
        vbox.getChildren().addAll(header, booksTable, bottom, warning);

        bookstore.getChildren().addAll(vbox);

        return bookstore;
    }

    public Group checkoutScreen(boolean usedPoints){
        Group cos = new Group();

        double total;
        double subtotal = 0;
        double discount;

        int pointsEarned;
        int i = 0;
        int bookCount = 0;

        String[][] booksBought = new String[25][2];

        for(Book b: Owner.books){
            if(b.getSelect().isSelected()){
                subtotal += b.getPrice();
                booksBought[i][0] = b.getTitle();
                booksBought[i][1] = String.valueOf(b.getPrice());
                i++;
            }
        }

        if(usedPoints){
            if((double)currentCustomer.getPoints()/100 >= subtotal){
                discount = subtotal;
                currentCustomer.setPoints(-(int)subtotal*100);
                    }
            else{
                discount = ((double)currentCustomer.getPoints()/100);
                currentCustomer.setPoints(-currentCustomer.getPoints());
                    }
            }
        else discount = 0;

        total = subtotal - discount;
        pointsEarned = (int)total*10;
        currentCustomer.setPoints(pointsEarned);

        HBox header = new HBox();
            header.setAlignment(Pos.CENTER);
            header.setSpacing(15);
            header.setPadding(new Insets(0,0,25,0));
            Label brandName = new Label("Thanks for your purchase!");

        brandName.setFont(new Font("Century", 35));
        brandName.setTextFill(Color.BLACK);

        header.getChildren().addAll(brandName);

        VBox receipt = new VBox();
            receipt.setSpacing(7);
            Text receiptTxt = new Text("Receipt");
            receiptTxt.setFont(Font.font(null, FontWeight.BOLD, 12));
            receiptTxt.setFill(Color.BLACK);

        Line thickLine = new Line(0, 150, 450, 150);
            thickLine.setStrokeWidth(3);

            receipt.getChildren().addAll(receiptTxt, thickLine);

        VBox receiptItems = new VBox();
            receiptItems.setStyle("-fx-background-color: #A4A1ED;");
            receiptItems.setSpacing(7);

        for (i = 0; i<25; i++) {
            if(booksBought[i][0] != null){
                Text bookTitle = new Text(booksBought[i][0]);
                Text bookPrice = new Text(booksBought[i][1]);

                BorderPane item = new BorderPane();
                    item.setLeft(bookTitle);
                    item.setRight(bookPrice);

                Line thinLine = new Line(0, 150, 450, 150);
                receiptItems.getChildren().addAll(item, thinLine);
                bookCount++;
            }
        }

        ScrollPane scrollReceipt = new ScrollPane(receiptItems);
        scrollReceipt.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollReceipt.setStyle("-fx-background-color: transparent;");
        scrollReceipt.setFitToWidth(true);

        if(bookCount<=4){
            scrollReceipt.setFitToHeight(true);
                 }
        else scrollReceipt.setPrefHeight(130);

        Text subtotalTxt = new Text("Subtotal: $" + (Math.round(subtotal*100.0))/100.0);
            subtotalTxt.setFill(Color.BLACK);

        Text totalTxt = new Text("Final Total: $" + (Math.round(total*100.0))/100.0);
            totalTxt.setFont(new Font("Century", 15));
            totalTxt.setFill(Color.BLACK);

        Line thickLine2 = new Line(0, 150, 450, 150);

        double totalDisc = (Math.round(discount*100.0))/100.0;

        thickLine2.setStrokeWidth(3);

        receipt.getChildren().addAll(scrollReceipt, subtotalTxt, totalTxt, thickLine2);


        VBox bottom = new VBox();
            bottom.setSpacing(40);
            bottom.setAlignment(Pos.CENTER);

        Text info = new Text("    You have saved $" + totalDisc + " and collected " + pointsEarned +" points!" + "\n\t Thank you for shopping at the Bookstore!");
        info.setFill(Color.BLACK);

        bottom.getChildren().addAll(info, logoutButton);

        VBox screen = new VBox();
            screen.setStyle("-fx-background-color: #A4A1ED;");
            screen.setPadding(new Insets(60,135,500,100));
            screen.setAlignment(Pos.CENTER);
            screen.setSpacing(10);
            screen.getChildren().addAll(header, receipt, bottom);

        cos.getChildren().addAll(screen);
        Owner.books.removeIf(b -> b.getSelect().isSelected());

        return cos;
    }

    public VBox adminStartScreen() {

        HBox header = new HBox();

        Label option = new Label("Options");
        option.setFont(new Font("Century", 50));
        option.setAlignment(Pos.CENTER);

        header.getChildren().addAll(option);
        header.setSpacing(5);
        header.setAlignment(Pos.CENTER);

        VBox osc = new VBox();
            osc.setStyle("-fx-background-color: #8A87EB;");
            osc.setAlignment(Pos.CENTER);
            osc.setSpacing(100);
            osc.setPadding(new Insets(80, 0, 30, 0));

        HBox buttons = new HBox();
            buttons.setAlignment(Pos.CENTER);
            buttons.setSpacing(40);

        Line vLine = new Line(150, 0, 150, 200);
            vLine.setStroke(Color.WHITE);

        buttons.getChildren().addAll(booksButton, vLine, customersButton);

        booksButton.setPrefSize(200, 200);

        customersButton.setPrefSize(200, 200);

        osc.getChildren().addAll(buttons, logoutButton);

        VBox bg = new VBox();
            bg.getChildren().addAll(header, osc);
            bg.setStyle("-fx-background-color: #8A87EB");
            bg.setPadding(new Insets(50, 0, 0, 0));
            bg.setSpacing(0);

        return bg;
    }

    public Group booksTableScreen() {
        Group bt = new Group();
            hb.getChildren().clear();
            booksTable.getItems().clear();
            booksTable.getColumns().clear();
            booksTable.setFocusModel(defaultFocusModel);

        Label label = new Label("Books");
            label.setFont(new Font("Century", 20));
            label.setTextFill(Color.BLACK);


        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
            titleColumn.setMinWidth(200);
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
            priceColumn.setMinWidth(100);
            priceColumn.setStyle("-fx-alignment: CENTER;");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        booksTable.setItems(addBooks());
        booksTable.getColumns().addAll(titleColumn, priceColumn);

        final TextField addBookTitle = new TextField();
            addBookTitle.setPromptText("Title");
            addBookTitle.setMaxWidth(titleColumn.getPrefWidth());

        final TextField addBookPrice = new TextField();
            addBookPrice.setMaxWidth(priceColumn.getPrefWidth());
            addBookPrice.setPromptText("Price");

            addBookTitle.setStyle("-fx-background-color: #DFDFF5;");
            addBookPrice.setStyle("-fx-background-color: #DFDFF5;");

        VBox core = new VBox();

        final Button addButton = new Button("Add");
            addButton.setStyle("-fx-background-color: #1E90FF;");
            addButton.setTextFill(Color.WHITE);

        Label bookAddErr = new Label("Invalid Input");
            bookAddErr.setTextFill(Color.RED);

        addButton.setOnAction(e -> {
            try {
                double price = Math.round((Double.parseDouble(addBookPrice.getText()))*100);
                Owner.books.add(new Book(addBookTitle.getText(), price/100));

                    booksTable.getItems().clear();
                    booksTable.setItems(addBooks());
                    addBookTitle.clear();
                    addBookPrice.clear();
                    core.getChildren().remove(bookAddErr);

                    }

            catch (Exception exception) {
                if (!core.getChildren().contains(bookAddErr)) {
                    core.getChildren().add(bookAddErr);
                }
            }
        });

        final Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #1E90FF;");
            deleteButton.setTextFill(Color.WHITE);
            deleteButton.setOnAction(e -> {
                Book selectedItem = booksTable.getSelectionModel().getSelectedItem();
                booksTable.getItems().remove(selectedItem);
                Owner.books.remove(selectedItem);
                        });

        hb.getChildren().addAll(addBookTitle, addBookPrice, addButton, deleteButton);
        hb.setSpacing(3);
        hb.setAlignment(Pos.CENTER);

        HBox back = new HBox();
            back.setPadding(new Insets(5));
            back.getChildren().addAll(backButton);
            core.setAlignment(Pos.CENTER);
            core.setSpacing(5);
            core.setPadding(new Insets(0, 0, 0, 150));
            core.getChildren().addAll(label, booksTable, hb);

        VBox vbox = new VBox();
            vbox.setStyle("-fx-background-color: #A4A1ED;");
            vbox.setPadding(new Insets(0, 200, 120, 0));
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(back, core);

        bt.getChildren().addAll(vbox);

        return bt;
    }

    public Group customerTableScreen() {
        Group ct = new Group();
            hb.getChildren().clear();
            customersTable.getItems().clear();
            customersTable.getColumns().clear();

        Label label = new Label("Customers");
        label.setFont(new Font("Century", 20));

        //Customer username column
        TableColumn<Customer, String> usernameCol = new TableColumn<>("Username");
            usernameCol.setMinWidth(140);
            usernameCol.setStyle("-fx-alignment: CENTER;");
            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        //Customer password column
        TableColumn<Customer, String> passwordCol = new TableColumn<>("Password");
            passwordCol.setMinWidth(140);
            passwordCol.setStyle("-fx-alignment: CENTER;");
            passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        //Customer points column
        TableColumn<Customer, Integer> pointsCol = new TableColumn<>("Points");
            pointsCol.setMinWidth(100);
            pointsCol.setStyle("-fx-alignment: CENTER;");
            pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

        customersTable.setItems(addCustomers());
        customersTable.getColumns().addAll(usernameCol, passwordCol, pointsCol);

        final TextField addUsername = new TextField();
            addUsername.setPromptText("Username");
            addUsername.setMaxWidth(usernameCol.getPrefWidth());

        final TextField addPassword = new TextField();
            addPassword.setMaxWidth(passwordCol.getPrefWidth());
            addPassword.setPromptText("Password");

            addPassword.setStyle("-fx-background-color: #DFDFF5;");
            addUsername.setStyle("-fx-background-color: #DFDFF5;");

        VBox core = new VBox();

        Text customerAddErr = new Text("User already in database. Please try again.");
            customerAddErr.setFill(Color.RED);

        final Button addButton = new Button("Add");
            addButton.setStyle("-fx-background-color: #1E90FF;");
            addButton.setTextFill(Color.WHITE);

        addButton.setOnAction(e -> {
            boolean duplicate = false;

            for(Customer c: admin.getCustomers()){
                if((c.getUsername().equals(addUsername.getText()) && c.getPassword().equals(addPassword.getText())) ||
                        (addUsername.getText().equals(admin.getUsername()))){
                    duplicate = true;

                    if(!core.getChildren().contains(customerAddErr)){
                        core.getChildren().add(customerAddErr);
                    }
                }
            }

            if(!(addUsername.getText().equals("") || addPassword.getText().equals("")) && !duplicate) {
                admin.addCustomer(new Customer(addUsername.getText(), addPassword.getText()));
                customersTable.getItems().clear();
                customersTable.setItems(addCustomers());
                core.getChildren().remove(customerAddErr);
                addPassword.clear();
                addUsername.clear();
                }
            }
        );

        final Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #1E90FF;");
            deleteButton.setTextFill(Color.WHITE);

        deleteButton.setOnAction(e -> {
            Customer selectedItem = customersTable.getSelectionModel().getSelectedItem();
            customersTable.getItems().remove(selectedItem);
            admin.deleteCustomer(selectedItem);}
                                            );

        hb.getChildren().addAll(addUsername, addPassword, addButton, deleteButton);
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(3);

        HBox back = new HBox();
            back.setPadding(new Insets(5));
            back.getChildren().addAll(backButton);
            core.setAlignment(Pos.CENTER);
            core.setSpacing(5);
            core.setPadding(new Insets(0,0,0,110));
            core.getChildren().addAll(label, customersTable, hb);

        VBox vbox = new VBox();
            vbox.setStyle("-fx-background-color: #a4a1ed");
            vbox.setPadding(new Insets(0, 150, 120, 0));
            vbox.getChildren().addAll(back, core);
            vbox.setAlignment(Pos.CENTER);

        ct.getChildren().addAll(vbox);

        return ct;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
