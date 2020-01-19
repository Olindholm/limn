package tk.lindholm.limn.gui;

import tk.lindholm.limn.model.Model;

import java.io.*;

import javafx.fxml.*;
import javafx.scene.*;

public abstract class WindowController extends Controller {



	protected Model model;



	public WindowController(View view, String fxmlPath, Model cfg) {
		super(view);
		this.model = cfg;

		Parent root = loadFXML(fxmlPath);
		Scene scene = new Scene(root);
		view.setScene(scene);
		view.initBounds();
	}



	/**
	 * Loads an internal FXML file from within the compiled jar file.
	 * If this fails, which should, and hopefully won't ever happen, the program
	 * will exit.
	 * 
	 * @param fxmlPath
	 * @return
	 */
	private Parent loadFXML(String fxmlPath) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setController(this);

			InputStream fxmlStream = getClass().getResourceAsStream(fxmlPath);
			return (Parent) loader.load(fxmlStream);
		} catch (IOException e) {
			// TODO make this more descriptive error message.
			System.out.println("I am corrupt, FXML load failed!");
			System.exit(0);
		}

		return null;
	}



	@Override
	public View getView() {
		return view;
	}



	@Override
	public Model getModel() {
		return model;
	}



}
