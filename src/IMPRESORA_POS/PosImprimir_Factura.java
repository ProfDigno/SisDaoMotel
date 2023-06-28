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
public class PosImprimir_Factura {

    EvenConexion eveconn = new EvenConexion();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private static ClaImpresoraPos pos = new ClaImpresoraPos();
    private static json_array_imprimir_pos jsprint = new json_array_imprimir_pos();
    private static int vec_limit = 300;
    private static String tk_ruta_archivo = "ticket_factura.txt";
    private static String[] iv1_it_cant = new String[vec_limit];
    private static String[] iv2_it_punit = new String[vec_limit];
    private static String[] iv3_if_subiva10 = new String[vec_limit];
    private static String[] iv4_it_descrip = new String[vec_limit];
    private static FileInputStream inputStream = null;
    private static int tk_iv_fila = 0;
    private String nombre_ticket = "ticket Factura";
    private static String v1_nro_factura = "0";
    private static String v2_fecha = "0";
    private static String v3_condicion = "0";
    private static String v4_mtotal;
    private static String v5_miva5;
    private static String v6_miva10;
    private static String v7_mletra;
    private static String v8_cli_nombre;
    private static String v9_cli_ruc;
    private static String v10_cli_direccion;
    private static String v11_cli_telefono;
    private static int cant_char = 10;
    private void cargar_datos_factura(Connection conn, int idfactura) {
        String titulo = "cargar_datos_factura";
        String sql = "select f.idfactura as idf,f.nro_factura,to_char(f.fecha_nota,'yyyy-MM-dd') as fecha,\n"
                + "f.condicion as condicion,"
                + "TRIM(to_char(f.monto_total,'999G999G999')) as mtotal,"
                + "TRIM(to_char(f.monto_iva5,'999G999G999')) as miva5,"
                + "TRIM(to_char(f.monto_iva10,'999G999G999')) as miva10,\n"
                + "f.monto_letra as mletra,\n"
                + "p.nombre as cli_nombre,p.ruc as cli_ruc,p.direccion as cli_direccion,p.telefono as cli_telefono,\n"
                + "fi.fk_idproducto as it_idp,fi.cantidad as it_cant,fi.descripcion as it_descrip,\n"
                + "TRIM(to_char((fi.precio_iva5+fi.precio_iva10+fi.precio_exenta),'999G999G999')) as it_punit,\n"
                + "TRIM(to_char((fi.precio_exenta*fi.cantidad),'999G999G999')) as it_subexe,\n"
                + "TRIM(to_char((fi.precio_iva5*fi.cantidad),'999G999G999')) as it_subiva5,\n"
                + "TRIM(to_char((fi.precio_iva10*fi.cantidad),'999G999G999')) as if_subiva10 \n"
                + "from factura f,persona p,factura_item fi\n"
                + "where f.fk_idpersona=p.idpersona\n"
                + "and f.idfactura=fi.fk_idfactura\n"
                + "and f.idfactura=" + idfactura;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            tk_iv_fila = 0;
            while (rs.next()) {
                v1_nro_factura = rs.getString("nro_factura");
                v2_fecha = rs.getString("fecha");
                v3_condicion = rs.getString("condicion");
                v4_mtotal = rs.getString("mtotal");
                v5_miva5 = rs.getString("miva5");
                v6_miva10 = rs.getString("miva10");
                v7_mletra = rs.getString("mletra");
                v8_cli_nombre = rs.getString("cli_nombre");
                v9_cli_ruc = rs.getString("cli_ruc");
                v10_cli_direccion = rs.getString("cli_direccion");
                v11_cli_telefono = rs.getString("cli_telefono");
                iv4_it_descrip[tk_iv_fila] = rs.getString("it_descrip");
                iv1_it_cant[tk_iv_fila] = rs.getString("it_cant");
                iv2_it_punit[tk_iv_fila] = rs.getString("it_punit");
                iv3_if_subiva10[tk_iv_fila] = rs.getString("if_subiva10");
                tk_iv_fila++;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private String cargar_datos_para_mensaje_textarea() {
        String html_1 = "<html><body>"
                + "<h2>NOTA FACTURA</h2>"
                + "<table border='1'>"
                + "<tr><td>NRO:</td><td>" + v1_nro_factura + "</td><td>FECHA:</td><td>" + v2_fecha + "</td></tr>"
                + "<tr><td>CLIENTE:</td><td>" + v8_cli_nombre + "</td><td>RUC:</td><td>" + v9_cli_ruc + "</td></tr>"
                + "<tr><td>DIRECCION:</td><td>" + v10_cli_direccion + "</td><td>TEL:</td><td>" + v11_cli_telefono + "</td></tr>"
                + "</table>";
        html_1 = html_1 + "<table border='1'>"
                + "<tr><th>CANT</th><th>DESCRIPCION</th><th>PRECIO</th><th>SUBTOTAL</th></tr>";
        for (int i = 0; i < tk_iv_fila; i++) {
            html_1 = html_1 + "<tr><td>" + iv1_it_cant[i] + "</td><td>" + iv4_it_descrip[i] + "</td><td>" + iv2_it_punit[i] + "</td><td>" + iv3_if_subiva10[i] + "</td></tr>";
        }
        html_1 = html_1 + "</table>";
        html_1 = html_1 + "<table border='1'>"
                + "<tr><td>LETRA:</td><td>" + v7_mletra + "</td><th>TOTAL:</td><td>" + v4_mtotal + "</td></tr>"
                + "<tr><td colspan='2'>-</td><td>IVA5:</td><td>" + v5_miva5 + "</td></tr>"
                + "<tr><td colspan='2'>-</td><td>IVA10:</td><td>" + v6_miva10 + "</td></tr>"
                + "</table>"
                + "</body></html>";

        return html_1;//mensaje_impresora;
    }

    private static String limitarLongitud(String texto, int longitudMaxima) {
        if (texto.length() <= longitudMaxima) {
            return texto;
        } else {
            return texto.substring(0, longitudMaxima);
        }
    }

    private static void crear_archivo_texto_impresion_calibrar() throws PrintException, FileNotFoundException, IOException {
        int totalColumna = 80;
        PrinterMatrix printer = new PrinterMatrix();
        Extenso e = new Extenso();
        e.setNumber(101.85);
        //Definir el tamanho del papel 
        int tempfila = 0;
        int totalfila = 43;//  + (tk_iv_fila * 2) ;
        printer.setOutSize(totalfila, totalColumna);
        printer.printTextWrap(7 + tempfila, 7, 10, totalColumna, v2_fecha);
        printer.printTextWrap(7 + tempfila, 7, 69, totalColumna, "X");
        printer.printTextWrap(8 + tempfila, 8, 14, totalColumna, v8_cli_nombre);
        printer.printTextWrap(8 + tempfila, 8, 55, totalColumna, v9_cli_ruc);
        printer.printTextWrap(9 + tempfila, 9, 5, totalColumna, v10_cli_direccion);
        printer.printTextWrap(9 + tempfila, 9, 60, totalColumna, v11_cli_telefono);
        printer.printTextWrap(11 + tempfila, 11, 63, totalColumna, v3_condicion);
        for (int i = 0; i < tk_iv_fila; i++) {
            printer.printTextWrap(15 + tempfila, 15, 8, totalColumna, iv1_it_cant[i]);
            printer.printTextWrap(15 + tempfila, 15, 14, totalColumna, limitarLongitud(iv4_it_descrip[i], 28));
            printer.printTextWrap(15 + tempfila, 15, 43, totalColumna, pos.alinearDerecha(iv2_it_punit[i],cant_char));
            printer.printTextWrap(15 + tempfila, 15, 70, totalColumna, pos.alinearDerecha(iv3_if_subiva10[i],cant_char));
            tempfila++;
        }
        tempfila = 0;
        printer.printTextWrap(27 + tempfila, 27, 10, totalColumna, v7_mletra);
        printer.printTextWrap(27 + tempfila, 27, 70, totalColumna, pos.alinearDerecha(v4_mtotal,cant_char));
        printer.printTextWrap(28 + tempfila, 28, 15, totalColumna, v5_miva5);
        printer.printTextWrap(28 + tempfila, 28, 33, totalColumna, v6_miva10);
        printer.printTextWrap(28 + tempfila, 28, 54, totalColumna, v6_miva10);

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
                crear_archivo_texto_impresion_calibrar();
                pos.setInputStream(inputStream);
                if (jsprint.isPrint_defauld_factura()) {
                    pos.imprimir_ticket_Pos();
                } else {
                    pos.imprimir_ticket_Pos_por_nombre(jsprint.getPrint_nombre_factura());
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
            }
        } else {
            jsprint.cargar_jsom_imprimir_pos();
        }
    }

    private void crear_mensaje_textarea_y_confirmar() {
//        JTextArea ta = new JTextArea(30, 30);
//        ta.setText(cargar_datos_para_mensaje_textarea());
        System.out.println(cargar_datos_para_mensaje_textarea());
        Object[] opciones = {"IMPRIMIR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(null, cargar_datos_para_mensaje_textarea(), tk_ruta_archivo, //new JScrollPane(ta)
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "IMPRIMIR");
        if (eleccion == JOptionPane.YES_OPTION) {
            crear_archivo_enviar_impresora();
        }
    }

    public void boton_imprimir_pos_FACTURA(Connection conn, int idfactura) {
        cargar_datos_factura(conn, idfactura);
        crear_mensaje_textarea_y_confirmar();
    }
}
