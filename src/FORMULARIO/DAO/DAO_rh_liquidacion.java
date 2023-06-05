package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.rh_liquidacion;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;

public class DAO_rh_liquidacion {

    EvenConexion eveconn = new EvenConexion();
    EvenJtable evejt = new EvenJtable();
    EvenJasperReport rep = new EvenJasperReport();
    EvenMensajeJoptionpane evemen = new EvenMensajeJoptionpane();
    EvenFecha evefec = new EvenFecha();
    private EvenEstado eveest = new EvenEstado();
    private String mensaje_insert = "RH_LIQUIDACION GUARDADO CORRECTAMENTE";
    private String mensaje_update = "RH_LIQUIDACION MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO rh_liquidacion(idrh_liquidacion,fecha_creado,creado_por,fecha_desde,fecha_hasta,estado,es_cerrado,monto_vale,monto_descuento,monto_liquidacion,salario_base,monto_letra,fk_idpersona) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE rh_liquidacion SET "
            //            + "fecha_creado=?,creado_por=?,fecha_desde=?,"
            + "fecha_hasta=?,estado=?,es_cerrado=?,"
            + "monto_vale=?,monto_descuento=?,"
            + "monto_liquidacion=?,salario_base=?,"
            + "monto_letra=?,fk_idpersona=? "
            + "WHERE idrh_liquidacion=?;";
    private String sql_select = "SELECT idrh_liquidacion,fecha_creado,creado_por,fecha_desde,fecha_hasta,estado,es_cerrado,monto_vale,monto_descuento,monto_liquidacion,salario_base,monto_letra,fk_idpersona FROM rh_liquidacion order by 1 desc;";
    private String sql_cargar = "SELECT rhl.idrh_liquidacion,rhl.fecha_creado,rhl.creado_por,"
            + "rhl.fecha_desde,rhl.fecha_hasta,rhl.estado,rhl.es_cerrado,"
            + "rhl.monto_vale,rhl.monto_descuento,rhl.monto_liquidacion,rhl.salario_base,"
            + "rhl.monto_letra,rhl.fk_idpersona,"
            + "(select sum(rhd.monto_descuento) as descuento from rh_descuento rhd, rh_liquidacion_descuento rhld\n"
            + "where rhd.idrh_descuento=rhld.fk_idrh_descuento\n"
            + "and rhld.fk_idrh_liquidacion=rhl.idrh_liquidacion) as sum_descuento,\n"
            + "(select sum(rhv.monto_vale) as vale from rh_vale rhv, rh_liquidacion_vale rhlv\n"
            + "where rhv.idrh_vale=rhlv.fk_idrh_vale\n"
            + "and rhlv.fk_idrh_liquidacion=rhl.idrh_liquidacion) as sum_vale "
            + "FROM rh_liquidacion rhl "
            + "WHERE rhl.idrh_liquidacion=";

    public void insertar_rh_liquidacion(Connection conn, rh_liquidacion rl) {
        rl.setC1idrh_liquidacion(eveconn.getInt_ultimoID_mas_uno(conn, rl.getTb_rh_liquidacion(), rl.getId_idrh_liquidacion()));
        String titulo = "insertar_rh_liquidacion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_insert);
            pst.setInt(1, rl.getC1idrh_liquidacion());
            pst.setTimestamp(2, evefec.getTimestamp_sistema());
            pst.setString(3, rl.getC3creado_por());
            pst.setDate(4, evefec.getDateSQL_sistema());
            pst.setDate(5, evefec.getDateSQL_sistema());
            pst.setString(6, rl.getC6estado());
            pst.setBoolean(7, rl.getC7es_cerrado());
            pst.setDouble(8, rl.getC8monto_vale());
            pst.setDouble(9, rl.getC9monto_descuento());
            pst.setDouble(10, rl.getC10monto_liquidacion());
            pst.setDouble(11, rl.getC11salario_base());
            pst.setString(12, rl.getC12monto_letra());
            pst.setInt(13, rl.getC13fk_idpersona());

            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_insert + "\n" + rl.toString(), titulo);
            evemen.guardado_correcto(mensaje_insert, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_insert + "\n" + rl.toString(), titulo);
        }
    }

    public void update_rh_liquidacion(Connection conn, rh_liquidacion rl) {
        String titulo = "update_rh_liquidacion";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql_update);
