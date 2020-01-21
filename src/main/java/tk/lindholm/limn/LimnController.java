package tk.lindholm.limn;

import tk.lindholm.limn.gui.*;

import java.io.IOException;

import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;



/**
 * The controller for Limn.
 * Basically, the main controller for the entire application.
 * 
 * @author Wiggy boy
 */
public class LimnController extends WindowController {



	ImageCanvas imageCanvas;

	@FXML HBox centerPane;



	public LimnController(View view, LimnModel model) throws IOException {
		super(view, "LimnView.fxml", model);
		view.initWindowBounds(model.getWindowBounds());


		Image dukeImage = new Image("file:duke.png");

		ImageCanvas imageCanvas = new ImageCanvas();
		imageCanvas.setImage(dukeImage);

		centerPane.getChildren().add(imageCanvas);
		HBox.setHgrow(imageCanvas, Priority.ALWAYS);

	}

	@Override
	public void handleClose(Event event) {
		// TODO Auto-generated method stub
		view.close();
	}



}
