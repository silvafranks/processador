
--Iniciando dados junto com a aplicação!
insert into cliente(CEP,EMAIL,NAME,SENHA,SOBRENOME) values(1234,'f@f.com','f',123,'f');
insert into CONTAS(BANCO,FLAG,ULTIMA_MOVIMENTACAO,VALOR_DISPONIVEL,CLIENTE_ID) values('Itau_Unibanco','Ativo','2022-11-21 17:35:33.734','2000.00',1);
insert into CONTAS(BANCO,FLAG,ULTIMA_MOVIMENTACAO,VALOR_DISPONIVEL,CLIENTE_ID) values('Banco_do_Brasil','Ativo','2022-11-21 17:35:33.734','100.50',1);
insert into CONTAS(BANCO,FLAG,ULTIMA_MOVIMENTACAO,VALOR_DISPONIVEL,CLIENTE_ID) values('Bradesco','Desativado','2022-11-21 17:35:33.734','200.00',1);