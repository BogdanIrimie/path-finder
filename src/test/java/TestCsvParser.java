import com.bpathfinder.dto.TrackingRecord;
import com.bpathfinder.parsers.CsvParser;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TestCsvParser {

    @Test
    public void testParser() {
        CsvParser csvParser = new CsvParser();
        List<TrackingRecord> trackingRecords = csvParser.parse();
        assertTrue(trackingRecords.stream().count() == 999);
    }
}
