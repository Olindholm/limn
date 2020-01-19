package tk.lindholm.limn.model;

import java.io.*;

import javax.xml.bind.*;



/**
 * A simple abstract class for constructing the most
 * basic of features of a Model in the MVC framework.
 * 
 * If you want to be able to load and store using JAXB,
 * the root model you'll need to add the annotation
 * <code>@XmlRootElement(name = "InsertYourNameHere")</code>
 * at the top of your class declaration.
 * 
 * @author Wiggy boy
 */
public abstract class Model {



	/**
	 * Loads a model from it's stored XML state.
	 * 
	 * @param file	the file where the model is stored.
	 * @param cls	the specific Model class that the XML represent.
	 * 
	 * @return	returns if successful, the specific Model class, <b>cls</b>,
	 * 			loaded from the stored XML file, <b>file</b>.
	 * 
	 * @throws	JAXBException	if the load is unsuccessful due to missing file,
	 * 							no read privileges of file, corrupt XML or the stored
	 * 							XML not corresponding to the specific Model, <b>cls</b>.
	 * 
	 * @see #store
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Model> T load(File file, Class<T> cls) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(cls);
		Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();

		return (T) jaxbUnmarshaller.unmarshal(file);
	}



	/**
	 * Creates a simple Model and initiates default values.
	 */
	public Model() {
		initDefaults();
	}



	/**
	 * Initiates the default values.This is called during
	 * creation of the model. Any model that extends this class
	 * should initiate it's (default) values in this method.
	 */
	protected abstract void initDefaults();



	/**
	 * Stores this model into a XML file.
	 * 
	 * @param file	the file where the model is stored.
	 * 
	 * @throws JAXBException	if any unexpected problem occurs during the marshalling.
	 * @throws IOException		if the file exists but is a directory rather than a regular file,
	 * 							does not exist but cannot be created (e.g. insufficient privileges),
	 * 							or cannot be opened for any other reason.
	 * 
	 * @see #load
	 */
	public void store(File file) throws JAXBException, IOException {

		JAXBContext context = JAXBContext.newInstance(this.getClass());
		Marshaller m = context.createMarshaller();

		// Set format to XML
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		FileWriter fw = new FileWriter(file);
		m.marshal(this, fw);

		fw.flush();
		fw.close();
	}



}
