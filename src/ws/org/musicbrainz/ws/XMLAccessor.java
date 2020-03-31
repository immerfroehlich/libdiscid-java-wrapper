package org.musicbrainz.ws;

import org.w3c.dom.Element;

public class XMLAccessor {

  protected Element element;

  public XMLAccessor(Element element) {
    this.element = element;
  }

  public Element getElement() {
    return element;
  }

  public Object getByPath(
      String xmlPath) {
    return XML.getByPath(element, xmlPath);
  }

  public String getStringByPath(
      String xmlPath) {
    Object result = XML.getByPath(element, xmlPath);
    if (result instanceof String)
      return (String) result;
    return null;
  }

  @Override
  public String toString() {
    return XML.elementToString(element);
  }
}
