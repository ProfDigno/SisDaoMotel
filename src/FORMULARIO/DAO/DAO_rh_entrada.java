package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.rh_entrada;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_rh_entrada {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "RH_ENTRADA GUARDADO CORRECTAMENTE";
    private String mensaje_update = "RH_ENTRADA MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO rh_entrada(idrh_entrada,fecha_creado,creado_por,fecha_entrada,fecha_salida,turno,es_cerrado,es_entrada,es_salida,fk_idpersona,fk_idusuario,fk_idrh_turno) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE rh_entrada SET fecha_creado=?,creado_por=?,fecha_entrada=?,fecha_salida=?,turno=?,es_cerrado=?,es_entrada=?,es_salida=?,fk_idpersona=?,fk_idusuario=?,fk_idrh_turno=? WHERE idrh_entrada=?;";
    private String sql_select = "SELECT idrh_entrada,fecha_creado,creado_por,fecha_entrada,fecha_salida,turno,es_cerrado,es_entrada,es_salida,fk_idpersona,fk_idusuario,fk_idrh_turno FROM rh_entrada order by 1 desc;";
    private String sql_cargar = "SELECT idrh_entrada,fecha_creado,creado_por,fecha_entrada,fecha_salida,turno,es_cerrado,es_entrada,es_salida,fk_idpersona,fk_idusuario,fk_idrh_turno FROM rh_entrada WHERE idrh_entrada=";

    public void insertar_rh_entrada(Connection conn, rh_entrada re) {
        re.setC1idrh_entrada(eveconn.getInt_ultimoID_mas_uno(conn, re.getTb_rh_entrada(), re.getId_idrh_entrada()));
        String titulo = "insertar_rh_entrada";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, re.getC1idrh_entrada());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, re.getC3creado_por());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setTimestamp(5, evefec.getTimestamp_sistema());
            pst.setString(6, re.getC6turno());
            pst.setBoolean(7, re.getC7es_cerrado());
            pst.setBoolean(8, re.getC8es_entrada());
            pst.setBoolean(9, re.getC9es_salida());
            pst.setInt(10, re.getC10fk_idpersona());
            pst.setInt(11, re.getC11fk_idusuario());
            pst.setInt(12, re.getC12fk_idrh_turno());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + re.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + re.toString(), titulo);
        }
    }

    public void update_rh_entrada(Connection conn, rh_entrada re) {
        String titulo = "update_rh_entrada";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, re.getC3creado_por());
            pst.setTimestamp(3, evefec.getTimestamp_sistema());
            pst.setTimestamp(4, evefec.getTimestamp_sistema());
            pst.setString(5, re.getC6turno());
            pst.setBoolean(6, re.getC7es_cerrado());
            pst.setBoolean(7, re.getC8es_entrada());
            pst.setBoolean(8, re.getC9es_salida());
            pst.setInt(9, re.getC10fk_idpersona());
            pst.setInt(10, re.getC11fk_idusuario());
            pst.setInt(11, re.getC12fk_idrh_turno());
            pst.setInt(12, re.getC1idrh_entrada());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + re.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + re.toString(), titulo);
        }
    }

    public void cargar_rh_entrada(Connection conn, rh_entrada re, int idrh_entrada) {
        String titulo = "Cargar_rh_entrada";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idrh_entrada, titulo);
            if (rs.next()) {
                re.setC1idrh_entrada(rs.getInt(1));
                re.setC2fecha_creado(rs.getString(2));
                re.setC3creado_por(rs.getString(3));
                re.setC4fecha_entrada(rs.getString(4));
                re.setC5fecha_salida(rs.getString(5));
                re.setC6turno(rs.getString(6));
                re.setC7es_cerrado(rs.getBoolean(7));
                re.setC8es_entrada(rs.getBoolean(8));
                re.setC9es_salida(rs.getBoolean(9));
                re.setC10fk_idpersona(rs.getInt(10));
                re.setC11fk_idusuario(rs.getInt(11));
                re.setC12fk_idrh_turno(rs.getInt(12));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + re.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + re.toString(), titulo);
        }
    }

    public void actualizar_tabla_rh_entrada(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_rh_entrada(tbltabla);
    }

    public void ancho_tabla_rh_entrada(JTable tbltabla) {
        int Ancho[] = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
    public boolean getBoo_cargar_rh_entrada_idpersonal_abierto(Connection conn, rh_entrada re, int fk_idpersona) {
        String sql_cargar_abierto = "SELECT idrh_entrada,fecha_creado,creado_por,"
                + "to_char(fecha_entrada,'dd-MM-yyyy HH24:MI') as fecha_entrada,fecha_salida,turno,"
                + "es_cerrado,es_entrada,es_salida,"
                + "fk_idpersona,fk_idusuario,fk_idrh_turno "
                + "FROM rh_entrada WHERE es_cerrado=false and es_entrada=true and fk_idpersona=";
        String titulo = "cargar_rh_entrada_idpersonal_abierto";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar_abierto + fk_idpersona, titulo);
            if (rs.next()) {
                re.setC1idrh_entrada(rs.getInt(1));
                re.setC2fecha_creado(rs.getString(2));
                re.setC3creado_por(rs.getString(3));
                re.setC4fecha_entrada(rs.getString(4));
                re.setC5fecha_salida(rs.getString(5));
                re.setC6turno(rs.getString(6));
                re.setC7es_cerrado(rs.getBoolean(7));
                re.setC8es_entrada(rs.getBoolean(8));
                re.setC9es_salida(rs.getBoolean(9));
                re.setC10fk_idpersona(rs.getInt(10));
                re.setC11fk_idusuario(rs.getInt(11));
                re.setC12fk_idrh_turno(rs.getInt(12));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + re.toString(), titulo);
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + re.toString(), titulo);
            return false;
        }
    }
    public void update_rh_entrada_salir(Connection conn, rh_entrada re) {
        String titulo = "update_rh_entrada_salir";
        String sql_update = "UPDATE rh_entrada SET fecha_salida=?,es_entrada=?,es_salida=? WHERE idrh_entrada=?;";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setBoolean(2, re.getC8es_entrada());
            pst.setBoolean(3, re.getC9es_salida());
            pst.setInt(4, re.getC1idrh_entrada());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + re.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + re.toString(), titulo);
        }
    }
}
