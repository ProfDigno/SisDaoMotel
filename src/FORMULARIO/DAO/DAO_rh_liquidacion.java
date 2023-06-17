package FORMULARIO.DAO;

import BASEDATO.EvenConexion;
import ESTADOS.EvenEstado;
import FORMULARIO.ENTIDAD.rh_liquidacion;
import Evento.JasperReport.EvenJasperReport;
import Evento.Jtable.EvenJtable;
import Evento.Mensaje.EvenMensajeJoptionpane;
import Evento.Fecha.EvenFecha;
import Evento.Jtable.EvenRender;
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
    EvenRender everender = new EvenRender();
    private EvenEstado eveest = new EvenEstado();
    private String mensaje_insert = "RH_LIQUIDACION GUARDADO CORRECTAMENTE";
    private String mensaje_update = "RH_LIQUIDACION MODIFICADO CORECTAMENTE";
    private String sql_insert = "INSERT INTO rh_liquidacion(idrh_liquidacion,fecha_creado,creado_por,"
            + "fecha_desde,fecha_hasta,estado,es_cerrado,"
            + "monto_vale,monto_descuento,monto_liquidacion,salario_base,"
            + "monto_letra,fk_idpersona,descripcion) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private String sql_update = "UPDATE rh_liquidacion SET "
            //            + "fecha_creado=?,creado_por=?,fecha_desde=?,"
            + "fecha_hasta=?,estado=?,es_cerrado=?,"
            + "monto_vale=?,monto_descuento=?,"
            + "monto_liquidacion=?,salario_base=?,"
            + "monto_letra=?,fk_idpersona=?,descripcion=? "
            + "WHERE idrh_liquidacion=?;";
    private String sql_select = "SELECT idrh_liquidacion,fecha_creado,creado_por,"
            + "fecha_desde,fecha_hasta,estado,es_cerrado,"
            + "monto_vale,monto_descuento,monto_liquidacion,salario_base,"
            + "monto_letra,fk_idpersona FROM rh_liquidacion order by 1 desc;";
    private String sql_cargar = "SELECT rhl.idrh_liquidacion,rhl.fecha_creado,rhl.creado_por,"
            + "rhl.fecha_desde,rhl.fecha_hasta,rhl.estado,rhl.es_cerrado,"
            + "rhl.monto_vale,rhl.monto_descuento,rhl.monto_liquidacion,rhl.salario_base,"
            + "rhl.monto_letra,rhl.fk_idpersona,"
            + "(select sum(rhd.monto_descuento) as descuento from rh_descuento rhd, rh_liquidacion_descuento rhld\n"
            + "where rhd.idrh_descuento=rhld.fk_idrh_descuento\n"
            + "and rhld.fk_idrh_liquidacion=rhl.idrh_liquidacion) as sum_descuento,\n"
            + "(select sum(rhv.monto_vale) as vale from rh_vale rhv, rh_liquidacion_vale rhlv\n"
            + "where rhv.idrh_vale=rhlv.fk_idrh_vale\n"
            + "and rhlv.fk_idrh_liquidacion=rhl.idrh_liquidacion) as sum_vale,rhl.descripcion "
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
            pst.setString(14, rl.getC14descripcion());
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
            pst.setDate(1, evefec.getDate_fecha_cargado(rl.getC5fecha_hasta()));
            pst.setString(2, rl.getC6estado());
            pst.setBoolean(3, rl.getC7es_cerrado());
            pst.setDouble(4, rl.getC8monto_vale());
            pst.setDouble(5, rl.getC9monto_descuento());
            pst.setDouble(6, rl.getC10monto_liquidacion());
            pst.setDouble(7, rl.getC11salario_base());
            pst.setString(8, rl.getC12monto_letra());
            pst.setInt(9, rl.getC13fk_idpersona());
            pst.setString(10, rl.getC14descripcion());
            pst.setInt(11, rl.getC1idrh_liquidacion());
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
                rl.setC14descripcion(rs.getString(16));
                evemen.Imprimir_serial_sql(sql_cargar + "\n" + rl.toString(), titulo);
            }
        } catch (Exception e) {
            evemen.mensaje_error(e, sql_cargar + "\n" + rl.toString(), titulo);
        }
    }

    public void actualizar_tabla_rh_liquidacion(Connection conn, JTable tbltabla) {
        String sql = "select l.idrh_liquidacion,l.fecha_desde,l.fecha_hasta, \n"
                + "p.nombre,\n"
                + "TRIM(to_char(l.salario_base,'999G999G999')) as salario,\n"
                + "TRIM(to_char(l.monto_vale,'999G999G999')) as vale,\n"
                + "TRIM(to_char(l.monto_descuento,'999G999G999')) as descuento,\n"
                + "TRIM(to_char(l.monto_liquidacion,'999G999G999')) as liquidacion,\n"
                + "l.estado\n"
                + "from rh_liquidacion l,persona p\n"
                + "where l.fk_idpersona=p.idpersona\n"
                + "order by l.estado asc,l.fecha_hasta desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_rh_liquidacion(tbltabla);
        everender.rendertabla_estados(tbltabla, 8);
    }

    public void ancho_tabla_rh_liquidacion(JTable tbltabla) {
        int Ancho[] = {5, 10, 10, 20, 8, 8, 8, 8, 6};
        evejt.setAnchoColumnaJtable(tbltabla, Ancho);
        evejt.alinear_derecha_columna(tbltabla, 4);
        evejt.alinear_derecha_columna(tbltabla, 5);
        evejt.alinear_derecha_columna(tbltabla, 6);
        evejt.alinear_derecha_columna(tbltabla, 7);
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
                + "and rhli.fk_idrh_liquidacion=" + idrh_liquidacion
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_rh_liquidacion_vale(tbltabla);
        everender.rendertabla_estados(tbltabla, 4);
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
                + "and rhli.fk_idrh_liquidacion=" + idrh_liquidacion
                + " order by 1 desc";
        eveconn.Select_cargar_jtable(conn, sql, tbltabla);
        ancho_tabla_rh_liquidacion_descuento(tbltabla);
        everender.rendertabla_estados(tbltabla, 4);
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
                + "and rh_vale.estado='" + eveest.getEst_Emitido() + "' \n"
                + "and l.idrh_liquidacion=" + ENTrhl.getC1idrh_liquidacion();
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void cerrar_descuento_de_liquidacion(Connection conn, rh_liquidacion ENTrhl) {
        String sql = "update rh_descuento set es_cerrado=true,estado='" + eveest.getEst_Cerrado() + "' \n"
                + "from rh_liquidacion l,rh_liquidacion_descuento ld\n"
                + "where l.idrh_liquidacion=ld.fk_idrh_liquidacion\n"
                + "and ld.fk_idrh_descuento=rh_descuento.idrh_descuento\n"
                + "and rh_descuento.estado='" + eveest.getEst_Emitido() + "' \n"
                + "and l.idrh_liquidacion=" + ENTrhl.getC1idrh_liquidacion();
        eveconn.SQL_execute_libre(conn, sql);
    }

    public void imprimir_rh_liquidacion(Connection conn, int idrh_liquidacion) {
        String titulo = "getInt_idrh_liquidacion_rh_liquidacion_abierto";
        String sql = "select count(*) as cant\n"
                + "from rh_liquidacion_detalle\n"
                + "where fk_idrh_liquidacion=" + idrh_liquidacion + " ;";
        try {
            ResultSet rs = eveconn.getResulsetSQL(conn, sql, titulo);
            if (rs.next()) {
                int cant = rs.getInt("cant");
                if (cant > 0) {
                    imprimir_nota_rh_liquidacion(conn, idrh_liquidacion);
                } else {
                    imprimir_nota_rh_liquidacion_sindet(conn, idrh_liquidacion);
                }
            }
        } catch (SQLException e) {
            evemen.mensaje_error(e, sql, titulo);
        }
    }

    private void imprimir_nota_rh_liquidacion(Connection conn, int idrh_liquidacion) {
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
                + "and d.estado='" + eveest.getEst_Emitido() + "'\n"
                + "and l.idrh_liquidacion=" + idrh_liquidacion
                + " order by d.tabla desc,l.idrh_liquidacion desc";
        String titulonota = "NOTA LIQUIDACION";
        String direccion = "src/REPORTE/VALE/repNotaLiquidacion.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    private void imprimir_nota_rh_liquidacion_sindet(Connection conn, int idrh_liquidacion) {
        String sql = "select l.idrh_liquidacion as idl,\n"
                + "to_char(l.fecha_desde,'yyyy-MM-dd') as fecdesde,\n"
                + "to_char(l.fecha_hasta,'yyyy-MM-dd') as fechasta,\n"
                + "l.monto_liquidacion as sum_liquidacion,\n"
                + "l.salario_base as salario,\n"
                + "l.monto_letra as letra,\n"
                + "p.nombre as persona,c.nombre as cargo \n"
                + "from rh_liquidacion l,persona p,persona_cargo c\n"
                + "where l.fk_idpersona=p.idpersona\n"
                + "and p.fk_idpersona_cargo=c.idpersona_cargo\n"
                + "and l.idrh_liquidacion=" + idrh_liquidacion;
        String titulonota = "NOTA LIQUIDACION SD";
        String direccion = "src/REPORTE/VALE/repNotaLiquidacionSD.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }

    public void imprimir_filtro_liquidacion(Connection conn, String filtro) {
        String sql = "select v.idrh_liquidacion as idv,\n"
                + "(p.nombre||'-'||p.ruc) as persona,\n"
                + "case \n"
                + "when date_part('month',v.fecha_creado)=1 then 'ENERO'\n"
                + "when date_part('month',v.fecha_creado)=2 then 'FEBRERO'\n"
                + "when date_part('month',v.fecha_creado)=3 then 'MARZO'\n"
                + "when date_part('month',v.fecha_creado)=4 then 'ABRIL'\n"
                + "when date_part('month',v.fecha_creado)=5 then 'MAYO'\n"
                + "when date_part('month',v.fecha_creado)=6 then 'JUNIO'\n"
                + "when date_part('month',v.fecha_creado)=7 then 'JULIO'\n"
                + "when date_part('month',v.fecha_creado)=8 then 'AGOSTO'\n"
                + "when date_part('month',v.fecha_creado)=9 then 'SEPTIEMBRE'\n"
                + "when date_part('month',v.fecha_creado)=10 then 'OCTUBRE'\n"
                + "when date_part('month',v.fecha_creado)=11 then 'NOVIEMBRE'\n"
                + "when date_part('month',v.fecha_creado)=12 then 'DICIEMBRE'\n"
                + "else 'error' end as mes,\n"
                + "to_char(v.fecha_creado,'yyyy-MM-dd') as fecha,\n"
                + "v.descripcion as descripcion,\n"
                + "v.salario_base as salario,\n"
                + "v.monto_vale as vale,\n"
                + "v.monto_descuento as descuento,\n"
                + "v.monto_liquidacion as liquidacion\n"
                + "from rh_liquidacion v,persona p\n"
                + "where v.fk_idpersona=p.idpersona\n"
                + "and (v.estado='CERRADO' or v.estado='EMITIDO')\n"+filtro
                + " order by p.nombre desc,"
                + "date_part('month',v.fecha_creado) desc,"
                + "v.fecha_creado desc";
        String titulonota = "FILTRO VALE";
        String direccion = "src/REPORTE/VALE/repFitroLiquidacion.jrxml";
        rep.imprimirjasper(conn, sql, titulonota, direccion);
    }
}
