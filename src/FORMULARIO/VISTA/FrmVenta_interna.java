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
import Evento.Jtable.EvenRender;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Utilitario.EvenNumero_a_Letra;
import FORMULARIO.BO.BO_compra;
import FORMULARIO.BO.BO_compra_item;
import FORMULARIO.BO.BO_venta_interno;
import FORMULARIO.BO.BO_venta_item_interno;
import FORMULARIO.DAO.DAO_caja_cierre_detalle;
import FORMULARIO.DAO.DAO_compra;
import FORMULARIO.DAO.DAO_compra_item;
import FORMULARIO.DAO.DAO_persona;
import FORMULARIO.DAO.DAO_producto;
import FORMULARIO.DAO.DAO_usuario;
import FORMULARIO.DAO.DAO_venta_interno;
import FORMULARIO.DAO.DAO_venta_item_interno;
import FORMULARIO.ENTIDAD.caja_cierre_detalle;
import FORMULARIO.ENTIDAD.compra;
import FORMULARIO.ENTIDAD.compra_item;
import FORMULARIO.ENTIDAD.persona;
import FORMULARIO.ENTIDAD.producto;
import FORMULARIO.ENTIDAD.usuario;
import FORMULARIO.ENTIDAD.venta_interno;
import FORMULARIO.ENTIDAD.venta_item_interno;
import FORMULARIO.VISTA.BUSCAR.ClaVarBuscar;
import IMPRESORA_POS.PosImprimir_Compra;
import IMPRESORA_POS.PosImprimir_Venta_interno;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Digno
 */
public class FrmVenta_interna extends javax.swing.JInternalFrame {

    private EvenJButton evebtn = new EvenJButton();
    private EvenJFRAME evetbl = new EvenJFRAME();
    private EvenJtable eveJtab = new EvenJtable();
    private EvenFecha evefec = new EvenFecha();
    private ClaVarBuscar vbus = new ClaVarBuscar();
    private EvenJTextField evejtf = new EvenJTextField();
    private EvenEstado eveest = new EvenEstado();
//    private EvenRender render=new EvenRender();
    private EvenCombobox evecmb = new EvenCombobox();
    private EvenConexion eveconn = new EvenConexion();
    private EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    private ComputerInfo pcinfo = new ComputerInfo();
    private EvenNumero_a_Letra nroletra = new EvenNumero_a_Letra();
    usuario ENTusu = new usuario(); //creado_por = ENTusu.getGlobal_nombre();
    Connection conn = ConnPostgres.getConnPosgres();
    private producto ENTp = new producto();
    private DAO_producto DAOp = new DAO_producto();
    private venta_interno ENTveni = new venta_interno();
    private DAO_venta_interno DAOveni = new DAO_venta_interno();
    private BO_venta_interno BOveni = new BO_venta_interno();
    private venta_item_interno ENTvenii = new venta_item_interno();
    private DAO_venta_item_interno DAOvenii = new DAO_venta_item_interno();
    private BO_venta_item_interno BOvenii = new BO_venta_item_interno();
    private caja_cierre_detalle ENTccd = new caja_cierre_detalle();
    private DAO_caja_cierre_detalle DAOccd = new DAO_caja_cierre_detalle();
    private DAO_usuario DAOusu = new DAO_usuario();
    private persona ENTper = new persona();
    private DAO_persona DAOper = new DAO_persona();
    private PosImprimir_Venta_interno posveni = new PosImprimir_Venta_interno();
    private EvenRender everen = new EvenRender();
//    private PosImprimir_Compra poscom=new PosImprimir_Compra();
//    private EvenEstado eveest = new EvenEstado();
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
    private String nombreTabla_pri = "venta_interno PRINCIPAL";
    private String nombreTabla_sec = "FILTRO";
    private String creado_por = "digno";
    private int idproducto;
    private int fk_idventa_interno;
    private int fk_idusuario;
    private int fk_idpersona;
    private String precio_venta_mostrar;
    private String precio_venta_oculto;
    private String precio_compra_oculto;
    private String iva_producto;
    private double monto_interno;
    private String monto_letra;
//    private double monto_iva5;
//    private double monto_iva10;
    private boolean carga_prod_codbarra = false;
    private boolean modifico_precio_compra;

    private void abrir_formulario() {
        cargar_usuario_acceso();
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        this.setTitle(nombreTabla_pri + " USUARIO:" + creado_por + " IP:" + pcinfo.getStringMiIP());
        evetbl.centrar_formulario_internalframa(this);
        botones_categoria = new ArrayList<>();
        botones_unidad = new ArrayList<>();
        botones_marca = new ArrayList<>();
        evefec.cargar_combobox_intervalo_fecha(cmbfecha_caja_cierre);
        DAOusu.cargar_usuario_combo(conn, cmbusuario);
        color_panel_venta(3);
        cargar_boton_categoria();
        crear_item_producto();
        reestableser_venta_interno();
    }

