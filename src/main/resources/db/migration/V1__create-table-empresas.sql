CREATE TABLE tb_lst_empresa (
    id_empresa    INTEGER NOT NULL,
    cnpj          INTEGER NOT NULL,
    nome_fantasia VARCHAR2(100) NOT NULL,
    razao_social  VARCHAR2(100) NOT NULL,
    telefone      INTEGER NOT NULL,
    email         VARCHAR2(100) NOT NULL
);

ALTER TABLE tb_lst_empresa ADD CONSTRAINT tb_lst_emp_id_empresa_pk PRIMARY KEY ( id_empresa );
