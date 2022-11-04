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
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import FORMULARIO.VISTA.BUSCAR.JDiaBuscar;
import java.awt.event.KeyEvent;
import java.sql.Connection;

/**
 *
 * @author Digno
 */
public class FrmGasto extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenConexion eveconn = new EvenConexion();
    private gasto ENTgt = new gasto();
    private DAO_gasto DAOgt = new DAO_gasto();
    private BO_gasto BOgt = new BO_gasto();
    private gasto_tipo ENTgti = new gasto_tipo();
    private DAO_gasto_tipo DAOgti = new DAO_gasto_tipo();
    private caja_cierre_detalle ENTccd = new caja_cierre_detalle();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenNumero_a_Letra nroLetra = new EvenNumero_a_Letra();
    private EvenFecha evefec = new EvenFecha();
    private EvenCombobox evecmb = new EvenCombobox();
    Connection conn = ConnPostgres.getConnPosgres();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    public usuario ENTusu = new usuario();
    private EvenEstado eveest = new EvenEstado();
    private String nombreTabla_pri = "GASTO";
    private String nombreTabla_sec = "CAJA GASTO";
    private String creado_por = "digno";
    private int fk_idusuario = 0;
    private static int fk_idgasto_tipo;
    private int idgasto;
    private double Dmonto_gasto;
    String usu_id = "idusuario";
    String usu_nombre = "nombre";
    String usu_tabla = "usuario";
    String usu_where = "where activo=true ";

    private void abrir_formulario() {
        cargar_usuario_acceso();
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        DAOgt.actualizar_tabla_gasto(conn, tbltabla_pri, "");
        evefec.cargar_combobox_intervalo_fecha(cmbfecha_caja_cierre);
        cargar_usuario();
    }

    private void cargar_usuario_acceso() {
        if (fk_idusuario != ENTusu.getGlobal_idusuario()) {
            usuario usu = new usuario();
            creado_por = usu.getGlobal_nombre();
            fk_idusuario = usu.getGlobal_idusuario();
        }
    }

    private void cargar_usuario() {
        evecmb.cargarCombobox(conn, cmbusuario, usu_id, usu_nombre, usu_tabla, usu_where);
    }

    public static int getFk_idgasto_tipo() {
        return fk_idgasto_tipo;
    }

    public static void setFk_idgasto_tipo(int fk_idgasto_tipo) {
        FrmGasto.fk_idgasto_tipo = fk_idgasto_tipo;
    }

    private void titulo_formulario(String fecha_creado, String creado_por) {
        this.setTitle(nombreTabla_pri + " / fecha creado: " + fecha_creado + " / Creado Por: " + creado_por);
    }

    private boolean validar_guardar() {
        if (getFk_idgasto_tipo() == 0) {
            txtgasto_tipo.setText(null);
            if (evejtf.getBoo_JTextField_vacio(txtgasto_tipo, "DEBE CARGO CORRECTAMENTE UN TIPO")) {
                return false;
            }
        }
        if (evejtf.getBoo_JTextField_vacio(txtmonto_gasto, "DEBE CARGAR UN MONTO")) {
            return false;
        }
        if (evejtf.getBoo_JTextarea_vacio(txtadescripcion, "DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        return true;
    }

    private void cargar_dato_gasto() {
        String monto_gasto = evejtf.getString_format_nro_entero1(txtmonto_gasto);
        String monto_letra = nroLetra.Convertir(monto_gasto, true);
        Dmonto_gasto = Double.parseDouble(monto_gasto);
        txtmonto_letra.setText(monto_letra);
        ENTgt.setC3creado_por(creado_por);
        ENTgt.setC4monto_gasto(Dmonto_gasto);
        ENTgt.setC5monto_letra(monto_letra);
        ENTgt.setC6descripcion(txtadescripcion.getText());
        ENTgt.setC7estado(eveest.getEst_Emitido());
        ENTgt.setC8fk_idgasto_tipo(fk_idgasto_tipo);
        ENTgt.setC9fk_idusuario(fk_idusuario);
    }

    private void cargar_dato_caja_detalle_GASTO() {
        String descripcion = idgasto + "-(" + eveest.getCaja_GASTO() + ")-TIPO: " + txtgasto_tipo.getText() + ",D:(" + txtadescripcion.getText() + ")";
        ENTccd.setC3creado_por(creado_por);
        ENTccd.setC4cerrado_por(eveest.getCaja_GASTO());
        ENTccd.setC5es_cerrado(false);
        ENTccd.setC6monto_apertura_caja(0);
        ENTccd.setC7monto_cierre_caja(0);
        ENTccd.setC8monto_ocupa_minimo(0);
        ENTccd.setC9monto_ocupa_adicional(0);
        ENTccd.setC10monto_ocupa_consumo(0);
        ENTccd.setC11monto_ocupa_descuento(0);
        ENTccd.setC12monto_ocupa_adelanto(0);
        ENTccd.setC13monto_gasto(Dmonto_gasto);
        ENTccd.setC14monto_compra(0);
        ENTccd.setC15monto_vale(0);
        ENTccd.setC16monto_liquidacion(0);
        ENTccd.setC17estado(eveest.getEst_Emitido());
        ENTccd.setC18descripcion(descripcion);
        ENTccd.setC19fk_idgasto(idgasto);
        ENTccd.setC20fk_idcompra(0);
        ENTccd.setC21fk_idventa(0);
        ENTccd.setC22fk_idusuario(fk_idusuario);
        ENTccd.setC23fk_idrh_vale(0);
        ENTccd.setC24fk_idrh_liquidacion(0);
        ENTccd.setC25monto_solo_adelanto(0);
    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato_gasto();
            cargar_dato_caja_detalle_GASTO();
            BOgt.insertar_gasto(ENTgt, ENTccd);
            reestableser();
        }
    }

    private void boton_anular() {
        if (tbltabla_pri.getSelectedRow() >= 0) {
            int fk_idgasto = eveJtab.getInt_select_id(tbltabla_pri);
            int idcaja_cierre_detalle = DAOccd.getInt_idcaja_cierre_detalle_por_otro_id(conn, "fk_idgasto", fk_idgasto);
            DAOccd.cargar_caja_cierre_detalle(conn, ENTccd, idcaja_cierre_detalle);
            ENTgt.setC1idgasto(fk_idgasto);
            ENTgt.setC7estado(eveest.getEst_Anulado());
            ENTgt.setC6descripcion(ENTgt.getC6descripcion() + " :" + eveest.getEst_Anulado());
            ENTccd.setC1idcaja_cierre_detalle(idcaja_cierre_detalle);
            ENTccd.setC17estado(eveest.getEst_Anulado());
            ENTccd.setC13monto_gasto(0);
            BOgt.update_gasto(ENTgt, ENTccd);
            reestableser();
        }
    }

    private void seleccionar_tabla() {
        cargar_usuario_acceso();
        int idgasto = eveJtab.getInt_select_id(tbltabla_pri);
        DAOgt.cargar_gasto(conn, ENTgt, idgasto);
        txtid.setText(String.valueOf(ENTgt.getC1idgasto()));
        fk_idgasto_tipo = ENTgt.getC8fk_idgasto_tipo();
        DAOgti.cargar_gasto_tipo(conn, ENTgti, fk_idgasto_tipo);
        txtgasto_tipo.setText(ENTgti.getC4nombre());
        txtmonto_gasto.setText(String.valueOf((int) (ENTgt.getC4monto_gasto())));
        evejtf.getString_format_nro_entero1(txtmonto_gasto);
        txtmonto_letra.setText(ENTgt.getC5monto_letra());
        txtadescripcion.setText(ENTgt.getC6descripcion());
        titulo_formulario(ENTgt.getC2fecha_creado(), ENTgt.getC3creado_por());
        btnguardar.setEnabled(false);
        if ((ENTgt.getC7estado().equals(eveest.getEst_Terminar())) || (ENTgt.getC7estado().equals(eveest.getEst_Anulado()))) {
            btnanular.setEnabled(false);
        }
        if ((ENTgt.getC7estado().equals(eveest.getEst_Emitido()))) {
            btnanular.setEnabled(true);
        }
    }

    private void reestableser() {
        cargar_usuario_acceso();
        this.setTitle(nombreTabla_pri + " USUARIO:" + creado_por);
//        DAOgt.actualizar_tabla_gasto(conn, tbltabla_pri,"");
        actualizar_tabla_caja_cierre();
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        setFk_idgasto_tipo(0);
        idgasto = (eveconn.getInt_ultimoID_mas_uno(conn, ENTgt.getTb_gasto(), ENTgt.getId_idgasto()));
        txtid.setText(String.valueOf(idgasto));
        txtgasto_tipo.setText(null);
        txtmonto_gasto.setText(null);
        txtmonto_letra.setText(null);
        txtadescripcion.setText(null);
        btnguardar.setEnabled(true);
        btnanular.setEnabled(false);
        txtgasto_tipo.grabFocus();
    }

    private void actualizar_tabla_caja_cierre() {
        int idusuario = evecmb.getInt_seleccionar_COMBOBOX(conn, cmbusuario, usu_id, usu_nombre, usu_tabla);
        String filtro = evefec.getIntervalo_fecha_combobox(cmbfecha_caja_cierre, "g.fecha_creado");
        if (idusuario > 0) {
            filtro = filtro + " and g.fk_idusuario=" + idusuario;
        }
        DAOgt.actualizar_tabla_gasto(conn, tbltabla_pri, filtro);
        double suma_gasto = eveJtab.getDouble_sumar_tabla(tbltabla_pri, 7);
        jFtotal_cc_gasto.setValue(suma_gasto);
    }

    private void buscar_tipo_gasto() {
        vbus.setNombre_tabla("GASTO TIPO");
        vbus.setTipo_tabla(7);
        vbus.setPre_busqueda(txtgasto_tipo.getText());
        JDiaBuscar frm = new JDiaBuscar(null, true);
        frm.setVisible(true);
    }

    private void boton_nuevo() {
        reestableser();
    }

    public FrmGasto() {
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

        jTab_principal = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panel_insertar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtgasto_tipo = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btnanular = new javax.swing.JButton();
        btnnuevo_gtipo = new javax.swing.JButton();
        btnbuscar_gtipo = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtadescripcion = new javax.swing.JTextArea();
        txtmonto_gasto = new javax.swing.JTextField();
        txtmonto_letra = new javax.swing.JTextField();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla_pri = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbfecha_caja_cierre = new javax.swing.JComboBox<>();
        cmbusuario = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jFtotal_cc_gasto = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("ID:");

        txtid.setEditable(false);
        txtid.setBackground(new java.awt.Color(204, 204, 204));
        txtid.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtgasto_tipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtgasto_tipo.setBorder(javax.swing.BorderFactory.createTitledBorder("GASTO TIPO:"));
        txtgasto_tipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtgasto_tipoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtgasto_tipoKeyReleased(evt);
            }
        });

        btnnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/nuevo.png"))); // NOI18N
        btnnuevo.setText("NUEVO");
        btnnuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnnuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevoActionPerformed(evt);
            }
        });

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnguardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btnanular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btnanular.setText("ANULAR");
        btnanular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnanular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnanular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanularActionPerformed(evt);
            }
        });

        btnnuevo_gtipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_gtipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_gtipoActionPerformed(evt);
            }
        });

        btnbuscar_gtipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_gtipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_gtipoActionPerformed(evt);
            }
        });

        txtadescripcion.setColumns(20);
        txtadescripcion.setRows(5);
        txtadescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));
        jScrollPane3.setViewportView(txtadescripcion);

        txtmonto_gasto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtmonto_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtmonto_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO GASTO"));
        txtmonto_gasto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmonto_gastoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmonto_gastoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtmonto_gastoKeyTyped(evt);
            }
        });

        txtmonto_letra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtmonto_letra.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO LETRA:"));

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(txtgasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_gtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscar_gtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_insertarLayout.createSequentialGroup()
                                .addComponent(btnnuevo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnguardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnanular))
                            .addComponent(txtmonto_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtmonto_letra))
                .addContainerGap())
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo_gtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtgasto_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_gtipo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btnanular))
                .addContainerGap())
        );

        panel_tabla.setBackground(new java.awt.Color(51, 204, 255));
        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tbltabla_pri.setModel(new javax.swing.table.DefaultTableModel(
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
        tbltabla_pri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbltabla_priMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbltabla_pri);

        jLabel3.setText("BUSCAR NOMBRE:");

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        jLabel2.setText("Fecha:");

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

        jLabel4.setText("Usuario:");

        jFtotal_cc_gasto.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL GASTO"));
        jFtotal_cc_gasto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_cc_gasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_cc_gasto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.Alignment.LEADING, 0, 159, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFtotal_cc_gasto, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tablaLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_tablaLayout.createSequentialGroup()
                        .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jFtotal_cc_gasto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTab_principal.addTab("PRINCIPAL", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1271, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );

        jTab_principal.addTab("SECUNDARIO", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        boton_guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOgt.ancho_tabla_gasto(tbltabla_pri);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbltabla_priMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_priMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tbltabla_priMouseReleased

    private void btnanularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanularActionPerformed
        // TODO add your handling code here:
        boton_anular();
    }//GEN-LAST:event_btnanularActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtgasto_tipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtgasto_tipoKeyPressed
        // TODO add your handling code here:
//        evejtf.saltar_campo_enter(evt, txtnombre, txtprecio_venta);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_tipo_gasto();
        }
    }//GEN-LAST:event_txtgasto_tipoKeyPressed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:
