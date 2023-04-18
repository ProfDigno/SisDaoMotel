/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.ComputerInfo;
import Config_JSON.json_array_formulario;
import ConnRPI.JSchExampleSSHConnection;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.Grafico.FunFreeChard;
import Evento.JButton.EvenJButton;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenSonido;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import IMPRESORA_POS.PosImprimir_Garantia;
import IMPRESORA_POS.PosImprimir_Venta;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Tooltip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmOcupacion_boton extends javax.swing.JInternalFrame {

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
    private EvenRender render = new EvenRender();
    private habitacion_costo ENThc = new habitacion_costo();
    private DAO_habitacion_costo DAOhc = new DAO_habitacion_costo();
    private habitacion_dato ENThd = new habitacion_dato();
    private DAO_habitacion_dato DAOhd = new DAO_habitacion_dato();
    private habitacion_recepcion_temp ENThrt = new habitacion_recepcion_temp();
    private DAO_habitacion_recepcion_temp DAOhrt = new DAO_habitacion_recepcion_temp();
    private BO_habitacion_recepcion_temp BOhrt = new BO_habitacion_recepcion_temp();
    private habitacion_recepcion ENThr = new habitacion_recepcion();
    private DAO_habitacion_recepcion DAOhr = new DAO_habitacion_recepcion();
    private caja_cierre_detalle ENTccd = new caja_cierre_detalle();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    private PosImprimir_Venta posv = new PosImprimir_Venta();
    private PosImprimir_Garantia posgar = new PosImprimir_Garantia();
    private venta ENTven = new venta();
    private DAO_venta DAOven = new DAO_venta();
    private BO_venta BOven = new BO_venta();
    private venta_item ENTveni = new venta_item();
    private DAO_venta_item DAOveni = new DAO_venta_item();
    private BO_venta_item BOveni = new BO_venta_item();
    private habitacion_mini_pc ENThmp = new habitacion_mini_pc();
    private DAO_habitacion_mini_pc DAOhmp = new DAO_habitacion_mini_pc();
    private garantia ENTgar = new garantia();
    private DAO_garantia DAOgar = new DAO_garantia();
    private BO_garantia BOgar = new BO_garantia();
    private JSchExampleSSHConnection connRPI = new JSchExampleSSHConnection();
    private ComputerInfo pcinfo = new ComputerInfo();
    json_array_formulario jsfrm = new json_array_formulario();
    private usuario ENTusu = new usuario(); //creado_por = ENTusu.getGlobal_nombre();
    private DAO_usuario DAOusu = new DAO_usuario();
    Connection conn = ConnPostgres.getConnPosgres();
    private producto ENTp = new producto();
    private DAO_producto DAOp = new DAO_producto();
    private java.util.List<JButton> botones_nro_hab;
    DefaultTableModel model_itemf = new DefaultTableModel();
    private String nombreTabla_pri = "VENTA PRINCIPAL";
    private String nombreTabla_sec = "FILTRO";
    private String creado_por = "digno";
    private Timer tiempo_boton;
    private int nro_habitacion_select;
    private int jbar_tie_min_max = 92;
    private int tiempo_boton_hab = 0;
    private int tiempo_boton_hab_max = 40;
    private String btnlibre_html = "<html><p style=\"color:green\"><font size=\"4\">LIBRE</font></p></html>";
    private String btncancelar_html = "<html><p style=\"color:purple\"><font size=\"4\">CANCELAR</font></p></html>";
    private String btnocupar_html = "<html><p style=\"color:red\"><font size=\"4\">OCUPAR</font></p></html>";
    private String btndesocupar_html = "<html><p style=\"color:green\"><font size=\"4\">DESOCUPAR</font></p></html>";
    private String btnreiniciar_html = "<html><p style=\"color:red\"><font size=\"4\">REINICIAR</font></p></html>";
    private String fecha_hora_ahora;
    private int sensor_puerta_cliente = 2;
    private int sensor_puerta_limpieza = 3;
    private boolean boton_hab_unavez = true;
    private boolean boton_mudar_unavez = true;
    private boolean boton_puerta_unavez = true;
    private String observacion_venta = "";
    private int cantidad_habitacion_temp;
    private String[] Sa_nombre_boton;
    private String[] Sa_icono;
    private String[] Sa_color_fondo;
    private String[] Sa_color_texto;
    private int seg_cant_sql = 58;
    boolean seg_intercalar = false;

    private void abrir_formulario() {
        creado_por = ENTusu.getGlobal_nombre();
        this.setTitle(nombreTabla_pri + " USUARIO:" + creado_por + " IP:" + pcinfo.getStringMiIP());
        evetbl.centrar_formulario_internalframa(this);
        botones_nro_hab = new ArrayList<>();
        iniciar_array_habitacion();
        cargar_boton_habitacion(true);
    }

    private Color getCol_Exa(String hexColor) {
        return Color.decode(hexColor);
    }

    public int getInt_cant_habitacion_activo(Connection conn) {
        String titulo = "getInt_ultimoID";
        int getid = 0;
        String sql = "select count(*) as cant from habitacion_recepcion_temp where activo=true;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                getid = rs.getInt("cant");
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return getid;
    }

    private void iniciar_array_habitacion() {
        cantidad_habitacion_temp = getInt_cant_habitacion_activo(conn);
        Sa_nombre_boton = new String[cantidad_habitacion_temp + 1];
        Sa_icono = new String[cantidad_habitacion_temp + 1];
        Sa_color_fondo = new String[cantidad_habitacion_temp + 1];
        Sa_color_texto = new String[cantidad_habitacion_temp + 1];
    }

    private void iniciarTiempo() {
        tiempo_boton = new Timer();
        tiempo_boton.schedule(new FrmOcupacion_boton.clasetiempo(), 0, 1000 * 1);
        System.out.println("Timer INICIAR");
    }

    private void ejecutar_update_habitacion_recepcion_temp_FUERTE() {
        String sql = DAOhrt.getSql_ocupacion_boton_FUERTE();
        eveconn.SQL_execute_libre_sin_print(conn, sql);
    }

    private void ejecutar_update_habitacion_recepcion_temp_LIBIANO() {
        String sql = DAOhrt.getSql_ocupacion_boton_LIBIANO();
        eveconn.SQL_execute_libre_sin_print(conn, sql);
    }

    private void cargar_boton_habitacion(boolean es_inicio) {
        String titulo = "cargar_boton_habitacion";
        String sql1 = "select ('<html><p><font size=\"4\">'||nro_habitacion||'</font>-'||tipo_habitacion||"
                + "'</p><p>'||idhabitacion_dato||':'||descrip_estado||'</p>'||\n"
                + "'<p><font size=\"4\">'||tiempo_estado||'</font></p>'||\n"
                + "'<p>'||TRIM(to_char(monto_gral,'999G999G999'))||'</p></html>') as nom_boton,"
                + "idhabitacion_dato, \n"
                + "ruta_icono as icono, \n"
                + "case when estado='LIBRE'     and puerta_limpieza = false and puerta_cliente = false then '#DFD3C3'\n"
                + "     when estado='OCUPADO'   and cant_hora_adicional>0 then '#FFCCFF'\n"
                + "     when estado='OCUPADO'   and (extract(epoch from (current_timestamp-fec_ocupado_inicio))<(minuto_cancelar * 60)) \n"
                + "     then (case when MOD((cast( extract(epoch from (current_timestamp-fec_ocupado_inicio)) as integer)/5),2)=0 then '#A85CF9' else '#6FDFDF' end)" //#DFD3C3
                + "     else '#F0F0F0' end as color_fondo, \n"
                + "case when estado='LIBRE' then '#006532' \n"
                + "     when estado='OCUPADO' then '#FF0000' \n"
                + "     when estado='SUCIO' then '#CB9800' \n"
                + "     else '#000000' end as color_texto \n"
                + "from habitacion_recepcion_temp \n"
                + "order by idhabitacion_dato asc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql1, titulo);
            if (es_inicio) {
                panel_habitaciones.removeAll();
                botones_nro_hab.clear();
            }
            int fila = 0;
            while (rs.next()) {
                String textboton = rs.getString("nom_boton");
                String nameboton = rs.getString("idhabitacion_dato");
                String icono = rs.getString("icono");
                String color_fondo = rs.getString("color_fondo");
                String color_texto = rs.getString("color_texto");
                Sa_nombre_boton[fila] = textboton;
                Sa_icono[fila] = icono;
                Sa_color_fondo[fila] = color_fondo;
                Sa_color_texto[fila] = color_texto;
                if (es_inicio) {
                    crear_boton_habitacion(textboton, nameboton, icono, color_fondo, color_texto);
                }
                fila++;
            }
            if (es_inicio) {
                panel_habitaciones.updateUI();
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql1, titulo);
        }
    }

    private void actualizar_boton_habitacion() {
        String titulo = "actualizar_boton_habitacion";
        for (int fila = 0; fila < cantidad_habitacion_temp; fila++) {
            try {
                botones_nro_hab.get(fila).setText(Sa_nombre_boton[fila]);
                botones_nro_hab.get(fila).setBackground(getCol_Exa(Sa_color_fondo[fila]));
                botones_nro_hab.get(fila).setForeground(getCol_Exa(Sa_color_texto[fila]));
                if (!Sa_icono[fila].equals("no")) {
                    botones_nro_hab.get(fila).setIcon(new ImageIcon(this.getClass().getResource(Sa_icono[fila])));
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, titulo);
            }
        }
    }

    private void crear_boton_habitacion(String textboton, String nameboton, String icono, String color_fondo, String color_texto) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setName(nameboton);
        if (!icono.equals("no")) {
            boton.setIcon(new ImageIcon(this.getClass().getResource(icono)));
        }
        boton.setBackground(getCol_Exa(color_fondo));
        boton.setForeground(getCol_Exa(color_texto));
        panel_habitaciones.add(boton);
        botones_nro_hab.add(boton);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idhabitacion_dato = ((JButton) e.getSource()).getName();
                System.out.println("idhabitacion_dato:" + idhabitacion_dato);
            }
        });
        boton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                evebtn.setColor_mouseMoved(((JButton) evt.getSource()));

            }
        });
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                evebtn.setColor_mouseExited_neutro(((JButton) evt.getSource()));
            }
        });
    }

    class clasetiempo extends TimerTask {

        public void run() {
            try {
                fecha_hora_ahora = evefec.getString_formato_fecha_hora();
                txttiempo_ahora.setText(fecha_hora_ahora);
                seg_cant_sql++;
                if (seg_cant_sql >= 60) {
                    ejecutar_update_habitacion_recepcion_temp_FUERTE();
                    jPanel_estado_habitacion.setBackground(Color.red);
                    seg_cant_sql = 0;
                } else {
                    jPanel_estado_habitacion.setBackground(new Color(240, 240, 240));
                    if (seg_intercalar) {
                        ejecutar_update_habitacion_recepcion_temp_LIBIANO();
                        cargar_boton_habitacion(false);
                        seg_intercalar = false;
                    } else {
                        actualizar_boton_habitacion();
                        seg_intercalar = true;
                    }
                }
            } catch (RuntimeException e) {
                System.err.println("Uncaught Runtime Exception" + e);
                JOptionPane.showMessageDialog(null, "Error:" + e + "\nEL SISTEMA SE REINICIA", "ERROR TIMER", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                return; // Keep working
            } catch (Throwable e) {
                System.err.println("Unrecoverable error" + e);
                JOptionPane.showMessageDialog(null, "Error:" + e + "\nEL SISTEMA SE REINICIA", "ERROR TIMER", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                throw e;
            } finally {
//                System.err.print("final");
            }
        }//fin run
    }

    private void pararTimer() {
        FrmMenuMotel.setHabilitar_sonido(false);
        tiempo_boton.cancel();
        System.out.println("Timer PARADO:");
    }

    public FrmOcupacion_boton() {
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

        gru_tarifa = new javax.swing.ButtonGroup();
        jTab_principal = new javax.swing.JTabbedPane();
        jPanel_estado_habitacion = new javax.swing.JPanel();
        panel_puerta = new javax.swing.JPanel();
        panel_habitaciones = new javax.swing.JPanel();
        txttiempo_ahora = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jTab_principal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTab_principalMouseReleased(evt);
            }
        });

        panel_puerta.setBorder(javax.swing.BorderFactory.createTitledBorder("PUERTAS"));
        panel_puerta.setLayout(new java.awt.GridLayout(0, 4));

        panel_habitaciones.setBorder(javax.swing.BorderFactory.createTitledBorder("HABITACIONES"));
        panel_habitaciones.setLayout(new java.awt.GridLayout(0, 5));

        txttiempo_ahora.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txttiempo_ahora.setForeground(new java.awt.Color(255, 0, 0));
        txttiempo_ahora.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttiempo_ahora.setText("jTextField3");

        jButton1.setText("cargar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_estado_habitacionLayout = new javax.swing.GroupLayout(jPanel_estado_habitacion);
        jPanel_estado_habitacion.setLayout(jPanel_estado_habitacionLayout);
        jPanel_estado_habitacionLayout.setHorizontalGroup(
            jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_estado_habitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_puerta, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_habitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 1085, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_estado_habitacionLayout.createSequentialGroup()
                        .addComponent(txttiempo_ahora, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_estado_habitacionLayout.setVerticalGroup(
            jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_estado_habitacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_estado_habitacionLayout.createSequentialGroup()
                        .addComponent(panel_habitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 555, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_estado_habitacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttiempo_ahora, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_estado_habitacionLayout.createSequentialGroup()
                        .addComponent(panel_puerta, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jTab_principal.addTab("ESTADO HABITACION", jPanel_estado_habitacion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 1271, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

        iniciarTiempo();
    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        FrmMenuMotel.setAbrir_frmventa(true);
        pararTimer();
    }//GEN-LAST:event_formInternalFrameClosing

    private void jTab_principalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTab_principalMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jTab_principalMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cargar_boton_habitacion(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        actualizar_boton_habitacion();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup gru_tarifa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel_estado_habitacion;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_habitaciones;
    private javax.swing.JPanel panel_puerta;
    private javax.swing.JTextField txttiempo_ahora;
    // End of variables declaration//GEN-END:variables
}
