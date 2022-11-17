/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import Evento.Color.cla_color_pelete;
import Evento.Fecha.EvenFecha;
import Evento.Grafico.EvenSQLDataSet;
import Evento.Grafico.FunFreeChard;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import FORMULARIO.BO.BO_inventario;
import FORMULARIO.BO.BO_inventario_item;
import FORMULARIO.BO.BO_producto;
import FORMULARIO.DAO.DAO_inventario;
import FORMULARIO.DAO.DAO_inventario_item;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.ENTIDAD.inventario;
import FORMULARIO.ENTIDAD.inventario_item;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.producto_categoria;
import FORMULARIO.ENTIDAD.producto_unidad;
import FORMULARIO.ENTIDAD.usuario;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Digno
 */
public class FrmCrearInventario extends javax.swing.JInternalFrame {
    Connection conn = ConnPostgres.getConnPosgres();
    cla_color_pelete clacolor = new cla_color_pelete();
    private EvenJFRAME eveJfra = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenConexion eveconn = new EvenConexion();
    private EvenFecha evefec = new EvenFecha();
    private producto ENTp = new producto();
    private BO_producto BOp = new BO_producto();
    private DAO_producto DAOp = new DAO_producto();
    private producto_categoria ENTpc = new producto_categoria();
    private producto_unidad ENTpu = new producto_unidad();
    private inventario ENTin = new inventario();
    private DAO_inventario DAOin = new DAO_inventario();
    private BO_inventario BOin = new BO_inventario();
    private inventario_item ENTini = new inventario_item();
    private DAO_inventario_item DAOini = new DAO_inventario_item();
    private BO_inventario_item BOini = new BO_inventario_item();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenSQLDataSet evedata = new EvenSQLDataSet();
    private FunFreeChard ffchar = new FunFreeChard();
    private usuario ENTusu = new usuario();
    private int idproducto;
    private int idinventario;

    private void abrir_formulario() {
        this.setTitle("CREAR INVENTARIO");
        eveJfra.centrar_formulario_internalframa(this);
        reestableser();
        DAOin.actualizar_tabla_inventario(conn, tblinventario);
//        color_formulario();
    }
    private void actualizar_tabla_inventario_item_buscar(){
        if(tblinventario.getSelectedRow()>=0){
            int fk_idinventario=eveJtab.getInt_select_id(tblinventario);
            DAOini.actualizar_tabla_item_inventario_diferencia(conn, fk_idinventario, tblitem_inventario_cargado);
        }
    }
    private void reestableser() {
        idproducto = -1;
        idinventario = DAOin.getInt_idinventario_iniciado(conn);
        if (idinventario >= 0) {
            DAOin.cargar_inventario(conn, ENTin, idinventario);
            txtidinventario.setText(String.valueOf(ENTin.getC1idinventario()));
            txtfechainicio.setText(ENTin.getC4fecha_inicio());
            txtdescripcion.setText(ENTin.getC6descripcion());
            DAOini.actualizar_tabla_item_inventario_diferencia(conn, idinventario, tblitem_inventario);
            txtpro_codbarra.setVisible(true);
            btniniciar_inventario.setEnabled(false);
        } else {
            txtfechainicio.setText(evefec.getString_formato_fecha_hora());
            int id = eveconn.getInt_ultimoID_mas_uno(conn, ENTin.getTb_inventario(), ENTin.getId_idinventario());
            txtidinventario.setText(String.valueOf(id));
            txtdescripcion.setText(null);
            txtpro_codbarra.setVisible(false);
            btniniciar_inventario.setEnabled(true);
        }
    }

