package tk.lindholm.limn;

import javafx.event.*;
import javafx.animation.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import static javafx.geometry.Orientation.*;
import static javafx.scene.layout.Priority.*;
import static javafx.scene.layout.BackgroundRepeat.*;



public class ImageCanvas extends Pane implements EventHandler<ScrollEvent> {



	Image transparencyTexture = new Image("file:transparency.png");


	Canvas canvas;
	ScrollBar xScroll, yScroll;

	Image image;
	double imageScale;

	boolean redraw = false;



	public ImageCanvas() {
		setImage(null);
		
		GridPane gridPane = new GridPane();
		gridPane.prefWidthProperty().bind(super.widthProperty());
		gridPane.prefHeightProperty().bind(super.heightProperty());
		super.getChildren().add(gridPane);

		gridPane.setBackground(new Background(new BackgroundImage(transparencyTexture, REPEAT, REPEAT, null, null)));

		xScroll = new ScrollBar();
		xScroll.setOrientation(HORIZONTAL);
		xScroll.setValue(0.5);
		xScroll.setMax(1);
		xScroll.valueProperty().addListener((observable, oldValue, newValue) -> { redraw = true; });
		xScroll.setVisibleAmount(0.5);

		gridPane.add(xScroll, 0, 1);
		GridPane.setHgrow(xScroll, ALWAYS);

		yScroll = new ScrollBar();
		yScroll.setOrientation(VERTICAL);
		yScroll.setValue(0.5);
		yScroll.setMax(1);
		yScroll.valueProperty().addListener((observable, oldValue, newValue) -> { redraw = true; });

		gridPane.add(yScroll, 1, 0);
		GridPane.setVgrow(yScroll, Priority.ALWAYS);

		Pane pane = new Pane();
		pane.setBackground(new Background(new BackgroundFill(new Color(233d/255, 233d/255, 233d/255, 1), null, null)));
		gridPane.add(pane, 1, 1);

		canvas = new Canvas();
		canvas.setOnScroll(this);
		canvas.widthProperty().bind(super.widthProperty().subtract(yScroll.widthProperty()));
		canvas.heightProperty().bind(super.heightProperty().subtract(xScroll.heightProperty()));
		canvas.widthProperty().addListener((observable, oldValue, newValue) -> { redraw = true; });
		canvas.heightProperty().addListener((observable, oldValue, newValue) -> { redraw = true; });
		gridPane.add(canvas, 0, 0);

		new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (redraw) {
					redraw = false;

					render();
				}
			}
		}.start();

		setImageScale(1);
	}



	public void setImage(Image image) {
		this.image = image;
		redraw = true;
		setDisable(image == null);
	}



	public void setImageScale(double imageScale) {
		// Scroll speed/sensitivity.
		// TODO Make it user adjustable
		double scrollSpeed = 0.1d / imageScale;

		xScroll.setUnitIncrement(scrollSpeed);
		xScroll.setBlockIncrement(scrollSpeed);

		yScroll.setUnitIncrement(scrollSpeed);
		yScroll.setBlockIncrement(scrollSpeed);

		this.imageScale = imageScale;
		redraw = true;
	}
	public double getImageScale() {
		return imageScale;
	}



	public void render() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		double canvasWidth = canvas.getWidth();
		double canvasHeight = canvas.getHeight();
		
		// Clear the canvas
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvasWidth, canvasHeight);
		
		// If image is null, do no more
		if (image == null) return;
		
		// Render Image
		int x, y;
		double canvasX, canvasY;

		double imageWidth = image.getWidth();
		double imageHeight = image.getHeight();

		double xScroll = this.xScroll.getValue();
		double yScroll = this.yScroll.getValue();

		// Calculate the position of the image and
		// what section of the image is visible (and thus should be rendered)
		double gCanvasX = canvasWidth/2 - xScroll * imageScale * imageWidth;
		double gCanvasY = canvasHeight/2 - yScroll * imageScale * imageHeight;

		int gImageX = 0;
		int gImageY = 0;

		if (gCanvasX < 0) {
			gImageX = (int) ((xScroll * imageWidth * imageScale - canvasWidth/2) / imageScale);
			gCanvasX += imageScale * gImageX;
		}
		if (gCanvasY < 0) {
			gImageY = (int) ((yScroll * imageHeight * imageScale - canvasHeight/2) / imageScale);
			gCanvasY += imageScale * gImageY;
		}

		canvasX = Math.round(gCanvasX);
		canvasY = Math.round(gCanvasY);

		int canvasXLimit = (int) Math.round(Math.min(canvasWidth, canvasX + imageScale * (imageWidth - gImageX)));
		int canvasYLimit = (int) Math.round(Math.min(canvasHeight, canvasY + imageScale * (imageHeight - gImageY)));

		gc.clearRect(canvasX, canvasY, canvasXLimit - canvasX, canvasYLimit - canvasY);

		// Image
		PixelReader reader = image.getPixelReader();

		int imageX = gImageX;
		canvasX = gCanvasX;
		while (imageX < imageWidth && canvasX < canvasWidth) {
			// X
			double nextCanvasX = canvasX + imageScale;

			canvasX = Math.max(0, canvasX);
			nextCanvasX = Math.min(canvasWidth, nextCanvasX);

			x = (int) Math.round(canvasX);
			int width = (int) Math.round(nextCanvasX - x);

			// Y
			int imageY = gImageY;
			canvasY = gCanvasY;
			while (imageY < imageHeight && canvasY < canvasHeight) {
				double nextCanvasY = canvasY + imageScale;

				canvasY = Math.max(0, canvasY);
				nextCanvasY = Math.min(canvasHeight, nextCanvasY);

				y = (int) Math.round(canvasY);
				int height = (int) Math.round(nextCanvasY - y);

				Color c = reader.getColor(imageX, imageY);
				if (c.getOpacity() > 0) {
					gc.setFill(c);
					gc.fillRect(x, y, width, height);
				}

				imageY++;
				canvasY = nextCanvasY;
			}

			imageX++;
			canvasX = nextCanvasX;
		}

	}



	@Override
	public void handle(ScrollEvent event) {

		double deltaY = event.getDeltaY();
		double deltaX = event.getDeltaX();

		if (event.isControlDown()) {

			double x = (int) event.getX();
			double y = (int) event.getY();

			double oldXScroll = xScroll.getValue();
			double oldYScroll = yScroll.getValue();

			double imageWidth = image.getWidth();
			double imageHeight = image.getHeight();

			double canvasWidth = canvas.getWidth();
			double canvasHeight = canvas.getHeight();

			double oldImageScale = getImageScale();
			double newImageScale = (deltaY > 0) ? Math.min(64, 2*oldImageScale) : Math.max(0.25, imageScale/2);

			double xCenterOffset = x - .5*canvasWidth;
			double yCenterOffset = y - .5*canvasHeight;

			double newXScroll = oldXScroll + (xCenterOffset / oldImageScale - xCenterOffset / newImageScale) / imageWidth;
			double newYScroll = oldYScroll + (yCenterOffset / oldImageScale - yCenterOffset / newImageScale) / imageHeight;

			// Zoom
			xScroll.setValue(Math.max(0, Math.min(1, newXScroll)));
			yScroll.setValue(Math.max(0, Math.min(1, newYScroll)));
			setImageScale(newImageScale);

		}
		else {

			// Pan
			if (deltaX < 0) xScroll.increment();
			else if (deltaX > 0) xScroll.decrement();

			if (deltaY < 0) yScroll.increment();
			else if (deltaY > 0) yScroll.decrement();

		}

	}



}
