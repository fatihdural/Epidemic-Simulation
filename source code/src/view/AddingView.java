package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import java.util.Observable;

public class AddingView implements View{
    MainController mainController;
    HBox addingBox;
    GridPane gridPane;

    public AddingView(MainController mainController, HBox addingBox){
        this.mainController = mainController;
        this.addingBox = addingBox;

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        // SPEED
        Label speedLabel = new Label("Speed in [1,500] : ");
        GridPane.setConstraints(speedLabel, 0, 0);

        TextField speedInput = new TextField();
        GridPane.setConstraints(speedInput, 1, 0);

        // MASK
        Label maskLabel = new Label("Mask (0.2 or 1.0) : ");
        GridPane.setConstraints(maskLabel, 0, 1);

        TextField maskInput = new TextField();
        GridPane.setConstraints(maskInput, 1, 1);

        // SOCIAL DISTANCE
        Label distanceLabel = new Label("Social Distance in [0,9] : ");
        GridPane.setConstraints(distanceLabel, 2, 0);

        TextField distanceInput = new TextField();
        GridPane.setConstraints(distanceInput, 3, 0);

        // COLLIDE VALUE
        Label collideLabel = new Label("Collide C in [1,5] : ");
        GridPane.setConstraints(collideLabel, 2, 1);

        TextField collideInput = new TextField();
        GridPane.setConstraints(collideInput, 3, 1);

        // NUMBER OF INDIVIDUAL
        Label numberLabel = new Label("Number of Individual in (1,2,...) : ");
        GridPane.setConstraints(numberLabel, 4, 0);

        TextField numberInput = new TextField();
        GridPane.setConstraints(numberInput, 5, 0);

        Button addPersonButton = new Button("Add Individual(s)");
        addPersonButton.setStyle("-fx-background-color: #3e5092");
        addPersonButton.setTextFill(Color.WHITE);

        // handle add person request from controller
        addPersonButton.setOnAction(event -> {
            mainController.addPersonHandler(
                    speedInput.getText(),
                    maskInput.getText(),
                    distanceInput.getText(),
                    collideInput.getText(),
                    numberInput.getText()
            );
        });
        GridPane.setConstraints(addPersonButton, 4, 1);

        gridPane.getChildren().addAll(speedLabel, speedInput, maskLabel, maskInput,
                distanceLabel, distanceInput, collideLabel, collideInput, numberLabel, numberInput, addPersonButton);

        this.draw();
    }

    @Override
    public void draw() {
        addingBox.getChildren().add(gridPane);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
