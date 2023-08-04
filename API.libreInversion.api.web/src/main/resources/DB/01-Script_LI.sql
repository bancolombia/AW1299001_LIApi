
--DROP TABLE "VENTASDBC"."JSONLI"
--@

CREATE TABLE "VENTASDBC"."JSONLI" (
		"ID" BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 NO CYCLE CACHE 20 NO ORDER ), 
		"FECHA" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		"ID_SESSION" VARCHAR(100), 
		"PASO" VARCHAR(30), 
		"JSON" BLOB(1572864), 
		CONSTRAINT "JSONLI_PK" PRIMARY KEY("ID")
	)
@

CREATE INDEX "VENTASDBC"."JSONLI_ID_SESSION"
	ON "VENTASDBC"."JSONLI"
	("ID_SESSION" ASC)
@

CREATE INDEX "VENTASDBC"."JSONLI_PASO"
	ON "VENTASDBC"."JSONLI"
	("PASO" ASC)
@

CREATE INDEX "VENTASDBC"."JSONLI_SESSION_PASO"
	ON "VENTASDBC"."JSONLI"
	("ID_SESSION" ASC, "PASO" ASC)
@

CREATE INDEX "VENTASDBC"."JSONLI_PASO_SESSION"
	ON "VENTASDBC"."JSONLI"
	("PASO" ASC, "ID_SESSION" ASC)
@


CREATE INDEX "VENTASDBC"."JSONLI_ID"
	ON "VENTASDBC"."JSONLI"
	("ID" ASC)
@



--DROP TABLE "VENTASDBC"."LI_AUTENTICACION"
--@
CREATE TABLE "VENTASDBC"."LI_AUTENTICACION" (
        "ID" BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 NO CYCLE CACHE 20 NO ORDER ), 
        "FECHA" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        "ID_SESSION" VARCHAR(100), 
        "PASO" VARCHAR(30), 
        "JTI_ACTUAL"  VARCHAR (500) NOT NULL UNIQUE,
        CONSTRAINT "LI_AUTENTICACION_PK" PRIMARY KEY("ID")
)
@


--DROP TABLE "VENTASDBC"."LI_SESSION"
--@
CREATE TABLE "VENTASDBC"."LI_SESSION" (
        "ID" BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 NO CYCLE CACHE 20 NO ORDER ), 
        "FECHA" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        "ID_SESSION" VARCHAR(100)  NOT NULL UNIQUE,
        "USUARIO" VARCHAR(100),
        "ESTADO" VARCHAR(1) DEFAULT '1',
        CONSTRAINT "LI_SESSION_PK" PRIMARY KEY("ID")
)
@


--DROP TABLE "VENTASDBC"."JSONLI_HIST"
--@

CREATE TABLE "VENTASDBC"."JSONLI_HIST" (
		"ID" BIGINT NOT NULL , 
		"FECHA" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		"ID_SESSION" VARCHAR(100), 
		"PASO" VARCHAR(30), 
		"JSON" BLOB(1572864), 
		CONSTRAINT "JSONLI_PK" PRIMARY KEY("ID")
	)
@


CREATE INDEX "VENTASDBC"."JSONLI_HIST_ID_SESSION"
	ON "VENTASDBC"."JSONLI_HIST"
	("ID_SESSION" ASC)
@

CREATE INDEX "VENTASDBC"."JSONLI_HIST_PASO"
	ON "VENTASDBC"."JSONLI_HIST"
	("PASO" ASC)
@
CREATE INDEX "VENTASDBC"."JSONLI_HIST_FECHA"
	ON "VENTASDBC"."JSONLI_HIST"
	("FECHA" ASC)
@
CREATE INDEX "VENTASDBC"."JSONLI_FECHA"
	ON "VENTASDBC"."JSONLI"
	("FECHA" ASC)
@



