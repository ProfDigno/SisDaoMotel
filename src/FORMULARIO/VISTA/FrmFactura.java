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
import FORMULARIO.BO.BO_factura;
import FORMULARIO.DAO.DAO_factura;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_timbrado;
import FORMULARIO.ENTIDAD.factura;
import FORMULARIO.ENTIDAD.factura_item;
import FORMULARIO.ENTIDAD.persona;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.timbrado;
import FORMULARIO.ENTIDAD.usuario;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import IMPRESORA_POS.PosImprimir_Factura;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmFactura extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenConexion eveconn = new EvenConexion();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenNumero_a_Letra nroLetra = new EvenNumero_a_Letra();
    private EvenFecha evefec = new EvenFecha();
    private EvenCombobox evecmb = new EvenCombobox();
    Connection conn = ConnPostgres.getConnPosgres();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    public usuario ENTusu = new usuario();
    private EvenEstado eveest = new EvenEstado();
    private String nombreTabla_pri = "FACTURA";
    private String nombreTabla_sec = "FILTRO FACTURA";
    private String creado_por = "digno";
    private int fk_idusuario = 0;
    private factura ENTf = new factura();
    private DAO_factura DAOf = new DAO_factura();
    private BO_factura BOf = new BO_factura();
    private timbrado ENTt = new timbrado();
    private DAO_timbrado DAOt = new DAO_timbrado();
    private producto ENTp = new producto();
    private DAO_producto DAOp = new DAO_producto();
    private persona ENTper = new persona();
    private factura_item ENTfi = new factura_item();
    private DefaultTableModel model_itemf = new DefaultTableModel();
    private PosImprimir_Factura posfac=new PosImprimir_Factura();
    private double monto_total;
    private double monto_iva5;
    private double monto_iva10;
    private String monto_letra;
    private int fk_idtimbrado = 1;
    private int fk_idpersona;
    private int fk_idventa = 0;
    private String cod_establecimiento = "001";
    private String punto_expedicion = "001";
    private int fk_idfactura;

    private void abrir_formulario() {
        evetbl.centrar_formulario_internalframa(this);
        crear_item_producto();
        reestableser_factura();
    }

    private void reestableser_factura() {
        DAOf.actualizar_tabla_factura(conn, tblfactura, "");
        creado_por = ENTusu.getGlobal_nombre();
        evefec.setFechaDCSistema(jDfecha_nota);
        jRcond_contado.setSelected(true);
        txtcli_nombre.setText(null);
        txtcli_direccion.setText(null);
        txtcli_ruc.setText(null);
        txtcli_telefono.setText(null);
        eveJtab.limpiar_tabla_datos(model_itemf);
        txtfac_monto_total.setText(null);
        txtfac_ivaexenta.setText(null);
        txtfac_iva5.setText(null);
        txtfac_iva10.setText(null);
        txtfac_monto_letra.setText(null);
        int numero=DAOf.getInt_ult_nro_factura(conn);
        txtfac_numero.setText(String.valueOf(numero));

    }

    private void crear_item_producto() {
        String dato[] = {"ID", "DESCRIPCION", "IVA", "PRECIO", "CANT", "SUBTOTAL",
            "Oprecio", "OpIvaExe", "OpIva5", "OpIva10", "OtIvaExe", "OtIva5", "OtIva10", "Osubtotal"};
        eveJtab.crear_tabla_datos(tblitem_producto, model_itemf, dato);
        ancho_item_producto();
    }
    private boolean getBoovalidarItem(){
        if(ENTp.getIdproducto_global() == 0){
            JOptionPane.showMessageDialog(btnnuevo_cliente, "NO SE ENCONTRO NINGUN PRODUCTO CARGADO\nBUSQUE UNO NUEVO",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            ENTp.setQuien_llama_global(1);
            JDiaBuscarProducto frm = new JDiaBuscarProducto(null, true);
            frm.setVisible(true);
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtitem_nombre,"DEBE CARGAR UNA DESCRIPCION")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtitem_precio,"DEBE CARGAR UN PRECIO")){
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtitem_cantidad,"DEBE CARGAR UNA CANTIDAD")){
            return false;
        }
        return true;
    }
    private void cargar_factura_item() {
        if (getBoovalidarItem()) {
            String OpIvaExe = "0";
            String OpIva5 = "0";
            String OpIva10 = "0";
            String OtIvaExe = "0";
            String OtIva5 = "0";
            String OtIva10 = "0";
            int Iidproducto = ENTp.getIdproducto_global();
            String id = String.valueOf(Iidproducto);
            String descripcion = txtitem_nombre.getText();
            int Iiva = ENTp.getIva_global();
            String iva = String.valueOf(Iiva);
            String precio_m = txtitem_precio.getText();
            double Iprecio = evejtf.getDouble_format_nro_entero(precio_m);
            String Oprecio = String.valueOf((int) Iprecio);
            String cant = txtitem_cantidad.getText();
            String subtotal = "0";
            String Osubtotal = "0";
            if (Iiva == 0) {
                OpIvaExe = Oprecio;
            }
            if (Iiva == 5) {
                OpIva5 = Oprecio;
            }
            if (Iiva == 10) {
                OpIva10 = Oprecio;
            }
            String dato[] = {id, descripcion, iva, precio_m, cant, subtotal, Oprecio, OpIvaExe, OpIva5, OpIva10, OtIvaExe, OtIva5, OtIva10, Osubtotal};
            eveJtab.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            sumar_item_factura();
            reestableser_item_factura();
        }
    }

    private void ancho_item_producto() {
        int Ancho[] = {5, 50, 5, 15, 5, 15, 1, 1, 1, 1, 1, 1, 1, 1};
        eveJtab.setAnchoColumnaJtable(tblitem_producto, Ancho);
        eveJtab.ocultar_columna(tblitem_producto, 6);
        eveJtab.ocultar_columna(tblitem_producto, 7);
        eveJtab.ocultar_columna(tblitem_producto, 8);
        eveJtab.ocultar_columna(tblitem_producto, 9);
        eveJtab.ocultar_columna(tblitem_producto, 10);
        eveJtab.ocultar_columna(tblitem_producto, 11);
        eveJtab.ocultar_columna(tblitem_producto, 12);
        eveJtab.ocultar_columna(tblitem_producto, 13);
        eveJtab.alinear_derecha_columna(tblitem_producto, 3);
        eveJtab.alinear_derecha_columna(tblitem_producto, 4);
        eveJtab.alinear_derecha_columna(tblitem_producto, 5);
    }

    private void titulo_formulario(String fecha_creado, String creado_por) {
        this.setTitle(nombreTabla_pri + " / fecha creado: " + fecha_creado + " / Creado Por: " + creado_por);
    }

    private void cargar_timbrado() {
        DAOt.cargar_timbrado(conn, ENTt, fk_idtimbrado);
        txttimbrado.setText(String.valueOf(ENTt.getC5numero_timbrado()));
        txtfecha_vigente.setText(ENTt.getC7fec_fin_vigente());
        cod_establecimiento = ENTt.getC8cod_establecimiento();
        punto_expedicion = ENTt.getC9punto_expedicion();
    }

    private void sumar_item_factura() {
        eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 4, 6, 5, true);
        eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 4, 6, 13, false);//monto_total
        eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 4, 7, 10, false);//monto_exenta
        eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 4, 8, 11, false);//monto_iva5
        eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 4, 9, 12, false);//monto_iva10
        double sum_monto_exenta = eveJtab.getDouble_sumar_tabla(tblitem_producto, 10);
        double sum_monto_iva5 = eveJtab.getDouble_sumar_tabla(tblitem_producto, 11);
        double sum_monto_iva10 = eveJtab.getDouble_sumar_tabla(tblitem_producto, 12);
        double sum_monto_total = eveJtab.getDouble_sumar_tabla(tblitem_producto, 13);
        monto_total = sum_monto_total;
        monto_iva5 = sum_monto_iva5 / 21;
        monto_iva10 = sum_monto_iva10 / 11;
        txtfac_ivaexenta.setText(evejtf.getString_format_nro_decimal(sum_monto_exenta));
        txtfac_iva5.setText(evejtf.getString_format_nro_decimal(monto_iva5));
        txtfac_iva10.setText(evejtf.getString_format_nro_decimal(monto_iva10));
        txtfac_monto_total.setText(evejtf.getString_format_nro_decimal(monto_total));
        int Imonto = (int) sum_monto_total;
        String Smonto = String.valueOf(Imonto);
        monto_letra = nroLetra.Convertir(Smonto, true);
        txtfac_monto_letra.setText(monto_letra);
    }

    private void reestableser_item_factura() {
        ENTp.setIdproducto_global(0);
        txtitem_nombre.setText(null);
        txtitem_cantidad.setText(null);
        txtitem_precio.setText(null);
        txtitem_subtotal.setText(null);
    }

    private void cargar_factura() {
        fk_idpersona = ENTper.getIdpersona_global();
        fk_idfactura = (eveconn.getInt_ultimoID_mas_uno(conn, ENTf.getTb_factura(), ENTf.getId_idfactura()));
        String fecha_nota = evefec.getfechaDCStringFormat(jDfecha_nota, "yyyy-MM-dd");
        String fac_numero = txtfac_numero.getText();
        String nro_factura = String.format("%7s", fac_numero).replace(' ', '0');
        String nro_factura_completo = cod_establecimiento + "-" + punto_expedicion + "-" + nro_factura;
        String condicion = "";
        if (jRcond_contado.isSelected()) {
            condicion = eveest.getCond_Contado();
        }
        if (jRcond_credito.isSelected()) {
            condicion = eveest.getCond_Credito();
        }
        ENTf.setC3creado_por(creado_por);
        ENTf.setC4nro_factura(nro_factura_completo);
        ENTf.setC5fecha_nota(fecha_nota);
        ENTf.setC6estado(eveest.getEst_Emitido());
        ENTf.setC7condicion(condicion);
        ENTf.setC8monto_total(monto_total);
        ENTf.setC9monto_iva5(monto_iva5);
        ENTf.setC10monto_iva10(monto_iva10);
        ENTf.setC11monto_letra(monto_letra);
        ENTf.setC12fk_idtimbrado(fk_idtimbrado);
        ENTf.setC13fk_idpersona(fk_idpersona);
        ENTf.setC14fk_idventa(fk_idventa);
        ENTf.setC15numero(Integer.parseInt(fac_numero));
    }

    private boolean getBoovalidar_factura() {
        fk_idpersona = ENTper.getIdpersona_global();
        if (fk_idpersona == 0) {
            JOptionPane.showMessageDialog(btnnuevo_cliente, "NO SE ENCONTRO NINGUN CLIENTE CARGADO\nBUSQUE UNO NUEVO",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            ENTper.setBus_quien_llama(3);
            JDiaBuscarPersona frm = new JDiaBuscarPersona(null, true);
            frm.setVisible(true);
            return false;
        }
        if (tblitem_producto.getRowCount() == 0) {
            JOptionPane.showMessageDialog(btnbuscar_producto, "NO SE ENCONTRO NINGUN PRODUCTO CARGADO\nBUSQUE UNO NUEVO",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            ENTp.setQuien_llama_global(1);
            JDiaBuscarProducto frm = new JDiaBuscarProducto(null, true);
            frm.setVisible(true);
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtfac_numero,"DEBE CARGAR UN NUMERO DE FACTURA")){
            return false;
        }
        return true;
    }

    private void boton_guardar_factura() {
        if (getBoovalidar_factura()) {
            cargar_factura();
            BOf.insertar_factura_de_factura(ENTf, ENTfi, tblitem_producto);
//            DAOf.imprimir_nota_factura(conn, fk_idfactura);
            posfac.boton_imprimir_pos_FACTURA(conn, fk_idfactura);
            reestableser_factura();
        }
    }

    private void imprimir_nota_factura() {
        if (eveJtab.getBoolean_validar_select_mensaje(tblfactura, "SELECCIONE UNA FACTURA PARA IMPRIMIR")) {
            int idfactura = eveJtab.getInt_select_id(tblfactura);
//            DAOf.imprimir_nota_factura(conn, idfactura);
            posfac.boton_imprimir_pos_FACTURA(conn, idfactura);
        }
    }

    public FrmFactura() {
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

        gru_cond = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtcli_nombre = new javax.swing.JTextField();
        txtcli_direccion = new javax.swing.JTextField();
        txtcli_telefono = new javax.swing.JTextField();
        txtcli_ruc = new javax.swing.JTextField();
        btnnuevo_cliente = new javax.swing.JButton();
        btnbuscar_cliente = new javax.swing.JButton();
        txtitem_nombre = new javax.swing.JTextField();
        txtitem_cantidad = new javax.swing.JTextField();
        txtitem_precio = new javax.swing.JTextField();
        txtitem_subtotal = new javax.swing.JTextField();
        btnbuscar_producto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtfac_monto_letra = new javax.swing.JTextField();
        txtfac_ivaexenta = new javax.swing.JTextField();
        txtfac_iva5 = new javax.swing.JTextField();
        txtfac_iva10 = new javax.swing.JTextField();
        txtfac_monto_total = new javax.swing.JTextField();
        btnguardar_factura = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jDfecha_nota = new com.toedter.calendar.JDateChooser();
        txtfac_numero = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jRcond_contado = new javax.swing.JRadioButton();
        jRcond_credito = new javax.swing.JRadioButton();
        lblnro_factura = new javax.swing.JLabel();
        txttimbrado = new javax.swing.JTextField();
        txtfecha_vigente = new javax.swing.JTextField();
        btneliminar_item = new javax.swing.JButton();
        btncargar_producto = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblfactura = new javax.swing.JTable();
        btnimprimir_factura = new javax.swing.JButton();

        setClosable(true);
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

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("CLIENTE:"));

        txtcli_nombre.setEditable(false);
        txtcli_nombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE:"));

        txtcli_direccion.setEditable(false);
        txtcli_direccion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_direccion.setBorder(javax.swing.BorderFactory.createTitledBorder("DIRECCION:"));

        txtcli_telefono.setEditable(false);
        txtcli_telefono.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_telefono.setBorder(javax.swing.BorderFactory.createTitledBorder("TELEFONO:"));

        txtcli_ruc.setEditable(false);
        txtcli_ruc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtcli_ruc.setBorder(javax.swing.BorderFactory.createTitledBorder("RUC:"));

        btnnuevo_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N

        btnbuscar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_clienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcli_nombre)
                    .addComponent(txtcli_direccion, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtcli_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtcli_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcli_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_cliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtcli_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcli_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_cliente)))
        );

        txtitem_nombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtitem_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE:"));

        txtitem_cantidad.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtitem_cantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtitem_cantidad.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT:"));
        txtitem_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtitem_cantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtitem_cantidadKeyTyped(evt);
            }
        });

        txtitem_precio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtitem_precio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtitem_precio.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO:"));
        txtitem_precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtitem_precioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtitem_precioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtitem_precioKeyTyped(evt);
            }
        });

        txtitem_subtotal.setEditable(false);
        txtitem_subtotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtitem_subtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtitem_subtotal.setBorder(javax.swing.BorderFactory.createTitledBorder("SUBTOTAL:"));

        btnbuscar_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_productoActionPerformed(evt);
            }
        });

        tblitem_producto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblitem_producto);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL :"));

        txtfac_monto_letra.setEditable(false);
        txtfac_monto_letra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtfac_monto_letra.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO LETRA:"));

        txtfac_ivaexenta.setEditable(false);
        txtfac_ivaexenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtfac_ivaexenta.setBorder(javax.swing.BorderFactory.createTitledBorder("IVA EXENTA:"));

        txtfac_iva5.setEditable(false);
        txtfac_iva5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfac_iva5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtfac_iva5.setBorder(javax.swing.BorderFactory.createTitledBorder("IVA 5%:"));

        txtfac_iva10.setEditable(false);
        txtfac_iva10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfac_iva10.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtfac_iva10.setBorder(javax.swing.BorderFactory.createTitledBorder("IVA 10%:"));

        txtfac_monto_total.setEditable(false);
        txtfac_monto_total.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfac_monto_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtfac_monto_total.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL GRAL:"));

        btnguardar_factura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        btnguardar_factura.setText("GUARDAR");
        btnguardar_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardar_facturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfac_monto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtfac_ivaexenta, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfac_iva5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfac_iva10, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtfac_monto_total)
                    .addComponent(btnguardar_factura, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfac_monto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfac_monto_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtfac_ivaexenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtfac_iva5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtfac_iva10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnguardar_factura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("TIMBRADO:"));

        jDfecha_nota.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA NOTA:"));
        jDfecha_nota.setDateFormatString("yyyy-MM-dd");
        jDfecha_nota.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtfac_numero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfac_numero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfac_numero.setBorder(javax.swing.BorderFactory.createTitledBorder("NUMERO FACTURA:"));
        txtfac_numero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfac_numeroKeyTyped(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("CONDICION:"));

        gru_cond.add(jRcond_contado);
        jRcond_contado.setSelected(true);
        jRcond_contado.setText("CONTADO");

        gru_cond.add(jRcond_credito);
        jRcond_credito.setText("CREDITO");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jRcond_contado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRcond_credito)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jRcond_contado)
                .addComponent(jRcond_credito))
        );

        lblnro_factura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblnro_factura.setText("jLabel1");

        txttimbrado.setEditable(false);
        txttimbrado.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txttimbrado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttimbrado.setBorder(javax.swing.BorderFactory.createTitledBorder("TIMBRADO:"));

        txtfecha_vigente.setEditable(false);
        txtfecha_vigente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtfecha_vigente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfecha_vigente.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA VIGENTE:"));

        btneliminar_item.setText("EMILINAR ITEM");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtfecha_vigente, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDfecha_nota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtfac_numero, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblnro_factura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txttimbrado)
                    .addComponent(btneliminar_item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jDfecha_nota, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblnro_factura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfac_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfecha_vigente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminar_item)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btncargar_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_flecha_abajo.png"))); // NOI18N
        btncargar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargar_productoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtitem_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtitem_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtitem_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtitem_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncargar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtitem_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtitem_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtitem_precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtitem_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btncargar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("CREAR FACTURA", jPanel1);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA FACTURA"));

        tblfactura.setModel(new javax.swing.table.DefaultTableModel(
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
        tblfactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblfacturaMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblfactura);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 943, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );

        btnimprimir_factura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        btnimprimir_factura.setText("IMPRIMIR FACTURA");
        btnimprimir_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_facturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnimprimir_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_factura)
                .addContainerGap())
        );

        jTabbedPane1.addTab("FILTRO FACTURA", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnbuscar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_clienteActionPerformed
        // TODO add your handling code here:
//        vbus.setTipo_tabla_cliente(1);
        ENTper.setBus_quien_llama(3);
        JDiaBuscarPersona frm = new JDiaBuscarPersona(null, true);
        frm.setVisible(true);
    }//GEN-LAST:event_btnbuscar_clienteActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_item_producto();
        DAOf.ancho_tabla_factura(tblfactura);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnbuscar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_productoActionPerformed
        // TODO add your handling code here:
        ENTp.setQuien_llama_global(1);
        JDiaBuscarProducto frm = new JDiaBuscarProducto(null, true);
        frm.setVisible(true);
    }//GEN-LAST:event_btnbuscar_productoActionPerformed

    private void txtitem_precioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtitem_precioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_factura_item();
        }
    }//GEN-LAST:event_txtitem_precioKeyPressed

    private void txtitem_cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtitem_cantidadKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_factura_item();
        }
    }//GEN-LAST:event_txtitem_cantidadKeyPressed

    private void btnguardar_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardar_facturaActionPerformed
        // TODO add your handling code here:
        boton_guardar_factura();
    }//GEN-LAST:event_btnguardar_facturaActionPerformed

    private void tblfacturaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblfacturaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblfacturaMouseReleased

    private void btnimprimir_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_facturaActionPerformed
        // TODO add your handling code here:
        imprimir_nota_factura();
    }//GEN-LAST:event_btnimprimir_facturaActionPerformed

    private void btncargar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargar_productoActionPerformed
        // TODO add your handling code here:
        cargar_factura_item();
    }//GEN-LAST:event_btncargar_productoActionPerformed

    private void txtitem_precioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtitem_precioKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtitem_precioKeyTyped

    private void txtitem_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtitem_cantidadKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtitem_cantidadKeyTyped

    private void txtitem_precioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtitem_precioKeyReleased
        // TODO add your handling code here:
        evejtf.getDouble_format_nro_entero(txtitem_precio);
    }//GEN-LAST:event_txtitem_precioKeyReleased

    private void txtfac_numeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfac_numeroKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtfac_numeroKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_cliente;
    private javax.swing.JButton btnbuscar_producto;
    private javax.swing.JButton btncargar_producto;
    private javax.swing.JButton btneliminar_item;
    private javax.swing.JButton btnguardar_factura;
    private javax.swing.JButton btnimprimir_factura;
    private javax.swing.JButton btnnuevo_cliente;
    private javax.swing.ButtonGroup gru_cond;
    private com.toedter.calendar.JDateChooser jDfecha_nota;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRcond_contado;
    private javax.swing.JRadioButton jRcond_credito;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblnro_factura;
    private javax.swing.JTable tblfactura;
    private javax.swing.JTable tblitem_producto;
    public static javax.swing.JTextField txtcli_direccion;
    public static javax.swing.JTextField txtcli_nombre;
    public static javax.swing.JTextField txtcli_ruc;
    public static javax.swing.JTextField txtcli_telefono;
    private javax.swing.JTextField txtfac_iva10;
    private javax.swing.JTextField txtfac_iva5;
    private javax.swing.JTextField txtfac_ivaexenta;
    private javax.swing.JTextField txtfac_monto_letra;
    private javax.swing.JTextField txtfac_monto_total;
    private javax.swing.JTextField txtfac_numero;
    private javax.swing.JTextField txtfecha_vigente;
    public static javax.swing.JTextField txtitem_cantidad;
    public static javax.swing.JTextField txtitem_nombre;
    public static javax.swing.JTextField txtitem_precio;
    public static javax.swing.JTextField txtitem_subtotal;
    private javax.swing.JTextField txttimbrado;
    // End of variables declaration//GEN-END:variables
}
