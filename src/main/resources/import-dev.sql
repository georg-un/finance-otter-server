-- User 1
insert into users (user_id, first_name, last_name)
values ('user1', 'Anna', 'Adler');

-- User 2
insert into users (user_id, first_name, last_name)
values ('user2', 'Bernd', 'Bieber');

-- User 3
insert into users (user_id, first_name, last_name)
values ('user3', 'Claudia', 'Chamäleon');

-- Purchase 1
insert into purchases (purchase_id, buyer_id, date, category, shop, description)
values ('initPurchaseId1', 1, '2020-02-04 09:38:25', 'groceries', 'BILLA', 'needed chocolate');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId1', 1, 23.56, 1);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId2', 2, 12.30, 1);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId3', 3, 12.30, 1);

-- Purchase 2
insert into purchases (purchase_id, buyer_id, date, category, shop)
values ('initPurchaseId2', 2, '2019-12-28 15:15:34', 'tools', 'OBI');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId4', 1, 8.45, 2);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId5', 2, 8.45, 2);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId6', 3, 8.45, 2);

-- Purchase 3
insert into purchases (purchase_id, buyer_id, date, category, shop)
values ('initPurchaseId3', 2, '2020-02-15 23:48:11', 'groceries', 'Bierkanter');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId7', 2, 23.45, 3);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId8', 3, 23.45, 3);

-- Compensation 1
insert into purchases (purchase_id, buyer_id, date, is_compensation)
values ('initPurchaseId4', 2, '2020-02-23 15:21:56', true);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId9', 3, 20.00, 4);
