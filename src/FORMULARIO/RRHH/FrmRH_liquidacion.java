/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.RRHH;

import FORMULARIO.VISTA.*;
import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Fecha.EvenFecha;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.BO_gasto_tipo;
import FORMULARIO.BO.BO_rh_liquidacion;
import FORMULARIO.DAO.DAO_gasto_tipo;
import FORMULARIO.DAO.DAO_persona;
import FORMULARIO.DAO.DAO_rh_liquidacion;
import FORMULARIO.DAO.DAO_rh_vale;
import FORMULARIO.ENTIDAD.gasto_tipo;
import FORMULARIO.ENTIDAD.persona;
import FORMULARIO.ENTIDAD.rh_liquidacion;
import FORMULARIO.ENTIDAD.usuario;
import IMPRESORA_POS.PosImprimir_vale;
//import FORMULARIO.BO.*;
//import FORMULARIO.DAO.*;
//import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmRH_liquidacion extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private rh_liquidacion ENTrhli = new rh_liquidacion();
    private rh_liquidacion ENTrhli2 = new rh_liquidacion();
    private DAO_rh_liquidacion DAOrhli = new DAO_rh_liquidacion();
    private BO_rh_liquidacion BOrhli = new BO_rh_liquidacion();
    private DAO_persona DAOper = new DAO_persona();
    private persona ENTper = new persona();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenEstado eveest = new EvenEstado();
    private EvenNumero_a_Letra evenrolt = new EvenNumero_a_Letra();
    private PosImprimir_vale posvale = new PosImprimir_vale();
    private EvenMensajeJoptionpane evemsj = new EvenMensajeJoptionpane();
    private EvenFecha evefec=new EvenFecha();
    private DAO_rh_vale DAOrhv = new DAO_rh_vale();
    Connection conn = ConnPostgres.getConnPosgres();
    private usuario ENTusu = new usuario();
    private String nombreTabla = "RH LIQUIDACION";
    private String creado_por = "digno";
    private int idpersona;
    private int idrh_liquidacion;

    private void abrir_formulario() {
        this.setTitle(nombreTabla);
        creado_por = ENTusu.getGlobal_nombre();
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        DAOrhli.actualizar_tabla_rh_liquidacion(conn, tblliquidacion);
        DAOper.actualizar_tabla_persona_liquida(conn, tblpersonal);
    }
