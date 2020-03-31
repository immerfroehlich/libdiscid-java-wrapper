/*
 * Created on Nov 30, 2003
 */
package test.org.musicbrainz.discid;

import org.musicbrainz.discid.DiscIdException;
import org.musicbrainz.discid.DiscInfo;


/**
 * An example of using the libdiscid and the DiscInfo classes.  Queries
 * a cd for all available disc information.
 * 
 * @author andy@benow.ca
 */
public class Query {

  public static void main(String[] args) {
    if (args.length>1&&args[0].equals("--help"))  
      dumpHelp();

    try {
      DiscInfo info;
      if (args.length == 0)
        info = DiscInfo.read();
      else
        info = DiscInfo.read(args[0]);
      System.out.println("Disc Information:\n" + info.toString());
      System.exit(0);

    } catch (DiscIdException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  private static void dumpHelp() {
    System.out.println("Usage: "+Query.class.getName()+" <device> [--help]\n"+
        "Reads disc information using musicbrainz libdiscid."+
        "<device>: device containing disc to read.  If no device is given, the default device is used.\n"+
        "--help: show this help");
    System.exit(1);
  }
}
