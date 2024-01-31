/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.RRHH;

import BASEDATO.EvenConexion;
import FORMULARIO.VISTA.*;
import BASEDATO.LOCAL.ConnPostgres;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.BO_gasto_tipo;
import FORMULARIO.BO.BO_rh_entrada;
import FORMULARIO.BO.BO_rh_descuento;
import FORMULARIO.DAO.DAO_caja_cierre;
import FORMULARIO.DAO.DAO_gasto_tipo;
import FORMULARIO.DAO.DAO_persona;
import FORMULARIO.DAO.DAO_rh_entrada;
import FORMULARIO.DAO.DAO_rh_liquidacion;
import FORMULARIO.DAO.DAO_rh_liquidacion_entrada;
import FORMULARIO.DAO.DAO_rh_liquidacion_descuento;
import FORMULARIO.DAO.DAO_rh_descuento;
import FORMULARIO.ENTIDAD.gasto_tipo;
import FORMULARIO.ENTIDAD.persona;
import FORMULARIO.ENTIDAD.rh_entrada;
import FORMULARIO.ENTIDAD.rh_liquidacion_entrada;
import FORMULARIO.ENTIDAD.rh_liquidacion_descuento;
import FORMULARIO.ENTIDAD.rh_descuento;
import FORMULARIO.ENTIDAD.rh_liquidacion;
import FORMULARIO.ENTIDAD.rh_liquidacion_detalle;
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
public class FrmRH_descuento extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private rh_descuento ENTrhd = new rh_descuento();
    private DAO_rh_descuento DAOrhd = new DAO_rh_descuento();
    private BO_rh_descuento BOrhd = new BO_rh_descuento();
    private persona ENTper = new persona();
    private DAO_persona DAOper = new DAO_persona();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenEstado eveest = new EvenEstado();
    private rh_liquidacion ENTrhli = new rh_liquidacion();
    private DAO_rh_liquidacion DAOrhli = new DAO_rh_liquidacion();
    private DAO_rh_liquidacion_descuento DAOrhld = new DAO_rh_liquidacion_descuento();
    private rh_liquidacion_descuento ENTrhld = new rh_liquidacion_descuento();
    private rh_liquidacion_detalle ENTrhlde = new rh_liquidacion_detalle();
    private EvenNumero_a_Letra evenrolt = new EvenNumero_a_Letra();
    private DAO_caja_cierre DAOcc = new DAO_caja_cierre();
    Connection conn = ConnPostgres.getConnPosgres();
    EvenConexion eveconn = new EvenConexion();
    private String nombreTabla = "RH DESCUENTO";
    private usuario ENTusu = new usuario();
    private String creado_por = "digno";
    private int fk_idusuario;
    private int fk_idpersona;
    private int fk_idrh_liquidacion;
    private int fk_idrh_entrada_abierto;

    private void abrir_formulario() {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        fk_idpersona = ENTper.getIdpersona_global();
        this.setTitle(nombreTabla);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        cargar_personal();
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtdescripcion, "DEBE CARGAR UNA DESCRIPCION ")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto, "DEBE CARGAR UN MONTO ")) {
            return false;
        }
        return true;
    }

    private void cargar_personal() {
        DAOper.cargar_persona(conn, ENTper, fk_idpersona);
        fk_idrh_liquidacion = DAOrhli.getInt_idrh_liquidacion_rh_liquidacion_abierto(conn, fk_idpersona);
        txtpersona.setText(ENTper.getC4nombre());
    }

    private void cargar_dato_descuento() {
        int idrh_descuento = (eveconn.getInt_ultimoID_mas_uno(conn, ENTrhd.getTb_rh_descuento(), ENTrhd.getId_idrh_descuento()));
        double monto_descuento = evejtf.getDouble_format_nro_entero(txtmonto);
        int Imonto = (int) monto_descuento;
        String Smonto = String.valueOf(Imonto);
        String monto_letra = evenrolt.Convertir(Smonto, true);
        ENTrhd.setC3creado_por(creado_por);
        ENTrhd.setC4descripcion(txtdescripcion.getText());
        ENTrhd.setC5monto_descuento(monto_descuento);
        ENTrhd.setC6estado(eveest.getEst_Emitido());
        ENTrhd.setC7es_cerrado(false);
        ENTrhd.setC8monto_letra(monto_letra);
        ENTrhd.setC9fk_idpersona(fk_idpersona);

        ENTrhld.setC3creado_por(creado_por);
        ENTrhld.setC4fk_idrh_liquidacion(fk_idrh_liquidacion);
        ENTrhld.setC5fk_idrh_descuento(idrh_descuento);
        
        ENTrhlde.setC3creado_por(creado_por);
        ENTrhlde.setC4descripcion(txtdescripcion.getText());
        ENTrhlde.setC5monto_descuento(monto_descuento);
        ENTrhlde.setC6monto_vale(0);
        ENTrhlde.setC7tabla("DESCUENTO");
        ENTrhlde.setC8estado(eveest.getEst_Emitido());
        ENTrhlde.setC9fk_idrh_liquidacion(fk_idrh_liquidacion);
        ENTrhlde.setC10fk_idrh_descuento(idrh_descuento);
        ENTrhlde.setC11fk_idrh_vale(0);
        BOrhd.insertar_rh_descuento(ENTrhd, ENTrhld,ENTrhlde);
    }

    private void cargar_dato_liquidacion() {
        DAOrhli.actualizar_tabla_rh_liquidacion_descuento(conn, FrmRH_liquidacion.tbldescuento, fk_idrh_liquidacion);
        DAOrhli.cargar_rh_liquidacion(conn, ENTrhli, fk_idrh_liquidacion);
        FrmRH_liquidacion.jfmonto_vale.setValue(ENTrhli.getSum_vale());
        FrmRH_liquidacion.jfmonto_descuento.setValue(ENTrhli.getSum_descuento());
        double liquidacion = ENTrhli.getC11salario_base() - (ENTrhli.getSum_vale() + ENTrhli.getSum_descuento());
        FrmRH_liquidacion.jfmonto_liquidacion.setValue(liquidacion);
        int Imonto = (int) liquidacion;
        String Smonto = String.valueOf(Imonto);
        String monto_letra = evenrolt.Convertir(Smonto, true);
        FrmRH_liquidacion.txtmonto_letra.setText(monto_letra);
    }

    private void boton_guardar_descuento() {
        if (validar_guardar()) {
            cargar_dato_descuento();
            reestableser();
            cargar_dato_liquidacion();
            DAOcc.exportar_excel_liquidacion_vale_N3(conn);
            this.dispose();
        }
    }

    private void reestableser() {
        this.setTitle(nombreTabla);
        txtpersona.setText(null);
        txtdescripcion.setText("DESCUENTO");
    }

    public FrmRH_descuento() {
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
        txtpersona = new javax.swing.JTextField();
        txtcarga = new javax.swing.JTextField();
        txtdescripcion = new javax.swing.JTextField();
        txtmonto = new javax.swing.JTextField();
        txtmonto_letra = new javax.swing.JTextField();
        btnguardar = new javax.swing.JButton();

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

        txtpersona.setEditable(false);
        txtpersona.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtpersona.setBorder(javax.swing.BorderFactory.createTitledBorder("PERSONAL"));

        txtcarga.setEditable(false);
        txtcarga.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcarga.setBorder(javax.swing.BorderFactory.createTitledBorder("CARGO"));

        txtdescripcion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtdescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));

        txtmonto.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtmonto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO"));
        txtmonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmontoKeyReleased(evt);
            }
        });

        txtmonto_letra.setEditable(false);
        txtmonto_letra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtmonto_letra.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO LETRA"));

        btnguardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(txtpersona, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcarga))
                    .addComponent(txtdescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                    .addComponent(txtmonto_letra, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtmonto, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtpersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcarga, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
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

    private void txtmontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmontoKeyReleased
        // TODO add your handling code here:
//        evejtf.getDouble_format_nro_entero(txtmonto);
        double monto_descuento = evejtf.getDouble_format_nro_entero(txtmonto);
        int Imonto = (int) monto_descuento;
        String Smonto = String.valueOf(Imonto);
        String monto_letra = evenrolt.Convertir(Smonto, true);
        txtmonto_letra.setText(monto_letra);
    }//GEN-LAST:event_txtmontoKeyReleased

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar_descuento();
    }//GEN-LAST:event_btnguardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnguardar;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JTextField txtcarga;
    private javax.swing.JTextField txtdescripcion;
    private javax.swing.JTextField txtmonto;
    private javax.swing.JTextField txtmonto_letra;
    private javax.swing.JTextField txtpersona;
    // End of variables declaration//GEN-END:variables
}