    private void color_panel_venta(int tipo) {
        if (tipo == 1) {
            panel_crear_venta.setBackground(new Color(245, 199, 169));
            panel_item_venta.setBackground(new Color(245, 199, 169));
        }
        if (tipo == 2) {
            panel_crear_venta.setBackground(new Color(209, 81, 45));
            panel_item_venta.setBackground(new Color(209, 81, 45));
        }
        if (tipo == 3) {
            panel_crear_venta.setBackground(new Color(0, 110, 127));
            panel_item_venta.setBackground(new Color(0, 110, 127));
            panel_filtro_venta.setBackground(new Color(0, 110, 127));
        }
    }

    private void reestableser_venta_interno() {
        cargar_usuario_acceso();
        actualizar_tabla_venta_interno();
        fk_idventa_interno = (eveconn.getInt_ultimoID_mas_uno(conn, ENTveni.getTb_venta_interno(), ENTveni.getId_idventa_interno()));
        txtidcompra.setText(String.valueOf(fk_idventa_interno));
        eveJtab.limpiar_tabla_datos(model_itemf);
        txtobservacion.setText("Ninguna");
        monto_letra = "cero";
        txtmonto_letra.setText(monto_letra);
        monto_interno = 0;
        fk_idpersona = 1;
        ENTper.setBus_idpersona(fk_idpersona);
        cargar_proveedor_ocacional();
        txtcod_barra.grabFocus();
    }

    private void cargar_proveedor_ocacional() {
        DAOper.cargar_persona(conn, ENTper, fk_idpersona);
        txtproveedor_nombre.setText(ENTper.getC4nombre());
        txtproveedor_ruc.setText(ENTper.getC5ruc());
    }

    private void cargar_usuario_acceso() {
        if (fk_idusuario != ENTusu.getGlobal_idusuario()) {
            usuario usu = new usuario();
            creado_por = usu.getGlobal_nombre();
            fk_idusuario = usu.getGlobal_idusuario();
            this.setTitle(nombreTabla_pri + " USUARIO:" + creado_por);
        }
    }

    private void precargar_producto(int idproducto) {
        DAOp.cargar_producto(conn, ENTp, idproducto);
        txtcod_barra.setText(ENTp.getC4codigo_barra());
        txtbuscar_producto.setText(ENTp.getC5nombre());
        precio_venta_mostrar = evejtf.getString_format_nro_decimal((int) ENTp.getC20precio_interno());
        precio_venta_oculto = String.valueOf((int) ENTp.getC20precio_interno());
        precio_compra_oculto = String.valueOf((int) ENTp.getC12precio_compra());
        iva_producto = String.valueOf((int) ENTp.getC16iva());
        txtprecio_interno.setText(precio_venta_mostrar);
        txtprecio_interno.setBackground(Color.white);
        txtprecio_interno.setForeground(Color.black);
        txtcantidad_producto.setText("1");
        txtcantidad_producto.grabFocus();
    }

    private void seleccionar_producto() {
        if (tblproducto.getSelectedRow() >= 0) {
            idproducto = eveJtab.getInt_select_id(tblproducto);
            precargar_producto(idproducto);
            carga_prod_codbarra = false;
        }
    }

