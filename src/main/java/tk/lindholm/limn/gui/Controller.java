package tk.lindholm.limn.gui;

import tk.lindholm.limn.model.Model;

import javafx.event.Event;



/**
 * A simple abstract class for constructing the most
 * basic of features of a Controller in the MVC framework.
 * 
 * @author Wiggy boy
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



	/**
	 * Closes the view of this controller.
	 */
	public void close() {
		handleClose(null);
	}


	/**
	 * Handles what happens the a
	 * close request has been issued.
	 * 
	 * @param event	the event which made the close request.
	 * 				If this is null, it was made programmatically
	 * 				by {@link #close()}.
	 */
	public void handleClose(Event event) {
		view.close();
	}



}
