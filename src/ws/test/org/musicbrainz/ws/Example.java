package test.org.musicbrainz.ws;

import org.musicbrainz.discid.DiscInfo;
import org.musicbrainz.ws.MusicBrainiac;
import org.musicbrainz.ws.model.Release;

public class Example {
  public static void main(
      String[] args) {
    try {
      if (args.length == 0) {
        System.out.println("Expected device name");
        System.exit(-1);
      }

      DiscInfo info = DiscInfo.read(args[0]);
      System.out.println("libdiscid read:\n" + info);

      MusicBrainiac mb = new MusicBrainiac();
      Release release = mb.getReleaseByDiscId(info.discid);
      if (release == null)
        System.out.println("Release not found for id: " + info.discid);
      else {
        System.out.println(release);
        System.out.println("Title: " + release.getTitle());
        System.out.println("Artist: " + release.getArtist().getName());
      }
      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

}
