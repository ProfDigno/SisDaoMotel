/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import BASEDATO.EvenConexion;
//import Config_JSON.json_config;
import Config_JSON.json_array_imprimir_pos;
import Evento.Mensaje.EvenMensajeJoptionpane;
//import FORMULARIO.DAO.DAO_empresa;
//import FORMULARIO.ENTIDAD.empresa;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Digno
 */
public class PosImprimir_Garantia {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static json_array_imprimir_pos jsprint = new json_array_imprimir_pos();
    private static String tk_ruta_archivo = "ticket_garantia.txt";
    private static FileInputStream inputStream = null;
//    private static int tk_iv_fila = 0;
    private String nombre_ticket = "ticket Garantia";
    private static String v1_idv = "0";
    private static String v2_fec_ini = "0";
//    private static String v3_fec_fin = "0";
    private static String v4_responsable="";
    private static String v5_descripcion;
    private static String v13_m_pagar;
    private static String v14_usuario;

    private void cargar_datos_garantia(Connection conn, int idgarantia) {
        String titulo = "cargar_datos_venta";
        String sql = "SELECT idgarantia as idg,\n"
                + "to_char(fecha_inicio,'yyyy-MM-dd HH24:MI') as f_inicio,\n"
                + "to_char(fecha_fin,'yyyy-MM-dd HH24:MI') as f_fin,\n"
                + "responsable,descripcion_objeto as descripcion,\n"
                + "TRIM(to_char(monto_garantia,'999G999G999')) as m_pagar,\n"
                + "estado,fk_idventa as idv,creado_por as usuario \n"
                + "FROM garantia where idgarantia=" + idgarantia;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                v1_idv = rs.getString("idg");
                v2_fec_ini = rs.getString("f_inicio");
//                v3_fec_fin = rs.getString("f_fin");
                v4_responsable = rs.getString("responsable");
                v5_descripcion = rs.getString("descripcion");
                v13_m_pagar = rs.getString("m_pagar");
                v14_usuario = rs.getString("usuario");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
//        int totalfila = jsprint.getTt_fila_ven() + (tk_iv_fila * 2);
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        String separador = "==========================================";
        mensaje_impresora = mensaje_impresora + "===============" + jsprint.getEmp_nombre() + "================" + saltolinea;
        mensaje_impresora = mensaje_impresora + jsprint.getEmp_telefono() + saltolinea;
        mensaje_impresora = mensaje_impresora + jsprint.getEmp_direccion() + saltolinea;
        mensaje_impresora = mensaje_impresora + "COD-GARANTIA:" + v1_idv + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA: " + v2_fec_ini + saltolinea;
        mensaje_impresora = mensaje_impresora + "RESPONSABLE: " + saltolinea+ v4_responsable + saltolinea;
        mensaje_impresora = mensaje_impresora + "DESCRIPCION:" + saltolinea+ v5_descripcion + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO:" + saltolinea+v14_usuario + saltolinea;
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL PAGAR : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + v13_m_pagar + saltolinea;
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + saltolinea+ saltolinea+ saltolinea+ saltolinea;
        mensaje_impresora = mensaje_impresora + "FIRMA Y ACLARACION" + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_ven() ;
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + jsprint.getEmp_nombre() + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, jsprint.getEmp_telefono());
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, jsprint.getEmp_direccion());
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "COD. VENTA:" + v1_idv);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, "FECHA:" + v2_fec_ini);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, "RESPONSABLE:" + v4_responsable);
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, "DESCRIPCION:" + v5_descripcion);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, "USUARIO:" + v14_usuario);
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, "TOTAL PAGAR:");
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_total_gral(), totalColumna, v13_m_pagar);
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(20 + tempfila, 20, jsprint.getSep_inicio(), totalColumna, "FIRMA Y ACLARACION");
        printer.toFile(tk_ruta_archivo);
        try {
            inputStream = new FileInputStream(tk_ruta_archivo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }
        if (inputStream == null) {
            return;
        }

    }

    private void crear_archivo_enviar_impresora() {
        if (jsprint.isError_carga_json()) {
            String titulo = "crear_archivo_enviar_impresora";
            try {
                crear_archivo_texto_impresion();
                pos.setInputStream(inputStream);
                pos.imprimir_ticket_Pos();
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
            }
        } else {
            jsprint.cargar_jsom_imprimir_pos();
        }
    }

    private void crear_mensaje_textarea_y_confirmar() {
        JTextArea ta = new JTextArea(30, 30);
        ta.setText(cargar_datos_para_mensaje_textarea());
        System.out.println(cargar_datos_para_mensaje_textarea());
        Object[] opciones = {"IMPRIMIR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), tk_ruta_archivo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            crear_archivo_enviar_impresora();
        }
    }

    public void boton_imprimir_pos_GARANTIA(Connection conn, int idgarantia) {
        cargar_datos_garantia(conn, idgarantia);
        crear_mensaje_textarea_y_confirmar();
    }
}
