/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ESTADOS;

/**
 *
 * @author Digno
 */
public class EvenEstado {
    private static String est_Emitido;
    private static String est_Anulado;
    private static String est_Pagado;
    private static String est_Cargado;
    private static String est_Libre;
    private static String est_Ocupado;
    private static String est_Sucio;
    private static String est_Limpiando;
    private static String est_Terminar;
    private static String est_Cancelado;
    private static String est_Desocupado;
    private static String est_Adelanto;
    private static String est_Cerrado;
    private static String est_Mudar;
    private static String est_INGRESADO;
    private static String est_PENDIENTE;
    private static String est_CARGADOSTOCK;
    private static String estdes_Limpiando;
    private static String estdes_cliingreso;
    private static String estdes_cliAbierto;
    private static String estdes_mante;
    private static String estdes_limpAbierto;
    private static String estdes_sucio;
    private static String estdes_cancelar;
    private static String caja_GASTO;
    private static String caja_COMPRA;
    private static String caja_APERTURA;
    private static String cond_Contado;
    private static String cond_Credito;
    private static String forPago_Efectivo;
    private static String forPago_TarjetaDebito;
    private static String forPago_TarjetaCredito;
    private static String per_CLIENTE;
    private static String per_PERSONAL;
    private static String per_PROVEEDOR;
    private static String per_USUARIO;
    private static String mone_PYG;//Gs
    private static String mone_USD;//Dolar
    private static String mone_BRL;//R$
    private static String mone_ARS;//peso
    private static String tipo_hab_estandar;
    private static String tipo_hab_vip;
    private static String tipo_hab_luxury;
    private static String tipo_hab_penthouse;
    private static String form_nro_9D;
    private static String son_pue_cli;
    private static String son_pue_limp;
    private static String son_ocupa;
    private static String son_libre;
    private static String son_desocu;
    private static String son_limpie;
    private static String fec_hms;
    private static String fec_amd;
    private static String ico_libre;
    private static String ico_mante;
    private static String ico_ocupa;
    private static String ico_puerta;
    private static String ico_dormir;
    private static String ico_limpie;
    private static String ico_escoba;
    private static String ico_candado;
    public EvenEstado() {
        setEst_Anulado("ANULADO");
        setEst_Emitido("EMITIDO");
        setEst_Libre("LIBRE");
        setEst_Ocupado("OCUPADO");
        setEst_Sucio("SUCIO");
        setEst_Limpiando("LIMPIANDO");
        setEst_Terminar("TERMINADO");
        setEst_Cancelado("CANCELADO");
        setEst_Desocupado("DESOCUPADO");
        setEst_Adelanto("ADELANTO");
        setEst_Cerrado("CERRADO");
        setEst_Pagado("PAGADO");
        setEst_Cargado("CARGADO");
        setEst_Mudar("MUDAR");
        setEst_INGRESADO("INGRESADO");
        setEst_PENDIENTE("PENDIENTE");
        setEst_CARGADOSTOCK("CARGADO_ST");
        setEstdes_Limpiando("-LIMPIANDO");
        setEstdes_cliingreso("-CLI-INGRESO");
        setEstdes_cliAbierto("-CLI-ABIERTO");
        setEstdes_limpAbierto("-LIMP-ABIERTO");
        setEstdes_cancelar("-(CANCELAR)");
        setEstdes_sucio("-SUCIO");
        setEstdes_mante("-MANTE");
        setCond_Contado("CONTADO");
        setCond_Credito("CREDITO");
        setForPago_Efectivo("EFECTIVO");
        setForPago_TarjetaDebito("TARJETA_DEBITO");
        setForPago_TarjetaCredito("TARJETA_CREDITO");
        setMone_PYG("Gs");
        setMone_USD("Dolar");
        setMone_BRL("R$");
        setMone_ARS("peso");
        setTipo_hab_estandar("MOTO");
        setTipo_hab_vip("NORMAL");
        setTipo_hab_luxury("SUIT");
        setTipo_hab_penthouse("PENTHOUSE");
        setCaja_GASTO("GASTO");
        setCaja_COMPRA("COMPRA");
        setCaja_APERTURA("APERTURA");
        setPer_CLIENTE("CLIENTE");
        setPer_PERSONAL("PERSONAL");
        setPer_PROVEEDOR("PROVEEDOR");
        setPer_USUARIO("USUARIO");
        setForm_nro_9D("999G999G999");
        setSon_pue_cli("sounds/puerta_cliente.wav");
        setSon_pue_limp("sounds/puerta_limpieza.wav");
        setSon_ocupa("sounds/son_ocu/son_ocu_");
        setSon_libre("sounds/son_libre/son_libre_");
        setSon_desocu("sounds/son_deso/son_deso_");
        setSon_limpie("sounds/son_limpieza/son_limpieza_");
        setFec_hms("HH24:MI:ss");
        setFec_amd("yyyy-MM-dd");
        setIco_libre("/graficos/libre.png");
        setIco_mante("/graficos/48_mante.png");
        setIco_ocupa("/iconos/motel/48_reloj.png");
        setIco_puerta("/graficos/48_puerta.png");
        setIco_dormir("/iconos/motel/48_dormir.png");///iconos/motel/48_dormir.png --/graficos/dormir.png
        setIco_limpie("/graficos/limpieza.png");
        setIco_escoba("/graficos/escoba.png");
        setIco_candado("/graficos/ocupado.png");
    }

