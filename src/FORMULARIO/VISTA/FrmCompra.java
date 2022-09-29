/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FORMULARIO.VISTA;

import BASEDATO.EvenConexion;
import BASEDATO.LOCAL.ConnPostgres;
import CONFIGURACION.ComputerInfo;
import ESTADOS.EvenEstado;
import Evento.Combobox.EvenCombobox;
import Evento.Fecha.EvenFecha;
import Evento.JButton.EvenJButton;
import Evento.JTextField.EvenJTextField;
import Evento.Jframe.EvenJFRAME;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.BO_compra;
import FORMULARIO.BO.BO_compra_item;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
//import FORMULARIO.BO.BO_venta;
//import FORMULARIO.BO.BO_venta_item;
import FORMULARIO.DAO.DAO_compra;
import FORMULARIO.DAO.DAO_compra_item;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
//import FORMULARIO.DAO.DAO_venta;
//import FORMULARIO.DAO.DAO_venta_item;
import FORMULARIO.ENTIDAD.compra;
import FORMULARIO.ENTIDAD.compra_item;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.usuario;
//import FORMULARIO.ENTIDAD.venta;
//import FORMULARIO.ENTIDAD.venta_item;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmCompra extends javax.swing.JInternalFrame {

    private EvenJButton evebtn = new EvenJButton();
    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenEstado eveest = new EvenEstado();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenConexion eveconn = new EvenConexion();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private ComputerInfo pcinfo = new ComputerInfo();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    usuario ENTusu = new usuario(); //creado_por = ENTusu.getGlobal_nombre();
    Connection conn = ConnPostgres.getConnPosgres();
    private producto ENTp = new producto();
    private DAO_producto DAOp = new DAO_producto();
    private compra ENTcom = new compra();
    private DAO_compra DAOcom = new DAO_compra();
    private BO_compra BOcom = new BO_compra();
    private compra_item ENTcomi = new compra_item();
    private DAO_compra_item DAOcomi = new DAO_compra_item();
    private BO_compra_item BOcomi = new BO_compra_item();
    private caja_cierre_detalle ENTccd = new caja_cierre_detalle();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    DefaultTableModel model_itemf = new DefaultTableModel();
    private java.util.List<JButton> botones_categoria;
    private java.util.List<JButton> botones_unidad;
    private java.util.List<JButton> botones_marca;
    private int cant_boton_cate;
    private int cant_boton_unid;
    private int cant_boton_marca;
    private String fk_idproducto_unidad;
    private String fk_idproducto_categoria;
    private String fk_idproducto_marca;
    private String nombreTabla_pri = "COMPRA PRINCIPAL";
    private String nombreTabla_sec = "FILTRO";
    private String creado_por = "digno";
    private int idproducto;
    private int fk_idcompra;
    private int fk_idusuario;
    private String precio_venta_mostrar;
    private String precio_venta_oculto;
    private String precio_compra_oculto;
    private String iva_producto;
    private double monto_total;
    private String monto_letra;
    private double monto_iva5;
    private double monto_iva10;

    private void abrir_formulario() {
        cargar_usuario_acceso();
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        this.setTitle(nombreTabla_pri + " USUARIO:" + creado_por + " IP:" + pcinfo.getStringMiIP());
        evetbl.centrar_formulario_internalframa(this);
        botones_categoria = new ArrayList<>();
        botones_unidad = new ArrayList<>();
        botones_marca = new ArrayList<>();
        cargar_boton_categoria();
        crear_item_producto();
        reestableser_compra();
    }

    private void reestableser_compra() {
        cargar_usuario_acceso();
        fk_idcompra = (eveconn.getInt_ultimoID_mas_uno(conn, ENTcom.getTb_compra(), ENTcom.getId_idcompra()));
        txtidcompra.setText(String.valueOf(fk_idcompra));
        eveJtab.limpiar_tabla_datos(model_itemf);
        txtobservacion.setText("Ninguna");
        monto_letra = "cero";
        txtmonto_letra.setText(monto_letra);
        txtnro_factura.setText("0");
        monto_total = 0;
        monto_iva5 = 0;
        monto_iva10 = 0;

    }

    private void cargar_usuario_acceso() {
        if (fk_idusuario != ENTusu.getGlobal_idusuario()) {
            usuario usu = new usuario();
            creado_por = usu.getGlobal_nombre();
            fk_idusuario = usu.getGlobal_idusuario();
            this.setTitle(nombreTabla_pri + " USUARIO:" + creado_por);
        }
    }

    private void seleccionar_producto() {
        if (tblproducto.getSelectedRow() >= 0) {
            idproducto = eveJtab.getInt_select_id(tblproducto);
            DAOp.cargar_producto(conn, ENTp, idproducto);
            txtcod_barra.setText(ENTp.getC4codigo_barra());
            txtbuscar_producto.setText(ENTp.getC5nombre());
            precio_venta_mostrar = eveJtab.getString_select(tblproducto, 4);
            precio_venta_oculto = String.valueOf((int) ENTp.getC11precio_venta());
            precio_compra_oculto = String.valueOf((int) ENTp.getC12precio_compra());
            iva_producto = String.valueOf((int) ENTp.getC16iva());
            txtcantidad_producto.setText("1");
            txtcantidad_producto.grabFocus();
        }
    }

    private void cargar_boton_categoria() {
        String titulo = "cargar_boton_categoria";
        String sql = "SELECT  c.idproducto_categoria, c.nombre,c.orden \n"
                + "From producto_categoria c,producto p \n"
                + "where c.idproducto_categoria=p.fk_idproducto_categoria \n"
                + "and c.activo=true \n"
                + "and p.es_compra=true \n"
                + "group by 1,2,3\n"
                + "order by c.orden desc;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            panel_referencia_categoria.removeAll();
            botones_categoria.clear();
            int cant = 0;
            while (rs.next()) {
                cant++;
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_categoria");
                crear_boton_categoria(textboton, nameboton);
                if (cant == 1) {
                    fk_idproducto_categoria = nameboton;
                    actualizar_tabla_producto(1);
                }
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void crear_boton_categoria(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setName(nameboton);
        panel_referencia_categoria.add(boton);
        botones_categoria.add(boton);
        cant_boton_cate++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int p = 0; p < cant_boton_cate; p++) {
                    botones_categoria.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_categoria = ((JButton) e.getSource()).getName();
                cargar_boton_unidad(fk_idproducto_categoria);
                cargar_boton_marca(fk_idproducto_categoria);
                System.out.println("fk_idproducto_categoria:" + fk_idproducto_categoria);
                actualizar_tabla_producto(1);
            }
        });
        panel_referencia_categoria.updateUI();
    }

    private void cargar_boton_unidad(String idproducto_categoria) {
        String titulo = "cargar_boton_unidad";
        panel_referencia_unidad.removeAll();
        botones_unidad.clear();
        cant_boton_unid = 0;
        String sql = "select u.idproducto_unidad, u.nombre \n"
                + "from producto p,producto_categoria c,producto_unidad u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad\n"
                + "and c.idproducto_categoria=" + idproducto_categoria
                + " group by 1,2 order by u.nombre asc  limit 8;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_unidad");
                boton_agregar_unidad(textboton, nameboton);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void boton_agregar_unidad(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setName(nameboton);
        panel_referencia_unidad.add(boton);
        botones_unidad.add(boton);
        cant_boton_unid++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int p = 0; p < cant_boton_unid; p++) {
                    botones_unidad.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_unidad = ((JButton) e.getSource()).getName();
                System.out.println("fk_idproducto_unidad: " + fk_idproducto_unidad);
                actualizar_tabla_producto(2);
            }
        });
        panel_referencia_unidad.updateUI();
    }

    private void cargar_boton_marca(String idproducto_categoria) {
        String titulo = "cargar_boton_marca";
        panel_referencia_marca.removeAll();
        botones_marca.clear();
        cant_boton_marca = 0;
        String sql = "select u.idproducto_marca, u.nombre \n"
                + "from producto p,producto_categoria c,producto_marca u \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_marca=u.idproducto_marca\n"
                + "and c.idproducto_categoria=" + idproducto_categoria
                + " group by 1,2 order by u.nombre asc  limit 8;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            while (rs.next()) {
                String textboton = rs.getString("nombre");
                String nameboton = rs.getString("idproducto_marca");
                boton_agregar_marca(textboton, nameboton);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void boton_agregar_marca(String textboton, String nameboton) {
        JButton boton = new JButton(textboton);
        boton.setFont(new Font("Arial", Font.BOLD, 10));
        boton.setName(nameboton);
        panel_referencia_marca.add(boton);
        botones_marca.add(boton);
        cant_boton_marca++;
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int p = 0; p < cant_boton_marca; p++) {
                    botones_marca.get(p).setBackground(new java.awt.Color(255, 255, 255));
                }
                ((JButton) e.getSource()).setBackground(new java.awt.Color(153, 153, 255));
                fk_idproducto_marca = ((JButton) e.getSource()).getName();
                System.out.println("fk_idproducto_marca: " + fk_idproducto_marca);
                actualizar_tabla_producto(4);
            }
        });
        panel_referencia_marca.updateUI();
    }

    private void actualizar_tabla_producto(int tipo) {
        String filtro_categoria = "";
        String filtro_unidad = "";
        String filtro_producto = "";
        String filtro_marca = "";
        if (tipo == 1) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
        }
        if (tipo == 2) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            filtro_unidad = " and p.fk_idproducto_unidad=" + fk_idproducto_unidad;
        }
        if (tipo == 3) {
            if (txtbuscar_producto.getText().trim().length() > 0) {
                String por = "%";
                String temp = por + txtbuscar_producto.getText() + por;
                filtro_producto = " and p.nombre ilike'" + temp + "' ";
            }
        }
        if (tipo == 4) {
            filtro_categoria = " and p.fk_idproducto_categoria=" + fk_idproducto_categoria;
            filtro_marca = " and p.fk_idproducto_marca=" + fk_idproducto_marca;
        }
        if (tipo == 5) {
            if (txtcod_barra.getText().trim().length() > 0) {
                String temp = txtcod_barra.getText();
                filtro_producto = " and p.codigo_barra='" + temp + "' ";
            }
        }

        String sql = "select p.idproducto as idp,\n"
                + "(p.nombre) as producto,u.nombre as unidad,\n"
                + "p.stock_actual as stock, \n"
                + "TRIM(to_char(p.precio_compra,'999G999G999')) as pcompra,"
                + "p.precio_venta as oculto_venta,p.precio_compra as oculto_compra,p.iva as iva \n"
                + "from producto p,producto_categoria c,producto_unidad u,producto_marca pm \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad \n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                + "and c.activo=true \n"
                + "and p.es_compra=true \n" + filtro_categoria + filtro_unidad + filtro_producto + filtro_marca
                + " order by p.idproducto  asc;";
        eveconn.Select_cargar_jtable(conn, sql, tblproducto);
        ancho_tabla_producto();
    }

    private void ancho_tabla_producto() {
        int Ancho[] = {5, 50, 20, 10, 15, 1, 1, 1};
        eveJtab.setAnchoColumnaJtable(tblproducto, Ancho);
        eveJtab.alinear_derecha_columna(tblproducto, 4);
        eveJtab.ocultar_columna(tblproducto, 5);
        eveJtab.ocultar_columna(tblproducto, 6);
        eveJtab.ocultar_columna(tblproducto, 7);
    }

    private void crear_item_producto() {
        String dato[] = {"id", "descripcion", "precio", "C", "total", "Opventa", "Opcompra", "Otipo", "Ototal", "Oiva"};
        eveJtab.crear_tabla_datos(tblitem_producto, model_itemf, dato);
        ancho_item_producto();
    }

    private void ancho_item_producto() {
        int Ancho[] = {5, 53, 15, 6, 14, 1, 1, 1, 1, 1};
        eveJtab.setAnchoColumnaJtable(tblitem_producto, Ancho);
        eveJtab.ocultar_columna(tblitem_producto, 5);
        eveJtab.ocultar_columna(tblitem_producto, 6);
        eveJtab.ocultar_columna(tblitem_producto, 7);
        eveJtab.ocultar_columna(tblitem_producto, 8);
        eveJtab.ocultar_columna(tblitem_producto, 9);
        eveJtab.alinear_derecha_columna(tblitem_producto, 2);
        eveJtab.alinear_derecha_columna(tblitem_producto, 3);
        eveJtab.alinear_derecha_columna(tblitem_producto, 4);
    }

    private boolean validar_cargar_item() {
        if (eveJtab.getBoolean_validar_select(tblproducto)) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtbuscar_producto, "DEBE CARGAR UNA DESCRIPCION")) {
            return false;
        }
        if (evejtf.getBoo_JTextField_vacio(txtcantidad_producto, "DEBE CARGAR UNA CANTIDAD")) {
            return false;
        }
        return true;
    }

    private void cargar_item_producto() {
        if (validar_cargar_item()) {
            String Sidproducto = String.valueOf(idproducto);
            String Sdescripcion = txtbuscar_producto.getText();
            String Sprecio_venta1 = precio_venta_mostrar;
            String Scantidad = txtcantidad_producto.getText();
            String Sprecio_venta2 = precio_venta_oculto;
            String Sprecio_compra = precio_compra_oculto;
            String Ssubtotal = "0";
            String Ssubtotal2 = "0";
            String Stipo = "COMPRA";
            String Siva = iva_producto;
            String dato[] = {Sidproducto, Sdescripcion, Sprecio_venta1, Scantidad, Ssubtotal, Sprecio_venta2, Sprecio_compra, Stipo, Ssubtotal2, Siva};
            eveJtab.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 3, 6, 4, true);
            eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 3, 6, 8, false);
            reestableser_item_compra();
            suma_item_producto();
        }
    }

    private void suma_item_producto() {
        monto_total = eveJtab.getDouble_sumar_tabla(tblitem_producto, 8);
        jFtotal_consumo.setValue(monto_total);
        String Smonto_total = String.valueOf(monto_total);
        monto_letra = nroletra.Convertir(Smonto_total, true);
        txtmonto_letra.setText(monto_letra);
        monto_iva5=0;
        monto_iva10=0;
        for (int row = 0; row < tblitem_producto.getRowCount(); row++) {
            String Siva = ((tblitem_producto.getModel().getValueAt(row, 9).toString()));
            String Ssubtotal2 = ((tblitem_producto.getModel().getValueAt(row, 8).toString()));
            int iva = Integer.parseInt(Siva);
            double subtotal=Double.parseDouble(Ssubtotal2);
            if (iva == 5) {
                monto_iva5 = monto_iva5 + subtotal;
            }
            if (iva == 10) {
                monto_iva10 = monto_iva10 + subtotal;
            }
        }
    }

    private void reestableser_item_compra() {
        txtcod_barra.setText(null);
        txtbuscar_producto.setText(null);
        precio_venta_mostrar = "0";
        precio_venta_oculto = "0";
        precio_compra_oculto = "0";
        iva_producto = "0";
        txtcantidad_producto.setText("1");
        actualizar_tabla_producto(1);
    }

    private void cargar_con_cantidad(int cant) {
        txtcantidad_producto.setText(String.valueOf(cant));
        cargar_item_producto();
    }

    private boolean validar_carga_compra() {
        if (tblitem_producto.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "CARGAR POR LO MENOS UN ITEM");
            return false;
        }
        if(evejtf.getBoo_JTextField_vacio(txtnro_factura,"CARGAR UN NUMERO DE FACTURA")){
            return  false;
        }
        return true;
    }

    private void cargar_dato_compra() {
        ENTcom.setC3creado_por(creado_por);
        ENTcom.setC4fecha_compra("now()");
        ENTcom.setC5nro_factura(txtnro_factura.getText());
        ENTcom.setC6es_factura(false);
        ENTcom.setC7monto_total(monto_total);
        ENTcom.setC8monto_iva5(monto_iva5);
        ENTcom.setC9monto_iva10(monto_iva10);
        ENTcom.setC10monto_letra(monto_letra);
        ENTcom.setC11observacion(txtobservacion.getText());
        ENTcom.setC12estado(eveest.getEst_Emitido());
        ENTcom.setC13fk_idpersona(0);
        ENTcom.setC14fk_idusuario(fk_idusuario);
    }
