package view;

import controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Model;

import java.util.ArrayList;
import java.util.Observable;

public class StatisticView implements View{
    MainController mainController;
    VBox layout;

    Text totalPopulation;
    Text totalInfected;
    Text totalHealthy;
    Text totalHospitalized;
    Text totalDead;

    public StatisticView(MainController mainController, VBox layout){
        this.mainController = mainController;
        this.layout = layout;

        layout.setPadding(new Insets(10, 10, 10, 10));

        totalPopulation = new Text("0");
        totalInfected = new Text("0");
        totalHealthy = new Text("0");
        totalHospitalized = new Text("0");
        totalDead = new Text("0");

        Text titleText = new Text("Statistics");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24);
        titleText.setFont(font);
        layout.getChildren().add(titleText);
        VBox.setMargin(titleText, new Insets(0, 0, 20, 5));

        // title
        Text totalPopulationText = new Text("Number of total population:");
        layout.getChildren().add(totalPopulationText);
        VBox.setMargin(totalPopulationText, new Insets(0, 0, 5, 0));
        // value
        layout.getChildren().add(totalPopulation);
        VBox.setMargin(totalPopulation, new Insets(0, 0, 20, 0));

        // title
        Text totalInfectedText = new Text("Number of total infected:");
        layout.getChildren().add(totalInfectedText);
        VBox.setMargin(totalInfectedText, new Insets(0, 0, 5, 0));
        // value
        layout.getChildren().add(totalInfected);
        VBox.setMargin(totalInfected, new Insets(0, 0, 20, 0));

        // title
        Text totalHealthyText = new Text("Number of total healthy:");
        layout.getChildren().add(totalHealthyText);
        VBox.setMargin(totalHealthyText, new Insets(0, 0, 5, 0));
        // value
        layout.getChildren().add(totalHealthy);
        VBox.setMargin(totalHealthy, new Insets(0, 0, 20, 0));

        // title
        Text totalHospitalizedText = new Text("Number of total hospitalized:");
        layout.getChildren().add(totalHospitalizedText);
        VBox.setMargin(totalHospitalizedText, new Insets(0, 0, 5, 0));
        // value
        layout.getChildren().add(totalHospitalized);
        VBox.setMargin(totalHospitalized, new Insets(0, 0, 20, 0));

        // title
        Text totalDeadText = new Text("Number of total dead:");
        layout.getChildren().add(totalDeadText);
        VBox.setMargin(totalDeadText, new Insets(0, 0, 5, 0));
        // value
        layout.getChildren().add(totalDead);
        VBox.setMargin(totalDead, new Insets(0, 0, 20, 0));

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 0));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        // SPEED
        Label speedLabel = new Label("Speed in [1,500] : ");
        GridPane.setConstraints(speedLabel, 0, 0);

        TextField speedInput = new TextField();
        GridPane.setConstraints(speedInput, 1, 0);

        // boxes
        // blue box
        Rectangle boxBlue = new Rectangle(24, 24, Color.BLUE);
        GridPane.setConstraints(boxBlue, 0, 0);
        Text textBlue = new Text("   HEALTHY");
        GridPane.setConstraints(textBlue, 1, 0);
        // red box

        Rectangle boxRed = new Rectangle(24, 24, Color.RED);
        GridPane.setConstraints(boxRed, 0, 1);
        Text textRed = new Text("   INFECTED");
        GridPane.setConstraints(textRed, 1, 1);

        gridPane.getChildren().addAll(boxBlue, textBlue, boxRed, textRed);

        layout.getChildren().add(gridPane);
    }

    @Override
    public void draw() {
    }

    @Override
    public void update(Observable o, Object arg) {
        int index = (Integer) arg;
        if( index == -1 ){
            Model model = (Model) o;
            ArrayList<String> statistics = model.getStatistics();

            if( statistics.size() == 5 ){
                if( statistics.get(0) != null ){
                    totalPopulation.setText(statistics.get(0));
                }
                if( statistics.get(1) != null ){
                    totalInfected.setText(statistics.get(1));
                }
                if( statistics.get(2) != null ){
                    totalHealthy.setText(statistics.get(2));
                }
                if( statistics.get(3) != null ){
                    totalHospitalized.setText(statistics.get(3));
                }
                if( statistics.get(4) != null ){
                    totalDead.setText(statistics.get(4));
                }
            }
        }
    }
}
