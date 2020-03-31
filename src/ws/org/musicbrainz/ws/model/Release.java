package org.musicbrainz.ws.model;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.ws.MusicBrainiac;
import org.musicbrainz.ws.XML;
import org.musicbrainz.ws.XMLAccessor;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Release extends XMLAccessor {

  public Release(Element releaseElem) {
    super(releaseElem);
  }

  public String getTitle() {
    return getStringByPath("title");
  }

  public String getId() {
    return getStringByPath("@id");
  }

  public Artist getArtist() {
    return new Artist((Element) getByPath("artist"));
  }

  public boolean isCompilation() {
    String type = getType();
    return type != null && type.contains("Compilation");
  }

  public List<Track> getTracks() {
    String numTracks = getStringByPath("track-list/@count");
    if (numTracks != null) {
      if (Integer.parseInt(numTracks) > 0) {
        // there was a track list element, is search result,
        // fetch full release for all tracks
        try {
          Release r = new MusicBrainiac().getRelease(getId());
          super.element = r.element;
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    List<Track> tracks = new ArrayList<Track>();
    Element listElem = (Element) XML.getByPath(element, "track-list");
    List<Element> trackElements = XML.getChildElements(listElem, "track");
    for (Element currTE : trackElements)
      tracks.add(new Track(currTE));
    return tracks;
  }

  public String getAmazonSIN() {
    return getStringByPath("asin");
  }

  public String getScore() {
    return getStringByPath("@ext:score");
  }

  /**
   * The value of the type attribute for a release.  This attribute
   * seems to contain two words, the first is the type the second is the
   * status.  If there are two words, the first is returned.  If not
   * the whole thing is returned.
   * {@link http://musicbrainz.org/doc/Release_Type}
   * @see #getStatus()
   * @return
   */
  public String getType() {
    String typeStr = getStringByPath("@type");
    if (typeStr == null)
      return null;
    if (typeStr.contains(" "))
      return typeStr.split(" ")[0];
    return typeStr;
  }

  /**
   * Gets the status, which (it seems) is the second word in the type
   * attribute.  If there is no type or no second word in type then
   * null is returned, otherwise the status is returned
   * {@link http://musicbrainz.org/doc/Release_Status}
   * @return
   */
  public String getStatus() {
    String typeStr = getStringByPath("@type");
    if (typeStr == null)
      return null;
    if (typeStr.contains(" "))
      return typeStr.split(" ")[1];
    return null;
  }

  public List<ReleaseEvent> getReleaseEvents() {
    List<ReleaseEvent> relEvts = new ArrayList<ReleaseEvent>();
    NodeList relEvtElements = element.getElementsByTagName("event");
    for (int i = 0; i < relEvtElements.getLength(); i++)
      relEvts.add(new ReleaseEvent((Element) relEvtElements.item(i)));
    return relEvts;
  }

  public Element getRelationList(
      String targetType) {
    List<Element> elems = XML.getChildElements(element, "relation-list");
    for (Element elem : elems) {
      if (elem.hasAttribute("target-type") && elem.getAttribute("target-type").equals(targetType))
        return elem;
    }
    return null;
  }

}
