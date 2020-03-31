package test.ca.benow.jni;


public class LinkerTest {
  public native void test();

  static {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // ignore
    }
  	System.loadLibrary("linkertest");
    System.out.println("Library linking validated.  Ensure to specify the native directory in the java.library.path, ie:\n"
        + "\tjava -Djava.library.path=native/" + System.getProperty("os.name").toLowerCase() + "/"
        + System.getProperty("os.arch")
        + " org.some.Class");
  }

  public static void main(String args[]) {
  	new LinkerTest().test();
  }
}
