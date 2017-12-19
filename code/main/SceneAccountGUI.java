
package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.swing.text.StyledEditorKit;
import java.util.Optional;

/**
 * The SceneAccountGUI program creates a screen for all accounts in which user can modify, delete, add accounts
 *
 * @author Group 3
 * @version 1.1
 * @since 2017-10-12
 */

public class SceneAccountGUI extends Application {

    private User user = new User("User", "Password");
    private ObservableList<Account> accountList = FXCollections.observableArrayList();
    private TextField userTextField;
    private TextField appTextField;
    private PasswordField passwordField;
    private CheckBox autoPassword;
    private CheckBox selfPassword;
    private TextField passwordLengthTextField;
    private Button addButton = new Button("Add Account");
    private Button modifyButton = new Button ("Modify Account");
    private Button deleteButton = new Button ("Delete Account");
    private VBox addBox = new VBox();
    private Label deleteError = new Label();
    private VBox deleteBox = new VBox();
    private Label modifyError = new Label();
    private VBox modifyBox = new VBox();
    private Label addError = new Label();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SplitPane root = new SplitPane();
        VBox left = new VBox();
        VBox right = new VBox();
        autoPassword = new CheckBox();
        selfPassword = new CheckBox();
        root.getItems().addAll(left, right);
        root.setDividerPosition(0, 0.45);

        Scene scene = new Scene(root, 800, 800);

        right.setSpacing(5);
        right.setPadding(new Insets(10, 10, 10, 10));
        left.setSpacing(5);
        left.setPadding(new Insets(10, 10, 10, 10));

        createButton(addButton, addError, addBox);
        createButton(deleteButton, deleteError, deleteBox);
        createButton(modifyButton, modifyError, modifyBox);

        Label userName = new Label("User Name:");
        userName.setFont(Font.font(null, FontWeight.BOLD, 15));
        userTextField = new TextField();

        autoPassword.setText("Automatically generate password");
        selfPassword.setText("Set password yourself");

        Label passwordLengthLabel = new Label("Password Length (must be longer than 8): ");
        passwordLengthTextField = new TextField();
        passwordLengthTextField.setEditable(false);
        passwordLengthTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    passwordLengthTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        Label password = new Label("Password: ");
        password.setFont(Font.font(null, FontWeight.BOLD, 15));
        passwordField = new PasswordField();
        passwordField.setEditable(false);

        Label appName = new Label("Application:");
        appName.setFont(Font.font(null, FontWeight.BOLD, 15));
        appTextField = new TextField();

        VBox vbox = new VBox();
        vbox.setSpacing(20);

        vbox.getChildren().addAll(userName, userTextField, password, autoPassword, passwordLengthLabel, passwordLengthTextField, selfPassword, passwordField, appName, appTextField, addBox, deleteBox, modifyBox);

        left.getChildren().addAll(vbox);

        TableView<Account> tvAccount = new TableView<>(accountList);
        TableColumn<Account, String> uName = new TableColumn<>("User Name");
        TableColumn<Account, String> pw = new TableColumn<>("Password");
        TableColumn<Account, String> aName = new TableColumn<>("Application name");

        uName.setCellValueFactory(new PropertyValueFactory<>("Username"));
        pw.setCellValueFactory(new PropertyValueFactory<>("password"));
        aName.setCellValueFactory(new PropertyValueFactory<>("Appname"));

        setUpTableViewAccount(tvAccount, uName, pw, aName);


        right.getChildren().addAll(tvAccount);

        user.getPlainAccounts().forEach(account -> {
            accountList.add(account);
        });

        primaryStage.setTitle("Password Protector");
        primaryStage.setScene(scene);
        primaryStage.show();

