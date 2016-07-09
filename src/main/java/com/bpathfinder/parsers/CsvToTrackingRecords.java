package com.bpathfinder.parsers;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.bpathfinder.dto.TrackingDevice;
import com.bpathfinder.dto.TrackingRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parse CSV file and extract list of TrackingRecords.
 */
public class CsvToTrackingRecords {

    public static final Logger logger = LoggerFactory.getLogger(CsvToTrackingRecords.class);

    public List<TrackingRecord> parse() {
        MappingIterator<TrackingRecord> tpIterator = null;
        List<TrackingRecord> trackingRecordList = null;

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
        File tpFile = new File("traces/in_trackingRecords.csv");

        try {
            tpIterator = mapper.reader(TrackingRecord.class).with(schema).readValues(tpFile);
            trackingRecordList = tpIterator.readAll();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return trackingRecordList;
    }

}
