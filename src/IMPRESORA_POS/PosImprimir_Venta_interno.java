/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IMPRESORA_POS;

import BASEDATO.EvenConexion;
import Config_JSON.json_array_imprimir_pos;
//import Config_JSON.json_config;
//import Config_JSON.json_imprimir_pos;
import Evento.Mensaje.EvenMensajeJoptionpane;
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
public class PosImprimir_Venta_interno {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
//    private static json_config config = new json_config();
    ClaImpresoraPos pos = new ClaImpresoraPos();
//    private static json_imprimir_pos jsprint = new json_imprimir_pos();
    private static json_array_imprimir_pos jsprint = new json_array_imprimir_pos();
    private static String v1_idventa_interna = "0";
    private static String v2_fecha = "0";
    private static String v3_usuario = "0";
    private static String v6_monto = "0";
    private static String v7_observacion = "0";
    private static String v8_cliente = "oca";
    private static String tk_nombre_empresa = jsprint.getEmp_nombre();
    private static String tk_ruta_archivo = "ticket_venta_interna.txt";
    private static int dato_vector = 500;
    private static String[] iv1_cantidad = new String[dato_vector];
    private static String[] iv2_precio = new String[dato_vector];
    private static String[] iv3_total = new String[dato_vector];
    private static String[] iv4_descripcion = new String[dato_vector];
    private static FileInputStream inputStream = null;
    private static int tk_iv_fila;
    private static String nombre_ticket = "VENTA INTERNA";

    private void cargar_datos_venta_interno(Connection conn, int idventa_interno) {
        String titulo = "cargar_datos_venta_interno";
        String sql = "select vi.idventa_interno as idventa_interno,\n"
                + "to_char(vi.fecha_creado,'yyyy-MM-dd HH24:MI') as fecha, \n"
                + "vi.observacion,u.nombre as usuario,\n"
                + "TRIM(to_char(vi.monto_interno,'999G999G999')) as monto,\n"
                + "(vii.fk_idproducto||'-'||vii.descripcion) as producto,\n"
                + "TRIM(to_char(vii.precio_venta,'999G999G999')) as precio,\n"
                + "TRIM(to_char(vii.cantidad,'9G999')) as cantidad,\n"
                + "TRIM(to_char((vii.cantidad*vii.precio_venta),'999G999G999'))  as subtotal,"
                + "p.nombre as cliente \n"
                + "from venta_interno vi,usuario u,venta_item_interno vii,persona p \n"
                + "where vi.fk_idusuario=u.idusuario \n"
                + "and vi.fk_idpersona=p.idpersona \n"
                + "and vi.idventa_interno=vii.fk_idventa_interno\n"
                + "and vi.idventa_interno=" + idventa_interno
                + " order by vii.descripcion desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila = 0;
            while (rs.next()) {
                v1_idventa_interna = rs.getString("idventa_interno");
                v2_fecha = rs.getString("fecha");
                v3_usuario = rs.getString("usuario");
                v6_monto = rs.getString("monto");
                v7_observacion = rs.getString("observacion");
                v8_cliente = rs.getString("cliente");
                iv1_cantidad[tk_iv_fila] = rs.getString("cantidad");
                iv2_precio[tk_iv_fila] = rs.getString("precio");
                iv3_total[tk_iv_fila] = rs.getString("subtotal");
                iv4_descripcion[tk_iv_fila] = rs.getString("producto");
                tk_iv_fila++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
        String mensaje_impresora = "";
        String saltolinea = "\n";
        String tabular = "\t";
        mensaje_impresora = mensaje_impresora + "=========NOTA DE VENTA INTERNA==========" + saltolinea;
        mensaje_impresora = mensaje_impresora + "ID:" + v1_idventa_interna + saltolinea;
        mensaje_impresora = mensaje_impresora + "FECHA: " + v2_fecha + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO: " + v3_usuario + saltolinea;
        mensaje_impresora = mensaje_impresora + "CLIENTE: " + v8_cliente + saltolinea;
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "Descripcion" + tabular + saltolinea;
        mensaje_impresora = mensaje_impresora + "Cant" + tabular  +  "Precio" + tabular +  "Subtotal" + saltolinea;
        for (int i = 0; i < tk_iv_fila; i++) {
            mensaje_impresora = mensaje_impresora + iv4_descripcion[i] + saltolinea;
            String item =  iv1_cantidad[i] + tabular +  iv2_precio[i] + tabular +  iv3_total[i] + saltolinea;
            mensaje_impresora = mensaje_impresora + item;
        }
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
        mensaje_impresora = mensaje_impresora + "OBS: " + v7_observacion + saltolinea;
        mensaje_impresora = mensaje_impresora + "TOTAL :" + tabular + tabular + v6_monto + saltolinea;
        return mensaje_impresora;
    }

    private static void crear_archivo_texto_impresion() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = jsprint.getTotal_columna();
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = jsprint.getTt_fila_com_in() + (tk_iv_fila * 2);
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + nombre_ticket + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, "ID:" + v1_idventa_interna);
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_fecha(), totalColumna, "FEC:" + v2_fecha);
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, "USU: " + v3_usuario);
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "CLI: " + v8_cliente);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        for (int i = 0; i < tk_iv_fila; i++) {
            printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), iv4_descripcion[i]);
            printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_cant(), totalColumna, iv1_cantidad[i] + " X");
            printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_precio(), totalColumna, iv2_precio[i] + " =");
            printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_item_subtotal(), totalColumna, iv3_total[i]);
            tempfila = tempfila + 2;
        }
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
        printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), totalColumna, "OBS: " + v7_observacion);
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_inicio(), totalColumna, "TOTAL :");
        printer.printTextWrap(11 + tempfila, 11, jsprint.getSep_total_gral(), totalColumna, v6_monto);
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

    void crear_archivo_enviar_impresora() {
        String titulo = "crear_archivo_enviar_impresora";
        try {
            crear_archivo_texto_impresion();
            pos.setInputStream(inputStream);
            pos.imprimir_ticket_Pos();
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
    }

    private void crear_mensaje_textarea_y_confirmar() {
        JTextArea ta = new JTextArea(20, 30);
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

    public void boton_imprimir_pos_venta_interno(Connection conn, int idventa_interno) {
        cargar_datos_venta_interno(conn, idventa_interno);
        if (tk_iv_fila > 0) {
            crear_mensaje_textarea_y_confirmar();
        } else {
            JOptionPane.showMessageDialog(null, "NO SE ENCONTRO DATOS PARA ESTA COMPRA");
        }
    }
}
