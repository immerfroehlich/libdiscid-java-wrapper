package org.musicbrainz.ws.model;

import java.text.ParseException;
import java.util.Date;

import org.musicbrainz.ws.MusicBrainiac;
import org.musicbrainz.ws.XMLAccessor;
import org.w3c.dom.Element;

/**
 * <event catalog-number="DEI2040-2" country="US" date="1996-04-09" format="CD"/>
 * 
 * @author andy
 *
 */
public class ReleaseEvent extends XMLAccessor {

  public ReleaseEvent(Element element) {
    super(element);
  }

  public Label getLabel() {
    Element labelE = (Element) getByPath("label");
    if (labelE == null)
      return null;
    return new Label(labelE);
  }

  public String getCatalogNumber() {
    return getStringByPath("@catalog-number");
  }

  public String getCountry() {
    return getStringByPath("@country");
  }

  public Date getDate() {
    String dateStr = getStringByPath("@date");
    if (dateStr != null) {
      try {
        return MusicBrainiac.DATE_FORMAT.parse(dateStr);
      } catch (ParseException e) {
        try {
          return MusicBrainiac.DATE_FORMAT_YEAR.parse(dateStr);
        } catch (ParseException ee) {
          System.err.println("Error parsing date from: " + dateStr);
        }
      }
    }
    return null;
  }

  public String getFormat() {
    return getStringByPath("format");
  }

}
