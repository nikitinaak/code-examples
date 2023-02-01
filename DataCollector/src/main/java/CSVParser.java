import Core.Station;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CSVParser {

    public void parse(String path, List<Station> allStations) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        lines.forEach(line -> {
            String firstLine = null;
            if (firstLine == null) {
                firstLine = line;
            }
            String[] fragments = line.split(",");
            allStations.forEach(station -> {
                if (station.getName().replace("ё", "е")
                        .equalsIgnoreCase(fragments[0].replace("ё",
                                "е"))
                        && station.getOpenDate() == null) {
                    try {
                        station.setOpenDate((new SimpleDateFormat("dd.MM.yyyy")).parse(fragments[1]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }
}
