package com.bpathfinder;

import com.bpathfinder.dto.TracePoint;
import com.bpathfinder.dto.TrackingRecord;
import com.bpathfinder.parsers.CsvParser;
import com.bpathfinder.parsers.ObjectsToCsv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleClient {
    public static void main(String[] args) {
        CsvParser csvParser = new CsvParser();
        List<TrackingRecord> trackingRecords = csvParser.parse();

        List<TrackingRecord> flatTime = new ArrayList<TrackingRecord>();

        // the idea is to consider event that happen in 1 second as happening at the same time
        trackingRecords.stream().forEach( trackingRecord -> {
            if (flatTime.size() >= 1) {
                if (flatTime.get(0).getTime() + 1 > trackingRecord.getTime()) {
                    flatTime.add(trackingRecord);
                }
                else {
                    computeFlatTimeUnit(flatTime);
                    while (flatTime.size() > 0 && flatTime.get(0).getTime() + 1 < trackingRecord.getTime()) {
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

        pathTraces.stream().forEach(System.out::println);
        System.out.println("Computer some triangulated points!");

        ObjectsToCsv objectsToCsv = new ObjectsToCsv();
        objectsToCsv.writePojoToCsv(pathTraces);
    }

    private static List<TracePoint> pathTraces = new ArrayList<TracePoint>();

    private static void computeFlatTimeUnit(List<TrackingRecord> trackingRecords) {
        if (trackingRecords.stream().distinct().count() >= 3) {
            System.out.println("Can do triangulation!");

            double x = 0, y = 0, time = 0;
            int counter = 0;

            Iterator<TrackingRecord> distinctPoints = trackingRecords.stream().distinct().collect(Collectors.toList()).iterator();
            while (distinctPoints.hasNext()) {
                TrackingRecord tr = distinctPoints.next();
                x += tr.getxAxis();
                y += tr.getyAxis();
                time += tr.getTime();
                counter++;
            };

            pathTraces.add(new TracePoint(time/counter, x/counter, y/counter));

        }
    }
}