    public static String getIco_candado() {
        return ico_candado;
    }

    public static void setIco_candado(String ico_candado) {
        EvenEstado.ico_candado = ico_candado;
    }

    public static String getIco_limpie() {
        return ico_limpie;
    }

    public static void setIco_limpie(String ico_limpie) {
        EvenEstado.ico_limpie = ico_limpie;
    }

    public static String getIco_escoba() {
        return ico_escoba;
    }

    public static void setIco_escoba(String ico_escoba) {
        EvenEstado.ico_escoba = ico_escoba;
    }

    public static String getIco_dormir() {
        return ico_dormir;
    }

    public static void setIco_dormir(String ico_dormir) {
        EvenEstado.ico_dormir = ico_dormir;
    }

    public static String getIco_libre() {
        return ico_libre;
    }

    public static void setIco_libre(String ico_libre) {
        EvenEstado.ico_libre = ico_libre;
    }

    public static String getIco_mante() {
        return ico_mante;
    }

    public static void setIco_mante(String ico_mante) {
        EvenEstado.ico_mante = ico_mante;
    }

    public static String getIco_ocupa() {
        return ico_ocupa;
    }

    public static void setIco_ocupa(String ico_ocupa) {
        EvenEstado.ico_ocupa = ico_ocupa;
    }

    public static String getIco_puerta() {
        return ico_puerta;
    }

    public static void setIco_puerta(String ico_puerta) {
        EvenEstado.ico_puerta = ico_puerta;
    }

    public static String getFec_amd() {
        return fec_amd;
    }

    public static void setFec_amd(String fec_amd) {
        EvenEstado.fec_amd = fec_amd;
    }

    public static String getFec_hms() {
        return fec_hms;
    }

    public static void setFec_hms(String fec_hms) {
        EvenEstado.fec_hms = fec_hms;
    }

    public static String getSon_limpie() {
        return son_limpie;
    }

    public static void setSon_limpie(String son_limpie) {
        EvenEstado.son_limpie = son_limpie;
    }

    public static String getSon_desocu() {
        return son_desocu;
    }

    public static void setSon_desocu(String son_desocu) {
        EvenEstado.son_desocu = son_desocu;
    }

    public static String getSon_libre() {
        return son_libre;
    }

    public static void setSon_libre(String son_libre) {
        EvenEstado.son_libre = son_libre;
    }

    public static String getSon_ocupa() {
        return son_ocupa;
    }

    public static void setSon_ocupa(String son_ocupa) {
        EvenEstado.son_ocupa = son_ocupa;
    }

    public static String getSon_pue_limp() {
        return son_pue_limp;
    }

    public static void setSon_pue_limp(String son_pue_limp) {
        EvenEstado.son_pue_limp = son_pue_limp;
    }

    public static String getSon_pue_cli() {
        return son_pue_cli;
    }

    public static void setSon_pue_cli(String son_pue_cli) {
        EvenEstado.son_pue_cli = son_pue_cli;
    }

    public static String getForm_nro_9D() {
        return form_nro_9D;
    }

    public static void setForm_nro_9D(String form_nro_9D) {
        EvenEstado.form_nro_9D = form_nro_9D;
    }

    public static String getEstdes_cancelar() {
        return estdes_cancelar;
    }

    public static void setEstdes_cancelar(String estdes_cancelar) {
        EvenEstado.estdes_cancelar = estdes_cancelar;
    }

    public static String getEstdes_sucio() {
        return estdes_sucio;
    }

    public static void setEstdes_sucio(String estdes_sucio) {
        EvenEstado.estdes_sucio = estdes_sucio;
    }

    public static String getEstdes_cliAbierto() {
        return estdes_cliAbierto;
    }

