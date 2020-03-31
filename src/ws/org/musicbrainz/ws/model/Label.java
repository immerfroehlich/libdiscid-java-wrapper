package org.musicbrainz.ws.model;

import java.util.ArrayList;
import java.util.List;

import org.musicbrainz.ws.XMLAccessor;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Label extends XMLAccessor {

  public Label(Element labelE) {
    super(labelE);
  }

  public String getId() {
    return getStringByPath("@id");
  }

  public String getName() {
    return getStringByPath("name");
  }

  public List<Relation> getRelations(
      String type) {
    NodeList rls = element.getElementsByTagName("relation-list");
    List<Relation> relations = new ArrayList<Relation>();
    for (int i = 0; i < rls.getLength(); i++) {
      Element curr = (Element) rls.item(i);
      String tt = curr.getAttribute("target-type");
      if (tt != null && tt.equals(type)) {
        NodeList rels = curr.getElementsByTagName("relation");
        for (int j = 0; j < rels.getLength(); j++) {
          Element relE = (Element) rels.item(j);
          relations.add(new Relation(relE));
        }
        break;
      }
    }
    return relations;
  }

  public List<Relation> getURLRelations() {
    return getRelations(Relation.RELATION_TYPE_URL);
  }


}
