/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.Grafico.FunFreeChard;
import Evento.JButton.EvenJButton;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.ENTIDAD.habitacion_recepcion_temp;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Digno
 */
public class FrmPuertas extends javax.swing.JFrame {

    private EvenJButton evebtn = new EvenJButton();
    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private FunFreeChard evechar = new FunFreeChard();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenEstado eveest = new EvenEstado();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenConexion eveconn = new EvenConexion();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private habitacion_recepcion_temp ENThrt = new habitacion_recepcion_temp();
    Connection conn = ConnPostgres.getConnPosgres();
    int[] Ia_puerta_nro_habitacion;
    boolean[] Ba_puerta_cliente;
    boolean[] Ba_puerta_limpieza;
//    boolean[] Ba_es_manual;
//    int[] Ia_pino_cli;
//    int[] Ia_pino_lim;
//    int[] Ia_idhabitacion_item_sensor_gpio;
//    String[] Sa_estado;
    private int cant_de_habitacion;
    private int sensor_puerta_cliente = 2;
    private int sensor_puerta_limpieza = 3;

    private void abrir_formulario() {
        this.setTitle("MODULO DE PRUEBA DE LAS PUERTAS DEL SISTEMA");
        cargar_array_habitacion();
        cargar_estados_puertas_gpio(sensor_puerta_cliente, sensor_puerta_limpieza);
        cargar_array_habitacion_puertas();
    }

    private void cargar_estados_puertas_gpio(int sensor_puerta_cliente, int sensor_puerta_limpieza) {
        String titulo = "cargar_estados_puertas_gpio";
        String sql = "select hd.idhabitacion_dato,hd.es_manual,\n"
                + "(select case when hd.es_manual=true then true else ig.alto_bajo end from habitacion_item_sensor_gpio ig \n"
                + "where ig.fk_idhabitacion_sensor=" + sensor_puerta_cliente + " \n"
                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato ) as cliente,\n"
                + "(select case when hd.es_manual=true then true else ig.alto_bajo end from habitacion_item_sensor_gpio ig \n"
                + "where ig.fk_idhabitacion_sensor=" + sensor_puerta_limpieza + " \n"
                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato ) as limpieza\n"
