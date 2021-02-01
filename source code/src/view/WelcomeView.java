package view;

import controller.WelcomeController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Observable;

public class WelcomeView extends Application implements View {
    WelcomeController welcomeController;
    Stage welcomeWindow;
    public WelcomeView(WelcomeController welcomeController){
        this.welcomeController = welcomeController;
        this.draw();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        welcomeWindow = primaryStage;

        VBox layout = new VBox();
        layout.setPadding(new Insets(20, 20, 20, 20));

        Text welcomeText = new Text("Welcome Epidemic Simulation");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24);
        welcomeText.setFont(font);
        layout.getChildren().add(welcomeText);
        VBox.setMargin(welcomeText, new Insets(0, 0, 30, 0));

        // consantRlabel
        Label consantRlabel = new Label("Please enter the constant spreading factor R in [0.5, 1.0] : ");
        layout.getChildren().add(consantRlabel);
        VBox.setMargin(consantRlabel, new Insets(0, 0, 5, 0));

        TextField consantRlabelInput = new TextField();
        layout.getChildren().add(consantRlabelInput);
        VBox.setMargin(consantRlabelInput, new Insets(0, 0, 20, 0));

        // consantZlabel
        Label consantZlabel = new Label("Please enter constant mortality rate Z in [0.1, 0.9] : ");
        layout.getChildren().add(consantZlabel);
        VBox.setMargin(consantZlabel, new Insets(0, 0, 5, 0));

        TextField consantZlabelInput = new TextField();
        layout.getChildren().add(consantZlabelInput);
        VBox.setMargin(consantZlabelInput, new Insets(0, 0, 20, 0));

        Button button = new Button("Start Simulation");
        button.setStyle("-fx-background-color: #3e5092");
        button.setTextFill(Color.WHITE);
        layout.getChildren().add(button);

        button.setOnAction(event -> {
            welcomeController.startSimulationHandler(consantRlabelInput.getText(), consantZlabelInput.getText());
        });

        primaryStage.setTitle("Epidemic Simulation");
        primaryStage.setScene(new Scene(layout));
        primaryStage.show();
    }

    public void close(){
        welcomeWindow.close();
    }

    @Override
    public void draw() {
        try {
            this.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.draw();

    }
}