//        DAOgt.actualizar_tabla_gasto_buscar(conn, tbltabla_pri, txtbuscar);
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtgasto_tipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtgasto_tipoKeyReleased
        // TODO add your handling code here:
        txtgasto_tipo.setText(txtgasto_tipo.getText().toUpperCase());
    }//GEN-LAST:event_txtgasto_tipoKeyReleased

    private void txtmonto_gastoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_gastoKeyReleased
        // TODO add your handling code here:
        String monto = evejtf.getString_format_nro_entero1(txtmonto_gasto);
        String monto_letra = nroLetra.Convertir(monto, true);
        txtmonto_letra.setText(monto_letra);
    }//GEN-LAST:event_txtmonto_gastoKeyReleased

    private void txtmonto_gastoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_gastoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtmonto_gastoKeyTyped

    private void btnbuscar_gtipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_gtipoActionPerformed
        // TODO add your handling code here:
        buscar_tipo_gasto();
    }//GEN-LAST:event_btnbuscar_gtipoActionPerformed

    private void btnnuevo_gtipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_gtipoActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmGasto_Tipo());
    }//GEN-LAST:event_btnnuevo_gtipoActionPerformed

    private void cmbfecha_caja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfecha_caja_cierreActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_cmbfecha_caja_cierreActionPerformed

    private void cmbusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbusuarioActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_caja_cierre();
    }//GEN-LAST:event_cmbusuarioActionPerformed

    private void txtmonto_gastoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmonto_gastoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtadescripcion.grabFocus();
        }
    }//GEN-LAST:event_txtmonto_gastoKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanular;
    private javax.swing.JButton btnbuscar_gtipo;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_gtipo;
    private javax.swing.JComboBox<String> cmbfecha_caja_cierre;
    private javax.swing.JComboBox<String> cmbusuario;
    private javax.swing.JFormattedTextField jFtotal_cc_gasto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTextArea txtadescripcion;
    private javax.swing.JTextField txtbuscar;
    public static javax.swing.JTextField txtgasto_tipo;
    private javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtmonto_gasto;
    private javax.swing.JTextField txtmonto_letra;
    // End of variables declaration//GEN-END:variables
}
