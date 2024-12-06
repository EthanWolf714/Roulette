import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        RouletteView view = new RouletteView(primaryStage);
        view.show();
        
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
        
    }

}