    private void cargar_boton_categoria() {
        String titulo = "cargar_boton_categoria";
        String sql = "SELECT  c.idproducto_categoria, c.nombre,c.orden \n"
                + "From producto_categoria c,producto p \n"
                + "where c.idproducto_categoria=p.fk_idproducto_categoria \n"
                + "and c.activo=true \n"
                + "and p.es_venta=true \n"
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
                + "TRIM(to_char(p.precio_interno,'999G999G999')) as pventa,"
                + "p.precio_interno as oculto_venta,p.precio_compra as oculto_compra,p.iva as iva \n"
                + "from producto p,producto_categoria c,producto_unidad u,producto_marca pm \n"
                + "where p.fk_idproducto_categoria=c.idproducto_categoria \n"
                + "and p.fk_idproducto_unidad=u.idproducto_unidad \n"
                + "and p.fk_idproducto_marca=pm.idproducto_marca \n"
                + "and c.activo=true \n"
                + "and p.es_venta=true \n" + filtro_categoria + filtro_unidad + filtro_producto + filtro_marca
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
        if (!carga_prod_codbarra) {
            if (eveJtab.getBoolean_validar_select(tblproducto)) {
                return false;
            }
        }
        if (evejtf.getBoo_JTextField_vacio(txtprecio_interno, "DEBE CARGAR UN PRECIO venta_interno")) {
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

    private void cargar_item_producto(boolean cargar_auto) {
        boolean validar = false;
        if (cargar_auto) {
            validar = true;
        } else {
            validar = validar_cargar_item();
        }
        if (validar) {
            String Sidproducto = String.valueOf(idproducto);
            String Sdescripcion = txtbuscar_producto.getText();
            String Sprecio_interno = precio_venta_mostrar;
            String Scantidad = txtcantidad_producto.getText();
            String Sprecio_interno2 = precio_venta_oculto;
            String Sprecio_compra = precio_compra_oculto;
            String Ssubtotal = "0";
            String Ssubtotal2 = "0";
            String Stipo = eveest.getEst_Emitido();
            String Siva = iva_producto;
            if (modifico_precio_compra) {
                Double Dprecio_interno = evejtf.getDouble_format_nro_entero(txtprecio_interno);
                ENTp.setC1idproducto(idproducto);
                ENTp.setC20precio_interno(Dprecio_interno);
//                DAOp.update_producto_precio_compra(conn, ENTp);
            }
            String dato[] = {Sidproducto, Sdescripcion, Sprecio_interno, Scantidad, Ssubtotal, Sprecio_interno2, Sprecio_compra, Stipo, Ssubtotal2, Siva};
            eveJtab.cargar_tabla_datos(tblitem_producto, model_itemf, dato);
            eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 3, 5, 4, true);
            eveJtab.calcular_subtotal(tblitem_producto, model_itemf, 3, 5, 8, false);
            reestableser_item_venta_interno();
            suma_item_producto();
        }
    }

    private void suma_item_producto() {
        monto_interno = eveJtab.getDouble_sumar_tabla(tblitem_producto, 8);
        jFtotal_consumo.setValue(monto_interno);
        String Smonto_total = String.valueOf(monto_interno);
        monto_letra = nroletra.Convertir(Smonto_total, true);
        txtmonto_letra.setText(monto_letra);
    }

    private void reestableser_item_venta_interno() {
        txtcod_barra.setText(null);
        txtbuscar_producto.setText(null);
        precio_venta_mostrar = "0";
        precio_venta_oculto = "0";
        precio_compra_oculto = "0";
        iva_producto = "0";
        txtcantidad_producto.setText("1");
        carga_prod_codbarra = false;
        modifico_precio_compra = false;
        actualizar_tabla_producto(1);
        txtprecio_interno.setText(null);
        txtprecio_interno.setBackground(Color.white);
        txtprecio_interno.setForeground(Color.black);
        txtbuscar_producto.grabFocus();
    }

    private void cargar_con_cantidad(int cant) {
        txtcantidad_producto.setText(String.valueOf(cant));
        cargar_item_producto(false);
    }

    private boolean validar_carga_venta_interno() {
        if (tblitem_producto.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "CARGAR POR LO MENOS UN ITEM");
            return false;
        }
//        if (evejtf.getBoo_JTextField_vacio(txtnro_factura, "CARGAR UN NUMERO DE FACTURA")) {
//            return false;
//        }
        if (ENTper.getBus_idpersona() == 0) {
            JOptionPane.showMessageDialog(null, "EL PROVEEDOR NO SE CARGO CORRECTAMENTE");
            return false;
        } else {
            fk_idpersona = ENTper.getBus_idpersona();
        }
        return true;
    }

    private void buscar_producto_cod_barra() {
        if (txtcod_barra.getText().trim().length() > 0) {
            String cod_barra = txtcod_barra.getText();
            String titulo = "buscar_producto_cod_barra";
            String sql = "select p.idproducto  "
                    + "from producto p "
                    + "where p.codigo_barra='" + cod_barra + "' ";
            try {
                ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
                int cant = 0;
                idproducto = 0;
                while (rs.next()) {
                    cant++;
                    idproducto = rs.getInt("idproducto");
                }
                if (cant == 1) {
                    precargar_producto(idproducto);
                    carga_prod_codbarra = true;
                }
                if (cant == 0) {
                    JOptionPane.showMessageDialog(this, "NO SE ENCONTRO NINGUN PRODUCTO", "ERROR DE BUSCAR", JOptionPane.ERROR_MESSAGE);
                    txtcod_barra.setText(null);
                    txtcod_barra.grabFocus();
                }
                if (cant > 1) {
                    JOptionPane.showMessageDialog(this, "SE ENCONTRO MAS DE UN PRODUCTO CON EL MISMO CODIGO DE BARRA"
                            + "\nSE DEBE MANTENER UN SOLO CODIGO DE BARRA PARA UN PRODUCTO", "ERROR DE BUSCAR", JOptionPane.ERROR_MESSAGE);
                    txtcod_barra.setText(null);
                    txtcod_barra.grabFocus();
                }
            } catch (Exception e) {
                evemen.mensaje_error(e, sql, titulo);
            }
        }
    }

