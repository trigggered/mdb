package mdb.core.db.sql.parser;

public class MatchItem {

  private String m_strMatch;
  private int m_nIndex;
  private int m_nLen;



 public MatchItem(String astrMatch, int anIndex) {
   m_strMatch = astrMatch;
   m_nIndex = anIndex;
 }


  public MatchItem() {

  }

  public String toString() {
    return new Integer(m_nIndex).toString() + ", " +
        new Integer(m_nLen).toString() + ", " + m_strMatch;
  }

  public String get_Match() {
    return m_strMatch;
  }

  public int getIndex() {
    return m_nIndex;
  }

  public int getLen() {
    return m_nLen;
  }

}
