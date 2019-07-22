CREATE TABLE aries_usuario(
   id_usuario NUMBER(9),
   usuario VARCHAR2(15),
   clave VARCHAR2(80),
   correo VARCHAR2(30) DEFAULT '',
   nombres VARCHAR2(40) DEFAULT '',
   paterno VARCHAR2(15) DEFAULT '',
   materno VARCHAR2(15) DEFAULT '',
   cargo VARCHAR2(50) DEFAULT '',
   ci VARCHAR2(15) DEFAULT '',
   fecha_cancelacion DATE ,
   fecha_creacion DATE DEFAULT SYSDATE,
   eliminado NUMBER DEFAULT 0,
   estado VARCHAR2(3),
   CONSTRAINT aries_usuario_pk PRIMARY KEY(id_usuario)
);

CREATE TABLE aries_roles(
   id_rol NUMBER(9),
   descripcion_rol VARCHAR2(15),
   fecha_apertura DATE,
   eliminado NUMBER DEFAULT 0,
   estado VARCHAR2(3) DEFAULT '',
   CONSTRAINT aries_roles_pk PRIMARY KEY(id_rol)
);

CREATE TABLE aries_usuario_rol(
    id_usuario_rol NUMBER,
    id_usuario NUMBER,
    id_rol NUMBER,
    eliminado NUMBER DEFAULT 0,
    gestion NUMBER,
    CONSTRAINT aries_usuario_rol_pk PRIMARY KEY(id_usuario_rol),
    CONSTRAINT aries_usuario_fk 
        FOREIGN KEY (id_usuario) 
        REFERENCES aries_usuario(id_usuario),
    CONSTRAINT aries_roles_fk 
        FOREIGN KEY (id_rol) 
        REFERENCES aries_roles(id_rol)
);
INSERT INTO aries_usuario(id_usuario,usuario,clave,correo,nombres,paterno,materno,cargo,ci,fecha_cancelacion,fecha_creacion,eliminado,estado) VALUES (1,'root','$2a$04$4vwa/ugGbBVDvbWaKUVZBuJbjyQyj6tqntjSmG8q.hi97.xSdhj/2','bhgonzales@gmail.com','Brayan Huber','Gonzales','Velasquez','PROGRAMADOR DE SERVICIO','9103168',TO_DATE('31/12/19','DD/MM/YYYY'),TO_DATE('22/07/19','DD/MM/YYYY'),0,'H');COMMIT;
INSERT INTO aries_roles(id_rol,descripcion_rol,fecha_apertura,eliminado,estado) VALUES(1,'ADMINISTRADOR',TO_DATE('22/07/19','DD/MM/YYYY'),0,'H');COMMIT;
INSERT INTO aries_roles(id_rol,descripcion_rol,fecha_apertura,eliminado,estado) VALUES(2,'CAJERO',TO_DATE('22/07/19','DD/MM/YYYY'),0,'H');COMMIT;
INSERT INTO aries_usuario_rol(id_usuario_rol,id_usuario,id_rol,eliminado,gestion) VALUES (1,1,1,0,2019);COMMIT;
INSERT INTO aries_usuario_rol(id_usuario_rol,id_usuario,id_rol,eliminado,gestion) VALUES (2,1,2,0,2019);COMMIT;




CREATE OR REPLACE PACKAGE  PKG_ARIES_ADM IS
    TYPE TAB_USUARIO IS TABLE OF ARIES_USUARIO%ROWTYPE;
    TYPE TAB_ROLES IS TABLE OF ARIES_ROLES%ROWTYPE;
    FUNCTION GET_USUARIO(P_USUARIO VARCHAR2) RETURN TAB_USUARIO PIPELINED;
    FUNCTION GET_ROL_USUARIO(P_USUARIO VARCHAR2,P_GESTION NUMBER) RETURN TAB_ROLES PIPELINED;
END PKG_ARIES_ADM;

CREATE OR REPLACE PACKAGE BODY PKG_ARIES_ADM IS
    FUNCTION GET_USUARIO(P_USUARIO VARCHAR2) RETURN TAB_USUARIO PIPELINED
    IS
    BEGIN
        FOR ROW_USUARIO IN (SELECT * 
                            FROM ARIES_USUARIO 
                            WHERE USUARIO=P_USUARIO AND ELIMINADO=0 AND ESTADO='H')
        LOOP
            PIPE ROW(ROW_USUARIO);
        END LOOP;               
        RETURN;             
    END GET_USUARIO;

    FUNCTION GET_ROL_USUARIO(P_USUARIO VARCHAR2,P_GESTION NUMBER) RETURN TAB_ROLES PIPELINED
    IS
    BEGIN
        FOR ROW_ROLES IN (SELECT T2.*
                          FROM ARIES_USUARIO_ROL T1
                          INNER JOIN ARIES_ROLES T2 ON T1.ID_ROL=T2.ID_ROL
                          INNER JOIN ARIES_USUARIO T3 ON T1.ID_USUARIO=T3.ID_USUARIO
                          WHERE T3.USUARIO=P_USUARIO AND T1.ELIMINADO=0 AND T1.GESTION=P_GESTION)
        LOOP
            PIPE ROW(ROW_ROLES);
        END LOOP;               
        RETURN;           
    END GET_ROL_USUARIO;
END PKG_ARIES_ADM;

