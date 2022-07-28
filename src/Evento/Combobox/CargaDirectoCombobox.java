/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Combobox;

import Evento.Mensaje.EvenMensajeJoptionpane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Digno
 */
public class CargaDirectoCombobox {

    EvenMensajeJoptionpane evmen = new EvenMensajeJoptionpane();

    private void cargar_combobox(JComboBox combo, String datos[], String titulo) {
        try {
            DefaultComboBoxModel model = (DefaultComboBoxModel) combo.getModel();
            model.removeAllElements();//eliminamos todo antes de cargar
            for (String dato : datos) {
                combo.addItem(dato);
            }
        } catch (Exception e) {
            evmen.mensaje_error(e, titulo);
        }
    }

    public void cargarCombobox_via_transporte(JComboBox combo) {
        String titulo = "cargarCombobox_via_transporte";
        String datos[] = {"-SELECCIONAR-", "AEREO", "FLUVIAL", "MARITIMO", "TERRESTRE","MULTI-MODAL"};
        cargar_combobox(combo, datos, titulo);
    }
    public void cargarCombobox_modo_transporte(JComboBox combo) {
        String titulo = "cargarCombobox_via_transporte";
        String datos[] = {"-SELECCIONAR-", "SIN-TIPO", "L.C.L.", "F.C.L."};
        cargar_combobox(combo, datos, titulo);
    }
}
