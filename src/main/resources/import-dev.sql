-- User 1
insert into users (user_id, username, first_name, last_name)
values ('user1', 'user1', 'Anna', 'Adler');

-- User 2
insert into users (user_id, username, first_name, last_name)
values ('user2', 'user2', 'Bernd', 'Bieber');

-- User 3
insert into users (user_id, username, first_name, last_name)
values ('user3', 'user3', 'Claudia', 'Chamäleon');

-- Purchase 1
insert into purchases (purchase_id, buyer_id, date, category, shop, description)
values ('initPurchaseId1', 1, current_timestamp, 'groceries', 'BILLA', 'needed chocolate');

insert into debits (debit_id, debtor_id, amount)
values ('initDebitId1', 1, 23.56);

insert into debits (debit_id, debtor_id, amount)
values ('initDebitId2', 2, 12.30);

insert into debits (debit_id, debtor_id, amount)
values ('initDebitId3', 3, 12.30);

insert into purchases_debits (purchase_gen_id, debits_gen_id)
values (1, 1);

insert into purchases_debits (purchase_gen_id, debits_gen_id)
values (1, 2);

insert into purchases_debits (purchase_gen_id, debits_gen_id)
values (1, 3);

-- Purchase 2
insert into purchases (purchase_id, buyer_id, date, category, shop)
values ('initPurchaseId2', 2, current_timestamp, 'tools', 'OBI');

insert into debits (debit_id, debtor_id, amount)
values ('initDebitId4', 1, 8.45);

insert into debits (debit_id, debtor_id, amount)
values ('initDebitId5', 2, 8.45);

insert into debits (debit_id, debtor_id, amount)
values ('initDebitId6', 3, 8.45);

insert into purchases_debits (purchase_gen_id, debits_gen_id)
values (2, 4);

insert into purchases_debits (purchase_gen_id, debits_gen_id)
values (2, 5);

insert into purchases_debits (purchase_gen_id, debits_gen_id)
values (2, 6);

-- Purchase 3
insert into purchases (purchase_id, buyer_id, date, category, shop)
values ('initPurchaseId3', 2, current_timestamp, 'beer', 'Bierkanter');

insert into debits (debit_id, debtor_id, amount)
values ('initDebitId7', 2, 23.45);

insert into debits (debit_id, debtor_id, amount)
values ('initDebitId8', 3, 23.45);

insert into purchases_debits (purchase_gen_id, debits_gen_id)
values (3, 7);

insert into purchases_debits (purchase_gen_id, debits_gen_id)
values (3, 8);
