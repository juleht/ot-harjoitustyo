package budget.ui;

import budget.dao.Database;
import budget.dao.EventDao;
import budget.dao.UserDao;
import budget.domain.BudgetLogic;
import budget.domain.Event;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {

    private Database database;
    private EventDao eventDao;
    private UserDao userDao;
    private BudgetLogic budgetLogic;
    private VBox eventsNODET;

    @Override
    public void init() throws SQLException {
        this.database = createDB();
        this.eventDao = new EventDao(database);
        this.userDao = new UserDao(database);
        this.budgetLogic = new BudgetLogic(userDao, eventDao);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //
        // log in scene
        // components
        // 
        Label usernameLogInLabel = new Label("Enter username: ");
        TextField usernameLoginTextField = new TextField();
        Label usernameLogInInfo = new Label();

        Button logInButton = new Button("log in");
        Button createUsernameButton = new Button("create username");
        Button endProgram = new Button("end");

        // Layout
        GridPane layoutLogIn = new GridPane();
        layoutLogIn.add(usernameLogInLabel, 0, 0);
        layoutLogIn.add(usernameLoginTextField, 1, 0);
        layoutLogIn.add(usernameLogInInfo, 0, 1, 2, 1);
        layoutLogIn.add(logInButton, 0, 2);
        layoutLogIn.add(createUsernameButton, 1, 2);
        layoutLogIn.add(endProgram, 0, 3);

        // Style
        layoutLogIn.setPrefSize(300, 180);
        layoutLogIn.setVgap(10);
        layoutLogIn.setHgap(10);
        layoutLogIn.setPadding(new Insets(10));

        // Show logInscene at start
        Scene logInScene = new Scene(layoutLogIn);

        primaryStage.setScene(logInScene);
        primaryStage.show();

        //
        // create user Scene
        // components
        //
        Label createUsernameLabel = new Label("Enter username: ");
        TextField createUsernameField = new TextField();
        Label createUsernameInfo = new Label();
        Button createUserButton = new Button("create username");
        Button returnLogInScene = new Button("return log in");

        // Layout
        GridPane layoutCreateUsername = new GridPane();
        layoutCreateUsername.add(createUsernameLabel, 0, 0);
        layoutCreateUsername.add(createUsernameField, 1, 0);
        layoutCreateUsername.add(createUsernameInfo, 0, 1, 2, 1);
        layoutCreateUsername.add(createUserButton, 0, 2);
        layoutCreateUsername.add(returnLogInScene, 1, 2);

        // Style
        layoutCreateUsername.setPrefSize(300, 180);
        layoutCreateUsername.setVgap(10);
        layoutCreateUsername.setHgap(10);
        layoutCreateUsername.setPadding(new Insets(10));

        // Scene
        Scene createUsernameScene = new Scene(layoutCreateUsername);

        //
        // main Scene
        // components
        //
        Label expences = new Label();
        Label balance = new Label();
        Label incomes = new Label();
        TextField amount = new TextField("amount");
        TextField eventtype = new TextField("event type");
        TextField date = new TextField("Date (yyyy-MM-dd)");
        Label eventInfo = new Label();
        Button logOut = new Button("Log out");
        Button statistics = new Button("Statistics");
        Button events = new Button("Events");
        Button addIncomeOrExpence = new Button("Add income or expence");

        // Layout
        GridPane mainLayout = new GridPane();
        mainLayout.add(events, 1, 0);
        mainLayout.add(statistics, 2, 0);
        mainLayout.add(logOut, 3, 0);
        mainLayout.add(expences, 0, 1);
        mainLayout.add(balance, 1, 1, 2, 1);
        mainLayout.add(incomes, 3, 1);
        mainLayout.add(eventInfo, 0, 2, 4, 1);
        mainLayout.add(amount, 0, 3);
        mainLayout.add(eventtype, 1, 3);
        mainLayout.add(date, 2, 3);
        mainLayout.add(addIncomeOrExpence, 3, 3);

        // Style
        mainLayout.setVgap(10);
        mainLayout.setHgap(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setPrefSize(550, 350);
        mainLayout.setAlignment(Pos.CENTER);

        // Scene
        Scene headScene = new Scene(mainLayout);

        //
        // Events scene
        // components
        //
        Button returnToMainButton = new Button("return to main");
        VBox returnToMainButtonLayout = new VBox();
        ScrollPane userEventsListLayout = new ScrollPane();
        BorderPane eventsLayoutMain = new BorderPane();
        eventsNODET = new VBox();
        returnToMainButtonLayout.getChildren().add(returnToMainButton);
        userEventsListLayout.setContent(eventsNODET);
        eventsLayoutMain.setCenter(userEventsListLayout);
        eventsLayoutMain.setTop(returnToMainButtonLayout);
        eventsLayoutMain.setPrefSize(550, 350);
        // Scene

        Scene eventsScene = new Scene(eventsLayoutMain);

        //
        // Button actions
        //
        // log in scene
        //
        // log in to program
        logInButton.setOnAction((event) -> {
            try {
                if (this.budgetLogic.logOn(usernameLoginTextField.getText()) != null) {
                    balance.setText(String.valueOf(this.budgetLogic.balance()));
                    expences.setText(String.valueOf(this.budgetLogic.expenses()));
                    incomes.setText(String.valueOf(this.budgetLogic.incomes()));
                    usernameLoginTextField.clear();
                    usernameLogInInfo.setText("");
                    primaryStage.setScene(headScene);
                } else {
                    usernameLogInInfo.setText("username wrong or user does not exist");
                    usernameLoginTextField.clear();
                }
            } catch (SQLException ex) {
            }
        });
        // end program
        endProgram.setOnAction((event) -> {
            Platform.exit();
        });
        // go to create username scene
        createUsernameButton.setOnAction((event) -> {
            usernameLogInInfo.setText("");
            usernameLoginTextField.clear();
            primaryStage.setScene(createUsernameScene);
        });
        //
        // Button actions
        //
        // create username scene
        //
        // create username
        createUserButton.setOnAction((event) -> {
            try {
                if (this.budgetLogic.createUser(createUsernameField.getText()) == false) {
                    createUsernameInfo.setText("username already exist try again or username length is under 3 character ");
                    createUsernameField.clear();
                } else {
                    createUsernameField.clear();
                    primaryStage.setScene(logInScene);
                }
            } catch (SQLException ex) {

            }
        });
        // return to log in scene
        returnLogInScene.setOnAction((event) -> {
            createUsernameField.clear();
            createUsernameInfo.setText("");
            primaryStage.setScene(logInScene);
        });
        //
        // Button actions
        //
        // main scene
        //
        // log out from program
        logOut.setOnAction((event) -> {
            primaryStage.setScene(logInScene);
        });
        // add new income or expence
        addIncomeOrExpence.setOnAction((event) -> {
            try {
                this.budgetLogic.createEvent(Double.valueOf(amount.getText()), eventtype.getText(), LocalDate.parse(date.getText()));
                balance.setText(String.valueOf(this.budgetLogic.balance()));
                expences.setText(String.valueOf(this.budgetLogic.expenses()));
                incomes.setText(String.valueOf(this.budgetLogic.incomes()));
                amount.setText("amount");
                eventtype.setText("event type");
                date.setText("Date (yyyy-MM-dd)");
            } catch (SQLException ex) {

            } catch (NumberFormatException e) {
                eventInfo.setText("amount incorrect should be integer or real example 1.1");
            } catch (DateTimeParseException e) {
                eventInfo.setText("Date format should be yyyy-MM-dd example 1000-01-01");
            }

        });
        // move to events scene
        events.setOnAction((event) -> {
            try {
                listEvents();
            } catch (SQLException ex) {

            }
            primaryStage.setScene(eventsScene);
        });

        // move to main scene
        returnToMainButton.setOnAction((event) -> {
            primaryStage.setScene(headScene);
        });
    }

    private Node eventsList(Event e) {
        VBox layout = new VBox();
        HBox insidelayout = new HBox();
        Label label = new Label(e.toString());
        Button button = new Button("delete");

        button.setOnAction((event) -> {

            try {
                this.budgetLogic.deleteEvent(e);
                listEvents();
            } catch (SQLException ex) {

            } catch (NullPointerException nullerror) {

            }

        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        layout.setSpacing(10);
        insidelayout.setSpacing(10);
        layout.setPadding(new Insets(10));
        insidelayout.setPadding(new Insets(10));

        insidelayout.getChildren().addAll(label, spacer, button);
        layout.getChildren().add(insidelayout);

        return layout;
    }

    private Database createDB() throws SQLException {
        String databaseName = "budgetDB";
        Database db = new Database(databaseName);
        db.init();
        return db;
    }

    private void listEvents() throws SQLException {
        eventsNODET.getChildren().clear();
        List<Event> eventlist = this.budgetLogic.listEvents();
        for (Event e : eventlist) {
            eventsNODET.getChildren().add(eventsList(e));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
