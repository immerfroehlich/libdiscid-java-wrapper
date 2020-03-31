package test.ca.benow.jni;

import java.util.Properties;
import java.util.TreeSet;

public class LinkerTest {
  public native void test();

  static {
    Properties props = System.getProperties();
    for (Object key : new TreeSet<Object>(props.keySet()))
      System.out.println(key + " -> " + System.getProperty("" + key));

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      // ignore
    }
  	System.loadLibrary("linkertest");
  }

  public static void main(String args[]) {
  	new LinkerTest().test();
  }
}