//    private void color_formulario(){
//        panel_tabla.setBackground(clacolor.getColor_tabla());
//        panel_insertar.setBackground(clacolor.getColor_insertar_primario());
//    }

    private boolean validar_guardar() {
//        if (evejtf.getBoo_JTextField_vacio(txtfecha_desde, "DEBE CARGAR UN NOMBRE")) {
//            return false;
//        }

        return true;
    }

    private void cargar_dato_liquidacion_cerrar() {
         idrh_liquidacion = DAOrhli.getInt_idrh_liquidacion_rh_liquidacion_abierto(conn, idpersona);
        DAOrhli.cargar_rh_liquidacion(conn, ENTrhli, idrh_liquidacion);
        String fecha=evefec.getfechaDCStringFormat(dcfecha_hasta,"yyyy-MM-dd");
        double liquidacion = ENTrhli.getC11salario_base() - (ENTrhli.getSum_vale() + ENTrhli.getSum_descuento());
        int Imonto = (int) liquidacion;
        String Smonto = String.valueOf(Imonto);
        String monto_letra = evenrolt.Convertir(Smonto, true);
        ENTrhli.setC1idrh_liquidacion(idrh_liquidacion);
        ENTrhli.setC5fecha_hasta(fecha);
        ENTrhli.setC6estado(eveest.getEst_Cerrado());
        ENTrhli.setC7es_cerrado(true);
        ENTrhli.setC8monto_vale(ENTrhli.getSum_vale());
        ENTrhli.setC9monto_descuento(ENTrhli.getSum_descuento());
        ENTrhli.setC10monto_liquidacion(liquidacion);
        ENTrhli.setC11salario_base(ENTrhli.getC11salario_base());
        ENTrhli.setC12monto_letra(monto_letra);
        ENTrhli.setC13fk_idpersona(idpersona);
    }
    private void cargar_dato_liquidacion_nuevo(){
        ENTrhli2.setC3creado_por(creado_por);
        ENTrhli2.setC6estado(eveest.getEst_ABIERTO());
        ENTrhli2.setC7es_cerrado(false);
        ENTrhli2.setC8monto_vale(0);
        ENTrhli2.setC9monto_descuento(0);
        ENTrhli2.setC10monto_liquidacion(0);
        ENTrhli2.setC11salario_base(ENTrhli.getC11salario_base());
        ENTrhli2.setC12monto_letra("cero");
        ENTrhli2.setC13fk_idpersona(idpersona);
    }
    private void boton_cerrar_liquidacion() {
        if (validar_guardar()) {
            cargar_dato_liquidacion_cerrar();
            cargar_dato_liquidacion_nuevo();
            BOrhli.update_rh_liquidacion_cerrar(ENTrhli, ENTrhli2);
            DAOrhli.actualizar_tabla_rh_liquidacion(conn, tblliquidacion);
            DAOrhli.imprimir_nota_rh_liquidacion(conn, idrh_liquidacion);
            reestableser();
        }
    }

    

    private void seleccionar_tabla() {
        int id = eveJtab.getInt_select_id(tblliquidacion);
        DAOrhli.cargar_rh_liquidacion(conn, ENTrhli, id);
        txtid.setText(String.valueOf(ENTrhli.getC1idrh_liquidacion()));
        this.setTitle(nombreTabla + "/fecha creado:" + ENTrhli.getC2fecha_creado() + "/ Creado Por:" + ENTrhli.getC3creado_por());
        btncerrar_liquidacion.setEnabled(false);
//        btneditar.setEnabled(true);
    }

    private void seleccionar_tabla_personal() {
        idpersona = eveJtab.getInt_select_id(tblpersonal);
        ENTper.setIdpersona_global(idpersona);
        DAOper.cargar_persona(conn, ENTper, idpersona);
        txtpersona.setText(ENTper.getC4nombre());
        jfsalario_base.setValue(ENTper.getC10salario_base());
        int idrh_liquidacion = DAOrhli.getInt_idrh_liquidacion_rh_liquidacion_abierto(conn, idpersona);
        DAOrhli.cargar_rh_liquidacion(conn, ENTrhli, idrh_liquidacion);
        txtfecha_desde.setText(ENTrhli.getC4fecha_desde());
        txtestado.setText(ENTrhli.getC6estado());
        jfmonto_vale.setValue(ENTrhli.getSum_vale());
        jfmonto_descuento.setValue(ENTrhli.getSum_descuento());
        double liquidacion = ENTrhli.getC11salario_base() - (ENTrhli.getSum_vale() + ENTrhli.getSum_descuento());
        jfmonto_liquidacion.setValue(liquidacion);
        int Imonto = (int) liquidacion;
        String Smonto = String.valueOf(Imonto);
        String monto_letra = evenrolt.Convertir(Smonto, true);
        txtmonto_letra.setText(monto_letra);
        btnnuevo_vale.setEnabled(true);
        btnnuevo_descuento.setEnabled(true);
        DAOrhli.actualizar_tabla_rh_liquidacion_entrada(conn, tblentrada, idrh_liquidacion);
        DAOrhli.actualizar_tabla_rh_liquidacion_vale(conn, tblvale, idrh_liquidacion);
        DAOrhli.actualizar_tabla_rh_liquidacion_descuento(conn, tbldescuento, idrh_liquidacion);
    }

    private void reestableser() {
        this.setTitle(nombreTabla);
        txtid.setText(null);
        txtfecha_desde.setText(null);
        evefec.setFechaDCSistema(dcfecha_hasta);
        btnnuevo_vale.setEnabled(false);
        btnnuevo_descuento.setEnabled(false);
        btncerrar_liquidacion.setEnabled(true);
//        btneditar.setEnabled(false);
        txtfecha_desde.grabFocus();
    }

    private void boton_nuevo() {
        reestableser();
    }

    private void boton_imprimir_vale() {
        if (eveJtab.getBoolean_validar_select_mensaje(tblvale, "SELECCIONE UN VALE PARA IMPRIMIR")) {
            int mensaje = (evemsj.getIntMensaje_informacion_3btn("DESEA IMPRIMIR VALE", "VALE", "TICKET", "A4", "CANCELAR"));
            int idrh_vale = eveJtab.getInt_select_id(tblvale);
            if (mensaje == 0) {
                posvale.boton_imprimir_pos_vale(conn, idrh_vale);
            }
            if(mensaje==1){
                DAOrhv.imprimir_nota_rh_vale(conn, idrh_vale);
            }
        }
    }

    public FrmRH_liquidacion() {
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
        panel_insertar = new javax.swing.JPanel();
        txtid = new javax.swing.JTextField();
        txtfecha_desde = new javax.swing.JTextField();
        btncerrar_liquidacion = new javax.swing.JButton();
        dcfecha_hasta = new com.toedter.calendar.JDateChooser();
        txtestado = new javax.swing.JTextField();
        txtpersona = new javax.swing.JTextField();
        jfmonto_vale = new javax.swing.JFormattedTextField();
        jfmonto_descuento = new javax.swing.JFormattedTextField();
        jfmonto_liquidacion = new javax.swing.JFormattedTextField();
        jfsalario_base = new javax.swing.JFormattedTextField();
        txtmonto_letra = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblentrada = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblvale = new javax.swing.JTable();
        btnnuevo_vale = new javax.swing.JButton();
        btnimprimir_vale = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbldescuento = new javax.swing.JTable();
        btnnuevo_descuento = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblpersonal = new javax.swing.JTable();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblliquidacion = new javax.swing.JTable();

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

        panel_insertar.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR DATO"));

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtid.setBorder(javax.swing.BorderFactory.createTitledBorder("ID"));

        txtfecha_desde.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfecha_desde.setBorder(javax.swing.BorderFactory.createTitledBorder("FEC. DESDE:"));
        txtfecha_desde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfecha_desdeKeyPressed(evt);
            }
        });

        btncerrar_liquidacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btncerrar_liquidacion.setText("CERRAR LIQUIDACION");
        btncerrar_liquidacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncerrar_liquidacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncerrar_liquidacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrar_liquidacionActionPerformed(evt);
            }
        });

        dcfecha_hasta.setBorder(javax.swing.BorderFactory.createTitledBorder("FEC. HASTA:"));

        txtestado.setBorder(javax.swing.BorderFactory.createTitledBorder("ESTADO:"));

        txtpersona.setBorder(javax.swing.BorderFactory.createTitledBorder("PERSONAL:"));

        jfmonto_vale.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA VALE:"));
        jfmonto_vale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jfmonto_vale.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jfmonto_descuento.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMA DESCUENTO:"));
        jfmonto_descuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jfmonto_descuento.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jfmonto_liquidacion.setBorder(javax.swing.BorderFactory.createTitledBorder("LIQUIDACION:"));
        jfmonto_liquidacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jfmonto_liquidacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jfsalario_base.setBorder(javax.swing.BorderFactory.createTitledBorder("SALARIO BASE:"));
        jfsalario_base.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jfsalario_base.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtmonto_letra.setBorder(javax.swing.BorderFactory.createTitledBorder("LIQUIDACION LETRA:"));

        tblentrada.setModel(new javax.swing.table.DefaultTableModel(
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
        tblentrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblentradaMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblentrada);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 769, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 169, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("ENTRADA", jPanel1);

        tblvale.setModel(new javax.swing.table.DefaultTableModel(
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
        tblvale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblvaleMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblvale);

        btnnuevo_vale.setText("NUEVO VALE");
        btnnuevo_vale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_valeActionPerformed(evt);
            }
        });

        btnimprimir_vale.setText("IMPRIMIR");
        btnimprimir_vale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_valeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(625, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnnuevo_vale, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(btnimprimir_vale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 169, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnnuevo_vale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_vale)
                .addContainerGap(207, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("VALE", jPanel2);

        tbldescuento.setModel(new javax.swing.table.DefaultTableModel(
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
        tbldescuento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbldescuentoMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tbldescuento);

        btnnuevo_descuento.setText("DESCUENTO");
        btnnuevo_descuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_descuentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(615, Short.MAX_VALUE)
                .addComponent(btnnuevo_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 169, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnnuevo_descuento)
                .addContainerGap(236, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("DESCUENTO", jPanel3);

        tblpersonal.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpersonal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblpersonalMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblpersonal);

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtpersona, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dcfecha_hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtestado, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfsalario_base, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_insertarLayout.createSequentialGroup()
                            .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panel_insertarLayout.createSequentialGroup()
                                    .addComponent(jfmonto_vale, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jfmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jfmonto_liquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btncerrar_liquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(87, 87, 87))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfecha_desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtpersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jfsalario_base, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dcfecha_hasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtestado)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jfmonto_vale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jfmonto_descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jfmonto_liquidacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btncerrar_liquidacion)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertarLayout.createSequentialGroup()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );

        jTabbedPane1.addTab("DATO", panel_insertar);

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tblliquidacion.setModel(new javax.swing.table.DefaultTableModel(
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
        tblliquidacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblliquidacionMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblliquidacion);

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("TABLA", panel_tabla);

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

    private void btncerrar_liquidacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrar_liquidacionActionPerformed
        // TODO add your handling code here:
        boton_cerrar_liquidacion();
    }//GEN-LAST:event_btncerrar_liquidacionActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOtc.ancho_tabla_tercero_ciudad(tbltabla);
        DAOper.ancho_tabla_persona_liquida(tblpersonal);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblliquidacionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblliquidacionMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tblliquidacionMouseReleased

    private void txtfecha_desdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfecha_desdeKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
    }//GEN-LAST:event_txtfecha_desdeKeyPressed

    private void tblvaleMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvaleMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblvaleMouseReleased

    private void tblentradaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblentradaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblentradaMouseReleased

    private void tbldescuentoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldescuentoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbldescuentoMouseReleased

    private void tblpersonalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpersonalMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla_personal();
    }//GEN-LAST:event_tblpersonalMouseReleased

    private void btnnuevo_valeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_valeActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRH_vale());
    }//GEN-LAST:event_btnnuevo_valeActionPerformed

    private void btnnuevo_descuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_descuentoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmRH_descuento());
    }//GEN-LAST:event_btnnuevo_descuentoActionPerformed

    private void btnimprimir_valeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_valeActionPerformed
        // TODO add your handling code here:
        boton_imprimir_vale();
    }//GEN-LAST:event_btnimprimir_valeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncerrar_liquidacion;
    private javax.swing.JButton btnimprimir_vale;
    private javax.swing.JButton btnnuevo_descuento;
    private javax.swing.JButton btnnuevo_vale;
    private com.toedter.calendar.JDateChooser dcfecha_hasta;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    public static javax.swing.JFormattedTextField jfmonto_descuento;
    public static javax.swing.JFormattedTextField jfmonto_liquidacion;
    public static javax.swing.JFormattedTextField jfmonto_vale;
    private javax.swing.JFormattedTextField jfsalario_base;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    public static javax.swing.JTable tbldescuento;
    private javax.swing.JTable tblentrada;
    private javax.swing.JTable tblliquidacion;
    private javax.swing.JTable tblpersonal;
    public static javax.swing.JTable tblvale;
    private javax.swing.JTextField txtestado;
    private javax.swing.JTextField txtfecha_desde;
    private javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtmonto_letra;
    private javax.swing.JTextField txtpersona;
    // End of variables declaration//GEN-END:variables
}
