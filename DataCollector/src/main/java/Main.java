import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    private static HTMLParser htmlParser = new HTMLParser();
    private static FileSearcher fileSearcher = new FileSearcher();
    private static JsonParser jsonParser = new JsonParser();
    private static CSVParser csvParser = new CSVParser();
    private static JsonBuilder jsonBuilder = new JsonBuilder();

    private static final String URL = "https://skillbox-java.github.io";
    private static final String PATH = "data";
    private static final String JSON_REGEX = "[a-z]+-[0-9]\\.json";
    private static final String CSV_REGEX = "[a-z]+-[0-9]\\.csv";

    public static void main(String[] args) {
        try {
            htmlParse();
            fileSearchingAndParsing();
            jsonWrite();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void htmlParse() throws Exception{
        htmlParser.parse(URL);
    }

    private static void fileSearchingAndParsing() {
        File file = new File(PATH);
        fileSearcher.searchFiles(file);
        fileSearcher.getFileList().forEach(f -> {
            try {
                if (f.getName().matches(JSON_REGEX)) {
                    String jsonFile = jsonParser.getJsonFile(f.getPath());
                    jsonParser.parser(jsonFile, htmlParser.getAllStations());
                } else if (f.getName().matches(CSV_REGEX)) {
                    csvParser.parse(f.getPath(), htmlParser.getAllStations());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void jsonWrite() throws IOException {
        String jsonMap = jsonBuilder.buildJsonMap(htmlParser.getAllLines());
        String path1 = "map.json";
        PrintWriter out = new PrintWriter(new FileWriter(path1));
        out.write(jsonMap);
        out.flush();
        out.close();

        String jsonStations = jsonBuilder.buildJsonStationInfo(htmlParser.getAllStations());
        String path2 = "stations.json";
        PrintWriter output = new PrintWriter(new FileWriter(path2));
        output.write(jsonStations);
        output.flush();
        output.close();
    }
}
