-- User 1
insert into users (username, first_name, last_name)
values ('user1', 'Anna', 'Adler');

-- User 2
insert into users (username, first_name, last_name)
values ('user2', 'Bernd', 'Bieber');

-- Purchase 1
insert into purchases (purchase_id, owner_id, date, category, shop, description)
values ('initPurchaseId1', 1, current_timestamp, 'groceries', 'BILLA', 'needed chocolate');

insert into debits (debit_id, amount, debtor_id, payer_id)
values ('initDebitId1', 1, 2, 12.30);

insert into debits (debit_id, amount, debtor_id, payer_id)
values ('initDebitId2', 1, 1, 23.56);

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId1', 'initDebitId1');

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId1', 'initDebitId2');

-- Purchase 2
insert into purchases (purchase_id, owner_id, date, category, shop)
values ('initPurchaseId2', 2, current_timestamp, 'tools', 'OBI');

insert into debits (debit_id, amount, debtor_id, payer_id)
values ('initDebitId3', 2, 1, 8.45);

insert into debits (debit_id, amount, debtor_id, payer_id)
values ('initDebitId4', 2, 2, 8.45);

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId2', 'initDebitId3');

insert into purchases_debits (purchase_purchase_id, debits_debit_id)
values ('initPurchaseId2', 'initDebitId4');
