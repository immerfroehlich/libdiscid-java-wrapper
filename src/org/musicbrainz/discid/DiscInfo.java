package org.musicbrainz.discid;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;


public class DiscInfo {

  public class TrackInfo {

    /** track number on disc */
    public final int num;
    /** sector offset for this track */
    public final int offset;
    /** length in sectors of this track */
    public final int length;

    public TrackInfo(LibDiscId libdiscid, int i) throws DiscIdException {
      this.num = i;
      this.offset = libdiscid.getTrackOffset(i);
      this.length = libdiscid.getTrackLength(i);
    }

  }

  /** MusicBrainz DiscId for the cd */
  public final String id;
  /** FreeDB id for the cd
   * FreeDB: <a href="http://www.freedb.org/">http://www.freedb.org/</a><br/>
   * BeNOW FreeDB Project: <a href="http://benow.ca/projects">http://benow.ca/projects</a>
   */
  public final String freeDBid;
  private final String device;
  private final URL submissionURL;
  private final String webServiceURL;
  private final int firstTrackNum;
  private final int lastTrackNum;
  private final int sectors;
  private final Map<Integer, TrackInfo> trackOffsets = new TreeMap<Integer, TrackInfo>();

  public DiscInfo(LibDiscId libdiscid, String device) throws DiscIdException {
    this.id = libdiscid.getId();
    this.freeDBid = libdiscid.getFreeDBId();
    this.device=device!=null?device:libdiscid.getDefaultDevice();
    this.submissionURL=libdiscid.getSubmissionURL();
    this.webServiceURL=libdiscid.getWebServiceURL();
    this.firstTrackNum=libdiscid.getFirstTrackNum();
    this.lastTrackNum=libdiscid.getLastTrackNum();
    this.sectors=libdiscid.getSectors();
    for (int i = firstTrackNum; i <= lastTrackNum; i++)
      trackOffsets.put(i, new TrackInfo(libdiscid,i));
  }

  public static DiscInfo read() throws DiscIdException {
    return read(null);
  }

  public static DiscInfo read(
      String device) throws DiscIdException {
    LibDiscId libdiscid = new LibDiscId();
    libdiscid.read(device);
    DiscInfo discId = new DiscInfo(libdiscid,
      device);
    libdiscid.close();
    return discId;
  }

  @Override
  public String toString() {
    String msg = "Device     : " + device + "\n" + "DiscId     : " + id + "\n" + "FreeDb Id  : " + freeDBid + "\n"
        + "Submit URL : " + submissionURL + "\n" + "Web Service: " + webServiceURL + "\n" + "First Track: " + firstTrackNum
        + "\n" + "Last Track : " + lastTrackNum + "\n" + "Sectors    : " + sectors + "\n";
    for (Integer key : trackOffsets.keySet()) {
      TrackInfo info = trackOffsets.get(key);
      msg += "  Track " + (key < 10 ? " " : "") + key + " : Length: " + info.length + " Offset: " + info.offset + "\n";
    }
    return msg;
  }
}
