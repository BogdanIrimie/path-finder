package com.bpathfinder.parsers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.bpathfinder.dto.TracePoint;

import java.io.*;
import java.util.List;

public class ObjectsToCsv {

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
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
