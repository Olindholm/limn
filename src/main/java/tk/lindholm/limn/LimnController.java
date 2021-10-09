package tk.lindholm.limn;

import tk.lindholm.limn.gui.*;

import java.io.*;

import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;



/**
 * The controller for Limn.
 * Basically, the main controller for the entire application.
 * 
 * @author Wiggy boy
 */
public class LimnController extends WindowController {



	ImageCanvas imageCanvas;

	@FXML VBox centerPane;



	public LimnController(View view, LimnModel model) throws IOException {
		super(view, "LimnView.fxml", model);
		view.initWindowBounds(model.getWindowBounds());

		imageCanvas = new ImageCanvas();

		centerPane.getChildren().add(imageCanvas);
		VBox.setVgrow(imageCanvas, Priority.ALWAYS);

	}
	
	@FXML
	public void handleOpen(Event event) throws FileNotFoundException {
		// Let user pick a file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(view);
		
		// If no file was picked, simply cancel
		if (file == null) return;
		
		// Load file
		Image image = new Image(new FileInputStream(file));
		imageCanvas.setImage(image);
	}

	@Override
	public void handleClose(Event event) {
		// TODO Auto-generated method stub
		view.close();
	}



}
