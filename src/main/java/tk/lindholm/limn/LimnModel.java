package tk.lindholm.limn;

import tk.lindholm.limn.model.*;

import javax.xml.bind.annotation.*;



/**
 * The Model for Limn.
 * Basically, the main model for the entire application.
 * 
 * @author Wiggy boy
 */
@XmlRootElement(name = "limn")
public class LimnModel extends Model {



	private WindowBounds windowBounds;



	@Override
	protected void initDefaults() {
		setWindowBounds(new WindowBounds());
	}



	@XmlElement
	public WindowBounds getWindowBounds() {
		return windowBounds;
	}
	public void setWindowBounds(WindowBounds windowBounds) {
		this.windowBounds = windowBounds;
	}




}
