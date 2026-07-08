package com.ejemplo.excel;

import com.ejemplo.database.SumaDAO;
import com.ejemplo.model.Resultado;

import org.apache.poi.ss.usermodel.*;
import  org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {

    /**
     * Lee el archivo Excel y devuelve una lista con todas las filas.
     */
    public List<Object[]> leerExcel(File archivo) {

        List<Object[]>  datos = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(archivo);
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet hoja = workbook.getSheetAt(0);

            SumaDAO dao = new SumaDAO();
            int numeroFila = 1;

            for (Row fila : hoja) {
                if (numeroFila > 10)
                    break;

                List<Object> columnas = new ArrayList<>();
                double suma = 0;

                for (Cell celda : fila) {

                    switch (celda.getCellType()) {

                        case NUMERIC:
                            double valor = celda.getNumericCellValue();
                            columnas.add(valor);
                            suma += valor;

                            break;
                        case STRING:
                            columnas.add(celda.getStringCellValue());

                            break;
                        default:

                            columnas.add("");
                    }
                }

                columnas.add(suma);
                datos.add(columnas.toArray());

                Resultado resultado =
                        new Resultado(numeroFila, suma);

                dao.guardar(resultado);
                numeroFila++;
            }
            workbook.close();
            fis.close();
        } catch (Exception e) {

            e.printStackTrace();

        }

        return datos;
    }   
}
