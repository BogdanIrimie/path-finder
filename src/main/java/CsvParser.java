import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import dto.TrackingDevicePoint;
import dto.TrackingRecord;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParser {

    public List<TrackingRecord> parse() {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(',');
        File tpFile = new File("trackingRecords.csv");


        MappingIterator<TrackingRecord> tpIterator = null;
        List<TrackingRecord> trackingRecordList = null;
        try {
            tpIterator = mapper.reader(TrackingRecord.class).with(schema).readValues(tpFile);
            trackingRecordList = tpIterator.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get tracking points from tracking records
        List<TrackingDevicePoint> listOfTrackingDevicePoints = trackingRecordList.stream().distinct()
                .map(f -> new TrackingDevicePoint(f.getxAxis(), f.getyAxis()))
                .collect(Collectors.toList());


        return trackingRecordList;
    }

    public static void main(String[] args) {
        CsvParser csvParser = new CsvParser();
        csvParser.parse();
    }
}