    private void cargar_dato_venta_interno() {
        ENTveni.setC3creado_por(creado_por);
        ENTveni.setC4monto_letra(monto_letra);
        ENTveni.setC5estado(eveest.getEst_Emitido());
        ENTveni.setC6observacion(txtobservacion.getText());
        ENTveni.setC7motivo_anulacion("sin");
        ENTveni.setC8monto_interno(monto_interno);
        ENTveni.setC9fk_idusuario(fk_idusuario);
        ENTveni.setC10fk_idpersona(fk_idpersona);
//        ENTveni.setC4fecha_compra("now()");
//        ENTveni.setC5nro_factura(txtnro_factura.getText());
//        ENTveni.setC6es_factura(false);
//        ENTveni.setC7monto_total(monto_total);
//        ENTveni.setC8monto_iva5(monto_iva5);
//        ENTveni.setC9monto_iva10(monto_iva10);
//        ENTveni.setC10monto_letra(monto_letra);
//        ENTveni.setC11observacion(txtobservacion.getText());
//        ENTveni.setC12estado(eveest.getEst_PENDIENTE());
//        ENTveni.setC13fk_idpersona(fk_idpersona);
//        ENTveni.setC14fk_idusuario(fk_idusuario);
    }

    private void cargar_dato_caja_detalle_venta_interno() {
        creado_por = ENTusu.getGlobal_nombre();
        fk_idusuario = ENTusu.getGlobal_idusuario();
        String descripcion = fk_idventa_interno + "-(" + eveest.getCaja_VEN_INTERNA() + ")-"
                + "Cli:(" + txtproveedor_nombre.getText() + ")";
        ENTccd.setC3creado_por(creado_por);
        ENTccd.setC4cerrado_por(eveest.getCaja_VEN_INTERNA());
        ENTccd.setC5es_cerrado(false);
        ENTccd.setC6monto_apertura_caja(0);
        ENTccd.setC7monto_cierre_caja(0);
        ENTccd.setC8monto_ocupa_minimo(0);
        ENTccd.setC9monto_ocupa_adicional(0);
        ENTccd.setC10monto_ocupa_consumo(0);
        ENTccd.setC11monto_ocupa_descuento(0);
        ENTccd.setC12monto_ocupa_adelanto(0);
        ENTccd.setC13monto_gasto(0);
        ENTccd.setC14monto_compra(0);
        ENTccd.setC15monto_vale(0);
        ENTccd.setC16monto_liquidacion(0);
        ENTccd.setC17estado(eveest.getEst_Emitido());
        ENTccd.setC18descripcion(descripcion);
        ENTccd.setC19fk_idgasto(0);
        ENTccd.setC20fk_idcompra(0);
        ENTccd.setC21fk_idventa(0);
        ENTccd.setC22fk_idusuario(fk_idusuario);
        ENTccd.setC23fk_idrh_vale(0);
        ENTccd.setC24fk_idrh_liquidacion(0);
        ENTccd.setC25monto_solo_adelanto(0);
        ENTccd.setC26monto_interno(monto_interno);
        ENTccd.setC27fk_idventa_interno(fk_idventa_interno);
        ENTccd.setC28monto_garantia(0);
        ENTccd.setC29fk_idgarantia(0);
    }

    private void boton_guardar_venta_interno() {
        if (validar_carga_venta_interno()) {
            cargar_dato_venta_interno();
            cargar_dato_caja_detalle_venta_interno();
            BOveni.insertar_venta_interno1(ENTveni, ENTccd, tblitem_producto);
            posveni.boton_imprimir_pos_venta_interno(conn, fk_idventa_interno);
//            poscom.boton_imprimir_pos_compra(conn, fk_idventa_interno);
            reestableser_venta_interno();
            suma_item_producto();
        }
    }

