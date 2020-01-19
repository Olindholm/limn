package tk.lindholm.limn;

import tk.lindholm.limn.gui.*;

import java.io.*;

import javax.xml.bind.JAXBException;

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

			File file = new File("settings.xml");
			LimnModel cfg = file.exists() ? LimnModel.load(file, LimnModel.class) : new LimnModel();

			Controller controller = new LimnController(new View(), cfg);
			controller.getView().showAndWait();

			cfg.store(file);

		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