private void cargar_dato_caja_detalle_COMPRA() {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        String descripcion = fk_idcompra + "-(" + eveest.getCaja_COMPRA() + ")-Factura: " + txtnro_factura.getText() + ",PROV:(" + txtproveedor_nombre.getText() + ")";
        ENTccd.setC3creado_por(creado_por);
        ENTccd.setC4cerrado_por(eveest.getCaja_COMPRA());
        ENTccd.setC5es_cerrado(false);
        ENTccd.setC6monto_apertura_caja(0);
        ENTccd.setC7monto_cierre_caja(0);
        ENTccd.setC8monto_ocupa_minimo(0);
        ENTccd.setC9monto_ocupa_adicional(0);
        ENTccd.setC10monto_ocupa_consumo(0);
        ENTccd.setC11monto_ocupa_descuento(0);
        ENTccd.setC12monto_ocupa_adelanto(0);
        ENTccd.setC13monto_gasto(0);
        ENTccd.setC14monto_compra(monto_total);
        ENTccd.setC15monto_vale(0);
        ENTccd.setC16monto_liquidacion(0);
        ENTccd.setC17estado(eveest.getEst_Emitido());
        ENTccd.setC18descripcion(descripcion);
        ENTccd.setC19fk_idgasto(0);
        ENTccd.setC20fk_idcompra(fk_idcompra);
        ENTccd.setC21fk_idventa(0);
        ENTccd.setC22fk_idusuario(fk_idusuario);
        ENTccd.setC23fk_idrh_vale(0);
        ENTccd.setC24fk_idrh_liquidacion(0);
        ENTccd.setC25monto_solo_adelanto(0);
    }
    private void boton_guardar_compra() {
        if (validar_carga_compra()) {
            cargar_dato_compra();
            cargar_dato_caja_detalle_COMPRA();
            BOcom.insertar_compra(ENTcom,tblitem_producto,ENTccd);
            reestableser_compra();
            suma_item_producto();
        }
    }

    private void boton_eliminar_item() {
        if (tblitem_producto.getSelectedRow() >= 0) {
            eveJtab.getBoolean_Eliminar_Fila(tblitem_producto, model_itemf);
            suma_item_producto();
        } else {
            JOptionPane.showMessageDialog(this, "SELECCIONAR EL ITEM PARA ELIMINAR");
        }
    }

    public FrmCompra() {
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
        jScrol_referencia_categoria = new javax.swing.JScrollPane();
        panel_referencia_categoria = new javax.swing.JPanel();
        panel_referencia_marca = new javax.swing.JPanel();
        panel_referencia_unidad = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_producto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtbuscar_producto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        txtcantidad_producto = new javax.swing.JTextField();
        btnagregar_cantidad = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtcod_barra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btncantidad_1 = new javax.swing.JButton();
        btncantidad_2 = new javax.swing.JButton();
        btncantidad_3 = new javax.swing.JButton();
        btncantidad_4 = new javax.swing.JButton();
        btncantidad_5 = new javax.swing.JButton();
        btncantidad_6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txtproveedor_nombre = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jFtotal_consumo = new javax.swing.JFormattedTextField();
        btncargar_consumo = new javax.swing.JButton();
        txtmonto_letra = new javax.swing.JTextField();
        btneliminar = new javax.swing.JButton();
        txtobservacion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtidcompra = new javax.swing.JTextField();
        txtnro_factura = new javax.swing.JTextField();

        setClosable(true);
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

        panel_referencia_categoria.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_categoria.setLayout(new java.awt.GridLayout(0, 1));
        jScrol_referencia_categoria.setViewportView(panel_referencia_categoria);

        panel_referencia_marca.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_marca.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_marca.setLayout(new java.awt.GridLayout(1, 0));

        panel_referencia_unidad.setBackground(new java.awt.Color(102, 153, 255));
        panel_referencia_unidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_referencia_unidad.setLayout(new java.awt.GridLayout(1, 0));

        panel_insertar_pri_producto.setBackground(new java.awt.Color(153, 204, 255));
        panel_insertar_pri_producto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("BUSCAR PRODUCTO:");

        txtbuscar_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        tblproducto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblproducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblproducto.setRowHeight(25);
        tblproducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblproductoMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblproducto);

        txtcantidad_producto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtcantidad_producto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcantidad_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcantidad_productoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcantidad_productoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcantidad_productoKeyTyped(evt);
            }
        });

        btnagregar_cantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Cantidad/flecha_derecha.png"))); // NOI18N
        btnagregar_cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregar_cantidadActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("COD.BARRA:");

        txtcod_barra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtcod_barra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyReleased(evt);
            }
        });

        jLabel2.setText("CANTIDAD");

        btncantidad_1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_1.setText("1");
        btncantidad_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_1ActionPerformed(evt);
            }
        });

        btncantidad_2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_2.setText("2");
        btncantidad_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_2ActionPerformed(evt);
            }
        });

        btncantidad_3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_3.setText("3");
        btncantidad_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_3ActionPerformed(evt);
            }
        });

        btncantidad_4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_4.setText("4");
        btncantidad_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_4ActionPerformed(evt);
            }
        });

        btncantidad_5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_5.setText("5");
        btncantidad_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_5ActionPerformed(evt);
            }
        });

        btncantidad_6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btncantidad_6.setText("6");
        btncantidad_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncantidad_6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insertar_pri_productoLayout = new javax.swing.GroupLayout(panel_insertar_pri_producto);
        panel_insertar_pri_producto.setLayout(panel_insertar_pri_productoLayout);
        panel_insertar_pri_productoLayout.setHorizontalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar_producto))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(71, 71, 71)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btncantidad_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtcantidad_producto, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                        .addComponent(btncantidad_2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncantidad_3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncantidad_4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncantidad_5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncantidad_6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        panel_insertar_pri_productoLayout.setVerticalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel29)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtbuscar_producto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(txtcod_barra, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(txtcantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(btncantidad_1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncantidad_6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnagregar_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTab_producto_ingrediente.addTab("PRODUCTOS", panel_insertar_pri_producto);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM COMPRA"));

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
        jScrollPane2.setViewportView(tblitem_producto);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("PROVEEDOR"));

        txtproveedor_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE"));

        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder("RUC"));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtproveedor_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtproveedor_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jFtotal_consumo.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL"));
        jFtotal_consumo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_consumo.setText("0");
        jFtotal_consumo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        btncargar_consumo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/motel/32_guardar.png"))); // NOI18N
        btncargar_consumo.setText("GUARDAR");
        btncargar_consumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargar_consumoActionPerformed(evt);
            }
        });

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/eliminar.png"))); // NOI18N
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btneliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFtotal_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btncargar_consumo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btncargar_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_consumo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        jLabel3.setText("OBS:");

        txtidcompra.setEditable(false);
        txtidcompra.setBackground(new java.awt.Color(0, 0, 255));
        txtidcompra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtidcompra.setForeground(new java.awt.Color(255, 255, 51));
        txtidcompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtidcompra.setBorder(javax.swing.BorderFactory.createTitledBorder("IDCOMPRA"));

        txtnro_factura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtnro_factura.setBorder(javax.swing.BorderFactory.createTitledBorder("NRO FACTURA"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtidcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnro_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(46, 46, 46))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrol_referencia_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panel_referencia_marca, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
                        .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(42, 42, 42)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtidcompra)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtnro_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrol_referencia_categoria)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panel_referencia_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 526, Short.MAX_VALUE)))
                    .addContainerGap()))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1266, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 612, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("CREAR COMPRA", jPanel1);

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

    private void txtbuscar_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscar_productoKeyReleased
        // TODO add your handling code here:
        actualizar_tabla_producto(3);
    }//GEN-LAST:event_txtbuscar_productoKeyReleased

    private void tblproductoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductoMouseReleased
        // TODO add your handling code here:
        seleccionar_producto();
    }//GEN-LAST:event_tblproductoMouseReleased

    private void txtcantidad_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_productoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cargar_item_producto();
        }
    }//GEN-LAST:event_txtcantidad_productoKeyPressed

    private void txtcantidad_productoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_productoKeyReleased
        // TODO add your handling code here:
        //        calcular_subtotal_itemventa();
        //        calculo_cantidad(evt);
        evejtf.setsuma_cantidad_flecha(evt, txtcantidad_producto);
    }//GEN-LAST:event_txtcantidad_productoKeyReleased

    private void txtcantidad_productoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcantidad_productoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtcantidad_productoKeyTyped

    private void btnagregar_cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregar_cantidadActionPerformed
        // TODO add your handling code here:
        //        cargar_item_producto();
        cargar_item_producto();
    }//GEN-LAST:event_btnagregar_cantidadActionPerformed

    private void txtcod_barraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //            buscar_cod_barra_producto();
        }
    }//GEN-LAST:event_txtcod_barraKeyPressed

    private void txtcod_barraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyReleased
        // TODO add your handling code here:
        //        actualizar_tabla_producto(5);
    }//GEN-LAST:event_txtcod_barraKeyReleased

    private void btncantidad_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_1ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(1);
    }//GEN-LAST:event_btncantidad_1ActionPerformed

    private void btncantidad_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_2ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(2);
    }//GEN-LAST:event_btncantidad_2ActionPerformed

    private void btncantidad_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_3ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(3);
    }//GEN-LAST:event_btncantidad_3ActionPerformed

    private void btncantidad_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_4ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(4);
    }//GEN-LAST:event_btncantidad_4ActionPerformed

    private void btncantidad_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_5ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(5);
    }//GEN-LAST:event_btncantidad_5ActionPerformed

    private void btncantidad_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncantidad_6ActionPerformed
        // TODO add your handling code here:
        cargar_con_cantidad(6);
    }//GEN-LAST:event_btncantidad_6ActionPerformed

    private void btncargar_consumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargar_consumoActionPerformed
        // TODO add your handling code here:
        boton_guardar_compra();
    }//GEN-LAST:event_btncargar_consumoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_tabla_producto();
        ancho_item_producto();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item();
    }//GEN-LAST:event_btneliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnagregar_cantidad;
    private javax.swing.JButton btncantidad_1;
    private javax.swing.JButton btncantidad_2;
    private javax.swing.JButton btncantidad_3;
    private javax.swing.JButton btncantidad_4;
    private javax.swing.JButton btncantidad_5;
    private javax.swing.JButton btncantidad_6;
    private javax.swing.JButton btncargar_consumo;
    private javax.swing.JButton btneliminar;
    private javax.swing.JFormattedTextField jFtotal_consumo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrol_referencia_categoria;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel panel_insertar_pri_producto;
    private javax.swing.JPanel panel_referencia_categoria;
    private javax.swing.JPanel panel_referencia_marca;
    private javax.swing.JPanel panel_referencia_unidad;
    private javax.swing.JTable tblitem_producto;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtcantidad_producto;
    private javax.swing.JTextField txtcod_barra;
    private javax.swing.JTextField txtidcompra;
    private javax.swing.JTextField txtmonto_letra;
    private javax.swing.JTextField txtnro_factura;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtproveedor_nombre;
    // End of variables declaration//GEN-END:variables
}