    private void boton_anular_venta_interno() {
        if (eveJtab.getBoolean_validar_select_mensaje(tblventa_interna, "DEBE SELECCIONAR UNA venta_interno PARA ANULAR")) {
            if (evemen.getBooMensaje_warning("ESTAS SEGURO DE ANULAR ESTA venta_interno\n"
                    + "ESTO REPONE EL STOCK",
                    "ANULAR venta_interno", "ANULAR", "CANCELAR")) {
                int idventa_interno = eveJtab.getInt_select_id(tblventa_interna);
                int idcaja_cierre_detalle = DAOccd.getInt_idcaja_cierre_detalle_por_otro_id(conn, "fk_idventa_interno", idventa_interno);
                DAOccd.cargar_caja_cierre_detalle(conn, ENTccd, idcaja_cierre_detalle);
                DAOveni.cargar_venta_interno(conn, ENTveni, idventa_interno);
                ENTveni.setC1idventa_interno(idventa_interno);
                ENTveni.setC5estado(eveest.getEst_Anulado());
                ENTvenii.setC9fk_idventa_interno(idventa_interno);
                ENTvenii.setC4tipo_item(eveest.getEst_Anulado());
                ENTccd.setC1idcaja_cierre_detalle(idcaja_cierre_detalle);
                ENTccd.setC18descripcion(ENTccd.getC18descripcion()+" "+eveest.getEst_Anulado());
                ENTccd.setC17estado(eveest.getEst_Anulado());
                ENTccd.setC26monto_interno(0);
                BOveni.update_venta_interno_anular(ENTveni,ENTvenii,ENTccd);
                actualizar_tabla_venta_interno();
            }
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

    private void boton_buscar_persona() {
        ENTper.setBus_quien_llama(2);
        ENTper.setBus_tipo_persona(eveest.getPer_PROVEEDOR());
        JDiaBuscarPersona frm = new JDiaBuscarPersona(null, true);
        frm.setVisible(true);
    }

    private void seleccionar_venta_interno() {
        if (tblventa_interna.getSelectedRow() >= 0) {
            int idventa_interna = eveJtab.getInt_select_id(tblventa_interna);
            DAOvenii.actualizar_tabla_venta_item_interno_creado(conn, tblitem_venta_interna, idventa_interna);
            String estado = eveJtab.getString_select(tblventa_interna, 4);
            if (estado.equals(eveest.getEst_Anulado())) {
                btncom_anular.setEnabled(false);
            }
            if (estado.equals(eveest.getEst_Terminar())) {
                btncom_anular.setEnabled(false);
            }
            if (estado.equals(eveest.getEst_Emitido())) {
                btncom_anular.setEnabled(true);
            }
        }

    }

    public String filtro_estado(JCheckBox jCest_emitido, JCheckBox jCest_terminado,
            JCheckBox jCest_anulado) {
        String estado = "";
        String sumaestado = "";
        int contestado = 0;
        String condi = "";
        String fin = "";
        if (jCest_emitido.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " vi.estado='" + eveest.getEst_Emitido() + "' ";
            sumaestado = sumaestado + estado;
        }
        if (jCest_terminado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " vi.estado='" + eveest.getEst_Terminar() + "' ";
            sumaestado = sumaestado + estado;
        }

        if (jCest_anulado.isSelected()) {
            contestado++;
            if (contestado == 1) {
                condi = " and(";
                fin = ") ";
            } else {
                condi = " or";
            }
            estado = condi + " vi.estado='" + eveest.getEst_Anulado() + "' ";
            sumaestado = sumaestado + estado;
        }

        return sumaestado + fin;
    }

    private String getfiltro_venta_interno() {
        String filtro_suma = "";
        String filtro_usu = "";
        String filtro_fec = "";
        String filtro_est = "";
        int idusuario = DAOusu.getInt_idusuario_combo(conn, cmbusuario);
        filtro_fec = evefec.getIntervalo_fecha_combobox(cmbfecha_caja_cierre, "vi.fecha_creado");
        filtro_est = filtro_estado(jCest_emitido, jCest_terminado, jCest_anulado);
        if (idusuario > 0) {
            filtro_usu = " and vi.fk_idusuario=" + idusuario;
        }
        filtro_suma = filtro_usu + filtro_fec + filtro_est;
        return filtro_suma;
    }

    private void actualizar_tabla_venta_interno() {
        DAOveni.actualizar_tabla_venta_interno(conn, tblventa_interna, getfiltro_venta_interno());
        everen.rendertabla_estados_venta_interno(tblventa_interna, 4);
        suma_venta_interno_filtro(getfiltro_venta_interno());
    }

    private void suma_venta_interno_filtro(String filtro) {
        String titulo = "suma_venta_interno_filtro";
        String sql = "select \n"
                + "(sum(vi.monto_interno)) as total \n"
                + "from venta_interno vi,persona p \n"
                + "where vi.fk_idpersona=p.idpersona " + filtro;
        try {
            ResultSet rs = eveconn.getResulsetSQL_sinprint(conn, sql, titulo);
            if (rs.next()) {
                double total = rs.getDouble("total");
                jFtotal_compra.setValue(total);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void boton_imprimir_venta_interno() {
        if (eveJtab.getBoolean_validar_select_mensaje(tblventa_interna, "SELECCIONE LA TABLA venta_interno")) {
            int idventa_interno = eveJtab.getInt_select_id(tblventa_interna);
            posveni.boton_imprimir_pos_venta_interno(conn, idventa_interno);
        }
    }

    public FrmVenta_interna() {
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
        panel_crear_venta = new javax.swing.JPanel();
        jScrol_referencia_categoria = new javax.swing.JScrollPane();
        panel_referencia_categoria = new javax.swing.JPanel();
        panel_referencia_marca = new javax.swing.JPanel();
        panel_referencia_unidad = new javax.swing.JPanel();
        jTab_producto_ingrediente = new javax.swing.JTabbedPane();
        panel_insertar_pri_producto = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproducto = new javax.swing.JTable();
        txtcantidad_producto = new javax.swing.JTextField();
        btncantidad_1 = new javax.swing.JButton();
        btncantidad_2 = new javax.swing.JButton();
        btncantidad_3 = new javax.swing.JButton();
        btncantidad_4 = new javax.swing.JButton();
        btncantidad_5 = new javax.swing.JButton();
        btncantidad_6 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        txtcod_barra = new javax.swing.JTextField();
        txtbuscar_producto = new javax.swing.JTextField();
        txtprecio_interno = new javax.swing.JTextField();
        panel_item_venta = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblitem_producto = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jFtotal_consumo = new javax.swing.JFormattedTextField();
        btncargar_consumo = new javax.swing.JButton();
        txtmonto_letra = new javax.swing.JTextField();
        btneliminar = new javax.swing.JButton();
        txtobservacion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtidcompra = new javax.swing.JTextField();
        txtproveedor_nombre = new javax.swing.JTextField();
        txtproveedor_ruc = new javax.swing.JTextField();
        btnbuscar_persona = new javax.swing.JButton();
        panel_filtro_venta = new javax.swing.JPanel();
        panel_tabla_venta = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblventa_interna = new javax.swing.JTable();
        btncom_anular = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblitem_venta_interna = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jCest_emitido = new javax.swing.JCheckBox();
        jCest_terminado = new javax.swing.JCheckBox();
        jCest_anulado = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        cmbfecha_caja_cierre = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbusuario = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jFtotal_compra = new javax.swing.JFormattedTextField();

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
        txtcantidad_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("CANT:"));
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

        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtcod_barra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtcod_barra.setBorder(javax.swing.BorderFactory.createTitledBorder("COD BARRA:"));
        txtcod_barra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcod_barraKeyReleased(evt);
            }
        });

        txtbuscar_producto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbuscar_producto.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));
        txtbuscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscar_productoKeyReleased(evt);
            }
        });

        txtprecio_interno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtprecio_interno.setBorder(javax.swing.BorderFactory.createTitledBorder("PRECIO"));
        txtprecio_interno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtprecio_internoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtprecio_internoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtprecio_internoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtprecio_interno))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcod_barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtbuscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtprecio_interno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_insertar_pri_productoLayout = new javax.swing.GroupLayout(panel_insertar_pri_producto);
        panel_insertar_pri_producto.setLayout(panel_insertar_pri_productoLayout);
        panel_insertar_pri_productoLayout.setHorizontalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btncantidad_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtcantidad_producto, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(btncantidad_2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncantidad_3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncantidad_4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncantidad_5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btncantidad_6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_insertar_pri_productoLayout.setVerticalGroup(
            panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insertar_pri_productoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insertar_pri_productoLayout.createSequentialGroup()
                        .addComponent(txtcantidad_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
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
                        .addComponent(btncantidad_6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        jTab_producto_ingrediente.addTab("PRODUCTOS", panel_insertar_pri_producto);

        panel_item_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM VENTA INTERNA"));

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

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 0, 204));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("VENTA INTERNA");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addContainerGap())
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

        javax.swing.GroupLayout panel_item_ventaLayout = new javax.swing.GroupLayout(panel_item_venta);
        panel_item_venta.setLayout(panel_item_ventaLayout);
        panel_item_ventaLayout.setHorizontalGroup(
            panel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_item_ventaLayout.createSequentialGroup()
                .addGroup(panel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(panel_item_ventaLayout.createSequentialGroup()
                        .addGroup(panel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panel_item_ventaLayout.createSequentialGroup()
                                    .addComponent(btneliminar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jFtotal_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_item_ventaLayout.createSequentialGroup()
                                .addComponent(btncargar_consumo)
                                .addGap(343, 343, 343)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_item_ventaLayout.setVerticalGroup(
            panel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_item_ventaLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_item_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btncargar_consumo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtotal_consumo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmonto_letra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel3.setText("OBS:");

        txtidcompra.setEditable(false);
        txtidcompra.setBackground(new java.awt.Color(0, 0, 255));
        txtidcompra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtidcompra.setForeground(new java.awt.Color(255, 255, 51));
        txtidcompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtidcompra.setBorder(javax.swing.BorderFactory.createTitledBorder("IDCOMPRA"));

        txtproveedor_nombre.setBorder(javax.swing.BorderFactory.createTitledBorder("NOMBRE"));

        txtproveedor_ruc.setBorder(javax.swing.BorderFactory.createTitledBorder("RUC"));

        btnbuscar_persona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ABM/mini_lupa.png"))); // NOI18N
        btnbuscar_persona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscar_personaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_crear_ventaLayout = new javax.swing.GroupLayout(panel_crear_venta);
        panel_crear_venta.setLayout(panel_crear_ventaLayout);
        panel_crear_ventaLayout.setHorizontalGroup(
            panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_crear_ventaLayout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addGroup(panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_crear_ventaLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_crear_ventaLayout.createSequentialGroup()
                        .addComponent(txtidcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtproveedor_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtproveedor_ruc, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnbuscar_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_item_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(46, 46, 46))
            .addGroup(panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_crear_ventaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrol_referencia_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panel_referencia_marca, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
                        .addComponent(panel_referencia_unidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(42, 42, 42)))
        );
        panel_crear_ventaLayout.setVerticalGroup(
            panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_crear_ventaLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addGroup(panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_item_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_crear_ventaLayout.createSequentialGroup()
                        .addComponent(jTab_producto_ingrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtobservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtidcompra)
                            .addGroup(panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtproveedor_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtproveedor_ruc))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_crear_ventaLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnbuscar_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel_crear_ventaLayout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addGroup(panel_crear_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrol_referencia_categoria)
                        .addGroup(panel_crear_ventaLayout.createSequentialGroup()
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
                    .addComponent(panel_crear_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 1266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel_crear_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("CREAR VENTA", jPanel1);

        panel_tabla_venta.setBorder(javax.swing.BorderFactory.createTitledBorder("TABLA VENTA"));

        tblventa_interna.setModel(new javax.swing.table.DefaultTableModel(
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
        tblventa_interna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblventa_internaMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblventa_interna);

        javax.swing.GroupLayout panel_tabla_ventaLayout = new javax.swing.GroupLayout(panel_tabla_venta);
        panel_tabla_venta.setLayout(panel_tabla_ventaLayout);
        panel_tabla_ventaLayout.setHorizontalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
        );
        panel_tabla_ventaLayout.setVerticalGroup(
            panel_tabla_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
        );

        btncom_anular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/anular.png"))); // NOI18N
        btncom_anular.setText("ANULAR");
        btncom_anular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btncom_anular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btncom_anular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncom_anularActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/venta/ult_print.png"))); // NOI18N
        jButton2.setText("IMPRIMIR");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("ITEM VENTA INTERNA"));

        tblitem_venta_interna.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblitem_venta_interna);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO VENTA INTERNA"));

        jCest_emitido.setText("EMITIDO");
        jCest_emitido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCest_emitidoActionPerformed(evt);
            }
        });

        jCest_terminado.setText("TERMINADO");
        jCest_terminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCest_terminadoActionPerformed(evt);
            }
        });

        jCest_anulado.setText("ANULADO");
        jCest_anulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCest_anuladoActionPerformed(evt);
            }
        });

        jLabel4.setText("Fecha:");

        cmbfecha_caja_cierre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbfecha_caja_cierre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbfecha_caja_cierreActionPerformed(evt);
            }
        });

        jLabel5.setText("Usuario:");

        cmbusuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbusuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.Alignment.LEADING, 0, 159, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCest_emitido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCest_terminado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCest_anulado)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCest_emitido)
                    .addComponent(jCest_terminado)
                    .addComponent(jCest_anulado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbfecha_caja_cierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL"));

        jFtotal_compra.setBackground(new java.awt.Color(255, 255, 102));
        jFtotal_compra.setBorder(javax.swing.BorderFactory.createTitledBorder("TOTAL VENTA INTERNA"));
        jFtotal_compra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0 Gs"))));
        jFtotal_compra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFtotal_compra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jFtotal_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jFtotal_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_filtro_ventaLayout = new javax.swing.GroupLayout(panel_filtro_venta);
        panel_filtro_venta.setLayout(panel_filtro_ventaLayout);
        panel_filtro_ventaLayout.setHorizontalGroup(
            panel_filtro_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_tabla_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_filtro_ventaLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_filtro_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_filtro_ventaLayout.createSequentialGroup()
                        .addComponent(btncom_anular, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel_filtro_ventaLayout.setVerticalGroup(
            panel_filtro_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_filtro_ventaLayout.createSequentialGroup()
                .addComponent(panel_tabla_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_filtro_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_filtro_ventaLayout.createSequentialGroup()
                        .addGroup(panel_filtro_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_filtro_ventaLayout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addGroup(panel_filtro_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                    .addComponent(btncom_anular, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("FILTRO DE VENTA", panel_filtro_venta);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            cargar_item_producto(false);
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

    private void txtcod_barraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcod_barraKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar_producto_cod_barra();
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
        boton_guardar_venta_interno();
    }//GEN-LAST:event_btncargar_consumoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        ancho_tabla_producto();
        ancho_item_producto();
        DAOveni.ancho_tabla_venta_interno(tblventa_interna);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        // TODO add your handling code here:
        boton_eliminar_item();
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnbuscar_personaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscar_personaActionPerformed
        // TODO add your handling code here:
        boton_buscar_persona();
    }//GEN-LAST:event_btnbuscar_personaActionPerformed

    private void tblventa_internaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblventa_internaMouseReleased
        // TODO add your handling code here:
        seleccionar_venta_interno();
    }//GEN-LAST:event_tblventa_internaMouseReleased

    private void btncom_anularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncom_anularActionPerformed
        // TODO add your handling code here:
        boton_anular_venta_interno();
    }//GEN-LAST:event_btncom_anularActionPerformed

    private void cmbfecha_caja_cierreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbfecha_caja_cierreActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_interno();
    }//GEN-LAST:event_cmbfecha_caja_cierreActionPerformed

    private void cmbusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbusuarioActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_interno();
    }//GEN-LAST:event_cmbusuarioActionPerformed

    private void jCest_emitidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCest_emitidoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_interno();
    }//GEN-LAST:event_jCest_emitidoActionPerformed

    private void jCest_terminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCest_terminadoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_interno();
    }//GEN-LAST:event_jCest_terminadoActionPerformed

    private void jCest_anuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCest_anuladoActionPerformed
        // TODO add your handling code here:
        actualizar_tabla_venta_interno();
    }//GEN-LAST:event_jCest_anuladoActionPerformed

    private void txtprecio_internoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_internoKeyReleased
        // TODO add your handling code here:
        evejtf.getString_format_nro_entero1(txtprecio_interno);
        txtprecio_interno.setBackground(Color.gray);
        txtprecio_interno.setForeground(Color.yellow);
        modifico_precio_compra = true;
    }//GEN-LAST:event_txtprecio_internoKeyReleased

    private void txtprecio_internoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_internoKeyTyped
        // TODO add your handling code here:
        evejtf.soloNumero(evt);
    }//GEN-LAST:event_txtprecio_internoKeyTyped

    private void txtprecio_internoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtprecio_internoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtcantidad_producto.grabFocus();
        }
    }//GEN-LAST:event_txtprecio_internoKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        boton_imprimir_venta_interno();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar_persona;
    private javax.swing.JButton btncantidad_1;
    private javax.swing.JButton btncantidad_2;
    private javax.swing.JButton btncantidad_3;
    private javax.swing.JButton btncantidad_4;
    private javax.swing.JButton btncantidad_5;
    private javax.swing.JButton btncantidad_6;
    private javax.swing.JButton btncargar_consumo;
    private javax.swing.JButton btncom_anular;
    private javax.swing.JButton btneliminar;
    private javax.swing.JComboBox<String> cmbfecha_caja_cierre;
    private javax.swing.JComboBox<String> cmbusuario;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCest_anulado;
    private javax.swing.JCheckBox jCest_emitido;
    private javax.swing.JCheckBox jCest_terminado;
    private javax.swing.JFormattedTextField jFtotal_compra;
    private javax.swing.JFormattedTextField jFtotal_consumo;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrol_referencia_categoria;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTab_producto_ingrediente;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel panel_crear_venta;
    private javax.swing.JPanel panel_filtro_venta;
    private javax.swing.JPanel panel_insertar_pri_producto;
    private javax.swing.JPanel panel_item_venta;
    private javax.swing.JPanel panel_referencia_categoria;
    private javax.swing.JPanel panel_referencia_marca;
    private javax.swing.JPanel panel_referencia_unidad;
    private javax.swing.JPanel panel_tabla_venta;
    private javax.swing.JTable tblitem_producto;
    private javax.swing.JTable tblitem_venta_interna;
    private javax.swing.JTable tblproducto;
    private javax.swing.JTable tblventa_interna;
    private javax.swing.JTextField txtbuscar_producto;
    private javax.swing.JTextField txtcantidad_producto;
    private javax.swing.JTextField txtcod_barra;
    private javax.swing.JTextField txtidcompra;
    private javax.swing.JTextField txtmonto_letra;
    private javax.swing.JTextField txtobservacion;
    private javax.swing.JTextField txtprecio_interno;
    public static javax.swing.JTextField txtproveedor_nombre;
    public static javax.swing.JTextField txtproveedor_ruc;
    // End of variables declaration//GEN-END:variables
}