//                + "(select  ig.gpio  from habitacion_item_sensor_gpio ig \n"
//                + "where ig.fk_idhabitacion_sensor=" + sensor_puerta_cliente + " \n"
//                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato ) as pin_cli,\n"
//                + "(select  ig.gpio  from habitacion_item_sensor_gpio ig \n"
//                + "where ig.fk_idhabitacion_sensor=" + sensor_puerta_limpieza + " \n"
//                + "and ig.fk_idhabitacion_dato=hd.idhabitacion_dato ) as pin_lim\n"
                + " from habitacion_dato hd \n"
                + "order by hd.idhabitacion_dato asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql, titulo);
            int fila = 0;
            while (rs.next()) {
                int idhabitacion_dato = rs.getInt("idhabitacion_dato");
                boolean cliente = rs.getBoolean("cliente");
                boolean limpieza = rs.getBoolean("limpieza");
//                boolean es_manual = rs.getBoolean("es_manual");
//                int pin_cli = rs.getInt("pin_cli");
//                int pin_lim = rs.getInt("pin_lim");
                Ia_puerta_nro_habitacion[fila] = idhabitacion_dato;
                Ba_puerta_cliente[fila] = cliente;
                Ba_puerta_limpieza[fila] = limpieza;
//                Ba_es_manual[fila] = es_manual;
//                Ia_pino_cli[fila] = pin_cli;
//                Ia_pino_lim[fila] = pin_lim;
                fila++;
            }
        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    private void cargar_array_habitacion() {
        cant_de_habitacion = (eveconn.getInt_ultimoID_max(conn, ENThrt.getTb_habitacion_recepcion_temp(), ENThrt.getId_idhabitacion_recepcion_temp()));
        Ia_puerta_nro_habitacion = new int[cant_de_habitacion];
        Ba_puerta_cliente = new boolean[cant_de_habitacion];
        Ba_puerta_limpieza = new boolean[cant_de_habitacion];
//        Ba_es_manual = new boolean[cant_de_habitacion];
//        Ia_pino_cli = new int[cant_de_habitacion];
//        Ia_pino_lim = new int[cant_de_habitacion];
    }

    private void limpiar_panel(JPanel panel) {
        String titulo = "limpiar_panel_puertas";
        try {
            panel.removeAll();
        } catch (Exception e) {
            evemen.mensaje_error(e, titulo);
        }
    }

    private void cargar_array_habitacion_puertas() {
        limpiar_panel(panel_puerta);
        for (int fila = 0; fila < (cant_de_habitacion); fila++) {
            agregar_label_puertas(fila, Ia_puerta_nro_habitacion[fila], Ba_puerta_cliente[fila],
                    Ba_puerta_limpieza[fila]);
        }
        panel_puerta.updateUI();
    }

    private void update_habitacion_item_sensor_gpio(boolean alto_bajo, int idhabitacion_dato, int sensor) {
        String titulo = "insertar_habitacion_recepcion_temp";
        String sql = "update habitacion_item_sensor_gpio set alto_bajo=" + alto_bajo + " \n"
                + "where fk_idhabitacion_dato=" + idhabitacion_dato
                + " and fk_idhabitacion_sensor=" + sensor+";\n"
                + "update habitacion_recepcion_temp set \n"
                + "puerta_limpieza=(select alto_bajo from habitacion_item_sensor_gpio \n"
                + "where fk_idhabitacion_dato=habitacion_recepcion_temp.idhabitacion_dato and fk_idhabitacion_sensor=" + sensor_puerta_limpieza+"); \n"
                + "update habitacion_recepcion_temp set \n"
                + "puerta_cliente=(select alto_bajo from habitacion_item_sensor_gpio \n"
                + "where fk_idhabitacion_dato=habitacion_recepcion_temp.idhabitacion_dato and fk_idhabitacion_sensor=" + sensor_puerta_cliente+"); \n";
        try {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            eveconn.SQL_execute_libre(conn, sql);
            conn.commit();
        } catch (SQLException e) {
            evemen.mensaje_error(e,sql, titulo);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                evemen.Imprimir_serial_sql_error(e1,sql, titulo);
            }
        }
    }

    private void agregar_label_puertas(int fila, int idhabitacion_dato, boolean cliente, boolean limpieza) {
        JButton btnpuerta_cliente = new JButton("C:" + idhabitacion_dato);
        JButton btnpuerta_limpieza = new JButton("L:" + idhabitacion_dato);
        btnpuerta_cliente.setFont(new Font("Arial", Font.BOLD, 18));
        btnpuerta_limpieza.setFont(new Font("Arial", Font.BOLD, 18));
//        if (es_manual) {
//            btnpuerta_cliente.setBackground(Color.white);
//            btnpuerta_limpieza.setBackground(Color.white);
//        } else {
            if (cliente) {
                btnpuerta_cliente.setBackground(Color.green);
                btnpuerta_cliente.setIcon(new ImageIcon(this.getClass().getResource("/graficos/48_puerta.png")));
            }
            if (!cliente) {
                btnpuerta_cliente.setBackground(Color.red);
                btnpuerta_cliente.setIcon(new ImageIcon(this.getClass().getResource("/graficos/48_puerta.png")));
            }
            if (limpieza) {
                btnpuerta_limpieza.setBackground(Color.green);
                btnpuerta_limpieza.setIcon(new ImageIcon(this.getClass().getResource("/graficos/escoba.png")));
            }
            if (!limpieza) {
                btnpuerta_limpieza.setBackground(Color.yellow);
                btnpuerta_limpieza.setIcon(new ImageIcon(this.getClass().getResource("/graficos/escoba.png")));
            }
//        }
        panel_puerta.add(btnpuerta_cliente);
        panel_puerta.add(btnpuerta_limpieza);
        btnpuerta_cliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cliente) {
//                    if (evemen.getBooMensaje_question_focus(panel_puerta,"ACCION DE PUERTA CLIENTE: " + idhabitacion_dato, "PUERTA", "ABRIR", "CANCELAR")) {
                        update_habitacion_item_sensor_gpio(false, idhabitacion_dato, sensor_puerta_cliente);
                        cargar_estados_puertas_gpio(sensor_puerta_cliente, sensor_puerta_limpieza);
                        cargar_array_habitacion_puertas();
//                    }
                }
                if (!cliente) {
//                    if (evemen.getBooMensaje_question_focus(panel_puerta,"ACCION DE PUERTA CLIENTE: " + idhabitacion_dato, "PUERTA", "CERRAR", "CANCELAR")) {
                        update_habitacion_item_sensor_gpio(true, idhabitacion_dato, sensor_puerta_cliente);
                        cargar_estados_puertas_gpio(sensor_puerta_cliente, sensor_puerta_limpieza);
                        cargar_array_habitacion_puertas();
//                    }
                }
            }
        });
        btnpuerta_limpieza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (limpieza) {
//                    if (evemen.getBooMensaje_question_focus(panel_puerta,"ACCION DE TARJETA LIMPIEZA: " + idhabitacion_dato, "TARJETA", "ABRIR", "CANCELAR")) {
                        update_habitacion_item_sensor_gpio(false, idhabitacion_dato, sensor_puerta_limpieza);
                        cargar_estados_puertas_gpio(sensor_puerta_cliente, sensor_puerta_limpieza);
                        cargar_array_habitacion_puertas();
//                    }
                }
                if (!limpieza) {
//                    if (evemen.getBooMensaje_question_focus(panel_puerta,"ACCION DE TARJETA LIMPIEZA: " + idhabitacion_dato, "TARJETA", "CERRAR", "CANCELAR")) {
                        update_habitacion_item_sensor_gpio(true, idhabitacion_dato, sensor_puerta_limpieza);
                        cargar_estados_puertas_gpio(sensor_puerta_cliente, sensor_puerta_limpieza);
                        cargar_array_habitacion_puertas();
//                    }
                }
            }
        });
    }

    public FrmPuertas() {
        initComponents();
        abrir_formulario();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_puerta = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_puerta.setBorder(javax.swing.BorderFactory.createTitledBorder("PUERTAS"));
        panel_puerta.setLayout(new java.awt.GridLayout(0, 6));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_puerta, javax.swing.GroupLayout.DEFAULT_SIZE, 1107, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_puerta, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1143, 634));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPuertas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPuertas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPuertas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPuertas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPuertas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel_puerta;
    // End of variables declaration//GEN-END:variables
}
