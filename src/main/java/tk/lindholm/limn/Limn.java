package tk.lindholm.limn;

import tk.lindholm.limn.gui.*;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Limn extends Application {

	/**
	 * See {@link Main}.
	 */
	public static void main_launch(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Controller controller = new LimnController(new View(), null);
		controller.getView().showAndWait();
	}

}
