-- User 1
insert into users (username, first_name, last_name)
values ('user1', 'Anna', 'Adler');

-- User 2
insert into users (username, first_name, last_name)
values ('user2', 'Bernd', 'Bieber');

-- User 3
insert into users (username, first_name, last_name)
values ('user3', 'Claudia', 'Chamäleon');

-- Purchase 1
insert into purchases (purchase_id, owner_id, date, category, shop, description)
values ('initPurchaseId1', 1, current_timestamp, 'groceries', 'BILLA', 'needed chocolate');

insert into debits (debit_id, payer_id, debtor_id, amount)
values ('initDebitId1', 1, 1, 23.56);

insert into debits (debit_id, payer_id, debtor_id, amount)
values ('initDebitId2', 1, 2, 12.30);

insert into debits (debit_id, payer_id, debtor_id, amount)
values ('initDebitId3', 1, 3, 12.30);

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId1', 'initDebitId1');

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId1', 'initDebitId2');

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId1', 'initDebitId3');

-- Purchase 2
insert into purchases (purchase_id, owner_id, date, category, shop)
values ('initPurchaseId2', 2, current_timestamp, 'tools', 'OBI');

insert into debits (debit_id, payer_id, debtor_id, amount)
values ('initDebitId4', 2, 1, 8.45);

insert into debits (debit_id, payer_id, debtor_id, amount)
values ('initDebitId5', 2, 2, 8.45);

insert into debits (debit_id, payer_id, debtor_id, amount)
values ('initDebitId6', 2, 3, 8.45);

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId2', 'initDebitId4');

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId2', 'initDebitId5');

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId2', 'initDebitId6');

-- Purchase 3
insert into purchases (purchase_id, owner_id, date, category, shop)
values ('initPurchaseId3', 2, current_timestamp, 'beer', 'Bierkanter');

insert into debits (debit_id, payer_id, debtor_id, amount)
values ('initDebitId7', 2, 2, 23.45);

insert into debits (debit_id, payer_id, debtor_id, amount)
values ('initDebitId8', 2, 3, 23.45);

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId3', 'initDebitId7');

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId3', 'initDebitId8');
