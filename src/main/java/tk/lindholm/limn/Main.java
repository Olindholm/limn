package tk.lindholm.limn;

import static tk.lindholm.limn.App.main_launch;

/**
 * 
 * Normally the {@link App} class would have the {@link #main} method.
 * 
 * However due to this bug/exception
 * "<a href="https://stackoverflow.com/questions/56894627">
 * 		Error:	JavaFX runtime components are missing,
 * 				and are required to run this application
 * </a>"
 * we are forced to make a Main class that does not include
 * {@link javafx.application.Application}. Thus this class.
 * 
 */
public class Main {
	
	public static void main(String[] args) {
		main_launch(args);
	}
	
}