    public static void setEstdes_cliAbierto(String estdes_cliAbierto) {
        EvenEstado.estdes_cliAbierto = estdes_cliAbierto;
    }

    public static String getEstdes_limpAbierto() {
        return estdes_limpAbierto;
    }

    public static void setEstdes_limpAbierto(String estdes_limpAbierto) {
        EvenEstado.estdes_limpAbierto = estdes_limpAbierto;
    }

    public static String getEstdes_mante() {
        return estdes_mante;
    }

    public static void setEstdes_mante(String estdes_mante) {
        EvenEstado.estdes_mante = estdes_mante;
    }

    public static String getEstdes_cliingreso() {
        return estdes_cliingreso;
    }

    public static void setEstdes_cliingreso(String estdes_cliingreso) {
        EvenEstado.estdes_cliingreso = estdes_cliingreso;
    }

    public static String getEstdes_Limpiando() {
        return estdes_Limpiando;
    }

    public static void setEstdes_Limpiando(String estdes_Limpiando) {
        EvenEstado.estdes_Limpiando = estdes_Limpiando;
    }

    public static String getEst_CARGADOSTOCK() {
        return est_CARGADOSTOCK;
    }

    public static void setEst_CARGADOSTOCK(String est_CARGADOSTOCK) {
        EvenEstado.est_CARGADOSTOCK = est_CARGADOSTOCK;
    }

    public static String getCaja_APERTURA() {
        return caja_APERTURA;
    }

    public static void setCaja_APERTURA(String caja_APERTURA) {
        EvenEstado.caja_APERTURA = caja_APERTURA;
    }

    public static String getEst_PENDIENTE() {
        return est_PENDIENTE;
    }

    public static void setEst_PENDIENTE(String est_PENDIENTE) {
        EvenEstado.est_PENDIENTE = est_PENDIENTE;
    }

    public static String getEst_INGRESADO() {
        return est_INGRESADO;
    }

    public static void setEst_INGRESADO(String est_INGRESADO) {
        EvenEstado.est_INGRESADO = est_INGRESADO;
    }

    public static String getPer_CLIENTE() {
        return per_CLIENTE;
    }

    public static void setPer_CLIENTE(String per_CLIENTE) {
        EvenEstado.per_CLIENTE = per_CLIENTE;
    }

    public static String getPer_PERSONAL() {
        return per_PERSONAL;
    }

    public static void setPer_PERSONAL(String per_PERSONAL) {
        EvenEstado.per_PERSONAL = per_PERSONAL;
    }

    public static String getPer_PROVEEDOR() {
        return per_PROVEEDOR;
    }

    public static void setPer_PROVEEDOR(String per_PROVEEDOR) {
        EvenEstado.per_PROVEEDOR = per_PROVEEDOR;
    }

    public static String getPer_USUARIO() {
        return per_USUARIO;
    }

    public static void setPer_USUARIO(String per_USUARIO) {
        EvenEstado.per_USUARIO = per_USUARIO;
    }

    public static String getCaja_COMPRA() {
        return caja_COMPRA;
    }

    public static void setCaja_COMPRA(String caja_COMPRA) {
        EvenEstado.caja_COMPRA = caja_COMPRA;
    }

    public static String getCaja_GASTO() {
        return caja_GASTO;
    }

    public static void setCaja_GASTO(String caja_GASTO) {
        EvenEstado.caja_GASTO = caja_GASTO;
    }

    public static String getEst_Mudar() {
        return est_Mudar;
    }

    public static void setEst_Mudar(String est_Mudar) {
        EvenEstado.est_Mudar = est_Mudar;
    }

    public static String getEst_Cargado() {
        return est_Cargado;
    }

    public static void setEst_Cargado(String est_Cargado) {
        EvenEstado.est_Cargado = est_Cargado;
    }

    public static String getEst_Pagado() {
        return est_Pagado;
    }

    public static void setEst_Pagado(String est_Pagado) {
        EvenEstado.est_Pagado = est_Pagado;
    }

    public static String getEst_Cerrado() {
        return est_Cerrado;
    }

    public static void setEst_Cerrado(String est_Cerrado) {
        EvenEstado.est_Cerrado = est_Cerrado;
    }

    public static String getEst_Adelanto() {
        return est_Adelanto;
    }

    public static void setEst_Adelanto(String est_Adelanto) {
        EvenEstado.est_Adelanto = est_Adelanto;
    }

    public static String getTipo_hab_estandar() {
        return tipo_hab_estandar;
    }

