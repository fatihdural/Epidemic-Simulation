package view;

import controller.MainController;
import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.util.Observable;

public class MenuView implements View{
    MainController mainController;
    Group menu;

    public MenuView(MainController mainController, Group menu){
        this.mainController = mainController;
        this.menu = menu;

        this.draw();
    }

    @Override
    public void draw() {
        Menu manageMenu = new Menu("Manage");
        manageMenu.setMnemonicParsing(true);
        MenuItem pause = new MenuItem("Pause Simulation");
        MenuItem resume = new MenuItem("Resume Simulation");
        resume.setDisable(true);
        MenuItem exit = new MenuItem("Exit");
        manageMenu.getItems().addAll(pause, resume, exit);

        pause.setOnAction(event -> {
            pause.setDisable(true);
            resume.setDisable(false);
            mainController.pauseAllHandler();
        });

        resume.setOnAction(event -> {
            resume.setDisable(true);
            pause.setDisable(false);
            mainController.resumeAllHandler();
        });

        MenuBar menuBar = new MenuBar(manageMenu);

        this.menu.getChildren().add(menuBar);
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
