package Core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Station implements Comparable<Station> {
    private Line line;
    private String name;
    private List<String> listConnections = new ArrayList<>();
    private double depth = 5000.0;
    private Date openDate;

    public Station(String name, Line line) {
        this.name = name;
        this.line = line;
    }

    public void addConnection(String connection) {
        listConnections.add(connection);
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public void setOpenDate(Date date) {
        openDate = date;
    }

    public Line getLine()
    {
        return line;
    }

    public String getName()
    {
        return name;
    }

    public double getDepth() { return depth; }

    public Date getOpenDate() {
        return openDate; }

    public List<String> getConnection() { return listConnections; }

    @Override
    public String toString() {
        return  name;
    }

    @Override
    public int compareTo(Station station)
    {
        int lineComparison = line.compareTo(station.getLine());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(station.getName());
    }
}

