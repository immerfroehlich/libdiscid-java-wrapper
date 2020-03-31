package org.musicbrainz.ws.model;

import org.musicbrainz.ws.XMLAccessor;
import org.w3c.dom.Element;


public class Track extends XMLAccessor {

  public Track(Element item) {
    super(item);
  }

  public String getTitle() {
    return getStringByPath("title");
  }

  public String getId() {
    return getStringByPath("@id");
  }

  public int getDurationInMillis() {
    String durStr = getStringByPath("duration");
    if (durStr == null)
      return 0;
    try {
      return Integer.parseInt(durStr);
    } catch (NumberFormatException e) {
      System.err.println("Error parsing duration from: " + durStr);
      return 0;
    }
  }

  public Artist getArtist() {
    Element artistE = (Element) getByPath("artist");
    if (artistE != null)
      return new Artist(artistE);
    return null;
  }

}
