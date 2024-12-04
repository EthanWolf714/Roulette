import javafx.stage.Stage;

public class roulletteController {
    /*
     * Manage interactions between the view and the model.
     Handle button clicks, chip placements, and coordination between
      the UI (RouletteView) and game state (RouletteWheel or other model classes).
     */
    private final RouletteView view;
    private final rouletteWheel wheel;

    public roulletteController(Stage stage){
        this.view = new RouletteView(stage);
        this.wheel = new rouletteWheel();

        
    }

    


}
