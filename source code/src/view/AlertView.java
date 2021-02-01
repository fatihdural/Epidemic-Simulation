package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Observable;

public class AlertView  implements View {       // notice user for wrong input

    public AlertView(){
        this.draw();
    }
    @Override
    public void draw() {
        ButtonType button = new ButtonType("OK");
        Alert a = new Alert(Alert.AlertType.NONE, "You must fill in all fields PROPERLY!!!",button);
        a.setTitle("NOTICE");
        a.setResizable(true);
        a.showAndWait();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.draw();
    }
}
