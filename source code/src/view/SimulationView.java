package view;

import controller.MainController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Observable;

public class SimulationView extends Application implements View{
    BorderPane root;
    Group menuBar;
    Pane canvas;
    HBox addingIndividual;
    VBox statics;

    MenuView menuView;
    CanvasView canvasView;
    AddingView addingView;
    StatisticView statisticView;

    MainController mainController;

    public SimulationView(MainController mainController){
        this.mainController = mainController;

        menuBar = new Group();
        canvas = new Pane();
        addingIndividual = new HBox();
        statics = new VBox();
        root = new BorderPane();

        menuView = new MenuView(mainController, menuBar);
        canvasView = new CanvasView(mainController, canvas);
        addingView = new AddingView(mainController, addingIndividual);
        statisticView = new StatisticView(mainController, statics);

        root.setTop(menuBar);
        root.setCenter(canvas);
        root.setBottom(addingIndividual);
        root.setRight(statics);

        this.draw();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Epidemic Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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
        this.canvasView.update(o, arg);
        this.statisticView.update(o, arg);
    }
}
