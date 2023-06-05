package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import FORMULARIO.ENTIDAD.rh_turno;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;

public class DAO_rh_turno {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private String mensaje_insert = "RH_TURNO GUARDADO CORRECTAMENTE";
    private String mensaje_update = "RH_TURNO MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO rh_turno(idrh_turno,fecha_creado,creado_por,turno,hora_inicio,hora_salida,tolera_llega_tarde,tolera_sale_antes,domindo,lunes,martes,miercoles,jueves,viernes,sabado,activo,incluye_dos_dia) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE rh_turno SET fecha_creado=?,creado_por=?,turno=?,hora_inicio=?,hora_salida=?,tolera_llega_tarde=?,tolera_sale_antes=?,domindo=?,lunes=?,martes=?,miercoles=?,jueves=?,viernes=?,sabado=?,activo=?,incluye_dos_dia=? WHERE idrh_turno=?;";
    private String sql_select = "SELECT idrh_turno,turno,hora_inicio,hora_salida,activo FROM rh_turno order by 1 desc;";
    private String sql_cargar = "SELECT idrh_turno,fecha_creado,creado_por,turno,hora_inicio,hora_salida,tolera_llega_tarde,tolera_sale_antes,domindo,lunes,martes,miercoles,jueves,viernes,sabado,activo,incluye_dos_dia FROM rh_turno WHERE idrh_turno=";

    public void insertar_rh_turno(Connection conn, rh_turno rhtu) {
        rhtu.setC1idrh_turno(eveconn.getInt_ultimoID_mas_uno(conn, rhtu.getTb_rh_turno(), rhtu.getId_idrh_turno()));
        String titulo = "insertar_rh_turno";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, rhtu.getC1idrh_turno());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, rhtu.getC3creado_por());
            pst.setString(4, rhtu.getC4turno());
            pst.setTime(5, evefec.getTime_sistema_cargado(rhtu.getC5hora_inicio()));
            pst.setTime(6, evefec.getTime_sistema_cargado(rhtu.getC6hora_salida()));
            pst.setInt(7, rhtu.getC7tolera_llega_tarde());
            pst.setInt(8, rhtu.getC8tolera_sale_antes());
            pst.setBoolean(9, rhtu.getC9domindo());
            pst.setBoolean(10, rhtu.getC10lunes());
            pst.setBoolean(11, rhtu.getC11martes());
            pst.setBoolean(12, rhtu.getC12miercoles());
            pst.setBoolean(13, rhtu.getC13jueves());
            pst.setBoolean(14, rhtu.getC14viernes());
            pst.setBoolean(15, rhtu.getC15sabado());
            pst.setBoolean(16, rhtu.getC16activo());
            pst.setBoolean(17, rhtu.getC17incluye_dos_dia());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + rhtu.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + rhtu.toString(), titulo);
        }
    }

    public void update_rh_turno(Connection conn, rh_turno rhtu) {
        String titulo = "update_rh_turno";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
            pst.setTimestamp(1, evefec.getTimestamp_sistema());
            pst.setString(2, rhtu.getC3creado_por());
            pst.setString(3, rhtu.getC4turno());
            pst.setTime(4, evefec.getTime_sistema_cargado(rhtu.getC5hora_inicio()));
            pst.setTime(5, evefec.getTime_sistema_cargado(rhtu.getC6hora_salida()));
            pst.setInt(6, rhtu.getC7tolera_llega_tarde());
            pst.setInt(7, rhtu.getC8tolera_sale_antes());
            pst.setBoolean(8, rhtu.getC9domindo());
            pst.setBoolean(9, rhtu.getC10lunes());
            pst.setBoolean(10, rhtu.getC11martes());
            pst.setBoolean(11, rhtu.getC12miercoles());
            pst.setBoolean(12, rhtu.getC13jueves());
            pst.setBoolean(13, rhtu.getC14viernes());
            pst.setBoolean(14, rhtu.getC15sabado());
            pst.setBoolean(15, rhtu.getC16activo());
            pst.setBoolean(16, rhtu.getC17incluye_dos_dia());
            pst.setInt(17, rhtu.getC1idrh_turno());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + rhtu.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + rhtu.toString(), titulo);
        }
    }

    public void cargar_rh_turno(Connection conn, rh_turno rhtu, int idrh_turno) {
        String titulo = "Cargar_rh_turno";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idrh_turno, titulo);
            if (rs.next()) {
                rhtu.setC1idrh_turno(rs.getInt(1));
                rhtu.setC2fecha_creado(rs.getString(2));
                rhtu.setC3creado_por(rs.getString(3));
                rhtu.setC4turno(rs.getString(4));
                rhtu.setC5hora_inicio(rs.getString(5));
                rhtu.setC6hora_salida(rs.getString(6));
                rhtu.setC7tolera_llega_tarde(rs.getInt(7));
                rhtu.setC8tolera_sale_antes(rs.getInt(8));
                rhtu.setC9domindo(rs.getBoolean(9));
                rhtu.setC10lunes(rs.getBoolean(10));
                rhtu.setC11martes(rs.getBoolean(11));
                rhtu.setC12miercoles(rs.getBoolean(12));
                rhtu.setC13jueves(rs.getBoolean(13));
                rhtu.setC14viernes(rs.getBoolean(14));
                rhtu.setC15sabado(rs.getBoolean(15));
                rhtu.setC16activo(rs.getBoolean(16));
                rhtu.setC17incluye_dos_dia(rs.getBoolean(17));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + rhtu.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + rhtu.toString(), titulo);
        }
    }

    public void actualizar_tabla_rh_turno(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_rh_turno(tbltabla);
    }

    public void ancho_tabla_rh_turno(JTable tbltabla) {
        int Ancho[] = {5,45, 20, 20,10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }
}