    private boolean validar_inventario() {
        if (evejtf.getBoo_JTextField_vacio(txtdescripcion, "DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        return true;
    }

    private void boton_crear_inventario() {
        if (validar_inventario()) {
            ENTin.setC3creado_por(ENTusu.getGlobal_nombre());
            ENTin.setC6descripcion(txtdescripcion.getText());
            ENTin.setC9total_precio_venta(0);
            ENTin.setC10total_precio_compra(0);
            ENTin.setC7es_abierto(true);
            ENTin.setC8es_cerrado(false);
            ENTin.setC11fk_idusuario(ENTusu.getGlobal_idusuario());
            BOin.insertar_inventario(ENTin);
            DAOin.actualizar_tabla_inventario(conn, tblinventario);
            reestableser();
        }
    }

    private void reestableser_producto() {
        txtpro_codbarra.setText(null);
        txtpro_nombre.setText(null);
        txtpro_pventa_mayo.setText(null);
        txtpro_precio_compra.setText(null);
        txtpro_stock_actual.setText(null);
        txtpro_categoria.setText(null);
        txtpro_unidad.setText(null);
        txtpro_marca.setText(null);
        txtstock_contado.setText(null);
        txtstock_contado.setEditable(false);
        txtpro_codbarra.grabFocus();
    }

    private void buscar_producto_cargar() {
        if (DAOp.getBoolean_cargar_producto_por_codbarra(conn, ENTp, txtpro_codbarra.getText())) {
            if (DAOini.getBoolean_buscar_codbarra_de_item_inventario(conn, idinventario, txtpro_codbarra.getText())) {
                JOptionPane.showMessageDialog(null, "ESTE PRODUCTO YA ESTA EN LA LISTA DE INVENTARIO");
            } else {
                idproducto = ENTp.getC1idproducto();
                txtpro_nombre.setText(ENTp.getC5nombre());
                txtpro_pventa_mayo.setText(evejtf.getString_format_nro_decimal(ENTp.getC11precio_venta()));
                txtpro_precio_compra.setText(evejtf.getString_format_nro_decimal(ENTp.getC12precio_compra()));
                txtpro_stock_actual.setText(evejtf.getString_format_nro_entero(ENTp.getC13stock_actual()));
                txtpro_categoria.setText("id:"+ENTp.getC17fk_idproducto_categoria());
                txtpro_unidad.setText("id:"+ENTp.getC18fk_idproducto_unidad());
                txtpro_marca.setText("id:"+ENTp.getC19fk_idproducto_marca());
                txtstock_contado.setEditable(true);
                txtstock_contado.grabFocus();
            }
        } else {
            txtstock_contado.setEditable(false);
            JOptionPane.showMessageDialog(null, "EL PRODUCTO NO SE ENCONTRO");
        }
    }

    private void boton_cargar_item_inventario() {
        if (txtstock_contado.getText().trim().length() > 0) {
            if (idproducto >= 0) {
                int stock = (int) ENTp.getC13stock_actual();
                ENTini.setC4stock_sistema(stock);
                int contado = Integer.parseInt(txtstock_contado.getText());
                ENTini.setC3creado_por(ENTusu.getGlobal_nombre());
                ENTini.setC5stock_contado(contado);
                ENTini.setC6precio_venta(ENTp.getC11precio_venta());
                ENTini.setC7precio_compra(ENTp.getC12precio_compra());
                ENTini.setC8es_temp(true);
                ENTini.setC9es_cargado(false);
                ENTini.setC10fk_idinventario(idinventario);
                ENTini.setC11fk_idproducto(idproducto);
                BOini.insertar_inventario_item(ENTini);
                DAOini.actualizar_tabla_item_inventario_diferencia(conn, idinventario, tblitem_inventario);
                reestableser_producto();
            }else{
                JOptionPane.showMessageDialog(null, "EL PRODUCTO NO SE CARGO");
                txtpro_codbarra.grabFocus();
            }
        }else{
            JOptionPane.showMessageDialog(null, "CARGAR LA CANTIDAD EXISTENTE","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void boton_eliminar_item() {
        if (!eveJtab.getBoolean_validar_select(tblitem_inventario)) {
            int iditem_inventario = eveJtab.getInt_select_id(tblitem_inventario);
            ENTini.setC8es_temp(false);
            ENTini.setC9es_cargado(false);
            ENTini.setC1idinventario_item(iditem_inventario);
            BOini.delete_item_inventario(ENTini);
            DAOini.actualizar_tabla_item_inventario_diferencia(conn, idinventario, tblitem_inventario);
        }
    }

    private void boton_terminar_inventario() {
        if (tblitem_inventario.getRowCount() > 0) {
            ENTin.setC7es_abierto(false);
            ENTin.setC8es_cerrado(true);
            ENTin.setC1idinventario(idinventario);
            BOin.update_inventario_terminar(ENTin);
            reestableser();
            DAOini.actualizar_tabla_item_inventario_diferencia(conn, idinventario, tblitem_inventario);
        } else {
            JOptionPane.showMessageDialog(null, "CARGAR POR LO MENOS UN PRODUCTO");
        }
    }
    private void boton_imprimir_inventario() {
        if (eveJtab.getBoolean_validar_select_mensaje(tblinventario, "SELECCIONAR LA TABLA INVENTARIO")) {
            int idinventario = eveJtab.getInt_select_id(tblinventario);
            select_imprimir_inventario(idinventario);
        }
    }
     private void select_imprimir_inventario(int idinventario) {
        Object[] botones = {"TICKET DETALLE", "REPORTE A4", "CANCELAR"};
        int eleccion_comando = JOptionPane.showOptionDialog(null, "SELECCIONA UNA PARA IMPRIMIR ",
                "CIERRE CAJA",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, botones, "TICKET DETALLE");
        if (eleccion_comando == 0) {
//            poscd.boton_imprimir_pos_CAJA_DETALLE(conn, idcaja_cierre, false);
        }
        if (eleccion_comando == 1) {
            DAOini.imprimir_inventario_cargado(conn, idinventario);
        }
    }
    public FrmCrearInventario() {
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

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        panel_inventario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtidinventario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtfechainicio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtdescripcion = new javax.swing.JTextField();
        btniniciar_inventario = new javax.swing.JButton();
        btnterminar_inventario = new javax.swing.JButton();
        panel_producto = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtpro_codbarra = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtpro_nombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtpro_categoria = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtpro_marca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtpro_unidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtpro_pventa_mayo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtpro_precio_compra = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtpro_stock_actual = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtstock_contado = new javax.swing.JTextField();
        btncargar = new javax.swing.JButton();
        panel_tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblitem_inventario = new javax.swing.JTable();
        btneliminar_item_inventario = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panel_tabla1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_inventario_cargado = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblinventario = new javax.swing.JTable();
        btnimprimir_inventario = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
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

        panel_inventario.setBorder(javax.swing.BorderFactory.createTitledBorder("CREAR INVENTARIO"));

        jLabel1.setText("ID INVENTARIO:");

        jLabel2.setText("FECHA DE INICIO:");

        jLabel3.setText("DESCRIPCION:");

        btniniciar_inventario.setText("INICIAR INVENTARIO");
        btniniciar_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btniniciar_inventarioActionPerformed(evt);
            }
        });

        btnterminar_inventario.setText("TERMINAR INVENTARIO");
        btnterminar_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnterminar_inventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_inventarioLayout = new javax.swing.GroupLayout(panel_inventario);
        panel_inventario.setLayout(panel_inventarioLayout);
        panel_inventarioLayout.setHorizontalGroup(
            panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_inventarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_inventarioLayout.createSequentialGroup()
                        .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtdescripcion)
                            .addGroup(panel_inventarioLayout.createSequentialGroup()
                                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtidinventario, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtfechainicio, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panel_inventarioLayout.createSequentialGroup()
                        .addComponent(btniniciar_inventario, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnterminar_inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_inventarioLayout.setVerticalGroup(
            panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_inventarioLayout.createSequentialGroup()
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtidinventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtfechainicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtdescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(panel_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btniniciar_inventario)
                    .addComponent(btnterminar_inventario))
                .addContainerGap())
        );

        panel_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("BUSCAR PRODUCTO"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("COD BARRA:");

        txtpro_codbarra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtpro_codbarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpro_codbarraKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("NOMBRE:");

        txtpro_nombre.setEditable(false);
        txtpro_nombre.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_nombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("CATEGORIA:");

        txtpro_categoria.setEditable(false);
        txtpro_categoria.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_categoria.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("MARCA:");

        txtpro_marca.setEditable(false);
        txtpro_marca.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_marca.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("UNIDAD:");

        txtpro_unidad.setEditable(false);
        txtpro_unidad.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_unidad.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("P. VENTA MAYO:");

        txtpro_pventa_mayo.setEditable(false);
        txtpro_pventa_mayo.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_pventa_mayo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("P. COMPRA:");

        txtpro_precio_compra.setEditable(false);
        txtpro_precio_compra.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_precio_compra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("STOCK ACTUAL:");

        txtpro_stock_actual.setEditable(false);
        txtpro_stock_actual.setBackground(new java.awt.Color(204, 204, 204));
        txtpro_stock_actual.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("CANTIDAD CONTADO"));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("CONTADO:");

        txtstock_contado.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        txtstock_contado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtstock_contado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtstock_contadoKeyPressed(evt);
            }
        });

        btncargar.setText("CARGAR");
        btncargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtstock_contado, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncargar, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(txtstock_contado)
                .addComponent(btncargar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.CENTER)
        );

        javax.swing.GroupLayout panel_productoLayout = new javax.swing.GroupLayout(panel_producto);
        panel_producto.setLayout(panel_productoLayout);
        panel_productoLayout.setHorizontalGroup(
            panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productoLayout.createSequentialGroup()
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_productoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtpro_nombre)
                            .addGroup(panel_productoLayout.createSequentialGroup()
                                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtpro_categoria)
                                    .addComponent(txtpro_marca)
                                    .addComponent(txtpro_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                    .addComponent(txtpro_stock_actual, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                                    .addComponent(txtpro_precio_compra)
                                    .addComponent(txtpro_pventa_mayo))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panel_productoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpro_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_productoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_productoLayout.setVerticalGroup(
            panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_productoLayout.createSequentialGroup()
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtpro_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtpro_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtpro_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtpro_marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtpro_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtpro_pventa_mayo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtpro_precio_compra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtpro_stock_actual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        panel_tabla.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM INVENTARIO"));

        tblitem_inventario.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblitem_inventario);

        btneliminar_item_inventario.setText("ELIMINAR");
        btneliminar_item_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminar_item_inventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tablaLayout = new javax.swing.GroupLayout(panel_tabla);
        panel_tabla.setLayout(panel_tablaLayout);
        panel_tablaLayout.setHorizontalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(btneliminar_item_inventario)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tablaLayout.setVerticalGroup(
            panel_tablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminar_item_inventario))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_inventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panel_inventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_producto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panel_tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("CREAR INVENTARIO", jPanel2);

        panel_tabla1.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM INVENTARIO"));

        tblitem_inventario_cargado.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblitem_inventario_cargado);

        javax.swing.GroupLayout panel_tabla1Layout = new javax.swing.GroupLayout(panel_tabla1);
        panel_tabla1.setLayout(panel_tabla1Layout);
        panel_tabla1Layout.setHorizontalGroup(
            panel_tabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
        );
        panel_tabla1Layout.setVerticalGroup(
            panel_tabla1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("INVENTARIO"));

        tblinventario.setModel(new javax.swing.table.DefaultTableModel(
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
        tblinventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblinventarioMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblinventario);

        btnimprimir_inventario.setText("IMPRIMIR INVENTARIO");
        btnimprimir_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimir_inventarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnimprimir_inventario)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnimprimir_inventario))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_tabla1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("TABLA INVENTARIO", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btniniciar_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniniciar_inventarioActionPerformed
        // TODO add your handling code here:
        boton_crear_inventario();
    }//GEN-LAST:event_btniniciar_inventarioActionPerformed

    private void txtpro_codbarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpro_codbarraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_producto_cargar();
        }
    }//GEN-LAST:event_txtpro_codbarraKeyPressed

    private void btncargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargarActionPerformed
        // TODO add your handling code here:
        boton_cargar_item_inventario();
    }//GEN-LAST:event_btncargarActionPerformed

    private void txtstock_contadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtstock_contadoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_cargar_item_inventario();
        }
    }//GEN-LAST:event_txtstock_contadoKeyPressed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        DAOini.ancho_tabla_item_inventario_diferencia(tblitem_inventario);
        DAOin.ancho_tabla_inventario(tblinventario);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneliminar_item_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminar_item_inventarioActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item();
    }//GEN-LAST:event_btneliminar_item_inventarioActionPerformed

    private void btnterminar_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnterminar_inventarioActionPerformed
        // TODO add your handling code here:
        boton_terminar_inventario();
    }//GEN-LAST:event_btnterminar_inventarioActionPerformed

    private void tblinventarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblinventarioMouseReleased
        // TODO add your handling code here:
        actualizar_tabla_inventario_item_buscar();
    }//GEN-LAST:event_tblinventarioMouseReleased

    private void btnimprimir_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimir_inventarioActionPerformed
        // TODO add your handling code here:
        boton_imprimir_inventario();
    }//GEN-LAST:event_btnimprimir_inventarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncargar;
    private javax.swing.JButton btneliminar_item_inventario;
    private javax.swing.JButton btnimprimir_inventario;
    private javax.swing.JButton btniniciar_inventario;
    private javax.swing.JButton btnterminar_inventario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel_inventario;
    private javax.swing.JPanel panel_producto;
    private javax.swing.JPanel panel_tabla;
    private javax.swing.JPanel panel_tabla1;
    private javax.swing.JTable tblinventario;
    private javax.swing.JTable tblitem_inventario;
    private javax.swing.JTable tblitem_inventario_cargado;
    private javax.swing.JTextField txtdescripcion;
    private javax.swing.JTextField txtfechainicio;
    private javax.swing.JTextField txtidinventario;
    private javax.swing.JTextField txtpro_categoria;
    private javax.swing.JTextField txtpro_codbarra;
    private javax.swing.JTextField txtpro_marca;
    private javax.swing.JTextField txtpro_nombre;
    private javax.swing.JTextField txtpro_precio_compra;
    private javax.swing.JTextField txtpro_pventa_mayo;
    private javax.swing.JTextField txtpro_stock_actual;
    private javax.swing.JTextField txtpro_unidad;
    private javax.swing.JTextField txtstock_contado;
    // End of variables declaration//GEN-END:variables
}
