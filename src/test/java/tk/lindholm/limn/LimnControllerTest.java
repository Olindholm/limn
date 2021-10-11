package tk.lindholm.limn;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;

public class LimnControllerTest {
	
	@Test
	public void openImage_throwExceptionOnNull() {
		assertThrows(NullPointerException.class, () -> LimnController.openImage(null));
	}

	@Test
	public void openImage_throwExceptionOnNull2() {
		File file = new File("NonExistantFile");
		
		assertEquals(false, file.exists());
		assertThrows(FileNotFoundException.class, () -> LimnController.openImage(file));
	}


	@Test
	public void openImage_fail() {
		//fail("Do failing test");
	}

}
