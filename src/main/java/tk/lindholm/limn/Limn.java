package tk.lindholm.limn;

import tk.lindholm.limn.gui.*;

import java.io.*;

import javafx.application.Application;
import javafx.stage.*;



/**
 * The main class for the application (sort of).
 * 
 * @author crimson
 */
public class Limn extends Application {



	/**
	 * See {@link Main}.
	 */
	public static void main_launch(String[] args) {
		launch(args);
	}



	/**
	 * Start of the application.
	 * This can be considered as the main method.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Controller controller = new LimnController(new View(), null);
			controller.getView().showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
