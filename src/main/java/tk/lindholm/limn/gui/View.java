package tk.lindholm.limn.gui;

import tk.lindholm.limn.model.WindowBounds;

import javafx.scene.*;
import javafx.stage.*;
import javafx.collections.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;



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



	/**
	 * Initiates and tracks the bounds (x, y, width and height)
	 * of this window using a {@link WindowBounds} model.
	 * 
	 * @param bounds the bounds model to use.
	 */
	public void initWindowBounds(WindowBounds bounds) {
		super.setMaximized(bounds.isMaximized());

		super.setX(bounds.getX());
		super.setY(bounds.getY());

		super.setWidth(bounds.getWidth());
		super.setHeight(bounds.getHeight());

		super.maximizedProperty().addListener((obsv, oldValue, newValue) -> {
			bounds.setMaximized(newValue);
		});

		super.xProperty().addListener((obsv, oldValue, newValue) -> {
			if (!super.isMaximized()) bounds.setX(newValue.doubleValue());
		});
		super.yProperty().addListener((obsv, oldValue, newValue) -> {
			if (!super.isMaximized()) bounds.setY(newValue.doubleValue());
		});

		super.widthProperty().addListener((obsv, oldValue, newValue) -> {
			if (!super.isMaximized()) bounds.setWidth(newValue.doubleValue());
		});
		super.heightProperty().addListener((obsv, oldValue, newValue) -> {
			if (!super.isMaximized()) bounds.setHeight(newValue.doubleValue());
		});
	}



	/**
	 * Finds all classes of <b>cls</b> within this view.
	 * 
	 * @param <T>
	 * @param cls
	 * 
	 * @return
	 */
	public <T> ObservableSet<T> findAllComponents(Class<T> cls) {
		return findAllComponents(super.getScene().getRoot(), cls);
	}

	@SuppressWarnings("unchecked")
	private <T> ObservableSet<T> findAllComponents(Parent p, Class<T> cls) {
		ObservableSet<T> set = FXCollections.observableSet();

		for (Node n : p.getChildrenUnmodifiable()) {
			if (n instanceof Parent) {
				if (cls.isInstance(n)) set.add((T) n);
				set.addAll(findAllComponents((Parent) n, cls));
			}
		}

		if (p instanceof TabPane) {
			for (Tab tab : ((TabPane) p).getTabs()) {
				if (cls.isInstance(tab)) set.add((T) tab);
				set.addAll(findAllComponents((Parent) tab.getContent(), cls));
			}
		}
		else if (p instanceof MenuBar) {
			for (Menu menu : ((MenuBar) p).getMenus()) {
				if (cls.isInstance(menu)) set.add((T) menu);

				for (MenuItem item : menu.getItems()) {
					if (cls.isInstance(item)) set.add((T) item);
				}
			}
		}

		return set;
	}



}
