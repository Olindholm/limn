package tk.lindholm.limn.gui;

import tk.lindholm.limn.model.Model;

import java.io.*;

import javafx.fxml.*;
import javafx.scene.*;



/**
 * Further improvements on the general {@link Controller} class,
 * with the intended use for Windows (i.e. not dialogs).
 * 
 * @author Wiggy boy
 */
public abstract class WindowController extends Controller {



	protected Model model;



	/**
	 * Creates a generic controller, loads and deploys the FXML
	 * from specified resource path.
	 * 
	 * @param view		The view the use when deploying the FXML.
	 * @param fxmlPath	the relative resource path to the FXML file.
	 * @param cfg		The model for the controller, may be null.
	 * 
	 * @throws IOException	if an error occurs during loading.
	 */
	public WindowController(View view, String fxmlPath, Model model) throws IOException {
		super(view);
		this.model = model;

		Parent root = loadFXML(fxmlPath);
		Scene scene = new Scene(root);
		view.setScene(scene);
		view.initBounds();
	}



	/**
	 * Loads an internal FXML file from the resources.
	 * 
	 * @param fxmlPath	the relative resource path file for the FXML file.
	 * 
	 * @return returns	the parent node of the parsed FXML.
	 * 
	 * @throws IOException	if an error occurs during loading.
	 */
	private Parent loadFXML(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);

		InputStream fxmlStream = getClass().getResourceAsStream(fxmlPath);
		return (Parent) loader.load(fxmlStream);
	}



	@Override
	public Model getModel() {
		return model;
	}



}
