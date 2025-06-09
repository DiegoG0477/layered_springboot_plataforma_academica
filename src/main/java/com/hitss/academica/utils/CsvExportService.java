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

    /**
     * Escribe una lista de objetos DTO a un Writer en formato CSV.
     * Mapea automáticamente los campos del bean a las columnas.
     * @param writer El Writer donde se escribirá el CSV (ej. response.getWriter()).
     * @param data La lista de DTOs a exportar.
     * @param <T> El tipo del DTO.
     */
    public <T> void writeDataToCsv(Writer writer, List<T> data) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        if (data == null || data.isEmpty()) {
            return;
        }
        try {
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    // .withSeparator(CSVWriter.DEFAULT_SEPARATOR) // Puedes cambiar el separador si lo necesitas
                    .build();
            beanToCsv.write(data);
        } catch (Exception e) {
            throw new RuntimeException("Error al escribir los datos del bean a CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Escribe una cabecera y una lista de arrays de strings a un Writer.
     * Útil para reportes con estructuras dinámicas o personalizadas.
     * @param writer El Writer donde se escribirá el CSV.
     * @param headers El array con los nombres de las columnas.
     * @param lines La lista de arrays, donde cada array representa una fila.
     */
    public void writeRawDataToCsv(Writer writer, String[] headers, List<String[]> lines) {
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(headers);
            csvWriter.writeAll(lines);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir los datos crudos a CSV: " + e.getMessage(), e);
        }
    }
}