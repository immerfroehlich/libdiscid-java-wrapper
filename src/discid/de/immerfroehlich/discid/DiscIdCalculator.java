package de.immerfroehlich.discid;

public class DiscIdCalculator {
	
	private static final String LIBNAME = "discid-java";

	static {
		System.loadLibrary(LIBNAME);
	}
	
	//TODO make this private and handle the error that the drive/cd could not
	//found in Java ways
	public native String calculate(String drivePath);

}