//            pst.setTimestamp(1, evefec.getTimestamp_sistema());
//            pst.setString(2, rl.getC3creado_por());
//            pst.setDate(3, evefec.getDateSQL_sistema());
            pst.setDate(1, evefec.getDate_fecha_cargado(rl.getC5fecha_hasta()));
            pst.setString(2, rl.getC6estado());
            pst.setBoolean(3, rl.getC7es_cerrado());
            pst.setDouble(4, rl.getC8monto_vale());
            pst.setDouble(5, rl.getC9monto_descuento());
            pst.setDouble(6, rl.getC10monto_liquidacion());
            pst.setDouble(7, rl.getC11salario_base());
            pst.setString(8, rl.getC12monto_letra());
            pst.setInt(9, rl.getC13fk_idpersona());
            pst.setInt(10, rl.getC1idrh_liquidacion());
            pst.execute();
            pst.close();
            evemen.Imprimir_serial_sql(sql_update + "\n" + rl.toString(), titulo);
            evemen.modificado_correcto(mensaje_update, true);
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_update + "\n" + rl.toString(), titulo);
        }
    }

    public void cargar_rh_liquidacion(Connection conn, rh_liquidacion rl, int idrh_liquidacion) {
        String titulo = "Cargar_rh_liquidacion";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql_cargar + idrh_liquidacion, titulo);
            if (rs.next()) {
                rl.setC1idrh_liquidacion(rs.getInt(1));
                rl.setC2fecha_creado(rs.getString(2));
                rl.setC3creado_por(rs.getString(3));
                rl.setC4fecha_desde(rs.getString(4));
                rl.setC5fecha_hasta(rs.getString(5));
                rl.setC6estado(rs.getString(6));
                rl.setC7es_cerrado(rs.getBoolean(7));
                rl.setC8monto_vale(rs.getDouble(8));
                rl.setC9monto_descuento(rs.getDouble(9));
                rl.setC10monto_liquidacion(rs.getDouble(10));
                rl.setC11salario_base(rs.getDouble(11));
                rl.setC12monto_letra(rs.getString(12));
                rl.setC13fk_idpersona(rs.getInt(13));
                rl.setSum_descuento(rs.getDouble(14));
                rl.setSum_vale(rs.getDouble(15));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + rl.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + rl.toString(), titulo);
        }
    }

    public void actualizar_tabla_rh_liquidacion(Connection conn, JTable tbltabla) {
        eveconn.Select_cargar_jtable(conn, sql_select, tbltabla);
        ancho_tabla_rh_liquidacion(tbltabla);
    }

    public void ancho_tabla_rh_liquidacion(JTable tbltabla) {
        int Ancho[] = {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public int getInt_idrh_liquidacion_rh_liquidacion_abierto(Connection conn, int fk_idpersona) {
        String titulo = "getInt_idrh_liquidacion_rh_liquidacion_abierto";
        int idrh_liquidacion = 0;
        String sql = "SELECT idrh_liquidacion "
                + "FROM rh_liquidacion WHERE es_cerrado=false and fk_idpersona=" + fk_idpersona + " ;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                idrh_liquidacion = rs.getInt("idrh_liquidacion");
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
        return idrh_liquidacion;
    }

    public void actualizar_tabla_rh_liquidacion_entrada(Connection conn, JTable tbltabla, int idrh_liquidacion) {
        String sql = "select rhli.idrh_liquidacion_entrada as idliq,\n"
                + "to_char(rhen.fecha_entrada,'dd-MM-yyyy HH24:MI:ss') as fec_entrada,\n"
                + "to_char(rhen.fecha_salida,'dd-MM-yyyy HH24:MI:ss') as fec_salida,"
                + "to_char((rhen.fecha_salida-rhen.fecha_entrada), 'HH24:MI:ss') as tiempo,"
                + "rhen.turno, \n"
                + "case when rhen.es_entrada=true then 'ENTRADA' when rhen.es_salida=true then 'SALIDA' else 'error' end as tipo \n"
                + "from rh_liquidacion_entrada rhli,rh_entrada rhen\n"
                + "where rhli.fk_idrh_entrada=rhen.idrh_entrada\n"
                + "and rhen.es_cerrado=false\n"
                + "and rhli.fk_idrh_liquidacion=" + idrh_liquidacion + " "
                + "order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_rh_liquidacion_entrada(tbltabla);
    }

    public void ancho_tabla_rh_liquidacion_entrada(JTable tbltabla) {
        int Ancho[] = {10, 25, 25, 10, 20, 10};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
    }

    public void actualizar_tabla_rh_liquidacion_vale(Connection conn, JTable tbltabla, int idrh_liquidacion) {
        String sql = "select rhen.idrh_vale,to_char(rhen.fecha_creado,'dd-MM-yyyy HH24:MI') as fecha,"
                + "rhen.descripcion,TRIM(to_char(rhen.monto_vale,'999G999G999')) as monto,rhen.estado\n"
                + "from rh_liquidacion_vale rhli,rh_vale rhen\n"
                + "where rhli.fk_idrh_vale=rhen.idrh_vale\n"
                + "and rhen.es_cerrado=false\n"
                + "and rhli.fk_idrh_liquidacion=" + idrh_liquidacion
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_rh_liquidacion_vale(tbltabla);
    }

    public void ancho_tabla_rh_liquidacion_vale(JTable tbltabla) {
        int Ancho[] = {5, 20, 45, 15, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 3);
    }

    public void actualizar_tabla_rh_liquidacion_descuento(Connection conn, JTable tbltabla, int idrh_liquidacion) {
        String sql = "select rhen.idrh_descuento,to_char(rhen.fecha_creado,'dd-MM-yyyy HH24:MI') as fecha,"
                + "rhen.descripcion,TRIM(to_char(rhen.monto_descuento,'999G999G999')) as monto,rhen.estado\n"
                + "from rh_liquidacion_descuento rhli,rh_descuento rhen\n"
                + "where rhli.fk_idrh_descuento=rhen.idrh_descuento\n"
                + "and rhen.es_cerrado=false\n"
                + "and rhli.fk_idrh_liquidacion=" + idrh_liquidacion
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_rh_liquidacion_descuento(tbltabla);
    }

    public void ancho_tabla_rh_liquidacion_descuento(JTable tbltabla) {
        int Ancho[] = {5, 20, 45, 15, 15};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 3);
    }

    public void cerrar_entradar_de_liquidacion(Connection conn, rh_liquidacion ENTrhl) {
        String sql = "update rh_entrada set es_cerrado=true "
                + "from rh_liquidacion l,rh_liquidacion_entrada le\n"
                + "where l.idrh_liquidacion=le.fk_idrh_liquidacion\n"
                + "and le.fk_idrh_entrada=rh_entrada.idrh_entrada\n"
                + "and l.idrh_liquidacion=" + ENTrhl.getC1idrh_liquidacion();
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void cerrar_vale_de_liquidacion(Connection conn, rh_liquidacion ENTrhl) {
        String sql = "update rh_vale set es_cerrado=true,estado='" + eveest.getEst_Cerrado() + "' "
                + "from rh_liquidacion l,rh_liquidacion_vale lv\n"
                + "where l.idrh_liquidacion=lv.fk_idrh_liquidacion\n"
                + "and lv.fk_idrh_vale=rh_vale.idrh_vale\n"
                + "and l.idrh_liquidacion=" + ENTrhl.getC1idrh_liquidacion();
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void cerrar_descuento_de_liquidacion(Connection conn, rh_liquidacion ENTrhl) {
        String sql = "update rh_descuento set es_cerrado=true,estado='" + eveest.getEst_Cerrado() + "' \n"
                + "from rh_liquidacion l,rh_liquidacion_descuento ld\n"
                + "where l.idrh_liquidacion=ld.fk_idrh_liquidacion\n"
                + "and ld.fk_idrh_descuento=rh_descuento.idrh_descuento\n"
                + "and l.idrh_liquidacion=" + ENTrhl.getC1idrh_liquidacion();
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void imprimir_nota_rh_liquidacion(Connection conn, int idrh_liquidacion) {
        String sql = "select l.idrh_liquidacion as idl,\n"
                + "to_char(l.fecha_desde,'yyyy-MM-dd') as fecdesde,\n"
                + "to_char(l.fecha_hasta,'yyyy-MM-dd') as fechasta,\n"
                + "l.monto_vale as sum_vale,\n"
                + "l.monto_descuento as sum_descuento,\n"
                + "l.monto_liquidacion as sum_liquidacion,\n"
                + "l.salario_base as salario,\n"
                + "l.monto_letra as letra,\n"
                + "p.nombre as persona,c.nombre as cargo,\n"
                + "to_char(d.fecha_creado,'yyyy-MM-dd') as fecdetalle,\n"
                + "d.descripcion as descripcion,d.monto_descuento as m_descuento,d.monto_vale as m_vale,d.tabla as tabla \n"
                + "from rh_liquidacion l,persona p,persona_cargo c,rh_liquidacion_detalle d\n"
                + "where l.fk_idpersona=p.idpersona\n"
                + "and p.fk_idpersona_cargo=c.idpersona_cargo\n"
                + "and l.idrh_liquidacion=d.fk_idrh_liquidacion\n"
                + "and d.estado='EMITIDO'\n"
                + "and l.idrh_liquidacion="+idrh_liquidacion
                + " order by d.tabla desc,d.fecha_creado desc";
        String titulonota = "NOTA LIQUIDACION";
        String direccion = "src/REPORTE/VALE/repNotaLiquidacion.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
