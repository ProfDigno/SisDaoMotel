/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.Mensaje;

//import Formulario.Version.ClaDirectorio;
import Evento.Fecha.EvenFecha;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.net.Socket;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Digno Tala
 */
public class EvenMensajeJoptionpane {

    private EvenFecha evefec = new EvenFecha();
    public static boolean activar_print_serial = true;
    private static int cant_error = 0;
    Icon iconoGuardar;
    Icon iconoEditar;
    private boolean print_msj;

    public boolean isPrint_msj() {
        return print_msj;
    }

    public void setPrint_msj(boolean print_msj) {
        this.print_msj = print_msj;
    }

    public EvenMensajeJoptionpane() {
//        iconoGuardar = new ImageIcon(getClass().getResource("Evento/Mensaje/guardar.png"));
//        iconoEditar = new ImageIcon(getClass().getResource("Evento/Mensaje/editar.png"));
    }

//    ClaDirectorio cldir=new ClaDirectorio();
    /**
     * Mensaje con boton de confirmacion contiene simbolo de pregunta
     *
     * @param mensaje el mensaje para mostrar
     * @param titulo el titulo del mensaje
     * @param boton1 el boton que confirma para retornar true
     * @param boton2 el boton que cancela para retornar false
     * @return true si es boton1, false si es boton2
     */
    public boolean getBooMensaje_question(String mensaje, String titulo, String boton1, String boton2) {

        Object[] opciones = {boton1, boton2};
        boolean mensajeok;
        int eleccion = JOptionPane.showOptionDialog(null, mensaje, titulo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, boton1);
        if (eleccion == JOptionPane.YES_OPTION) {
            mensajeok = true;
        } else {
            mensajeok = false;
        }
        return mensajeok;
    }

    public boolean getBooMensaje_question_focus(JPanel foco, String mensaje, String titulo, String boton1, String boton2) {

        Object[] opciones = {boton1, boton2};
        boolean mensajeok;
        int eleccion = JOptionPane.showOptionDialog(foco, mensaje, titulo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, boton1);
        if (eleccion == JOptionPane.YES_OPTION) {
            mensajeok = true;
        } else {
            mensajeok = false;
        }
        return mensajeok;
    }

    public boolean getBooMensaje_informacion(String mensaje, String titulo, String boton1, String boton2) {
        JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.setBackground(Color.RED);
        jOptionPane.setOpaque(true);
        Object[] opciones = {boton1, boton2};
        boolean mensajeok;
        int eleccion = jOptionPane.showOptionDialog(null, mensaje, titulo,
                jOptionPane.YES_NO_OPTION,
                jOptionPane.INFORMATION_MESSAGE, null, opciones, boton1);
        if (eleccion == jOptionPane.YES_OPTION) {
            mensajeok = true;
        } else {
            mensajeok = false;
        }
        return mensajeok;
    }

    public int getIntMensaje_informacion_3btn(String mensaje, String titulo, String boton1, String boton2, String boton3) {
        JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.setBackground(Color.RED);
        jOptionPane.setOpaque(true);
        Object[] opciones = {boton1, boton2, boton3};
        int eleccion = jOptionPane.showOptionDialog(null, mensaje, titulo,
                jOptionPane.YES_NO_OPTION,
                jOptionPane.INFORMATION_MESSAGE, null, opciones, boton1);
        return eleccion;
    }
public int getIntMensaje_informacion_4btn(String mensaje, String titulo, String boton1, String boton2, String boton3, String boton4) {
        JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.setBackground(Color.RED);
        jOptionPane.setOpaque(true);
        Object[] opciones = {boton1, boton2, boton3,boton4};
        int eleccion = jOptionPane.showOptionDialog(null, mensaje, titulo,
                jOptionPane.YES_NO_OPTION,
                jOptionPane.INFORMATION_MESSAGE, null, opciones, boton1);
        return eleccion;
    }
    public boolean getBooMensaje_warning(String mensaje, String titulo, String boton1, String boton2) {
        JOptionPane jOptionPane = new JOptionPane();
        Object[] opciones = {boton1, boton2};
        boolean mensajeok;
        int eleccion = jOptionPane.showOptionDialog(null, mensaje, titulo,
                jOptionPane.YES_NO_OPTION,
                jOptionPane.WARNING_MESSAGE, null, opciones, boton1);
        if (eleccion == jOptionPane.YES_OPTION) {
            mensajeok = true;
        } else {
            mensajeok = false;
        }
        return mensajeok;
    }

    public boolean getBooMensaje_error(String mensaje, String titulo, String boton1, String boton2) {
        JOptionPane jOptionPane = new JOptionPane();
        Object[] opciones = {boton1, boton2};
        boolean mensajeok;
        int eleccion = jOptionPane.showOptionDialog(null, mensaje, titulo,
                jOptionPane.YES_NO_OPTION,
                jOptionPane.ERROR_MESSAGE, null, opciones, boton1);
        if (eleccion == jOptionPane.YES_OPTION) {
            mensajeok = true;
        } else {
            mensajeok = false;
        }
        return mensajeok;
    }

