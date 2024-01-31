/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONFIGURACION;

import java.awt.GraphicsEnvironment;

public class ListFontsInWindows {
    public static void main(String[] args) {
        // Obtén la lista de tipos de fuentes disponibles en Windows
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        // Imprime la lista de tipos de fuentes
        System.out.println("Tipos de fuentes disponibles en Windows:");
        for (String fontName : fontNames) {
            System.out.println(fontName);
        }
    }
}