package tk.lindholm.limn;

import javafx.event.Event;
import tk.lindholm.limn.gui.*;

public class LimnController extends WindowController {

	public LimnController(View view, LimnModel model) {
		super(view, "LimnView.fxml", model);
	}

	@Override
	public void handleClose(Event event) {
		// TODO Auto-generated method stub
		view.close();
	}

}
