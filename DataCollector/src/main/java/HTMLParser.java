import Core.Line;
import Core.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.*;

public class HTMLParser {

    private  List<Line> allLines = new ArrayList<>();
    private  List<Station> allStations = new ArrayList<>();
    private Map<Station, HashSet<Station>> allConnections = new HashMap<>();

    public void parse(String url) throws Exception{
        Document doc = Jsoup.connect(url).get();
        addLines(doc);
        addStation(doc);
        addConnections();
    }

    private void addLines(Document doc) {
        Elements lines = doc.select(".js-metro-line");
        lines.forEach(l -> {
            Line line = new Line(l.attr("data-line"), l.text());
            allLines.add(line);
        });
    }

    private void addStation(Document doc) {
        allLines.forEach(line -> {
            Elements stations = doc.select("div.js-metro-stations[data-line=\"" + line.getNumber() +
                    "\"]").select("span.name");
            stations.forEach(s -> {
                Station station = new Station(s.text(), line);
                allStations.add(station);
                line.addStations(station);
                Elements connectionInfo = s.parent().select("span.t-icon-metroln");
                connectionInfo.forEach(connect -> {
                    String connection = connect.attr("title");
                    station.addConnection(connection);
                });
            });
        });
    }

    private void addConnections() {
        for (Station station : allStations) {
            if (station.getConnection().isEmpty()) {
                continue;
            }
            if (!allConnections.containsKey(station)) {
                allConnections.put(station, new HashSet<>());
            }
            station.getConnection().forEach(connect -> {
                String[] fragments = connect.split("«|»");
                String stationConnect = fragments[1];
                allStations.forEach(s -> {
                    if (s.getName().equals(stationConnect) && !s.equals(station)) {
                        HashSet<Station> connectedStations = allConnections.get(station);
                        connectedStations.add(s);
                    }
                });

            });
        }
    }

    public List<Line> getAllLines() {
        return allLines;
    }

    public List<Station> getAllStations() {
        return allStations;
    }

    public Map<Station, HashSet<Station>> getAllConnections() {
        return allConnections;
    }

}
