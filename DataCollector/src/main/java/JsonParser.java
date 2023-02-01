import Core.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonParser {

    public String getJsonFile(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        List<String> lines = Files.readAllLines(Paths.get(path));
        lines.forEach(builder::append);
        return builder.toString();
    }

    public void parser(String jsonFile, List<Station> allStations) throws Exception {
        JSONParser parser = new JSONParser();
        JSONArray stationsDepth = (JSONArray) parser.parse(jsonFile);
        stationsDepth.forEach(stationDepth -> parseDepth((JSONObject) stationDepth, allStations));
    }

    private void parseDepth(JSONObject jsonObject, List<Station> allStations) {
        allStations.forEach(station -> {
            String jsonName = (String) jsonObject.get("station_name");
            String depth = (String) jsonObject.get("depth");
            double d;
            if (!depth.equals("?")) {
                d = Double.parseDouble(depth.replace(",", "."));
            } else {
                d = 5000.0;
            }
            if (station.getName().replace("ё", "е")
                    .equalsIgnoreCase(jsonName.replace("ё", "е"))
                    && station.getDepth() == 5000.0) {
                station.setDepth(d);
            } else if (station.getName().replace("ё", "е")
                    .equalsIgnoreCase(jsonName.replace("ё", "е"))
                    && station.getDepth() != 5000.0
                    && !jsonObject.get("depth").equals("?")
                    &&  station.getDepth() > d) {
                station.setDepth(d);
            }
        });
    }
}
