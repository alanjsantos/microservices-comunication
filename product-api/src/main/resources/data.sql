INSERT INTO CATEGORY (id, description) VALUES (1000, 'Comic Books');
INSERT INTO CATEGORY (id, description) VALUES (1001, 'Movies');
INSERT INTO CATEGORY (id, description) VALUES (1002, 'Books');

INSERT INTO SUPPLIER VALUES (1000, 'Panini Commics');
INSERT INTO SUPPLIER VALUES (1001, 'Amazon');


INSERT INTO PRODUCT (id, name, FK_SUPPLIER, FK_CATEGORY, quantity_available) VALUES (1001, 'Crise nas Infinitas Terras', 1000, 1000, 10);
INSERT INTO PRODUCT (id, name, FK_SUPPLIER, FK_CATEGORY, quantity_available) VALUES (1002, 'Interestelar', 1002, 1002, 5);
INSERT INTO PRODUCT (id, name, FK_SUPPLIER, FK_CATEGORY, quantity_available) VALUES (1003, 'Herry Potter', 1001, 1002, 3);

commit;