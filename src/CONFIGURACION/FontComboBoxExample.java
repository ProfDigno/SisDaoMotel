/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONFIGURACION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FontComboBoxExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ComboBox de Tipos de Fuentes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JPanel panel = new JPanel();
            
            // Obtener la lista de tipos de fuentes disponibles
            String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            
            // Crear un JComboBox y agregar los tipos de fuentes
            JComboBox<String> comboBox = new JComboBox<>(fontNames);
            
            // Agregar un ActionListener para manejar la selección
            comboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JComboBox<?> source = (JComboBox<?>) e.getSource();
                    String selectedFont = (String) source.getSelectedItem();
                    System.out.println("Tipo de fuente seleccionado: " + selectedFont);
                }
            });
            
            panel.add(comboBox);
            frame.add(panel);
            
            frame.setPreferredSize(new Dimension(300, 100));
            frame.pack();
            frame.setVisible(true);
        });
    }
}
