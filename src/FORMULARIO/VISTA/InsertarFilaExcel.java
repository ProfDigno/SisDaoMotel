/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class InsertarFilaExcel {

    public static void main(String[] args) {
        String filePath = "C:/Users/Digno/Documents/NetBeansProjects/SisDaoMotel/APPSHEET/EXCEL/ArchivoCarga.xlsx";

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Obtén la hoja en la que deseas insertar la fila

            // Crear una nueva fila
            int rowNum = sheet.getLastRowNum() + 1;
            Row newRow = sheet.createRow(rowNum);

            // Insertar datos en la nueva fila
            Cell cell1 = newRow.createCell(0); // Celda 1 en la nueva fila
            cell1.setCellValue("Dato1");

            Cell cell2 = newRow.createCell(1); // Celda 2 en la nueva fila
            cell2.setCellValue("Dato2");

            // Guardar los cambios en el archivo
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            System.out.println("Fila insertada con éxito.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
