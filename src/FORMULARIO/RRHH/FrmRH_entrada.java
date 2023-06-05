/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.RRHH;

import BASEDATO.EvenConexion;
import FORMULARIO.VISTA.*;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.BO_gasto_tipo;
import FORMULARIO.BO.BO_rh_entrada;
import FORMULARIO.DAO.DAO_gasto_tipo;
import FORMULARIO.DAO.DAO_persona;
import FORMULARIO.DAO.DAO_rh_entrada;
import FORMULARIO.DAO.DAO_rh_liquidacion;
import FORMULARIO.DAO.DAO_rh_liquidacion_entrada;
import FORMULARIO.ENTIDAD.gasto_tipo;
import FORMULARIO.ENTIDAD.persona;
import FORMULARIO.ENTIDAD.rh_entrada;
import FORMULARIO.ENTIDAD.rh_liquidacion_entrada;
import FORMULARIO.ENTIDAD.usuario;
import java.awt.Color;
import java.awt.event.KeyEvent;
//import FORMULARIO.BO.*;
//import FORMULARIO.DAO.*;
//import FORMULARIO.ENTIDAD.*;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmRH_entrada extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private rh_entrada ENTrhe = new rh_entrada();
    private DAO_rh_entrada DAOrhe = new DAO_rh_entrada();
    private BO_rh_entrada BOrhe = new BO_rh_entrada();
    private persona ENTper = new persona();
    private DAO_persona DAOper = new DAO_persona();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenCombobox evecmb = new EvenCombobox();
    private DAO_rh_liquidacion DAOrhli = new DAO_rh_liquidacion();
    private DAO_rh_liquidacion_entrada DAOrhle = new DAO_rh_liquidacion_entrada();
    private rh_liquidacion_entrada ENTrhle = new rh_liquidacion_entrada();
    Connection conn = ConnPostgres.getConnPosgres();
    EvenConexion eveconn = new EvenConexion();
    private String nombreTabla = "RH ENTRADA";
    private usuario ENTusu = new usuario();
    private String creado_por = "digno";
    private int fk_idusuario;
    private int fk_idpersona;
    private int fk_idrh_liquidacion;
    private int fk_idrh_entrada_abierto;

    private void abrir_formulario() {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        this.setTitle(nombreTabla);
        evetbl.centrar_formulario_internalframa(this);

        reestableser();
        cargar_turno();
    }

    private void cargar_turno() {
        String where = " where (current_timestamp>(date(current_timestamp)+ hora_inicio))\n"
                + " and (current_timestamp<(date(current_timestamp) + hora_salida)) ";
        evecmb.cargarCombobox(conn, cmbturno, "idrh_turno", "turno", "rh_turno", where);
        cmbturno.setSelectedIndex(1);
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtbuscar_nro_tarjeta, "DEBE CARGAR UN TARJETA")) {
            return false;
        }

        return true;
    }

    private void cargar_personal_tarjeta() {
        if (txtbuscar_nro_tarjeta.getText().trim().length() > 0) {
            String nro_tarjeta = txtbuscar_nro_tarjeta.getText();
            ENTper.setC12nro_tarjeta(nro_tarjeta);
            if (DAOper.getBoolean_buscar_persona_nro_tarjeta(conn, ENTper)) {
                txtpersona.setText(ENTper.getC4nombre());
                fk_idpersona = ENTper.getC1idpersona();
                fk_idrh_liquidacion = DAOrhli.getInt_idrh_liquidacion_rh_liquidacion_abierto(conn, fk_idpersona);
                if (DAOrhe.getBoo_cargar_rh_entrada_idpersonal_abierto(conn, ENTrhe, fk_idpersona)) {
                    txtult_entrada.setText(ENTrhe.getC4fecha_entrada());
                    fk_idrh_entrada_abierto = ENTrhe.getC1idrh_entrada();
                    btnentrada.setBackground(Color.white);
                    btnsalida.setBackground(Color.yellow);
                } else {
                    txtult_entrada.setText(null);
                    btnentrada.setBackground(Color.yellow);
                    btnsalida.setBackground(Color.white);
                }
            } else {
                txtbuscar_nro_tarjeta.setText(null);
                txtpersona.setText(null);
                txtbuscar_nro_tarjeta.grabFocus();
            }
        }
    }

    private void cargar_dato_entrada() {
        int fk_idrh_turno = evecmb.getInt_seleccionar_COMBOBOX(conn, cmbturno, "idrh_turno", "turno", "rh_turno");
        int fk_idrh_entrada = (eveconn.getInt_ultimoID_mas_uno(conn, ENTrhe.getTb_rh_entrada(), ENTrhe.getId_idrh_entrada()));
        ENTrhe.setC3creado_por(creado_por);
        ENTrhe.setC6turno(cmbturno.getSelectedItem().toString());
        ENTrhe.setC7es_cerrado(false);
        ENTrhe.setC8es_entrada(true);
        ENTrhe.setC9es_salida(false);
        ENTrhe.setC10fk_idpersona(fk_idpersona);
        ENTrhe.setC11fk_idusuario(fk_idusuario);
        ENTrhe.setC12fk_idrh_turno(fk_idrh_turno);
        //////
        ENTrhle.setC3creado_por(creado_por);
        ENTrhle.setC4fk_idrh_liquidacion(fk_idrh_liquidacion);
        ENTrhle.setC5fk_idrh_entrada(fk_idrh_entrada);
        BOrhe.insertar_rh_entrada(ENTrhe, ENTrhle);
    }

    private void cargar_dato_salida() {
        ENTrhe.setC8es_entrada(false);
        ENTrhe.setC9es_salida(true);
        ENTrhe.setC1idrh_entrada(fk_idrh_entrada_abierto);
        BOrhe.update_rh_entrada(ENTrhe);
    }

    private void boton_guardar_entrada() {
        if (validar_guardar()) {
            cargar_dato_entrada();
            reestableser();
        }
    }

    private void boton_guardar_salida() {
        if (validar_guardar()) {
            cargar_dato_salida();
            reestableser();
        }
    }

    private void reestableser() {
        this.setTitle(nombreTabla);
        txthora_actual.setText(evefec.getString_formato_hora_min_seg());
        txtbuscar_nro_tarjeta.setText(null);
        txtpersona.setText(null);
        txtult_entrada.setText(null);
        btnentrada.setBackground(Color.white);
        btnsalida.setBackground(Color.white);
        txtbuscar_nro_tarjeta.grabFocus();
    }

    private void boton_nuevo() {
        reestableser();
    }

    public FrmRH_entrada() {
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

        panel_insertar = new javax.swing.JPanel();
        txtbuscar_nro_tarjeta = new javax.swing.JTextField();
        txtpersona = new javax.swing.JTextField();
        txtcarga = new javax.swing.JTextField();
        txthora_actual = new javax.swing.JTextField();
        cmbturno = new javax.swing.JComboBox<>();
        btnentrada = new javax.swing.JButton();
        btnsalida = new javax.swing.JButton();
        txtult_entrada = new javax.swing.JTextField();

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

        txtbuscar_nro_tarjeta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtbuscar_nro_tarjeta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtbuscar_nro_tarjeta.setBorder(javax.swing.BorderFactory.createTitledBorder("NUMERO TARJETA"));
        txtbuscar_nro_tarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscar_nro_tarjetaKeyPressed(evt);
            }
        });

        txtpersona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtpersona.setBorder(javax.swing.BorderFactory.createTitledBorder("PERSONAL"));

        txtcarga.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcarga.setBorder(javax.swing.BorderFactory.createTitledBorder("CARGO"));

        txthora_actual.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txthora_actual.setForeground(new java.awt.Color(255, 0, 0));
        txthora_actual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txthora_actual.setBorder(javax.swing.BorderFactory.createTitledBorder("HORA ACTUAL:"));

        cmbturno.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cmbturno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbturno.setBorder(javax.swing.BorderFactory.createTitledBorder("TURNO"));

        btnentrada.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnentrada.setForeground(new java.awt.Color(0, 153, 0));
        btnentrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_flecha_abajo.png"))); // NOI18N
        btnentrada.setText("ENTRADA");
        btnentrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnentradaActionPerformed(evt);
            }
        });

        btnsalida.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnsalida.setForeground(new java.awt.Color(204, 0, 0));
        btnsalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_flecha_arriba.png"))); // NOI18N
        btnsalida.setText("SALIDA");
        btnsalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalidaActionPerformed(evt);
            }
        });

        txtult_entrada.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txtult_entrada.setForeground(new java.awt.Color(255, 0, 0));
        txtult_entrada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtult_entrada.setBorder(javax.swing.BorderFactory.createTitledBorder("ULTIMA ENTRADA"));

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnentrada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                            .addComponent(cmbturno, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnsalida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtult_entrada)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtbuscar_nro_tarjeta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(txtpersona)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcarga, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(txthora_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addComponent(txthora_actual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_nro_tarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtpersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbturno, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtult_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnentrada, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsalida, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
//        DAOtc.ancho_tabla_tercero_ciudad(tbltabla);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtbuscar_nro_tarjetaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_nro_tarjetaKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_personal_tarjeta();
        }
    }//GEN-LAST:event_txtbuscar_nro_tarjetaKeyPressed

    private void btnentradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnentradaActionPerformed
        // TODO add your handling code here:
        boton_guardar_entrada();
    }//GEN-LAST:event_btnentradaActionPerformed

    private void btnsalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalidaActionPerformed
        // TODO add your handling code here:
        boton_guardar_salida();
    }//GEN-LAST:event_btnsalidaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnentrada;
    private javax.swing.JButton btnsalida;
    private javax.swing.JComboBox<String> cmbturno;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JTextField txtbuscar_nro_tarjeta;
    private javax.swing.JTextField txtcarga;
    private javax.swing.JTextField txthora_actual;
    private javax.swing.JTextField txtpersona;
    private javax.swing.JTextField txtult_entrada;
    // End of variables declaration//GEN-END:variables
}
