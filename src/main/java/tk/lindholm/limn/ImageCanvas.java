package tk.lindholm.limn;

import javafx.animation.*;
import javafx.geometry.Orientation;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

public class ImageCanvas extends Pane {



	Canvas canvas;
	ScrollBar xScroll, yScroll;

	Image image;
	double imageScale = 1;

	boolean redraw = false;



	public ImageCanvas() {

		GridPane gridPane = new GridPane();
		gridPane.prefWidthProperty().bind(super.widthProperty());
		gridPane.prefHeightProperty().bind(super.heightProperty());
		super.getChildren().add(gridPane);

		xScroll = new ScrollBar();
		xScroll.setOrientation(Orientation.HORIZONTAL);
		xScroll.setValue(0.5);
		xScroll.setMax(1);
		xScroll.valueProperty().addListener((observable, oldValue, newValue) -> { redraw = true; });

		gridPane.add(xScroll, 0, 1);
		GridPane.setHgrow(xScroll, Priority.ALWAYS);

		yScroll = new ScrollBar();
		yScroll.setOrientation(Orientation.VERTICAL);
		yScroll.setValue(0.5);
		yScroll.setMax(1);
		yScroll.valueProperty().addListener((observable, oldValue, newValue) -> { redraw = true; });

		gridPane.add(yScroll, 1, 0);
		GridPane.setVgrow(yScroll, Priority.ALWAYS);

		canvas = new Canvas();
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
	}



	public void setImage(Image image) {
		this.image = image;
	}



	public void setImageScale(double imageScale) {
		this.imageScale = imageScale;

		int imageWidth = (int) image.getWidth();
		int imageHeight = (int) image.getHeight();

		this.setWidth(imageScale * imageWidth);
		this.setHeight(imageScale * imageHeight);
	}



	public double getImageScale() {
		return imageScale;
	}



	public void render() {
		double imageWidth = image.getWidth();
		double imageHeight = image.getHeight();

		double canvasWidth = canvas.getWidth();
		double canvasHeight = canvas.getHeight();

		double xScroll = this.xScroll.getValue();
		double yScroll = this.yScroll.getValue();

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Clear the canvas
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvasWidth, canvasHeight);

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


		// Image
		PixelReader reader = image.getPixelReader();

		int imageX = gImageX;
		double canvasX = gCanvasX;
		while (imageX < imageWidth && canvasX < canvasWidth) {
			// X
			double nextCanvasX = canvasX + imageScale;

			canvasX = Math.max(0, canvasX);
			nextCanvasX = Math.min(canvasWidth, nextCanvasX);

			int x = (int) Math.round(canvasX);
			int width = (int) Math.round(nextCanvasX - x);

			// Y
			int imageY = gImageY;
			double canvasY = gCanvasY;
			while (imageY < imageHeight && canvasY < canvasHeight) {
				double nextCanvasY = canvasY + imageScale;

				canvasY = Math.max(0, canvasY);
				nextCanvasY = Math.min(canvasHeight, nextCanvasY);

				int y = (int) Math.round(canvasY);
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



}
