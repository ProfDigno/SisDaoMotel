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
public class PosImprimir_Caja_Producto {

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
    private static String tk_ruta_archivo = "ticket_caja_producto.txt";
    private static int dato_vector = 500;
    private static String[] iv1_pventa = new String[dato_vector];
    private static String[] iv2_stock_ini = new String[dato_vector];
    private static String[] iv3_c_venta = new String[dato_vector];
    private static String[] iv4_c_interno = new String[dato_vector];
    private static String[] iv5_c_carga = new String[dato_vector];//iv4_c_interno
    private static String[] iv6_stock_act = new String[dato_vector];
    private static String[] iv0_descripcion = new String[dato_vector];
    private static FileInputStream inputStream = null;
    private static int tk_iv_fila;
    private static String nombre_ticket = "VENTA INTERNA";
    private static String cd_fk_idcaja_cierre = "0";
    private static String cd_fec_inicio = "0";
    private static String cd_fec_fin = "0";
    private static String cd_creado_por = "digno";

    private void cargar_datos_caja(Connection conn, int fk_idcaja_cierre) {
        String titulo = "cargar_datos_caja";
        String sql = "select cca.idcaja_cierre as fk_idcaja_cierre,\n"
                + "to_char(cca.fecha_inicio,'yyyy-MM-dd HH24:MI') as fec_inicio, \n"
                + "to_char(cca.fecha_fin,'yyyy-MM-dd HH24:MI') as fec_fin,\n"
                + "cca.creado_por as creado_por\n"
                + "from caja_cierre cca\n"
                + "where cca.idcaja_cierre=" + fk_idcaja_cierre
                + ";";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                cd_fk_idcaja_cierre = rs.getString("fk_idcaja_cierre");
                cd_fec_inicio = rs.getString("fec_inicio");
                cd_fec_fin = rs.getString("fec_fin");
                cd_creado_por = rs.getString("creado_por");
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void cargar_caja_producto_item_por_caja(Connection conn, int fk_idcaja_cierre) {
        String titulo = "cargar_datos_venta_interno";
        String sql = "select cpi.fk_idproducto as idp,pc.nombre as categoria,\n"
                + "cpi.descripcion,cpi.precio_venta as pventa,\n"
                + "((cpi.stock_actual-cpi.cant_cargado)+(ABS(cpi.cant_vendido)+ABS(cpi.cant_interno))) as stock_ini,\n"
                + "cpi.cant_vendido as c_venta,cpi.cant_interno as c_interno,cpi.cant_cargado as c_carga,\n"
                + "cpi.stock_actual as stock_act\n"
                + "from caja_producto_item cpi,producto p,producto_categoria pc\n"
                + "where cpi.fk_idproducto=p.idproducto \n"
                + "and p.fk_idproducto_categoria=pc.idproducto_categoria\n"
                + "and cpi.fk_idcaja_cierre="+fk_idcaja_cierre
                + " order by pc.nombre asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila = 0;
            while (rs.next()) {
                iv0_descripcion[tk_iv_fila] = rs.getString("descripcion");
                iv1_pventa[tk_iv_fila] = rs.getString("pventa");
                iv2_stock_ini[tk_iv_fila] = rs.getString("stock_ini");
                iv3_c_venta[tk_iv_fila] = rs.getString("c_venta");
                iv4_c_interno[tk_iv_fila] = rs.getString("c_interno");
                iv5_c_carga[tk_iv_fila] = rs.getString("c_carga");
                iv6_stock_act[tk_iv_fila] = rs.getString("stock_act");
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
        String spacio="  ";
        String separador = "==========================================";
        mensaje_impresora = mensaje_impresora + "===============" + jsprint.getEmp_nombre() + "================" + saltolinea;
        mensaje_impresora = mensaje_impresora + jsprint.getEmp_telefono() + saltolinea;
        mensaje_impresora = mensaje_impresora + jsprint.getEmp_direccion() + saltolinea;
        mensaje_impresora = mensaje_impresora + "NRO CIERRE:" + cd_fk_idcaja_cierre + saltolinea;
        mensaje_impresora = mensaje_impresora + "FEC-INI: " + cd_fec_inicio + saltolinea;
        mensaje_impresora = mensaje_impresora + "FEC-FIN: " + cd_fec_fin + saltolinea;
        mensaje_impresora = mensaje_impresora + "USUARIO:" + cd_creado_por + saltolinea;
        mensaje_impresora = mensaje_impresora + separador + saltolinea;
        mensaje_impresora = mensaje_impresora + "p_ven" + tabular + "s_ini" + tabular + "c_ven"+ tabular+ "c_int"+ tabular + "c_car"+ tabular + "s_act" + saltolinea;
        for (int i = 0; i < tk_iv_fila; i++) {
            mensaje_impresora = mensaje_impresora + "#=>"+iv0_descripcion[i] + saltolinea;
            String item = iv1_pventa[i] + tabular + iv2_stock_ini[i] + tabular 
                        + iv3_c_venta[i] + tabular + iv4_c_interno[i] + tabular 
                    + iv5_c_carga[i] + tabular + iv6_stock_act[i]+ saltolinea;
            mensaje_impresora = mensaje_impresora + item;
        }
        mensaje_impresora = mensaje_impresora + "==========================================" + saltolinea;
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
        printer.printTextWrap(1 + tempfila, 1, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_cabezera() + jsprint.getEmp_nombre() + jsprint.getLinea_cabezera());
        printer.printTextWrap(2 + tempfila, 2, jsprint.getSep_inicio(), totalColumna, jsprint.getEmp_telefono());
        printer.printTextWrap(3 + tempfila, 3, jsprint.getSep_inicio(), totalColumna, jsprint.getEmp_direccion());
        printer.printTextWrap(4 + tempfila, 4, jsprint.getSep_inicio(), totalColumna, "NRO CAJA:" + cd_fk_idcaja_cierre);
        printer.printTextWrap(5 + tempfila, 5, jsprint.getSep_inicio(), totalColumna, "FEC INI: " + cd_fec_inicio);
        printer.printTextWrap(6 + tempfila, 6, jsprint.getSep_inicio(), totalColumna, "FEC FIN: " + cd_fec_fin);
        printer.printTextWrap(7 + tempfila, 7, jsprint.getSep_inicio(), totalColumna, "USUARIO:" + cd_creado_por);
        printer.printTextWrap(8 + tempfila, 8, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
            printer.printTextWrap(9 + tempfila, 9, 2, totalColumna,  "Pventa");
            printer.printTextWrap(9 + tempfila, 9, 15, totalColumna,  "S_I");
            printer.printTextWrap(9 + tempfila, 9, 20, totalColumna,  "CV");
            printer.printTextWrap(9 + tempfila, 9, 25, totalColumna,  "CI");
            printer.printTextWrap(9 + tempfila, 9, 30, totalColumna,  "CC");
            printer.printTextWrap(9 + tempfila, 9, 35, totalColumna, "S_A");
        for (int i = 0; i < tk_iv_fila; i++) {
            printer.printTextWrap(10 + tempfila, 10, jsprint.getSep_inicio(), jsprint.getTt_text_descrip(), "#=>"+iv0_descripcion[i]);
            printer.printTextWrap(11 + tempfila, 11, 2, totalColumna, iv1_pventa[i] + ", ");
            printer.printTextWrap(11 + tempfila, 11, 15, totalColumna, iv2_stock_ini[i] + ", ");
            printer.printTextWrap(11 + tempfila, 11, 20, totalColumna, iv3_c_venta[i] + ", ");
            printer.printTextWrap(11 + tempfila, 11, 25, totalColumna, iv4_c_interno[i] + ", ");
            printer.printTextWrap(11 + tempfila, 11, 30, totalColumna, iv5_c_carga[i] + ", ");
            printer.printTextWrap(11 + tempfila, 11, 35, totalColumna, iv6_stock_act[i]);
            tempfila = tempfila + 2;
        }
        printer.printTextWrap(12 + tempfila, 12, jsprint.getSep_inicio(), totalColumna, jsprint.getLinea_separador());
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
        JTextArea ta = new JTextArea(30, 45);
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

    public void boton_imprimir_pos_producto_item_por_caja(Connection conn, int fk_idcaja_cierre) {
        cargar_datos_caja(conn, fk_idcaja_cierre);
        cargar_caja_producto_item_por_caja(conn, fk_idcaja_cierre);
        crear_mensaje_textarea_y_confirmar();
    }
}
