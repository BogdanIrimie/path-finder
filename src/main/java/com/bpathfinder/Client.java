package com.bpathfinder;

import com.bpathfinder.dto.TrackingRecord;
import com.bpathfinder.csvmanipulation.CsvToTrackingRecords;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        // parse CSV
        CsvToTrackingRecords csvToTrackingRecords = new CsvToTrackingRecords();
        List<TrackingRecord> trackingRecords = csvToTrackingRecords.parse();

        TraceProcessor traceProcessor = new TraceProcessor();
        traceProcessor.processTraces(trackingRecords);
    }

}
