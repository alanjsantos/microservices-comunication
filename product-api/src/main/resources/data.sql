INSERT INTO SUPPLIER (id, name) VALUES (1, 'Panini Commics');
INSERT INTO SUPPLIER VALUES (2, 'Amazon');

INSERT INTO CATEGORY (id, description) VALUES (1, 'Comic Books');
INSERT INTO CATEGORY (id, description) VALUES (2, 'Movies');
INSERT INTO CATEGORY (id, description) VALUES (3, 'Books');

INSERT INTO PRODUCT (id, name, FK_SUPPLIER, FK_CATEGORY, quantity_available) VALUES (1, 'Crise nas Infinitas Terras', 1, 1, 10);
INSERT INTO PRODUCT (id, name, FK_SUPPLIER, FK_CATEGORY, quantity_available) VALUES (2, 'Interestelar', 2, 2, 5);
INSERT INTO PRODUCT (id, name, FK_SUPPLIER, FK_CATEGORY, quantity_available) VALUES (3, 'Herry Potter', 2, 3, 3);

commit;







