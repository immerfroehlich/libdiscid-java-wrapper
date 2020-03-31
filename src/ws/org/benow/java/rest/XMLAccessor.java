package org.benow.java.rest;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLAccessor {

  protected Element element;

  public XMLAccessor(Element element) {
    this.element = element;
  }

  public Element getElement() {
    return element;
  }

  /**
   * Gets the item at the given path.  If the item is a element with text, returns
   * the text
   * @param xmlPath
   * @return string or node at path, if any
   */
  public Object getByPath(
      String xmlPath) {
    return XML.getByPath(element, xmlPath);
  }

  /**
   * Gets node at given path, guarenteed to be a node (or null)
   * @param xmlPath
   * @return node at path
   */
  public Node getNodeByPath(
      String xmlPath) {
    return XML.getNodeByPath(element, xmlPath);
  }

  public String getStringByPath(
      String xmlPath) {
    Object result = XML.getByPath(element, xmlPath);
    if (result instanceof String)
      return (String) result;
    return null;
  }

  protected int getIntByPath(
      String path) {
    String asStr = getStringByPath(path);
    if (asStr != null) {
      try {
        return Integer.parseInt(asStr);
      } catch (NumberFormatException e) {
        System.err.println("Error in: " + path + " can't parsing integer from: " + asStr + " returning 0");
      }
    }
    return 0;
  }

  @Override
  public String toString() {
    return XML.elementToString(element);
  }
}
