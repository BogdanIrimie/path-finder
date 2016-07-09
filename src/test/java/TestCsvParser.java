import com.bpathfinder.dto.TrackingRecord;
import com.bpathfinder.parsers.CsvToTrackingRecords;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TestCsvParser {

    @Test
    public void testParser() {
        CsvToTrackingRecords csvToTrackingRecords = new CsvToTrackingRecords();
        List<TrackingRecord> trackingRecords = csvToTrackingRecords.parse();
        assertTrue(trackingRecords.stream().count() == 999);
    }
}