    public static void setTipo_hab_estandar(String tipo_hab_estandar) {
        EvenEstado.tipo_hab_estandar = tipo_hab_estandar;
    }

    public static String getTipo_hab_vip() {
        return tipo_hab_vip;
    }

    public static void setTipo_hab_vip(String tipo_hab_vip) {
        EvenEstado.tipo_hab_vip = tipo_hab_vip;
    }

    public static String getTipo_hab_luxury() {
        return tipo_hab_luxury;
    }

    public static void setTipo_hab_luxury(String tipo_hab_luxury) {
        EvenEstado.tipo_hab_luxury = tipo_hab_luxury;
    }

    public static String getTipo_hab_penthouse() {
        return tipo_hab_penthouse;
    }

    public static void setTipo_hab_penthouse(String tipo_hab_penthouse) {
        EvenEstado.tipo_hab_penthouse = tipo_hab_penthouse;
    }

    public static String getMone_PYG() {
        return mone_PYG;
    }

    public static void setMone_PYG(String mone_PYG) {
        EvenEstado.mone_PYG = mone_PYG;
    }

    public static String getMone_USD() {
        return mone_USD;
    }

    public static void setMone_USD(String mone_USD) {
        EvenEstado.mone_USD = mone_USD;
    }

    public static String getMone_BRL() {
        return mone_BRL;
    }

    public static void setMone_BRL(String mone_BRL) {
        EvenEstado.mone_BRL = mone_BRL;
    }

    public static String getMone_ARS() {
        return mone_ARS;
    }

    public static void setMone_ARS(String mone_ARS) {
        EvenEstado.mone_ARS = mone_ARS;
    }

    public static String getForPago_Efectivo() {
        return forPago_Efectivo;
    }

    public static void setForPago_Efectivo(String forPago_Efectivo) {
        EvenEstado.forPago_Efectivo = forPago_Efectivo;
    }

    public static String getForPago_TarjetaDebito() {
        return forPago_TarjetaDebito;
    }

    public static void setForPago_TarjetaDebito(String forPago_TarjetaDebito) {
        EvenEstado.forPago_TarjetaDebito = forPago_TarjetaDebito;
    }

    public static String getForPago_TarjetaCredito() {
        return forPago_TarjetaCredito;
    }

    public static void setForPago_TarjetaCredito(String forPago_TarjetaCredito) {
        EvenEstado.forPago_TarjetaCredito = forPago_TarjetaCredito;
    }

    public static String getEst_Emitido() {
        return est_Emitido;
    }

    public static void setEst_Emitido(String est_Emitido) {
        EvenEstado.est_Emitido = est_Emitido;
    }

    public static String getEst_Anulado() {
        return est_Anulado;
    }

    public static void setEst_Anulado(String est_Anulado) {
        EvenEstado.est_Anulado = est_Anulado;
    }

    public static String getCond_Contado() {
        return cond_Contado;
    }

    public static void setCond_Contado(String cond_Contado) {
        EvenEstado.cond_Contado = cond_Contado;
    }

    public static String getCond_Credito() {
        return cond_Credito;
    }

    public static void setCond_Credito(String cond_Credito) {
        EvenEstado.cond_Credito = cond_Credito;
    }

    public static String getEst_Libre() {
        return est_Libre;
    }

    public static void setEst_Libre(String est_Libre) {
        EvenEstado.est_Libre = est_Libre;
    }

    public static String getEst_Ocupado() {
        return est_Ocupado;
    }

    public static void setEst_Ocupado(String est_Ocupado) {
        EvenEstado.est_Ocupado = est_Ocupado;
    }

    public static String getEst_Sucio() {
        return est_Sucio;
    }

    public static void setEst_Sucio(String est_Sucio) {
        EvenEstado.est_Sucio = est_Sucio;
    }

    public static String getEst_Limpiando() {
        return est_Limpiando;
    }

    public static void setEst_Limpiando(String est_Limpiando) {
        EvenEstado.est_Limpiando = est_Limpiando;
    }

    public static String getEst_Terminar() {
        return est_Terminar;
    }

    public static void setEst_Terminar(String est_Terminar) {
        EvenEstado.est_Terminar = est_Terminar;
    }

    public static String getEst_Cancelado() {
        return est_Cancelado;
    }

    public static void setEst_Cancelado(String est_Cancelado) {
        EvenEstado.est_Cancelado = est_Cancelado;
    }

    public static String getEst_Desocupado() {
        return est_Desocupado;
    }

    public static void setEst_Desocupado(String est_Desocupado) {
        EvenEstado.est_Desocupado = est_Desocupado;
    }
    
}
