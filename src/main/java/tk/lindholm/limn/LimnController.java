package tk.lindholm.limn;

import tk.lindholm.limn.gui.*;

import java.io.IOException;

import javafx.event.Event;



/**
 * The controller for Limn.
 * Basically, the main controller for the entire application.
 * 
 * @author Wiggy boy
 */
public class LimnController extends WindowController {



	public LimnController(View view, LimnModel model) throws IOException {
		super(view, "LimnView.fxml", model);
		view.initWindowBounds(model.getWindowBounds());
	}



	@Override
	public void handleClose(Event event) {
		// TODO Auto-generated method stub
		view.close();
	}



}
