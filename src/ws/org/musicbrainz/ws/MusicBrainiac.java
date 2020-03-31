package org.musicbrainz.ws;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.benow.java.rest.DocumentLoader;
import org.musicbrainz.discid.DiscInfo;
import org.musicbrainz.ws.model.Release;
import org.musicbrainz.ws.model.Track;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Various tools for musicbrains lookup via rest interface
 * @author andy
 *
 */
public class MusicBrainiac {
  private static final String RELEASE_PREFIX = "a href=\"/release/";

  public enum ReleaseInclude {
    Tracks, ReleaseEvents, Artist, Discs, ReleaseGroups, ArtistRels, LabelRels, ReleaseRels, TrackRels, URLRels, TrackLevelRels, Labels, UserTags, UserRatings, Isrcs, AllImpersonal, All
  }

  private static String[] releaseIncludeConstants = new String[] { "tracks", "release-events", "artist", "discs",
      "release-groups", "artist-rels", "label-rels", "release-rels", "track-rels", "url-rels", "track-level-rels", "labels",
      "tags", "ratings", "isrcs", null, null };

  // private static final Logger log = Logger.getLogger(MusicBrainiac.class);

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat DATE_FORMAT_YEAR = new SimpleDateFormat("yyyy");

  private final DocumentLoader loader;

  public MusicBrainiac() {
    this(null, null);
  }

  public MusicBrainiac(String user, String pass) {
    super();
    try {
      URL baseURL = new URL("http://" + (user != null ? user + ":" + pass + "@" : "") + "musicbrainz.org/ws/1/");
      loader = new DocumentLoader(baseURL);
      loader.setLoadInterval(1500);
      loader.setUserAgent("libdiscid-java/0.01 +http://benow.ca/projects/libdiscid-java/");
    } catch (MalformedURLException e) {
      throw new RuntimeException("Impossible",
        e);
    }
  }

  /**
   * Resolves a freedb id to a musicbrainz id, via a web lookup.  Throws releasenotfoundexception
   * if no release found.
   * 
   * @param freeDBId
   * @return
   * @throws IOException 
   * @throws ParseException 
   * @throws ReleaseNotFoundException 
   */
  public String freeDBIdToMusicBrainzId(
      String freeDBId) throws IOException, ParseException, ReleaseNotFoundException {
    String page = loader.loadString("/bare/cdlookup.html?freedbid=" + freeDBId);
    int pos = page.indexOf(RELEASE_PREFIX);
    if (pos > -1) {
      int endPos = page.indexOf("\"", pos + RELEASE_PREFIX.length() + 1);
      if (endPos > -1) {
        String tid = page.substring(pos, endPos);
        if (tid.endsWith(".html") && !tid.contains("/")) {
          String mbId = tid.substring(0, tid.lastIndexOf("."));
          System.out.println("Resolved freedbid: " + freeDBId + " to musicbrainz id: " + mbId);
          return mbId;
        }
      }
    }
    throw new ReleaseNotFoundException("No musicbrains release for freedb id: " + freeDBId);
  }

  /**
   * Simple accessor to first release for given disc id.  May not be correct
   * but probably is.
   * 
   * @param discId
   * @return release or null if not found
   * @throws Exception
   */
  public Release getReleaseByDiscId(
      String discId,
      ReleaseInclude... includes) throws Exception {
    if (discId == null)
      return null;
    List<Release> results = queryReleases(null, discId, null, null, null, 0, null, null, null, null, false, 0,
        (ReleaseInclude[]) null);
    if (results.isEmpty()) {
      System.out.println("No release found for discid: " + discId);
      return null;
    }
    // query again, because list results are not as complete as specific results.
    Release rel = results.get(0);
    return getRelease(rel.getId(), includes);
  }

  /**
   * Queries releases, returning results.
   * 
   * @param title Fetch a list of releases with a matching title
   * @param discId Fetch all releases matching to the given DiscID
   * @param artist The returned releases should match the given artist name
   * @param artistId The returned releases should match the given artist ID (36 character ASCII representation). If this is given, the artist parameter is ignored.
   * @param releaseTypes The returned releases must match all of the given release types. This is a list of space separated values like Official, Bootleg, Album, Compilation, etc.
   * @param count Number of tracks in the release
   * @param releaseDate Earliest release date for the release
   * @param asin The Amazon ASIN
   * @param language The language for this release
   * @param script The script used this release
   * @param cdStubs Indicates whether to include CD stubs in the result or not. By default CD stubs are included.
   * @param limit The maximum number of releases returned. Defaults to 25, the maximum allowed value is 100.
   * @return
   * @throws Exception
   */
  public List<Release> queryReleases(
      String title,
      String discId,
      String artist,
      String artistId,
      String releaseTypes,
      int count,
      Date releaseDate,
      String asin,
      String language,
      String script,
      boolean cdStubs,
      int limit) throws Exception {
    return queryReleases(title, discId, artist, artistId, releaseTypes, count, releaseDate, asin, language, script, cdStubs,
        limit, (ReleaseInclude[]) null);
  }

