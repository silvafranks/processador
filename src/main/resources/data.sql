
-- Iniciando dados junto com a aplicação!
insert into tb_role values('ee6de6aa-a0ab-4ca1-863d-be5d2fc49ee0', 'ROLE_USUARIO');

insert into cliente(CEP,EMAIL,NAME,SENHA,SOBRENOME)
    values(1234,'f@f.com','f','$2a$10$giDdBOwiQQoD4o/o2aqorujjPDsko9IJkvlNyYgUwq.hqTbURhYi6','f');

INSERT INTO TB_USER_ROLES VALUES (1,'ee6de6aa-a0ab-4ca1-863d-be5d2fc49ee0');

insert into CONTAS(BANCO,FLAG,ULTIMA_MOVIMENTACAO,VALOR_DISPONIVEL,CLIENTE_ID)
    values('Itau_Unibanco','Ativo','2022-11-21 17:35:33.734','2000.00',1);

insert into CONTAS(BANCO,FLAG,ULTIMA_MOVIMENTACAO,VALOR_DISPONIVEL,CLIENTE_ID)
    values('Banco_do_Brasil','Ativo','2022-11-21 17:35:33.734','100.50',1);

insert into CONTAS(BANCO,FLAG,ULTIMA_MOVIMENTACAO,VALOR_DISPONIVEL,CLIENTE_ID)
    values('Bradesco','Desativado','2022-11-21 17:35:33.734','200.00',1);

insert into TRANSACOES(DATA_TRANSACAO,TYPE,VALOR_TRANSFERENCIA,CONTA_ENTRADA_ID,CONTA_SAIDA_ID)
    values(NOW(),'Interno','100.50',2,1);

insert into TRANSACOES(DATA_TRANSACAO,TYPE,VALOR_TRANSFERENCIA,CONTA_ENTRADA_ID,CONTA_SAIDA_ID)
    values(NOW(),'Interno','200.70',1,2);