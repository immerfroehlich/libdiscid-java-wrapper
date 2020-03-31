package org.musicbrainz.ws.model;

import java.text.ParseException;
import java.util.Date;

import org.musicbrainz.ws.MusicBrainiac;
import org.musicbrainz.ws.XMLAccessor;
import org.w3c.dom.Element;

public class Relation extends XMLAccessor {

  public static final String RELATION_TYPE_URL = "Url";

  public Relation(Element curr) {
    super(curr);
  }

  public String getType() {
    return getStringByPath("@type");
  }

  public String getTarget() {
    return getStringByPath("@target");
  }

  public Date getBegin() {
    String begin = getStringByPath("@begin");
    if (begin == null)
      return null;
    try {
      return MusicBrainiac.DATE_FORMAT.parse(begin);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Date getEnd() {
    String start = getStringByPath("@end");
    if (start == null)
      return null;
    try {
      return MusicBrainiac.DATE_FORMAT.parse(start);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

}