        setOnActionForButtons(tvAccount);
    }

    private void setOnActionForButtons(TableView<Account> tvAccount) {
        selfPassword.setOnAction(event ->
        {
            activateSelfPassword();
        });

        autoPassword.setOnAction(event ->
        {
            activateAutoPassword();
        });

        addButton.setOnAction(event -> {
            addAccountAction(autoPassword, selfPassword, addError, deleteError, modifyError, userTextField, passwordField, appTextField);
        });

        deleteButton.setOnAction(event -> {
            deleteAccountAction(addError, deleteError, modifyError, userTextField, passwordField, appTextField, tvAccount);
        });

        modifyButton.setOnAction(event -> {
            modifyAccountAction(autoPassword, selfPassword, addError, deleteError, modifyError, userTextField, passwordField, appTextField, tvAccount);
        });

        tvAccount.setOnMouseClicked(event -> {
            selectOneTableItem(tvAccount);
        });
    }

    private void addAccountAction(CheckBox autoPassword, CheckBox selfPassword, Label addDuplicate, Label deleteErr, Label modifyErr, TextField userTextField, PasswordField pwBox, TextField appTextField) {
        deleteErrorMessage();
        Account acc = createAccount(autoPassword, selfPassword);
        if (user.addAccount(acc)) {
            accountList.add(acc);
            addDuplicate.setText("");
        } else {
            addDuplicate.setText ("Fail to add this invalid or duplicate account.");
        }
    }

    private void selectOneTableItem(TableView<Account> tvAccount) {
        deleteErrorMessage();
        Account selectedItem = tvAccount.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            userTextField.setText(selectedItem.getUsername());
            passwordField.setText(selectedItem.getPassword());
            appTextField.setText(selectedItem.getAppname());
        }
    }

    private void activateAutoPassword() {
        if (autoPassword.isSelected()) {
            passwordField.setEditable(false);
            passwordField.setText("");
            selfPassword.setSelected(false);
            passwordLengthTextField.setEditable(true);
        }
    }

    private void activateSelfPassword() {
        if (selfPassword.isSelected()) {
            passwordField.setEditable(true);
            autoPassword.setSelected(false);
        }
    }

    private void setUpTableViewAccount(TableView<Account> tvAccount, TableColumn<Account, String> uName, TableColumn<Account, String> pw, TableColumn<Account, String> aName) {
        tvAccount.getColumns().addAll(uName, pw, aName);
        tvAccount.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tvAccount.setPrefWidth(300);
        tvAccount.setPrefHeight(700);
    }

    private void inputToVoid(TextField userName, TextField pw, TextField appName) {
        userName.setText("");
        pw.setText("");
        appName.setText("");
        autoPassword.setSelected(false);
        selfPassword.setSelected(false);
    }


    private void modifyAccountAction(CheckBox autoPw, CheckBox selfPw, Label addDuplicate, Label deleteErr, Label modifyErr, TextField userTextField, PasswordField pwBox, TextField appTextField, TableView<Account> tvAccount) {
        deleteErrorMessage();
        selfPw.setSelected(true);
            Account modifiedItem  = tvAccount.getSelectionModel().getSelectedItem();
            if (modifiedItem != null) {
                Account newItem = createAccount(autoPw, selfPw);
                if (user.modifyAccount(modifiedItem.getId(), newItem)) {
                    if (alertMessage("modify")) {
                        tvAccount.getItems().set(accountList.indexOf(modifiedItem), newItem);
                        modifyErr.setText("");
                    }
                }
                else {
                    modifyErr.setText("You cannot modify this entry with a duplicated or blank account. If you omit the password options, the account is considered blank.");
                    modifyErr.setWrapText(true);
                }
            }
            else
                modifyErr.setText("Please choose an account you want to modify.");
            tvAccount.getSelectionModel().clearSelection();
            inputToVoid(userTextField,pwBox,appTextField);
            addDuplicate.setText("");
            deleteErr.setText("");
    }

    private void deleteAccountAction(Label addDuplicate, Label deleteErr, Label modifyErr, TextField userTextField, PasswordField pwBox, TextField appTextField, TableView<Account> tvAccount) {
        deleteErrorMessage();
        Account deletedItem = tvAccount.getSelectionModel().getSelectedItem();
        if (deletedItem != null) {
            if (alertMessage("delete")) {
                user.deleteAccount(deletedItem.getId());
                tvAccount.getItems().remove(deletedItem);
                deleteErr.setText("");
            }
        } else
            deleteErr.setText("Please choose an account you want to delete.");
        tvAccount.getSelectionModel().clearSelection();
        inputToVoid(userTextField, pwBox, appTextField);
        modifyErr.setText("");
        addDuplicate.setText("");
    }

    private Account createAccount(CheckBox autoPassword, CheckBox selfPassword) {
        Account newItem = new Account("", "", "");
        if (autoPassword.isSelected()) {
            String passwordLengthInput = passwordLengthTextField.getText();
            int passwordLength = Integer.parseInt(passwordLengthInput);
            if (passwordLength >= 8) {
                newItem = new Account(userTextField.getText().trim(), passwordLength, appTextField.getText().trim());
            }
        }
        else if (selfPassword.isSelected()) {
            newItem = new Account(userTextField.getText().trim(), passwordField.getText().trim(), appTextField.getText().trim());
        }
        return newItem;
    }


    private static void createButton(Button button, Label label, VBox vBox) {
        button.setPrefSize(200, 20);
        vBox.getChildren().addAll(button, label);
    }

    private boolean alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to permanently " + message + " this account?");
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }

    private void deleteErrorMessage () {
        addError.setText("");
        modifyError.setText("");
        deleteError.setText("");
    }


}