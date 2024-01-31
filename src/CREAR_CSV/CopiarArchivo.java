/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CREAR_CSV;

/**
 *
 * @author Digno
 */
import java.io.File;
import java.io.IOException;

public class CopiarArchivo {
    public static void main(String[] args) {
        // Ruta del archivo de origen
        File origen = new File("C:\\CSV\\caja_temp.csv");

        // Ruta de la carpeta de destino
        File destino = new File("C:\\Users\\Digno\\Documents\\NetBeansProjects\\SisDaoMotel\\CSV\\");

        try {
            // Copiar el archivo al destino
            java.nio.file.Files.copy(origen.toPath(), destino.toPath().resolve(origen.getName()));
            System.out.println("Archivo copiado con éxito.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al copiar el archivo: " + e.getMessage());
        }
    }
}
