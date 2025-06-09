package com.hitss.academica.utils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
public class CsvExportService {

    /**
     * Convierte una lista de objetos (DTOs) a un string en formato CSV.
     * @param data La lista de DTOs a exportar.
     * @return Un String con el contenido del CSV.
     */
    public <T> String writeDataToCsv(List<T> data) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        if (data == null || data.isEmpty()) {
            return "";
        }

        StringWriter writer = new StringWriter();

        // Usamos StatefulBeanToCsv para mapear automáticamente los campos del DTO a las columnas
        StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();

        beanToCsv.write(data);

        return writer.toString();
    }
}