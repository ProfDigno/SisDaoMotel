CREATE TABLE "rh_liquidacion" (
	"idrh_liquidacion" INTEGER NOT NULL ,
	"fecha_creado" TIMESTAMP NOT NULL ,
	"creado_por" TEXT NOT NULL ,
	"fecha_desde" DATE NOT NULL ,
	"fecha_hasta" DATE NOT NULL ,
	"estado" TEXT NOT NULL ,
	"es_cerrado" BOOLEAN NOT NULL ,
	"monto_vale" NUMERIC(14,0) NOT NULL ,
	"monto_descuento" NUMERIC(14,0) NOT NULL ,
	"monto_liquidacion" NUMERIC(14,0) NOT NULL ,
	"salario_base" NUMERIC(14,0) NOT NULL ,
	"monto_letra" TEXT NOT NULL ,
	"fk_idpersona" INTEGER NOT NULL ,
	PRIMARY KEY("idrh_liquidacion")
);
CREATE TABLE "rh_entrada" (
	"idrh_entrada" INTEGER NOT NULL ,
	"fecha_creado" TIMESTAMP NOT NULL ,
	"creado_por" TEXT NOT NULL ,
	"fecha_entrada" TIMESTAMP NOT NULL ,
	"fecha_salida" TIMESTAMP NOT NULL ,
	"turno" TEXT NOT NULL ,
	"es_cerrado" BOOLEAN NOT NULL ,
	"es_entrada" BOOLEAN NOT NULL ,
	"es_salida" BOOLEAN NOT NULL ,
	"fk_idpersona" INTEGER NOT NULL ,
	"fk_idusuario" INTEGER NOT NULL ,
	"fk_idrh_turno" INTEGER NOT NULL ,
	PRIMARY KEY("idrh_entrada")
);
CREATE TABLE "venta_eliminar" (
	"idventa_eliminar" INTEGER NOT NULL ,
	"fecha_creado" TIMESTAMP NOT NULL ,
	"creado_por" TEXT NOT NULL ,
	"monto_letra" TEXT NOT NULL ,
	"estado" TEXT NOT NULL ,
	"observacion" TEXT NOT NULL ,
	"motivo_anulacion" TEXT NOT NULL ,
	"monto" NUMERIC(14,0) NOT NULL ,
	"fk_idusuario" INTEGER NOT NULL ,
	"fk_idpersona" INTEGER NOT NULL ,
	PRIMARY KEY("idventa_eliminar")
);
CREATE TABLE "venta_item_eliminar" (
	"idventa_item_eliminar" INTEGER NOT NULL ,
	"fecha_creado" TIMESTAMP NOT NULL ,
	"creado_por" TEXT NOT NULL ,
	"tipo_item" TEXT NOT NULL ,
	"descripcion" TEXT NOT NULL ,
	"cantidad" NUMERIC(5,0) NOT NULL ,
	"precio_venta" NUMERIC(10,0) NOT NULL ,
	"precio_compra" NUMERIC(10,0) NOT NULL ,
	"fk_idproducto" INTEGER NOT NULL ,
	"fk_idventa_eliminar" INTEGER NOT NULL ,
	PRIMARY KEY("idventa_item_eliminar")
);