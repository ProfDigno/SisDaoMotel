package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.admin_caja_cierre_detalle;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_admin_caja_cierre_detalle {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "ADMIN_CAJA_CIERRE_DETALLE GUARDADO CORRECTAMENTE";
    private String mensaje_update = "ADMIN_CAJA_CIERRE_DETALLE MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO admin_caja_cierre_detalle(idadmin_caja_cierre_detalle,idcaja_cierre_detalle,fecha_creado,creado_por,cerrado_por,es_cerrado,monto_apertura_caja,monto_cierre_caja,monto_ocupa_minimo,monto_ocupa_adicional,monto_ocupa_consumo,monto_ocupa_descuento,monto_ocupa_adelanto,monto_gasto,monto_compra,monto_vale,monto_liquidacion,monto_interno,monto_solo_adelanto,monto_garantia,estado,descripcion,maquina,id_maquina,fk_idgasto,fk_idcompra,fk_idventa,fk_idusuario,fk_idrh_vale,fk_idrh_liquidacion,fk_idventa_interno,fk_idgarantia) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE admin_caja_cierre_detalle SET idcaja_cierre_detalle=?,fecha_creado=?,creado_por=?,cerrado_por=?,es_cerrado=?,"
            + "monto_apertura_caja=?,monto_cierre_caja=?,monto_ocupa_minimo=?,monto_ocupa_adicional=?,"
            + "monto_ocupa_consumo=?,monto_ocupa_descuento=?,monto_ocupa_adelanto=?,monto_gasto=?,"
            + "monto_compra=?,monto_vale=?,monto_liquidacion=?,monto_interno=?,monto_solo_adelanto=?,monto_garantia=?,"
            + "estado=?,descripcion=?,maquina=?,id_maquina=?,"
            + "fk_idgasto=?,fk_idcompra=?,fk_idventa=?,fk_idusuario=?,fk_idrh_vale=?,"
            + "fk_idrh_liquidacion=?,fk_idventa_interno=?,fk_idgarantia=? "
            + "WHERE idadmin_caja_cierre_detalle=?;";
    private String sql_select = "SELECT idadmin_caja_cierre_detalle,idcaja_cierre_detalle,fecha_creado,creado_por,cerrado_por,es_cerrado,monto_apertura_caja,monto_cierre_caja,monto_ocupa_minimo,monto_ocupa_adicional,monto_ocupa_consumo,monto_ocupa_descuento,monto_ocupa_adelanto,monto_gasto,monto_compra,monto_vale,monto_liquidacion,monto_interno,monto_solo_adelanto,monto_garantia,estado,descripcion,maquina,id_maquina,fk_idgasto,fk_idcompra,fk_idventa,fk_idusuario,fk_idrh_vale,fk_idrh_liquidacion,fk_idventa_interno,fk_idgarantia FROM admin_caja_cierre_detalle order by 1 desc;";
    private String sql_cargar = "SELECT idadmin_caja_cierre_detalle,idcaja_cierre_detalle,fecha_creado,creado_por,cerrado_por,es_cerrado,monto_apertura_caja,monto_cierre_caja,monto_ocupa_minimo,monto_ocupa_adicional,monto_ocupa_consumo,monto_ocupa_descuento,monto_ocupa_adelanto,monto_gasto,monto_compra,monto_vale,monto_liquidacion,monto_interno,monto_solo_adelanto,monto_garantia,estado,descripcion,maquina,id_maquina,fk_idgasto,fk_idcompra,fk_idventa,fk_idusuario,fk_idrh_vale,fk_idrh_liquidacion,fk_idventa_interno,fk_idgarantia FROM admin_caja_cierre_detalle WHERE idadmin_caja_cierre_detalle=";

    public void insertar_admin_caja_cierre_detalle(Connection conn, admin_caja_cierre_detalle ENTaccd) {
        ENTaccd.setC1idadmin_caja_cierre_detalle(eveconn.getInt_ultimoID_mas_uno(conn, ENTaccd.getTb_admin_caja_cierre_detalle(), ENTaccd.getId_idadmin_caja_cierre_detalle()));
        String titulo = "insertar_admin_caja_cierre_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, ENTaccd.getC1idadmin_caja_cierre_detalle());
            pst.setInt(2, ENTaccd.getC2idcaja_cierre_detalle());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setString(4, ENTaccd.getC4creado_por());
            pst.setString(5, ENTaccd.getC5cerrado_por());
            pst.setBoolean(6, ENTaccd.getC6es_cerrado());
            pst.setDouble(7, ENTaccd.getC7monto_apertura_caja());
            pst.setDouble(8, ENTaccd.getC8monto_cierre_caja());
            pst.setDouble(9, ENTaccd.getC9monto_ocupa_minimo());
            pst.setDouble(10, ENTaccd.getC10monto_ocupa_adicional());
            pst.setDouble(11, ENTaccd.getC11monto_ocupa_consumo());
            pst.setDouble(12, ENTaccd.getC12monto_ocupa_descuento());
            pst.setDouble(13, ENTaccd.getC13monto_ocupa_adelanto());
            pst.setDouble(14, ENTaccd.getC14monto_gasto());
            pst.setDouble(15, ENTaccd.getC15monto_compra());
            pst.setDouble(16, ENTaccd.getC16monto_vale());
            pst.setDouble(17, ENTaccd.getC17monto_liquidacion());
            pst.setDouble(18, ENTaccd.getC18monto_interno());
            pst.setDouble(19, ENTaccd.getC19monto_solo_adelanto());
            pst.setDouble(20, ENTaccd.getC20monto_garantia());
            pst.setString(21, ENTaccd.getC21estado());
            pst.setString(22, ENTaccd.getC22descripcion());
            pst.setString(23, ENTaccd.getC23maquina());
            pst.setString(24, ENTaccd.getC24id_maquina());
            pst.setInt(25, ENTaccd.getC25fk_idgasto());
            pst.setInt(26, ENTaccd.getC26fk_idcompra());
            pst.setInt(27, ENTaccd.getC27fk_idventa());
            pst.setInt(28, ENTaccd.getC28fk_idusuario());
            pst.setInt(29, ENTaccd.getC29fk_idrh_vale());
            pst.setInt(30, ENTaccd.getC30fk_idrh_liquidacion());
            pst.setInt(31, ENTaccd.getC31fk_idventa_interno());
            pst.setInt(32, ENTaccd.getC32fk_idgarantia());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + ENTaccd.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + ENTaccd.toString(), titulo);
        }
    }

    public void update_admin_caja_cierre_detalle(Connection conn, admin_caja_cierre_detalle ENTaccd) {
        String titulo = "update_admin_caja_cierre_detalle";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setInt(1, ENTaccd.getC2idcaja_cierre_detalle());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, ENTaccd.getC4creado_por());
            pst.setString(4, ENTaccd.getC5cerrado_por());
            pst.setBoolean(5, ENTaccd.getC6es_cerrado());
            pst.setDouble(6, ENTaccd.getC7monto_apertura_caja());
            pst.setDouble(7, ENTaccd.getC8monto_cierre_caja());
            pst.setDouble(8, ENTaccd.getC9monto_ocupa_minimo());
            pst.setDouble(9, ENTaccd.getC10monto_ocupa_adicional());
            pst.setDouble(10, ENTaccd.getC11monto_ocupa_consumo());
            pst.setDouble(11, ENTaccd.getC12monto_ocupa_descuento());
            pst.setDouble(12, ENTaccd.getC13monto_ocupa_adelanto());
            pst.setDouble(13, ENTaccd.getC14monto_gasto());
            pst.setDouble(14, ENTaccd.getC15monto_compra());
            pst.setDouble(15, ENTaccd.getC16monto_vale());
            pst.setDouble(16, ENTaccd.getC17monto_liquidacion());
            pst.setDouble(17, ENTaccd.getC18monto_interno());
            pst.setDouble(18, ENTaccd.getC19monto_solo_adelanto());
            pst.setDouble(19, ENTaccd.getC20monto_garantia());
            pst.setString(20, ENTaccd.getC21estado());
            pst.setString(21, ENTaccd.getC22descripcion());
            pst.setString(22, ENTaccd.getC23maquina());
            pst.setString(23, ENTaccd.getC24id_maquina());
            pst.setInt(24, ENTaccd.getC25fk_idgasto());
            pst.setInt(25, ENTaccd.getC26fk_idcompra());
            pst.setInt(26, ENTaccd.getC27fk_idventa());
            pst.setInt(27, ENTaccd.getC28fk_idusuario());
            pst.setInt(28, ENTaccd.getC29fk_idrh_vale());
            pst.setInt(29, ENTaccd.getC30fk_idrh_liquidacion());
            pst.setInt(30, ENTaccd.getC31fk_idventa_interno());
            pst.setInt(31, ENTaccd.getC32fk_idgarantia());
            pst.setInt(32, ENTaccd.getC1idadmin_caja_cierre_detalle());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + ENTaccd.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + ENTaccd.toString(), titulo);
        }
    }

    public void cargar_admin_caja_cierre_detalle(Connection conn, admin_caja_cierre_detalle ENTaccd, int idadmin_caja_cierre_detalle) {
        String titulo = "Cargar_admin_caja_cierre_detalle";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idadmin_caja_cierre_detalle, titulo);
            if (rs.next()) {
                ENTaccd.setC1idadmin_caja_cierre_detalle(rs.getInt(1));
                ENTaccd.setC2idcaja_cierre_detalle(rs.getInt(2));
                ENTaccd.setC3fecha_creado(rs.getString(3));
                ENTaccd.setC4creado_por(rs.getString(4));
                ENTaccd.setC5cerrado_por(rs.getString(5));
                ENTaccd.setC6es_cerrado(rs.getBoolean(6));
                ENTaccd.setC7monto_apertura_caja(rs.getDouble(7));
                ENTaccd.setC8monto_cierre_caja(rs.getDouble(8));
                ENTaccd.setC9monto_ocupa_minimo(rs.getDouble(9));
                ENTaccd.setC10monto_ocupa_adicional(rs.getDouble(10));
                ENTaccd.setC11monto_ocupa_consumo(rs.getDouble(11));
                ENTaccd.setC12monto_ocupa_descuento(rs.getDouble(12));
                ENTaccd.setC13monto_ocupa_adelanto(rs.getDouble(13));
                ENTaccd.setC14monto_gasto(rs.getDouble(14));
                ENTaccd.setC15monto_compra(rs.getDouble(15));
                ENTaccd.setC16monto_vale(rs.getDouble(16));
                ENTaccd.setC17monto_liquidacion(rs.getDouble(17));
                ENTaccd.setC18monto_interno(rs.getDouble(18));
                ENTaccd.setC19monto_solo_adelanto(rs.getDouble(19));
                ENTaccd.setC20monto_garantia(rs.getDouble(20));
                ENTaccd.setC21estado(rs.getString(21));
                ENTaccd.setC22descripcion(rs.getString(22));
                ENTaccd.setC23maquina(rs.getString(23));
                ENTaccd.setC24id_maquina(rs.getString(24));
                ENTaccd.setC25fk_idgasto(rs.getInt(25));
                ENTaccd.setC26fk_idcompra(rs.getInt(26));
                ENTaccd.setC27fk_idventa(rs.getInt(27));
                ENTaccd.setC28fk_idusuario(rs.getInt(28));
                ENTaccd.setC29fk_idrh_vale(rs.getInt(29));
                ENTaccd.setC30fk_idrh_liquidacion(rs.getInt(30));
                ENTaccd.setC31fk_idventa_interno(rs.getInt(31));
                ENTaccd.setC32fk_idgarantia(rs.getInt(32));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + ENTaccd.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + ENTaccd.toString(), titulo);
        }
    }

    public void actualizar_tabla_admin_caja_cierre_detalle(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_admin_caja_cierre_detalle(tbltabla);
    }

    public void ancho_tabla_admin_caja_cierre_detalle(JTable tbltabla) {
        int Ancho[] = {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
