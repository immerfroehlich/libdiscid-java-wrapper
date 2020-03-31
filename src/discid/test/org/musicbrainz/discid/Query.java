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
    if (args.length > 0 && args[0].equals("--help"))
      dumpHelp();

    try {
      DiscInfo info;
      if (args.length == 0)
        info = DiscInfo.read();
      else {
        try {
          // if first is a number, it's an offset
          Integer.parseInt(args[0]);
          int[] offsets = new int[args.length];
          for (int i = 0; i < args.length; i++)
            offsets[i] = Integer.parseInt(args[i]);
          info = DiscInfo.fromOffsets(offsets);
        } catch (NumberFormatException e) {
          info = DiscInfo.read(args[0]);
        }
      }
      System.out.println("Disc Information:\n" + info.toString());
      System.exit(0);

    } catch (DiscIdException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  private static void dumpHelp() {
    System.out.println("Reads disc information using musicbrainz libdiscid.\n" + "Usage:\n" + Query.class.getName()
        + " --help\n\tshows this help.\n"
        + Query.class.getName() + " [device]\n" 
        + "\tdevice containing disc to read.  If no device is given, the default device is used.\n" + Query.class.getName()
        + " offset1 [offset2] ... [offsetN]\n" + "\toffset1..N: offsets for track 1 to N");
    System.exit(1);
  }
}
