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
public class PosImprimir_vale {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    ClaImpresoraPos pos = new ClaImpresoraPos();
    private static json_array_imprimir_pos jsprint = new json_array_imprimir_pos();
    private static String tk_ruta_archivo = "ticket_vale.txt";
    private static FileInputStream inputStream = null;
//    private static int tk_iv_fila = 0;
    private String nombre_ticket = "ticket Garantia";
    private static String v1_idv = "0";
    private static String v2_fecha = "0";
//    private static String v3_fec_fin = "0";
    private static String v4_personal = "";
    private static String v5_concepto;
    private static String v6_monto_letra;
    private static String v13_m_pagar;
    private static String v14_usuario;

    private void cargar_datos_vale(Connection conn, int idrh_vale) {
        String titulo = "cargar_datos_venta";
        String sql = "select v.idrh_vale as idv,to_char(v.fecha_creado,'dd-MM-yyyy') as fecha,\n"
                + "v.creado_por as usuario,\n"
                + "v.descripcion as concepto,v.monto_letra as letra,\n"
                + "v.monto_vale as dmonto,TRIM(to_char(v.monto_vale,'999G999G999')) as smonto,\n"
                + "p.nombre as personal \n"
                + "from rh_vale v,persona p\n"
                + "where v.fk_idpersona=p.idpersona\n"
                + "and v.idrh_vale=" + idrh_vale;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                v1_idv = rs.getString("idv");
                v2_fecha = rs.getString("fecha");
                v4_personal = rs.getString("personal");
                v6_monto_letra = rs.getString("letra");
                v5_concepto = rs.getString("concepto");
                v13_m_pagar = rs.getString("smonto");
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
        mensaje_impresora = mensaje_impresora + "####-RECIBO-####" + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO:" + saltolinea + v14_usuario + saltolinea;
        mensaje_impresora = mensaje_impresora + "VALE:" + v1_idv + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA: " + v2_fecha + saltolinea;
        mensaje_impresora = mensaje_impresora + ">>-----RECIBIDO POR:" + saltolinea + v4_personal + saltolinea;
        mensaje_impresora = mensaje_impresora + ">>-----RECIBIDO DE:" + saltolinea + jsprint.getEmp_nombre() + saltolinea;
        mensaje_impresora = mensaje_impresora + ">>-----CONCEPTO DE:" + saltolinea + v5_concepto + saltolinea;
        
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL PAGAR : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + v13_m_pagar + saltolinea;
        mensaje_impresora = mensaje_impresora + "LETRA : " + saltolinea;
        mensaje_impresora = mensaje_impresora + tabular + tabular + v6_monto_letra + saltolinea;
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + saltolinea + saltolinea + saltolinea + saltolinea;
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
        int totalfila = jsprint.getTt_fila_ven();
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, "####-RECIBO-####");
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, "USUARIO:" + v14_usuario);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "VALE:" + v1_idv);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, "FECHA:" + v2_fecha);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, ">>-----RECIBIDO POR:");
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, v4_personal);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, ">>-----RECIBIDO DE:");
        printer.printTextWrap(9 + tempfila, 9, jsprint.getSep_inicio(), totalColumna, jsprint.getEmp_nombre());
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, ">>-----CONCEPTO DE:");
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna,  v5_concepto);
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(13 + tempfila, 13, jsprint.getSep_inicio(), totalColumna, "TOTAL PAGAR:");
        printer.printTextWrap(14 + tempfila, 14, jsprint.getSep_total_gral(), totalColumna, v13_m_pagar);
        printer.printTextWrap(15 + tempfila, 15, jsprint.getSep_inicio(), totalColumna, "LETRA:");
        printer.printTextWrap(16 + tempfila, 16, jsprint.getSep_inicio(), totalColumna, v6_monto_letra);
        printer.printTextWrap(17 + tempfila, 17, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
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

    public void boton_imprimir_pos_vale(Connection conn, int idrh_vale) {
        cargar_datos_vale(conn, idrh_vale);
        crear_mensaje_textarea_y_confirmar();
    }
}
