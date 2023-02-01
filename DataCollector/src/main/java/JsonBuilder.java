import Core.Line;
import Core.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonBuilder {

    public String buildJsonMap(List<Line> allLines) {
        JSONObject jsonObject = new JSONObject();
        LinkedHashMap stationsMap = new LinkedHashMap<>();
        JSONArray jsonLinesArray = new JSONArray();
        allLines.forEach(line -> {
            JSONArray jsonStationsArray = new JSONArray();
            line.getStations().forEach(station -> jsonStationsArray.add(station.getName()));
            stationsMap.put(line.getNumber(), jsonStationsArray);
            JSONObject jsonLineInfo = new JSONObject();
            jsonLineInfo.put("number", line.getNumber());
            jsonLineInfo.put("name", line.getName());
            jsonLinesArray.add(jsonLineInfo);
        });
        jsonObject.put("stations", stationsMap);
        jsonObject.put("lines", jsonLinesArray);
        return jsonObject.toJSONString();
    }

    public String buildJsonStationInfo(List<Station> allStations) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        allStations.forEach(station -> {
            LinkedHashMap infoMap = new LinkedHashMap<>();
            infoMap.put("name", station.getName());
            infoMap.put("line", station.getLine().getName());
            if (station.getOpenDate() != null) {
                infoMap.put("date",
                        new SimpleDateFormat("dd.MM.yyyy").format(station.getOpenDate()));
            }
            if (station.getDepth() != 5000.0) {
                infoMap.put("depth", station.getDepth());
            }
            boolean connection = !station.getConnection().isEmpty();
            infoMap.put("hasConnection", connection);

            jsonArray.add(infoMap);
        });
        jsonObject.put("stations", jsonArray);
        return jsonObject.toJSONString();
    }
}