    public void cerrarSistema(JFrame frm) {
        Object[] opciones = {"Aceptar", "Cancelar"};

        int eleccion = JOptionPane.showOptionDialog(null, "En realidad desea realizar cerrar la aplicacion", "Mensaje de Confirmacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
        if (eleccion == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            frm.setDefaultCloseOperation(0);
        }
    }

    public void cerrarJInternalFrame(JInternalFrame frm) {
        Object[] opciones = {"Aceptar", "Cancelar"};

        int eleccion = JOptionPane.showOptionDialog(null, "En realidad desea realizar cerrar la aplicacion", "Mensaje de Confirmacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
        if (eleccion == JOptionPane.YES_OPTION) {
            frm.setDefaultCloseOperation(1);
        } else {
            frm.setDefaultCloseOperation(0);
        }
    }

    public void mensaje_error(Exception e, String sql, String titulo) {
        setCant_error(getCant_error() + 1);
        String sumasql = "";
        for (int caracter = 1; caracter <= sql.length(); caracter++) {
            sumasql = sumasql + sql.substring((caracter - 1), caracter);
            if (caracter % 150 == 0) {
                sumasql = sumasql + "\n";
            }
        }
        JOptionPane.showMessageDialog(null, "Error:" + e + "\n" + sumasql, titulo, JOptionPane.ERROR_MESSAGE);
        Imprimir_serial_sql_error(e, sql, titulo);
        if (getCant_error() > 2) {//cantidad de error antes de cerrar
            setCant_error(0);
            JOptionPane.showMessageDialog(null, "Error:" + e + "\nEL SISTEMA SE REINICIA", titulo, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public void mensaje_error_sql(SQLException e, String sql, String titulo) {
        setCant_error(getCant_error() + 1);
        String sumasql = "";
        for (int caracter = 1; caracter <= sql.length(); caracter++) {
            sumasql = sumasql + sql.substring((caracter - 1), caracter);
            if (caracter % 150 == 0) {
                sumasql = sumasql + "\n";
            }
        }
        JOptionPane.showMessageDialog(null, "Error:" + e + "\n" + sumasql, titulo, JOptionPane.ERROR_MESSAGE);
        Imprimir_serial_sql_error(e, sql, titulo);
        if (getCant_error() > 2) {//cantidad de error antes de cerrar
            setCant_error(0);
            JOptionPane.showMessageDialog(null, "Error:" + e + "\nEL SISTEMA SE REINICIA", titulo, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public void mensaje_error(Exception e, String titulo) {
        setCant_error(getCant_error() + 1);
        JOptionPane.showMessageDialog(null, "Error:" + e, titulo, JOptionPane.ERROR_MESSAGE);
        System.err.print(titulo + "\nError:" + e);
        if (getCant_error() > 2) {//cantidad de error antes de cerrar
            setCant_error(0);
            JOptionPane.showMessageDialog(null, "Error:" + e + "\nEL SISTEMA SE REINICIA", titulo, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public boolean senha_crear_tablas(String senha) {
        boolean habilitar = false;
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "PASSWORD", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (okCxl == JOptionPane.OK_OPTION) {
            String password = new String(pf.getPassword());
            System.err.println("You entered: " + password);
            if (password.equals(senha)) {
                habilitar = true;
            } else {
                habilitar = false;
                JOptionPane.showConfirmDialog(null, "SENHA INCORRECTA", "ERROR", JOptionPane.ERROR_MESSAGE);

            }
        }
        return habilitar;
    }

    public void Imprimir_serial_sql(String sql, String titulo) {
        String saltolinea = "\n";
        String linea = "----------------------------------" + titulo + "/" + evefec.getString_formato_hora() + "-------------------------------------";
        System.out.println(linea + saltolinea + sql + saltolinea + linea);
    }

    public void Imprimir_serial_sql_error(Exception e, String sql, String titulo) {
        String saltolinea = "\n";
        String linea = "----------------------------------" + titulo + "-------------------------------------";
        System.err.println(linea + saltolinea + e + saltolinea + sql + saltolinea + linea);
    }

    public void guardado_correcto(String mensaje, boolean mostrar) {
        if (mostrar) {
//            iconoGuardar = new ImageIcon(getClass().getResource("Evento/Mensaje/guardar.png"));
            JOptionPane.showMessageDialog(null, mensaje, "GUARDADO CORRECTO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void modificado_correcto(String mensaje, boolean mostrar) {
        if (mostrar) {
            JOptionPane.showMessageDialog(null, mensaje, "MODIFICADO CORRECTO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * @return the cant_error
     */
    public static int getCant_error() {
        return cant_error;
    }

    /**
     * @param aCant_error the cant_error to set
     */
    public static void setCant_error(int aCant_error) {
        cant_error = aCant_error;
    }

    //String html_1 = "<html><p style=\"color:red\"><font size=\"6\">";
    //    String html_2 = "</font></p></html>";
    public String getHtml_rojo(int size, String texto) {
        String html = "";
        html = "<p style=\"color:red\"><font size=\"" + size + "\">" + texto + "</font></p>";
        return html;
    }

    public String getHtml_azul(int size, String texto) {
        String html = "";
        html = "<p style=\"color:blue\"><font size=\"" + size + "\">" + texto + "</font></p>";
        return html;
    }

    public String getHtml_negro(int size, String texto) {
        String html = "";
        html = "<p style=\"color:black\"><font size=\"" + size + "\">" + texto + "</font></p>";
        return html;
    }

    public void mensaje_JTextArea(String mensaje) {
        JTextArea ta = new JTextArea(30, 50);
        ta.setText(mensaje);
        Object[] opciones = {"ACEPTAR", "CANCELAR"};
        int eleccion = JOptionPane.showOptionDialog(null, new JScrollPane(ta), "MENSAJE TEXT AREA",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, "ACEPTAR");
        if (eleccion == JOptionPane.YES_OPTION) {
            System.out.println(mensaje);
        }
    }
}
