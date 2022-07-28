/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Combobox;

import BASEDATO.EvenConexion;
import Evento.Mensaje.EvenMensajeJoptionpane;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class EvenCombobox {
    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evmen=new EvenMensajeJoptionpane();
    public void setSeleccionarCombobox(JComboBox combo,int id,String nombre){
        String titulo="setSeleccionarCombobox";
        try {
            combo.setSelectedItem(nombre+"-("+id+")");
        } catch (Exception e) {
            evmen.mensaje_error(e, titulo);
        }
        
    }
    public void cargarCombobox(Connection conn,JComboBox combo,String id,String nombre,String tabla,String where){  
        String titulo="cargarCombobox";
      DefaultComboBoxModel model = (DefaultComboBoxModel) combo.getModel();
      model.removeAllElements();//eliminamos todo antes de cargar
      String sql="select ("+nombre+"||'-('||"+id+"||')') as nombre from "+tabla+" "+where+";";
        try{
            ResultSet rs=eveconn.getResulsetSQL(conn, sql, titulo);
            combo.addItem("-SELECCIONAR-"); 
            while (rs.next()){
                combo.addItem(rs.getObject("nombre")); 
            }
//            System.out.println("---Cargade de Jcombobox sin error su sql es ---:"+sql);
        }catch(Exception e){
            evmen.mensaje_error(e, sql, titulo);
        }
    }  
    public void setSeleccionarCombobox(Connection conn,JComboBox combo,String id,String nombre,String tabla,int idselect){  
        String titulo="setSeleccionarCombobox";
      String sql="select ("+nombre+"||'-('||"+id+"||')') as nombre from "+tabla+" where "+id+"="+idselect+" ;";
        try{
            ResultSet rs=eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()){
                combo.setSelectedItem(rs.getString("nombre")); 
            }
        }catch(Exception e){
            evmen.mensaje_error(e, sql, titulo);
        }
    } 
    public int getInt_seleccionar_COMBOBOX(Connection conn,JComboBox cmbnom,String id,String nombre,String tabla) {
        int getcampo=0;
            String titulo="getInt_seleccionar_JLista";
            String select=cmbnom.getSelectedItem().toString();
            String sql = "SELECT * FROM "+tabla+" where concat("+nombre+",'-(',"+id+",')')='"+select+"';";
            try {
                ResultSet rs=eveconn.getResulsetSQL(conn, sql, titulo);
                if (rs.next()) {
                    getcampo=rs.getInt(id);
                }
            } catch (Exception e) {
                evmen.mensaje_error(e, sql, titulo);
            }
        return getcampo;
    }
    public boolean getBoo_JCombobox_seleccionar(JComboBox cmbnom, String mensaje) {
        if (cmbnom.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(cmbnom, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
            cmbnom.setBackground(Color.RED);
            cmbnom.grabFocus();
            return true;
        } else {
            cmbnom.setBackground(Color.WHITE);
            return false;
        }
    }
}
