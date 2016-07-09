import dto.TrackingDevicePoint;
import dto.TrackingRecord;
import eu.jacquet80.minigeo.MapWindow;
import eu.jacquet80.minigeo.POI;
import eu.jacquet80.minigeo.Point;
import eu.jacquet80.minigeo.Segment;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Client extends JPanel {

    public static void main(String[] args) {
        MapWindow window = new MapWindow();
//        window.addPOI(new POI(new Point(48.8567, 2.3508), "Paris"));
//        window.addPOI(new POI(new Point(28.8567, 2.3508), "Berlin"));
//        window.addPOI(new POI(new Point(48.8567, 52.3508), "Munched"));
        window.setVisible(true);

        window.addSegment(new Segment(new Point(0, 0), new Point(100, 100), Color.WHITE));
        CsvParser csvParser = new CsvParser();
        List<TrackingRecord> trackingRecords = csvParser.parse();
        drawTrackingPoints(trackingRecords, window);


        List<TrackingRecord> flatTime = new ArrayList<TrackingRecord>();

        // the idea is to consider event that happen in 1 second as happening at the same time
        trackingRecords.stream().forEach( trackingRecord -> {
           if (flatTime.size() >= 1) {
               if (flatTime.get(0).getTime() + 1 > trackingRecord.getTime()) {
                   flatTime.add(trackingRecord);
               }
               else {
                   computeFlatTimeUnit();
                   while (flatTime.get(0).getTime() + 1 < trackingRecord.getTime()) {
                       flatTime.remove(0);
                   }
                   flatTime.add(trackingRecord);
               }
           }
           else {
               flatTime.add(trackingRecord);
           }
            System.out.println("Time flattened!");
        });
    }

    private static void computeFlatTimeUnit() {
    }


    public static void drawTrackingPoints(List<TrackingRecord> trackingRecordList, MapWindow mapWindow) {
        // get tracking points from tracking records
        trackingRecordList.stream().distinct()
                .map(f -> new TrackingDevicePoint(f.getxAxis(), f.getyAxis()))
                .forEach(trackingPoint -> {
                    mapWindow.addPOI(new POI(new Point(trackingPoint.getxAxis(), trackingPoint.getyAxis()), trackingPoint.getxAxis() + ""));
                });
    }
}