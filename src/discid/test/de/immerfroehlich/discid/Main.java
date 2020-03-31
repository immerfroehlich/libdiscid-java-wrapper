package test.de.immerfroehlich.discid;

import de.immerfroehlich.discid.DiscIdCalculator;

public class Main {

	public static void main(String[] args) {
		DiscIdCalculator calculator = new DiscIdCalculator();
		String discId = calculator.calculate("/dev/sr0");
		System.out.println(discId);
	}

}
