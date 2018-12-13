DROP TABLE IDENTIFIANT;

CREATE TABLE IDENTIFIANT (
	ID MEDIUMINT NOT NULL AUTO_INCREMENT,
        LOGIN VARCHAR(10) NOT NULL,
        PASSWORD VARCHAR(10) NOT NULL,
	PRIMARY KEY (ID)
);

INSERT INTO IDENTIFIANT (login,password) VALUES
    ("admin","admin");


DROP TABLE EMPLOYES;

CREATE TABLE EMPLOYES (
	ID MEDIUMINT NOT NULL  AUTO_INCREMENT,
	NOM VARCHAR(25) NOT NULL,
	PRENOM VARCHAR(25) NOT NULL,
	TELDOM VARCHAR(10) NOT NULL,
	TELPORT VARCHAR(10) NOT NULL,
	TELPRO VARCHAR(10) NOT NULL,
	ADRESSE VARCHAR(150) NOT NULL,
	CODEPOSTAL VARCHAR(5) NOT NULL,
	VILLE VARCHAR(25) NOT NULL,
	EMAIL VARCHAR(25) NOT NULL,
	PRIMARY KEY (ID)
);

INSERT INTO EMPLOYES(NOM,PRENOM,TELDOM,TELPORT,TELPRO,ADRESSE,CODEPOSTAL,VILLE,EMAIL) VALUES
('Turing','Alan','0123456789','0612345678','0698765432','2 rue des Automates','92700','Colombes','aturing@efrei.fr'),
('Galois','Evariste','0145362787','0645362718','0611563477','21 rue des Morts-trop-jeunes','92270','Bois-colombes','egalois@efrei.fr'),
('Boole','George','0187665987','0623334256','0654778654','65 rue de la Logique','92700','Colombes','gboole@efrei.fr'),
('Gauss','Carl Friedrich','0187611987','0783334256','0658878654','6 rue des Transformations','75016','Paris','cgauss@efrei.fr'),
('Pascal','Blaise','0187384987','0622494256','0674178654','39 bvd de Port-Royal','21000','Dijon','bpascal@efrei.fr'),
('Euler','Leonhard','0122456678','0699854673','0623445166','140 avenue Complexe','90000','Nanterre','leuler@efrei.fr'),
('Curie','Pierre', '0123456789','0606060606','0606060607', '45 rue Nagazaki', '01000','Paris','pcurry@efrei.fr'),
('Curie','Marie', '0123456780','0706060607','0706060607', '45 rue Hiroshima', '02000','Paris','mcurry@efrei.fr'),
('Chollet','Kilian', '0123456790','0606060888','0606060889', '123 rue du soleil', '03000','Marseille','kchollet@efrei.fr'),
('Lucas','Amauray', '0123456791','0606060999','0606060990', '131 rue Rinner', '04000','Pointe-à-Pitre','alucas@efrei.fr'),
('Masson','Lucas', '0123456792','0606060000','0606060001', '97 place du 18 juin 1940', '05000','Quimper','lmasson@efrei.fr');

CREATE USER 'adm'@'localhost' IDENTIFIED BY 'adm';
GRANT ALL PRIVILEGES ON projet.* to 'adm'@'localhost'