  /**
   * Queries releases, returning results.
   * 
   * @param title Fetch a list of releases with a matching title
   * @param discId Fetch all releases matching to the given DiscID
   * @param artist The returned releases should match the given artist name
   * @param artistId The returned releases should match the given artist ID (36 character ASCII representation). If this is given, the artist parameter is ignored.
   * @param releaseTypes The returned releases must match all of the given release types. This is a list of space separated values like Official, Bootleg, Album, Compilation, etc.
   * @param count Number of tracks in the release
   * @param releaseDate Earliest release date for the release
   * @param asin The Amazon ASIN
   * @param language The language for this release
   * @param script The script used this release
   * @param cdStubs Indicates whether to include CD stubs in the result or not. By default CD stubs are included.
   * @param limit The maximum number of releases returned. Defaults to 25, the maximum allowed value is 100.
   * @param includes items to include in the release results
   * @return
   * @throws Exception
   */
  public List<Release> queryReleases(
      String title,
      String discId,
      String artist,
      String artistId,
      String releaseTypes,
      Integer count,
      Date releaseDate,
      String asin,
      String language,
      String script,
      Boolean cdStubs,
      Integer limit,
      ReleaseInclude... includes) throws Exception {
    String urlStr = "";
    if (discId != null)
    urlStr += "&discid=" + discId;
    if (title != null)
      urlStr += "&title=" + URLEncoder.encode(title, "UTF-8");
    if (artist != null)
      urlStr += "&artist=" + URLEncoder.encode(artist, "UTF-8");
    if (artistId != null)
      urlStr += "&artistid=" + artistId;
    if (releaseTypes != null)
      urlStr += "&releasetypes=" + URLEncoder.encode(releaseTypes, "UTF-8");
    if (count != null && count > 0)
      urlStr += "&count=" + count;
    if (releaseDate != null)
      urlStr += "&date=" + MusicBrainiac.DATE_FORMAT.format(releaseDate);
    if (asin != null)
      urlStr += "&asin=" + asin;
    if (language != null)
      urlStr += "&lang=" + language;
    if (script != null)
      urlStr += "&script=" + script;
    if (cdStubs != null && !cdStubs)
      urlStr += "&cdstubs=no";
    if (limit != null && limit >= 0)
      urlStr += "&limit=" + limit;
    if (urlStr.length() == 0)
      throw new IllegalArgumentException("Expected at least one argument");
    urlStr = "release/?type=xml" + urlStr;
    urlStr += getReleaseIncludesParameter(includes);
    Document doc = loader.loadDocument(urlStr);
    NodeList elems = doc.getElementsByTagName("release");
    List<Release> releases = new ArrayList<Release>();
    for (int i = 0; i < elems.getLength(); i++)
      releases.add(new Release((Element) elems.item(i)));
    return releases;
  }

  public Release getRelease(
      String id,
      ReleaseInclude... includes) throws Exception {

    String urlStr = "release/" + id + "?type=xml";
    urlStr += getReleaseIncludesParameter(includes);
    NodeList releases = loader.loadDocument(urlStr).getElementsByTagName("release");
    if (releases.getLength()==0)
      throw new FileNotFoundException("No release with id: " + id);
    return new Release((Element) releases.item(0));
  }

  private String getReleaseIncludesParameter(
      ReleaseInclude[] includes) {
    String incStr = "";
    if (includes != null && includes.length > 0) {
      List<String> incs = new ArrayList<String>();
      ReleaseInclude[] vals = ReleaseInclude.values();
      for (ReleaseInclude curr : includes) {
        if (curr == ReleaseInclude.All) {
          for (ReleaseInclude currAdd : vals) {
            if (currAdd != ReleaseInclude.All && currAdd != ReleaseInclude.AllImpersonal)
              incs.add(releaseIncludeConstants[currAdd.ordinal()]);
          }
        } else if (curr == ReleaseInclude.AllImpersonal) {
          for (ReleaseInclude currAdd : vals) {
            if (currAdd != ReleaseInclude.All && currAdd != ReleaseInclude.AllImpersonal) {
              if (currAdd != ReleaseInclude.UserTags && currAdd != ReleaseInclude.UserRatings)
                incs.add(releaseIncludeConstants[currAdd.ordinal()]);
            }
          }
        } else {
          incs.add(releaseIncludeConstants[curr.ordinal()]);
        }
      }
      incStr = "&inc=";
      for (int i = 0; i < incs.size(); i++) {
        String inc = incs.get(i);
        incStr += inc + (i + 1 < incs.size() ? "+" : "");
      }
    }
    return incStr;

  }

  public static void main(
      String[] args) {
    try {
      if (args.length == 0) {
        System.out.println("Expected device name");
        System.exit(-1);
      }

      DiscInfo info = DiscInfo.read(args[0]);
      System.out.println("libdiscid read:\n" + info);

      MusicBrainiac mb = new MusicBrainiac();
      Release release = mb.getReleaseByDiscId(info.discid, ReleaseInclude.AllImpersonal);
      if (release == null)
        System.out.println("Release not found for id: " + info.discid);
      else {
        System.out.println(release);
        System.out.println("Title: " + release.getTitle());
        System.out.println("Artist: " + release.getArtist().getName());
        System.out.println("Tracks");
        List<Track> tracks = release.getTracks();
        for (int i = 0; i < tracks.size(); i++) {
          Track curr = tracks.get(i);
          System.out.println("\t" + (i + 1) + ": " + curr.getTitle());
        }
      }
      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

}
