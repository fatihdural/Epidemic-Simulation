import controller.WelcomeController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;

public class TestApp extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{

        Model model = new Model();
        WelcomeController controller = new WelcomeController(model);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
