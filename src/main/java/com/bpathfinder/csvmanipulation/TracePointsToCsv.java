package com.bpathfinder.csvmanipulation;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.bpathfinder.dto.TracePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Save TracePoint array to CSV.
 */
public class TracePointsToCsv {

    private static final Logger logger = LoggerFactory.getLogger(TracePointsToCsv.class);

    public void writePojoToCsv(List<TracePoint> tracePointList) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(TracePoint.class);
        schema = schema.withColumnSeparator(',');

        // output writer
        ObjectWriter myObjectWriter = mapper.writer(schema);
        File tempFile = new File("traces/out_tracePoints.csv");
        FileOutputStream tempFileOutputStream = null;
        try {
            tempFileOutputStream = new FileOutputStream(tempFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream);
            OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
            myObjectWriter.writeValue(writerOutputStream, tracePointList);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
