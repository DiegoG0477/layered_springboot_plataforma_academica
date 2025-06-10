package com.hitss.academica.utils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class CsvExportService {

    public <T> void writeDataToCsv(Writer writer, List<T> data) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        if (data == null || data.isEmpty()) {
            return;
        }
        try {
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(data);
        } catch (Exception e) {
            throw new RuntimeException("Error al escribir los datos del bean a CSV: " + e.getMessage(), e);
        }
    }

    public void writeRawDataToCsv(Writer writer, String[] headers, List<String[]> lines) {
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(headers);
            csvWriter.writeAll(lines);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir los datos crudos a CSV: " + e.getMessage(), e);
        }
    }
}