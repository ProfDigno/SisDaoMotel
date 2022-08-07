/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.LOCAL.ConnPostgres;
//import Evento.Color.cla_color_palete;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.*;
import FORMULARIO.DAO.*;
import FORMULARIO.ENTIDAD.*;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmProd_dato extends javax.swing.JInternalFrame {

    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private producto ENTp = new producto();
    private DAO_producto DAOp = new DAO_producto();
    private BO_producto BOp = new BO_producto();
    private producto_categoria ENTpc = new producto_categoria();
    private DAO_producto_categoria DAOpc = new DAO_producto_categoria();
    private producto_unidad ENTpu = new producto_unidad();
    private DAO_producto_unidad DAOpu = new DAO_producto_unidad();
    private producto_marca ENTpm = new producto_marca();
    private DAO_producto_marca DAOpm = new DAO_producto_marca();
    private EvenJTextField evejtf = new EvenJTextField();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    Connection conn = ConnPostgres.getConnPosgres();
    private String nombreTabla_pri = "PRODUCTO";
    private String nombreTabla_sec = "FILTROS";
    private String creado_por = "digno";
    public static int fk_idproducto_categoria;
    public static int fk_idproducto_unidad;
    public static int fk_idproducto_marca;

    public static int getFk_idproducto_categoria() {
        return fk_idproducto_categoria;
    }

    public static void setFk_idproducto_categoria(int fk_idproducto_categoria) {
        FrmProd_dato.fk_idproducto_categoria = fk_idproducto_categoria;
    }

    public static int getFk_idproducto_unidad() {
        return fk_idproducto_unidad;
    }

    public static void setFk_idproducto_unidad(int fk_idproducto_unidad) {
        FrmProd_dato.fk_idproducto_unidad = fk_idproducto_unidad;
    }

    public static int getFk_idproducto_marca() {
        return fk_idproducto_marca;
    }

    public static void setFk_idproducto_marca(int fk_idproducto_marca) {
        FrmProd_dato.fk_idproducto_marca = fk_idproducto_marca;
    }

    private void abrir_formulario() {
        this.setTitle(nombreTabla_pri);
        evetbl.centrar_formulario_internalframa(this);
        reestableser();
        DAOp.actualizar_tabla_producto(conn, tbltabla_pri, "", getInt_orden());
    }

    private void titulo_formulario(String fecha_creado, String creado_por) {
        this.setTitle(nombreTabla_pri + " / fecha creado: " + fecha_creado + " / Creado Por: " + creado_por);
    }

    private boolean validar_guardar() {
        if (evejtf.getBoo_JTextField_vacio(txtnombre, "DEBE CARGAR UN NOMBRE")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcod_barra, "DEBE CARGAR UN CODIGO BARRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtprecio_compra, "DEBE CARGAR UN PRECIO COMPRA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtprecio_venta, "DEBE CARGAR UN PRECIO VENTA")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtstock_actual, "DEBE CARGAR UN STOCK ACTUAL")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtstock_minimo, "DEBE CARGAR UN STOCK MINIMO")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtstock_maximo, "DEBE CARGAR UN STOCK MAXIMO")) {
            return false;
        }
        if (getFk_idproducto_categoria() == 0) {
            JOptionPane.showMessageDialog(null, "NO SE CARGO CORRECTAMENTE LA CATEGORIA");
            txtprod_categoria.grabFocus();
            return false;
        }
        if (getFk_idproducto_unidad() == 0) {
            JOptionPane.showMessageDialog(null, "NO SE CARGO CORRECTAMENTE LA UNIDAD");
            txtprod_unidad.grabFocus();
            return false;
        }
        if (getFk_idproducto_marca() == 0) {
            JOptionPane.showMessageDialog(null, "NO SE CARGO CORRECTAMENTE LA MARCA");
            txtprod_marca.grabFocus();
            return false;
        }

        return true;
    }

    private int getInt_orden() {
        int ord = 1;
        if (jRord_id.isSelected()) {
            ord = 1;
        }
        if (jRord_prod.isSelected()) {
            ord = 3;
        }
        if (jRord_cate.isSelected()) {
            ord = 4;
        }
        if (jRord_unidad.isSelected()) {
            ord = 5;
        }
        if (jRord_marca.isSelected()) {
            ord = 6;
        }
        return ord;
    }

    private int getInt_iva() {
        int iva = 0;
        if (jRiva_exenta.isSelected()) {
            iva = 0;
        }
        if (jRiva_5.isSelected()) {
            iva = 5;
        }
        if (jRiva_10.isSelected()) {
            iva = 10;
        }
        return iva;
    }

    private void setInt_iva(double iva) {
        if (iva == 0) {
            jRiva_exenta.setSelected(true);
        }
        if (iva == 5) {
            jRiva_5.setSelected(true);
        }
        if (iva == 10) {
            jRiva_10.setSelected(true);
        }

    }

    private void cargar_dato() {
        ENTp.setC3creado_por(creado_por);
        ENTp.setC4codigo_barra(txtcod_barra.getText());
        ENTp.setC5nombre(txtnombre.getText());
        ENTp.setC6controlar_stock(jCescontrolstock.isSelected());
        ENTp.setC7es_venta(jCesventa.isSelected());
        ENTp.setC8es_compra(jCescompra.isSelected());
        ENTp.setC9es_insumo(jCesinsumo.isSelected());
        ENTp.setC10es_patrimonio(jCespatrimonio.isSelected());
        ENTp.setC11precio_venta(Integer.parseInt(txtprecio_venta.getText()));
        ENTp.setC12precio_compra(Integer.parseInt(txtprecio_compra.getText()));
        ENTp.setC13stock_actual(Integer.parseInt(txtstock_actual.getText()));
        ENTp.setC14stock_minimo(Integer.parseInt(txtstock_minimo.getText()));
        ENTp.setC15stock_maximo(Integer.parseInt(txtstock_maximo.getText()));
        ENTp.setC16iva(getInt_iva());
        ENTp.setC17fk_idproducto_categoria(getFk_idproducto_categoria());
        ENTp.setC18fk_idproducto_unidad(getFk_idproducto_unidad());
        ENTp.setC19fk_idproducto_marca(getFk_idproducto_marca());

    }

    private void boton_guardar() {
        if (validar_guardar()) {
            cargar_dato();
            BOp.insertar_producto(ENTp);
            DAOp.actualizar_tabla_producto(conn, tbltabla_pri, "", getInt_orden());
            reestableser();
        }
    }

    private void boton_editar() {
        if (validar_guardar()) {
            ENTp.setC1idproducto(Integer.parseInt(txtid.getText()));
            cargar_dato();
            BOp.update_producto(ENTp);
            DAOp.actualizar_tabla_producto(conn, tbltabla_pri, "", getInt_orden());
        }
    }

    private void seleccionar_tabla() {
        int idproducto = eveJtab.getInt_select_id(tbltabla_pri);
        DAOp.cargar_producto(conn, ENTp, idproducto);
        txtid.setText(String.valueOf(ENTp.getC1idproducto()));
        titulo_formulario(ENTp.getC2fecha_creado(), ENTp.getC3creado_por());
        txtcod_barra.setText(ENTp.getC4codigo_barra());
        txtnombre.setText(ENTp.getC5nombre());
        jCescontrolstock.setSelected(ENTp.getC6controlar_stock());
        jCesventa.setSelected(ENTp.getC7es_venta());
        jCescompra.setSelected(ENTp.getC8es_compra());
        jCesinsumo.setSelected(ENTp.getC9es_insumo());
        jCespatrimonio.setSelected(ENTp.getC10es_patrimonio());
        txtprecio_venta.setText(String.valueOf((int) ENTp.getC11precio_venta()));
        txtprecio_compra.setText(String.valueOf((int) ENTp.getC12precio_compra()));
        txtstock_actual.setText(String.valueOf((int) ENTp.getC13stock_actual()));
        txtstock_minimo.setText(String.valueOf((int) ENTp.getC14stock_minimo()));
        txtstock_maximo.setText(String.valueOf((int) ENTp.getC15stock_maximo()));
        setInt_iva(ENTp.getC16iva());
        DAOpc.cargar_producto_categoria(conn, ENTpc, ENTp.getC17fk_idproducto_categoria());
        txtprod_categoria.setText(ENTpc.getC4nombre());
        setFk_idproducto_categoria(ENTp.getC17fk_idproducto_categoria());
        DAOpu.cargar_producto_unidad(conn, ENTpu, ENTp.getC18fk_idproducto_unidad());
        txtprod_unidad.setText(ENTpu.getC4nombre());
        setFk_idproducto_unidad(ENTp.getC18fk_idproducto_unidad());
        DAOpm.cargar_producto_marca(conn, ENTpm, ENTp.getC19fk_idproducto_marca());
        txtprod_marca.setText(ENTpm.getC4nombre());
        setFk_idproducto_marca(ENTp.getC19fk_idproducto_marca());
        btnguardar.setEnabled(false);
        btneditar.setEnabled(true);
    }

    private void reestableser() {
        this.setTitle(nombreTabla_pri);
        jTab_principal.setTitleAt(0, nombreTabla_pri);
        jTab_principal.setTitleAt(1, nombreTabla_sec);
        txtid.setText(null);
        txtnombre.setText(null);
        txtcod_barra.setText(null);
        txtprecio_compra.setText(null);
        txtprecio_venta.setText(null);
        txtstock_actual.setText(null);
        txtstock_minimo.setText(null);
        txtstock_maximo.setText(null);
        txtprod_categoria.setText(null);
        txtprod_unidad.setText(null);
        txtprod_marca.setText(null);
        jRiva_10.setSelected(true);
        jCesventa.setSelected(true);
        jCescompra.setSelected(true);
        jCescontrolstock.setSelected(true);
        jCesinsumo.setSelected(false);
        jCespatrimonio.setSelected(false);
        setFk_idproducto_categoria(0);
        setFk_idproducto_unidad(0);
        setFk_idproducto_marca(0);
        DAOp.actualizar_tabla_producto(conn, tbltabla_pri, "", getInt_orden());
        btnguardar.setEnabled(true);
        btneditar.setEnabled(false);
        txtnombre.grabFocus();
    }

    private void boton_nuevo() {
        reestableser();
    }

    public FrmProd_dato() {
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

        gru_iva = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        gru_ord = new javax.swing.ButtonGroup();
        jTab_principal = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panel_insertar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        btnnuevo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtcod_barra = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtstock_actual = new javax.swing.JTextField();
        txtstock_minimo = new javax.swing.JTextField();
        txtstock_maximo = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        txtprecio_compra = new javax.swing.JTextField();
        txtprecio_venta = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jRiva_exenta = new javax.swing.JRadioButton();
        jRiva_5 = new javax.swing.JRadioButton();
        jRiva_10 = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        txtprod_categoria = new javax.swing.JTextField();
        txtprod_unidad = new javax.swing.JTextField();
        txtprod_marca = new javax.swing.JTextField();
        jCesventa = new javax.swing.JCheckBox();
        btnbuscar_categoria = new javax.swing.JButton();
        btnnuevo_categoria = new javax.swing.JButton();
        btnbuscar_unidad = new javax.swing.JButton();
        btnnuevo_unidad = new javax.swing.JButton();
        btnbuscar_marca = new javax.swing.JButton();
        btnnuevo_marca = new javax.swing.JButton();
        jCescompra = new javax.swing.JCheckBox();
        jCescontrolstock = new javax.swing.JCheckBox();
        jCesinsumo = new javax.swing.JCheckBox();
        jCespatrimonio = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltabla_pri = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        txtbuscar_codbarra = new javax.swing.JTextField();
        txtbuscar_producto = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jRord_prod = new javax.swing.JRadioButton();
        jRord_cate = new javax.swing.JRadioButton();
        jRord_unidad = new javax.swing.JRadioButton();
        jRord_marca = new javax.swing.JRadioButton();
        jRord_id = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbltabla_sec = new javax.swing.JTable();

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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("NOMBRE:");

        txtnombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnombreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnombreKeyReleased(evt);
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

        btneditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/modificar.png"))); // NOI18N
        btneditar.setText("EDITAR");
        btneditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btneditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Cod Barra:");

        txtcod_barra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyPressed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("STOCK"));

        txtstock_actual.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtstock_actual.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtstock_actual.setText("1000");
        txtstock_actual.setBorder(javax.swing.BorderFactory.createTitledBorder("ACTUAL"));
        txtstock_actual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtstock_actualKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtstock_actualKeyTyped(evt);
            }
        });

        txtstock_minimo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtstock_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtstock_minimo.setText("1000");
        txtstock_minimo.setBorder(javax.swing.BorderFactory.createTitledBorder("MINIMO"));
        txtstock_minimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtstock_minimoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtstock_minimoKeyTyped(evt);
            }
        });

        txtstock_maximo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtstock_maximo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtstock_maximo.setText("1000");
        txtstock_maximo.setBorder(javax.swing.BorderFactory.createTitledBorder("MAXIMO"));
        txtstock_maximo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtstock_maximoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtstock_maximoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txtstock_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtstock_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtstock_maximo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtstock_actual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstock_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtstock_maximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO"));

        txtprecio_compra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtprecio_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprecio_compra.setText("1000");
        txtprecio_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO COMPRA"));
        txtprecio_compra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecio_compraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecio_compraKeyTyped(evt);
            }
        });

        txtprecio_venta.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtprecio_venta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtprecio_venta.setText("1000");
        txtprecio_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO VENTA"));
        txtprecio_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecio_ventaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(txtprecio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprecio_venta))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtprecio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprecio_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO IVA"));

        gru_iva.add(jRiva_exenta);
        jRiva_exenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRiva_exenta.setText("EXENTA");

        gru_iva.add(jRiva_5);
        jRiva_5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRiva_5.setText("IVA 5%");

        gru_iva.add(jRiva_10);
        jRiva_10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRiva_10.setSelected(true);
        jRiva_10.setText("IVA 10%");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jRiva_exenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRiva_5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRiva_10)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRiva_exenta)
                    .addComponent(jRiva_5)
                    .addComponent(jRiva_10))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("RELACIONES PRODUCTO"));

        txtprod_categoria.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtprod_categoria.setBorder(javax.swing.BorderFactory.createTitledBorder("CATEGORIA"));
        txtprod_categoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_categoriaKeyPressed(evt);
            }
        });

        txtprod_unidad.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtprod_unidad.setBorder(javax.swing.BorderFactory.createTitledBorder("UNIDAD"));
        txtprod_unidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_unidadKeyPressed(evt);
            }
        });

        txtprod_marca.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtprod_marca.setBorder(javax.swing.BorderFactory.createTitledBorder("MARCA"));
        txtprod_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtprod_marcaActionPerformed(evt);
            }
        });
        txtprod_marca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprod_marcaKeyPressed(evt);
            }
        });

        jCesventa.setSelected(true);
        jCesventa.setText("ES VENTA");

        btnbuscar_categoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_categoriaActionPerformed(evt);
            }
        });

        btnnuevo_categoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_categoriaActionPerformed(evt);
            }
        });

        btnbuscar_unidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_unidadActionPerformed(evt);
            }
        });

        btnnuevo_unidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_unidadActionPerformed(evt);
            }
        });

        btnbuscar_marca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_marcaActionPerformed(evt);
            }
        });

        btnnuevo_marca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_nuevo.png"))); // NOI18N
        btnnuevo_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnuevo_marcaActionPerformed(evt);
            }
        });

        jCescompra.setSelected(true);
        jCescompra.setText("ES COMPRA");

        jCescontrolstock.setSelected(true);
        jCescontrolstock.setText("CONTROLAR STOCK");

        jCesinsumo.setSelected(true);
        jCesinsumo.setText("INSUMO");

        jCespatrimonio.setSelected(true);
        jCespatrimonio.setText("PATRIMONIO");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCesventa, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCescompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCescontrolstock))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jCesinsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCespatrimonio))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(txtprod_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnnuevo_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(txtprod_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnbuscar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnnuevo_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(txtprod_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnbuscar_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnnuevo_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtprod_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtprod_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtprod_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnuevo_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCesventa)
                    .addComponent(jCescompra)
                    .addComponent(jCescontrolstock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCesinsumo)
                    .addComponent(jCespatrimonio)))
        );

        javax.swing.GroupLayout panel_insertarLayout = new javax.swing.GroupLayout(panel_insertar);
        panel_insertar.setLayout(panel_insertarLayout);
        panel_insertarLayout.setHorizontalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnombre))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(btnnuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnguardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btneditar)))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        panel_insertarLayout.setVerticalGroup(
            panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertarLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(panel_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnnuevo)
                    .addComponent(btnguardar)
                    .addComponent(btneditar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_insertar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTab_principal.addTab("PRINCIPAL", jPanel1);

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

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR"));

        txtbuscar_codbarra.setBorder(javax.swing.BorderFactory.createTitledBorder("COD BARRA:"));
        txtbuscar_codbarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_codbarraKeyReleased(evt);
            }
        });

        txtbuscar_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTO"));
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtbuscar_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_producto, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtbuscar_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("ORDEN"));

        gru_ord.add(jRord_prod);
        jRord_prod.setSelected(true);
        jRord_prod.setText("PRODUCTO");

        gru_ord.add(jRord_cate);
        jRord_cate.setText("CATEGORIA");
        jRord_cate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRord_cateActionPerformed(evt);
            }
        });

        gru_ord.add(jRord_unidad);
        jRord_unidad.setText("UNIDAD");
        jRord_unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRord_unidadActionPerformed(evt);
            }
        });

        gru_ord.add(jRord_marca);
        jRord_marca.setText("MARCA");
        jRord_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRord_marcaActionPerformed(evt);
            }
        });

        gru_ord.add(jRord_id);
        jRord_id.setText("ID");
        jRord_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRord_idActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRord_id, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRord_prod)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRord_cate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRord_unidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRord_marca)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRord_prod)
                    .addComponent(jRord_cate)
                    .addComponent(jRord_unidad)
                    .addComponent(jRord_marca)
                    .addComponent(jRord_id))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTab_principal.addTab("SECUNDARIO", jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA"));

        tbltabla_sec.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbltabla_sec);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTab_principal.addTab("VENTA", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTab_principal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        DAOp.ancho_tabla_producto(tbltabla_pri);
    }//GEN-LAST:event_formInternalFrameOpened

    private void tbltabla_priMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbltabla_priMouseReleased
        // TODO add your handling code here:
        seleccionar_tabla();
    }//GEN-LAST:event_tbltabla_priMouseReleased

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed
        // TODO add your handling code here:
        boton_editar();
    }//GEN-LAST:event_btneditarActionPerformed

    private void btnnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevoActionPerformed
        // TODO add your handling code here:
        boton_nuevo();
    }//GEN-LAST:event_btnnuevoActionPerformed

    private void txtnombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtnombre, txtcod_barra);
    }//GEN-LAST:event_txtnombreKeyPressed

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
//        DAOgt.actualizar_tabla_producto_buscar(conn, tbltabla_pri, txtbuscar);
        if (txtbuscar_producto.getText().trim().length() >= 3) {
            String buscar = txtbuscar_producto.getText();
            String filtro = " and p.nombre ilike'%" + buscar + "%' ";
            DAOp.actualizar_tabla_producto(conn, tbltabla_pri, filtro, getInt_orden());
        }
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void txtnombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyReleased
        // TODO add your handling code here:
        txtnombre.setText(txtnombre.getText().toUpperCase());
    }//GEN-LAST:event_txtnombreKeyReleased

    private void btnnuevo_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_categoriaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_categoria());
    }//GEN-LAST:event_btnnuevo_categoriaActionPerformed

    private void btnnuevo_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_unidadActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_unidad());
    }//GEN-LAST:event_btnnuevo_unidadActionPerformed

    private void btnnuevo_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnuevo_marcaActionPerformed
        // TODO add your handling code here:
        evetbl.abrir_TablaJinternal(new FrmProd_marca());
    }//GEN-LAST:event_btnnuevo_marcaActionPerformed

    private void txtprod_categoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_categoriaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            vbus.abrir_buscar(1, "CATEGORIA", txtprod_categoria);
        }
    }//GEN-LAST:event_txtprod_categoriaKeyPressed

    private void txtprod_unidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_unidadKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            vbus.abrir_buscar(2, "UNIDAD", txtprod_unidad);
        }
    }//GEN-LAST:event_txtprod_unidadKeyPressed

    private void btnbuscar_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_categoriaActionPerformed
        // TODO add your handling code here:
        vbus.abrir_buscar(1, "CATEGORIA", txtprod_categoria);
    }//GEN-LAST:event_btnbuscar_categoriaActionPerformed

    private void btnbuscar_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_unidadActionPerformed
        // TODO add your handling code here:
        vbus.abrir_buscar(2, "UNIDAD", txtprod_unidad);
    }//GEN-LAST:event_btnbuscar_unidadActionPerformed

    private void btnbuscar_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_marcaActionPerformed
        // TODO add your handling code here:
        vbus.abrir_buscar(3, "MARCA", txtprod_marca);
    }//GEN-LAST:event_btnbuscar_marcaActionPerformed

    private void txtprod_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtprod_marcaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtprod_marcaActionPerformed

    private void txtprod_marcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprod_marcaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            vbus.abrir_buscar(3, "MARCA", txtprod_marca);
        }
    }//GEN-LAST:event_txtprod_marcaKeyPressed

    private void txtprecio_compraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_compraKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprecio_compraKeyTyped

    private void txtprecio_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprecio_ventaKeyTyped

    private void txtstock_actualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstock_actualKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtstock_actualKeyTyped

    private void txtstock_minimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstock_minimoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtstock_minimoKeyTyped

    private void txtstock_maximoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstock_maximoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtstock_maximoKeyTyped

    private void txtcod_barraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtcod_barra, txtprecio_compra);
    }//GEN-LAST:event_txtcod_barraKeyPressed

    private void txtprecio_compraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_compraKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtprecio_compra, txtprecio_venta);
    }//GEN-LAST:event_txtprecio_compraKeyPressed

    private void txtprecio_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_ventaKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtprecio_venta, txtstock_actual);
    }//GEN-LAST:event_txtprecio_ventaKeyPressed

    private void txtstock_actualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstock_actualKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtstock_actual, txtstock_minimo);
    }//GEN-LAST:event_txtstock_actualKeyPressed

    private void txtstock_minimoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstock_minimoKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtstock_minimo, txtstock_maximo);
    }//GEN-LAST:event_txtstock_minimoKeyPressed

    private void txtstock_maximoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstock_maximoKeyPressed
        // TODO add your handling code here:
        evejtf.saltar_campo_enter(evt, txtstock_maximo, txtprod_categoria);
    }//GEN-LAST:event_txtstock_maximoKeyPressed

    private void txtbuscar_codbarraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_codbarraKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            if(txtbuscar_codbarra.getText().trim().length()>3){
            String buscar = txtbuscar_codbarra.getText();
            String filtro = " and p.codigo_barra='" + buscar + "' ";
            DAOp.actualizar_tabla_producto(conn, tbltabla_pri, filtro, getInt_orden());
//            }
        }
    }//GEN-LAST:event_txtbuscar_codbarraKeyReleased

    private void jRord_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRord_idActionPerformed
        // TODO add your handling code here:
        DAOp.actualizar_tabla_producto(conn, tbltabla_pri, "", getInt_orden());
    }//GEN-LAST:event_jRord_idActionPerformed

    private void jRord_cateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRord_cateActionPerformed
        // TODO add your handling code here:
        DAOp.actualizar_tabla_producto(conn, tbltabla_pri, "", getInt_orden());
    }//GEN-LAST:event_jRord_cateActionPerformed

    private void jRord_unidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRord_unidadActionPerformed
        // TODO add your handling code here:
        DAOp.actualizar_tabla_producto(conn, tbltabla_pri, "", getInt_orden());
    }//GEN-LAST:event_jRord_unidadActionPerformed

    private void jRord_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRord_marcaActionPerformed
        // TODO add your handling code here:
        DAOp.actualizar_tabla_producto(conn, tbltabla_pri, "", getInt_orden());
    }//GEN-LAST:event_jRord_marcaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_categoria;
    private javax.swing.JButton btnbuscar_marca;
    private javax.swing.JButton btnbuscar_unidad;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnuevo;
    private javax.swing.JButton btnnuevo_categoria;
    private javax.swing.JButton btnnuevo_marca;
    private javax.swing.JButton btnnuevo_unidad;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup gru_iva;
    private javax.swing.ButtonGroup gru_ord;
    private javax.swing.JCheckBox jCescompra;
    private javax.swing.JCheckBox jCescontrolstock;
    private javax.swing.JCheckBox jCesinsumo;
    private javax.swing.JCheckBox jCespatrimonio;
    private javax.swing.JCheckBox jCesventa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRiva_10;
    private javax.swing.JRadioButton jRiva_5;
    private javax.swing.JRadioButton jRiva_exenta;
    private javax.swing.JRadioButton jRord_cate;
    private javax.swing.JRadioButton jRord_id;
    private javax.swing.JRadioButton jRord_marca;
    private javax.swing.JRadioButton jRord_prod;
    private javax.swing.JRadioButton jRord_unidad;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTab_principal;
    private javax.swing.JPanel panel_insertar;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JTable tbltabla_pri;
    private javax.swing.JTable tbltabla_sec;
    private javax.swing.JTextField txtbuscar_codbarra;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtcod_barra;
    private javax.swing.JTextField txtid;
    public static javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtprecio_compra;
    private javax.swing.JTextField txtprecio_venta;
    public static javax.swing.JTextField txtprod_categoria;
    public static javax.swing.JTextField txtprod_marca;
    public static javax.swing.JTextField txtprod_unidad;
    private javax.swing.JTextField txtstock_actual;
    private javax.swing.JTextField txtstock_maximo;
    private javax.swing.JTextField txtstock_minimo;
    // End of variables declaration//GEN-END:variables
}
