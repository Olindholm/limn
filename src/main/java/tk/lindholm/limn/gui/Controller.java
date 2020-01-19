package tk.lindholm.limn.gui;

import tk.lindholm.limn.model.Model;

import javafx.event.Event;



/**
 * A simple abstract class for constructing the most
 * basic of features of a Controller in the MVC framework.
 * 
 * @author Wiggy boy
 *
 */
public abstract class Controller {

	protected View view;

	public Controller(View view) {
		this.view = view;
		view.setOnCloseRequest(event -> {
			handleClose(event);
			event.consume();
		});
	}

	public View getView() {
		return view;
	}
	public abstract Model getModel();

	public void close() {
		handleClose(null);
	}

	public abstract void handleClose(Event event);
}
