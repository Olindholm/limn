package tk.lindholm.limn.gui;

import javafx.scene.layout.*;
import javafx.stage.*;



/**
 * A more equipped {@link Stage} class for handling
 * simple window operations for the MVC framework.
 * 
 * <br/><br/>
 * Features include,
 * <ul>
 * 		<li> easy creation of child views.
 * 		<li> support for external bounds model, see {@link WindowBounds}.
 * 		<li> easily finding of all components, a problematic task when using FXML.
 * </ul>
 * 
 * @author Wiggy boy
 */
public class View extends Stage {



	/**
	 * Creates a child view to this view.
	 * Initiate the proper Modality, sets the owner, initiates
	 * the window bounds of the owner onto this view, and centers it.
	 * 
	 * @return	returns the created child view.
	 */
	public View createChildView() {
		View view = new View();
		view.initModality(Modality.WINDOW_MODAL);
		view.initOwner(this);
		view.initWindowBounds(this);
		view.setOnShowing(e -> view.centerOnParent());

		return view;
	}



	/**
	 * Centers this view onto it's parents view.
	 * If it is an orphan this will have no effect.
	 */
	public void centerOnParent() {
		if (this.getOwner() != null) {
			View parent = (View) this.getOwner();

			this.setX(parent.getX() + parent.getWidth() / 2 - this.getWidth() / 2);
			this.setY(parent.getY() + parent.getHeight() / 2 - this.getHeight() / 2);
		}
	}



	/**
	 * Initiates the minimum and maximum bounds of the window,
	 * determined from the root node of the scene.
	 * 
	 * @throws NullPointerException	if the view has no scene or the scene has no root node.
	 */
	public void initBounds() {
		Region p = (Region) super.getScene().getRoot();

		if (p.getMinWidth() >= 0) super.setMinWidth(p.getMinWidth());
		if (p.getMinHeight() >= 0) super.setMinHeight(p.getMinHeight());

		if (p.getMaxWidth() >= 0) super.setMaxWidth(p.getMaxWidth());
		if (p.getMaxHeight() >= 0) super.setMaxHeight(p.getMaxHeight());
	}



	/**
	 * Sets the window's x, y, width and height the same
	 * as the <b>parentView</b>.
	 * 
	 * @param parentView	the view to get the bounds from.
	 */
	private void initWindowBounds(View parentView) {
		super.setMaximized(parentView.isMaximized());

		super.setX(parentView.getX());
		super.setY(parentView.getY());

		super.setWidth(parentView.getWidth());
		super.setHeight(parentView.getHeight());
	}



}
