-- Categories
insert into categories (label, color) values ('groceries', '#287763');
insert into categories (label, color) values ('house & garden', '#1B5035');
insert into categories (label, color) values ('bars & restaurants', '#C65953');
insert into categories (label, color) values ('leisure activities', '#C19B44');
insert into categories (label, color) values ('fixed costs', '#1D4558');

-- User 1
insert into users (user_id, first_name, last_name, avatar_url)
values ('user1', 'Anna', 'Adler', 'https://images.pexels.com/photos/1855582/pexels-photo-1855582.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=250&w=420');

-- User 2
insert into users (user_id, first_name, last_name, avatar_url)
values ('user2', 'Bernd', 'Bieber', 'https://images.pexels.com/photos/1250426/pexels-photo-1250426.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=250&w=420');

-- User 3
insert into users (user_id, first_name, last_name, avatar_url)
values ('user3', 'Claudia', 'Chamäleon', 'https://images.pexels.com/photos/1878522/pexels-photo-1878522.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=250&w=420');

-- Purchase 1
insert into purchases (purchase_id, buyer_id, date, category_id, shop, description)
values ('initPurchaseId1', 1, '2020-02-04 09:38:25', 1, 'BILLA', 'needed chocolate');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId1', 1, 23.56, 1);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId2', 2, 12.30, 1);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId3', 3, 12.30, 1);

-- Purchase 2
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId2', 2, '2019-12-28 15:15:34', 2, 'OBI');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId4', 1, 8.45, 2);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId5', 2, 8.45, 2);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId6', 3, 8.45, 2);

-- Purchase 3
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId3', 2, '2020-02-15 23:48:11', 1, 'Bierkanter');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId7', 2, 23.45, 3);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId8', 3, 23.45, 3);

-- Compensation 1
insert into purchases (purchase_id, buyer_id, date, is_compensation)
values ('initPurchaseId4', 2, '2020-02-23 15:21:56', true);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId9', 3, 20.00, 4);

-- Purchase 5
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId5', 3, '2020-04-15 23:48:11', 1, 'Hofer');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId10', 1, 26.80, 5);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId11', 2, 26.80, 5);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId12', 3, 26.80, 5);

-- Purchase 6
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId6', 2, '2020-02-04 23:48:11', 3, 'GRU');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId13', 2, 24.50, 6);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId14', 3, 23.45, 6);

-- Purchase 7
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId7', 2, '2020-01-09 23:48:11', 3, 'Itoya Sushi');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId15', 2, 23.45, 7);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId16', 3, 23.45, 7);

-- Purchase 8
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId8', 1, '2020-02-15 23:48:11', 3, 'GRU');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId17', 1, 12.50, 8);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId18', 2, 12.50, 8);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId19', 3, 12.50, 8);

-- Purchase 9
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId9', 3, '2020-05-23 23:48:11', 1, 'Billa');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId20', 2, 3.45, 9);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId21', 3, 3.45, 9);

-- Purchase 10
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId10', 3, '2020-04-25 23:48:11', 1, 'Bierkanter');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId22', 2, 23.56, 10);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId23', 3, 23.56, 10);

-- Purchase 11
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId11', 1, '2020-02-15 23:48:11', 1, 'Brunnenmarkt');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId24', 2, 10, 11);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId25', 2, 10, 11);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId26', 3, 10, 11);

-- Purchase 12
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId12', 1, '2020-01-24 23:48:11', 4, 'Staatsoper');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId27', 1, 14.40, 12);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId28', 2, 14.40, 12);

-- Compensation 2
insert into purchases (purchase_id, buyer_id, date, is_compensation)
values ('initPurchaseId13', 1, '2020-01-15 15:21:56', true);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId29', 3, 38.00, 13);

-- Purchase 13
insert into purchases (purchase_id, buyer_id, date, category_id, shop)
values ('initPurchaseId14', 1, '2020-01-01 00:00:00', 5, 'Strom');

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId30', 1, 48.50, 14);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId31', 2, 48.50, 14);

insert into debits (debit_id, debtor_id, amount, purchase_gen_id)
values ('initDebitId32', 3, 48.50, 14);
