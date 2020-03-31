package org.musicbrainz.ws.model;

import org.musicbrainz.ws.XMLAccessor;
import org.w3c.dom.Element;


public class Artist extends XMLAccessor {

  public Artist(Element artistElem) {
    super(artistElem);
  }

  public String getName() {
    return getStringByPath("name");
  }

  public String getSortName() {
    return getStringByPath("sort-name");
  }

  public String getId() {
    return getStringByPath("@id");
  }

}
