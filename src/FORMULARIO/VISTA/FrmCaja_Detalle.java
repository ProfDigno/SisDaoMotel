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
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import IMPRESORA_POS.PosImprimir_Venta;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JTable;

/**
 *
 * @author Digno
 */
public class FrmCaja_Detalle extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private EvenCombobox evecmb = new EvenCombobox();
    private caja_cierre_detalle ENTccd = new caja_cierre_detalle();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    private caja_cierre ENTcc = new caja_cierre();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    private BO_caja_cierre BOcc = new BO_caja_cierre();
    private caja_cierre_item ENTcci = new caja_cierre_item();
    private DAO_caja_cierre_item DAOcci = new DAO_caja_cierre_item();
    private DAO_venta DAOven = new DAO_venta();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenConexion eveconn = new EvenConexion();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private EvenEstado eveest = new EvenEstado();
    private PosImprimir_Venta posv = new PosImprimir_Venta();
    usuario ENTusu = new usuario();
    Connection conn = ConnPostgres.getConnPosgres();
    private String nombreTabla = "CAJA CIERRE";
    int fk_idusuario = ENTusu.getGlobal_idusuario();
    private String fec_inicio;
    private String fec_final;
    private String creado_por;
    String usu_id = "idusuario";
    String usu_nombre = "nombre";
    String usu_tabla = "usuario";

    private void abrir_formulario() {
        creado_por = ENTusu.getGlobal_nombre();
        this.setTitle(nombreTabla + " USUARIO:" + creado_por);
        evetbl.centrar_formulario_internalframa(this);

        fk_idusuario = ENTusu.getGlobal_idusuario();
        evefec.cargar_combobox_intervalo_fecha(cmbfecha_caja_cierre);
        cargar_usuario();
        actualizar_tabla_caja_cierre_detalle_ABIERTO();
        actualizar_tabla_caja_cierre();
    }

    private void cargar_usuario() {
        evecmb.cargarCombobox(conn, cmbusuario, usu_id, usu_nombre, usu_tabla, "");
    }

    private void actualizar_tabla_caja_cierre_detalle_ABIERTO() {
        DAOccd.actualizar_tabla_caja_cierre_detalle_ABIERTO(conn, tblcaja_abierto, fk_idusuario);
        suma_total_caja_detalle_abierto(fk_idusuario);
    }

    private void actualizar_tabla_caja_cierre() {
        int idusuario = evecmb.getInt_seleccionar_COMBOBOX(conn, cmbusuario, usu_id, usu_nombre, usu_tabla);
        String filtro = evefec.getIntervalo_fecha_combobox(cmbfecha_caja_cierre, "cc.fecha_creado");
        if (idusuario > 0) {
            filtro = filtro + " and cc.fk_idusuario=" + idusuario;
        }
        DAOcc.actualizar_tabla_caja_cierre(conn, tblresumen_caja_cierre, filtro);
        suma_total_caja_detalle_cerrado(filtro);
    }

    private void suma_total_caja_detalle_abierto(int idusuario) {
        String titulo = "suma_total_caja_detalle_abierto";
        String sql = "select \n"
                + "sum(cd.monto_solo_adelanto) as adelanto,\n"
                + "sum(cd.monto_ocupa_minimo) as minimo,\n"
                + "sum(cd.monto_ocupa_adicional) as adicional,\n"
                + "sum(cd.monto_ocupa_consumo) as consumo,\n"
                + "sum((0-(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))) as descuento,\n"
                + "sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))) as ingreso, \n"
                + "to_char(min(fecha_creado),'yyyy-MM-dd HH24:MI:ss') as fec_inicio, "
                + "to_char(max(fecha_creado),'yyyy-MM-dd HH24:MI:ss') as fec_final "
                + "from caja_cierre_detalle cd\n"
                + "where cd.es_cerrado=false \n"
                + "and cd.estado='EMITIDO'\n"
                + "and cd.fk_idusuario=" + idusuario;
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                double adelanto = rs.getDouble("adelanto");
                double minimo = rs.getDouble("minimo");
                double adicional = rs.getDouble("adicional");
                double consumo = rs.getDouble("consumo");
                double descuento = rs.getDouble("descuento");
                double ingreso = rs.getDouble("ingreso");
                jFtotal_ocupa_adelanto.setValue(adelanto);
                jFtotal_ocupa_minimo.setValue(minimo);
                jFtotal_ocupa_adicional.setValue(adicional);
                jFtotal_ocupa_consumo.setValue(consumo);
                jFtotal_ocupa_descuento.setValue(descuento);
                jFtotal_ocupa_ingreso.setValue(ingreso);
                fec_inicio = rs.getString("fec_inicio");
                txtfecha_inicio.setText(fec_inicio);
                fec_final = rs.getString("fec_final");
                txtfecha_final.setText(fec_final);
            }

        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    private void boton_cerrar_caja() {
        if (tblcaja_abierto.getRowCount() > 0) {
            if (evemen.MensajeGeneral_warning("ESTAS SEGURO DE CERRAR LA CAJA:"
                    + "\nUSUARIO:         " + creado_por
                    + "\nTOTAL INGRESO:   " + jFtotal_ocupa_ingreso.getText(),
                    "CERRAR CAJA", "ACEPTAR", "CANCELAR")) {
                ENTcc.setC3creado_por(creado_por);
                ENTcc.setC4fecha_inicio(fec_inicio);
                ENTcc.setC5fecha_fin(fec_final);
                ENTcc.setC6estado(eveest.getEst_Cerrado());
                ENTcc.setC7fk_idusuario(fk_idusuario);
                BOcc.insertar_caja_cierre(ENTcc);
                actualizar_tabla_caja_cierre_detalle_ABIERTO();
                actualizar_tabla_caja_cierre();
                if (evemen.MensajeGeneral_question("DESEA IMPRIMIR EL REPORTE DE CIERRE DE CAJA ", "IMPRIMIR CAJA", "IMPRIMIR", "CANCELAR")) {
                    DAOcc.imprimir_caja_cierre_jasper(conn, ENTcc.getC1idcaja_cierre());
                }
            }
        }
    }

    private void seleccionar_caja_cierre() {
        if (tblresumen_caja_cierre.getSelectedRow() >= 0) {
            int idcaja_cierre = eveJtab.getInt_select_id(tblresumen_caja_cierre);
            DAOven.actualizar_tabla_venta_desde_caja_cierre(conn, tblventa, idcaja_cierre);
            DAOven.actualizar_tabla_venta_item_desde_caja_cierre(conn, tblventa_consumo, idcaja_cierre);
            double total_consumo=eveJtab.getDouble_sumar_tabla(tblventa_consumo,5);
            jFtotal_consumo.setValue(total_consumo);
        }
    }

    private void boton_imprimir_caja_cierre() {
        if (eveJtab.getBoolean_select_tabla_mensaje(tblresumen_caja_cierre, "SELECCIONAR LA TABLA CAJA CIERRE")) {
            int idcaja_cierre = eveJtab.getInt_select_id(tblresumen_caja_cierre);
            DAOcc.imprimir_caja_cierre_jasper(conn, idcaja_cierre);
        }
    }

    private void suma_total_caja_detalle_cerrado(String filtro) {
        String titulo = "suma_total_caja_detalle_abierto";
        String sql = "select \n"
                + "sum((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto)) as ingreso,\n"
                + "sum(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion) as egreso,\n"
                + "sum(((cd.monto_solo_adelanto+cd.monto_ocupa_minimo+cd.monto_ocupa_adicional+cd.monto_ocupa_consumo)-\n"
                + "(cd.monto_ocupa_descuento+cd.monto_ocupa_adelanto))-\n"
                + "(cd.monto_gasto+cd.monto_compra+cd.monto_vale+cd.monto_liquidacion)) as saldo\n"
                + "from caja_cierre_detalle cd,caja_cierre_item cci,caja_cierre cc \n"
                + "where cci.fk_idcaja_cierre_detalle=cd.idcaja_cierre_detalle \n"
                + "and cd.es_cerrado=true \n"
                + "and cci.fk_idcaja_cierre=cc.idcaja_cierre \n" + filtro
                + " ";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                double ingreso = rs.getDouble("ingreso");
                double egreso = rs.getDouble("egreso");
                double saldo = rs.getDouble("saldo");
                jFtotal_ingreso.setValue(ingreso);
                jFtotal_egreso.setValue(egreso);
                jFtotal_saldo.setValue(saldo);
            }

        } catch (Exception e) {
            evemen.Imprimir_serial_sql_error(e, sql, titulo);
        }
    }

    private void boton_imprimir_ticket_venta(JTable tblventa) {
        if (eveJtab.getBoolean_select_tabla_mensaje(tblventa, "SELECCIONAR LA TABLA DE VENTA")) {
            int idventa = eveJtab.getInt_select_id(tblventa);
            posv.boton_imprimir_pos_VENTA(conn, idventa);
        }
    }

    public FrmCaja_Detalle() {
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblcaja_abierto = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jFtotal_ocupa_minimo = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_adelanto = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_adicional = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_consumo = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_descuento = new javax.swing.JFormattedTextField();
        jFtotal_ocupa_ingreso = new javax.swing.JFormattedTextField();
        txtfecha_inicio = new javax.swing.JTextField();
        txtfecha_final = new javax.swing.JTextField();
        btncarrar_caja = new javax.swing.JButton();
        btnimprimir_ticket = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblresumen_caja_cierre = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblventa = new javax.swing.JTable();
        btnimprimir_ticket1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblventa_consumo = new javax.swing.JTable();
        jFtotal_consumo = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        btnimprimir_caja_cierre = new javax.swing.JButton();
        cmbfecha_caja_cierre = new javax.swing.JComboBox<>();
        cmbusuario = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jFtotal_ingreso = new javax.swing.JFormattedTextField();
        jFtotal_egreso = new javax.swing.JFormattedTextField();
        jFtotal_saldo = new javax.swing.JFormattedTextField();

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("CAJA ABIERTO"));

        tblcaja_abierto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblcaja_abierto);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESOS"));

        jFtotal_ocupa_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL MINIMO"));
        jFtotal_ocupa_minimo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_minimo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_adelanto.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL ADELANTO"));
        jFtotal_ocupa_adelanto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_adelanto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_adelanto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_adicional.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL ADICIONAL"));
        jFtotal_ocupa_adicional.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_adicional.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_adicional.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_consumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL CONSUMO"));
        jFtotal_ocupa_consumo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_consumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_consumo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_descuento.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL DESCUENTO"));
        jFtotal_ocupa_descuento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_descuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_descuento.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jFtotal_ocupa_ingreso.setBackground(new java.awt.Color(255, 255, 102));
        jFtotal_ocupa_ingreso.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL INGRESO"));
        jFtotal_ocupa_ingreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ocupa_ingreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ocupa_ingreso.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFtotal_ocupa_adelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jFtotal_ocupa_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_ocupa_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_ocupa_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFtotal_ocupa_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                .addComponent(jFtotal_ocupa_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFtotal_ocupa_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_ocupa_adelanto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_ocupa_adicional, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_ocupa_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_ocupa_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_ocupa_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtfecha_inicio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_inicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfecha_inicio.setText("jTextField1");
        txtfecha_inicio.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA INICIO"));

        txtfecha_final.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_final.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfecha_final.setText("jTextField1");
        txtfecha_final.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA FINAL"));

        btncarrar_caja.setText("CERRAR CAJA");
        btncarrar_caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncarrar_cajaActionPerformed(evt);
            }
        });

        btnimprimir_ticket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_ticket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ticketActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfecha_final, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btncarrar_caja, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnimprimir_ticket, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtfecha_inicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfecha_final, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btncarrar_caja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnimprimir_ticket, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("DETALLE CAJA ABIERTO", jPanel1);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("RESUMEN CAJA CERRADO"));

        tblresumen_caja_cierre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblresumen_caja_cierre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblresumen_caja_cierreMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblresumen_caja_cierre);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
        );

        tblventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblventa);

        btnimprimir_ticket1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_ticket1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_ticket1ActionPerformed(evt);
            }
        });

        tblventa_consumo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblventa_consumo);

        jFtotal_consumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL CONSUMO"));
        jFtotal_consumo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_consumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_consumo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnimprimir_ticket1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                        .addComponent(jFtotal_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnimprimir_ticket1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFtotal_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPane2.addTab("DETALLE OCUPACION", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1226, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 379, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("tab2", jPanel7);

        btnimprimir_caja_cierre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ven_imprimir.png"))); // NOI18N
        btnimprimir_caja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_caja_cierreActionPerformed(evt);
            }
        });

        cmbfecha_caja_cierre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfecha_caja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbfecha_caja_cierreActionPerformed(evt);
            }
        });

        cmbusuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbusuarioActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha:");

        jLabel2.setText("Usuario:");

        jFtotal_ingreso.setEditable(false);
        jFtotal_ingreso.setBackground(new java.awt.Color(255, 255, 204));
        jFtotal_ingreso.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL INGRESO"));
        jFtotal_ingreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_ingreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_ingreso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jFtotal_egreso.setEditable(false);
        jFtotal_egreso.setBackground(new java.awt.Color(255, 255, 204));
        jFtotal_egreso.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL EGRESO"));
        jFtotal_egreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_egreso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_egreso.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jFtotal_saldo.setEditable(false);
        jFtotal_saldo.setBackground(new java.awt.Color(255, 255, 204));
        jFtotal_saldo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL SALDO"));
        jFtotal_saldo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_saldo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_saldo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jFtotal_saldo)
                        .addGap(159, 159, 159)
                        .addComponent(btnimprimir_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jFtotal_ingreso, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.Alignment.LEADING, 0, 159, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jFtotal_egreso, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbusuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jTabbedPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_ingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_egreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnimprimir_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFtotal_saldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2))
        );

        jTabbedPane1.addTab("CAJA CERRADO", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOccd.ancho_tabla_caja_cierre_detalle_ABIERTO(tblcaja_abierto);
        DAOcc.ancho_tabla_caja_cierre(tblresumen_caja_cierre);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btncarrar_cajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncarrar_cajaActionPerformed
        // TODO add your handling code here:
        boton_cerrar_caja();
    }//GEN-LAST:event_btncarrar_cajaActionPerformed

    private void tblresumen_caja_cierreMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblresumen_caja_cierreMouseReleased
        // TODO add your handling code here:
        seleccionar_caja_cierre();
    }//GEN-LAST:event_tblresumen_caja_cierreMouseReleased

    private void btnimprimir_caja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_caja_cierreActionPerformed
        // TODO add your handling code here:
        boton_imprimir_caja_cierre();
    }//GEN-LAST:event_btnimprimir_caja_cierreActionPerformed

    private void cmbfecha_caja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfecha_caja_cierreActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_cmbfecha_caja_cierreActionPerformed

    private void cmbusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbusuarioActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_cmbusuarioActionPerformed

    private void btnimprimir_ticketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ticketActionPerformed
        // TODO add your handling code here:
        boton_imprimir_ticket_venta(tblcaja_abierto);
    }//GEN-LAST:event_btnimprimir_ticketActionPerformed

    private void btnimprimir_ticket1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_ticket1ActionPerformed
        // TODO add your handling code here:
        boton_imprimir_ticket_venta(tblventa);
    }//GEN-LAST:event_btnimprimir_ticket1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncarrar_caja;
    private javax.swing.JButton btnimprimir_caja_cierre;
    private javax.swing.JButton btnimprimir_ticket;
    private javax.swing.JButton btnimprimir_ticket1;
    private javax.swing.JComboBox<String> cmbfecha_caja_cierre;
    private javax.swing.JComboBox<String> cmbusuario;
    private javax.swing.JFormattedTextField jFtotal_consumo;
    private javax.swing.JFormattedTextField jFtotal_egreso;
    private javax.swing.JFormattedTextField jFtotal_ingreso;
    private javax.swing.JFormattedTextField jFtotal_ocupa_adelanto;
    private javax.swing.JFormattedTextField jFtotal_ocupa_adicional;
    private javax.swing.JFormattedTextField jFtotal_ocupa_consumo;
    private javax.swing.JFormattedTextField jFtotal_ocupa_descuento;
    private javax.swing.JFormattedTextField jFtotal_ocupa_ingreso;
    private javax.swing.JFormattedTextField jFtotal_ocupa_minimo;
    private javax.swing.JFormattedTextField jFtotal_saldo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tblcaja_abierto;
    private javax.swing.JTable tblresumen_caja_cierre;
    private javax.swing.JTable tblventa;
    private javax.swing.JTable tblventa_consumo;
    private javax.swing.JTextField txtfecha_final;
    private javax.swing.JTextField txtfecha_inicio;
    // End of variables declaration//GEN-END:variables
}
