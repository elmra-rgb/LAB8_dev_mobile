# LAB 8 – Consommation d'un Web Service PHP avec Volley

## Aperçu du projet

Ce laboratoire a pour objectif de développer une application Android complète communiquant avec un web service PHP via la bibliothèque Volley. L'application permet d'ajouter des étudiants à une base de données MySQL et d'afficher la liste mise à jour après chaque ajout.

---

## Architecture du projet

Le projet est structuré en deux parties distinctes :

1. **Backend PHP** : Web service RESTful développé en PHP 8, connecté à une base de données MySQL.
2. **Application Android** : Interface utilisateur développée en Java utilisant Volley pour les requêtes réseau et Gson pour le parsing JSON.

---

## Partie 1 – Configuration du serveur et de la base de données

### Étape 1.1 – Installation et configuration de MAMP

<img src="screenshots/mamp.png" width="600" alt="Configuration MAMP">

**Actions réalisées :**
- Installation de MAMP sur macOS (équivalent de XAMPP)
- Configuration du document root : `/Applications/MAMP/htdocs/`
- Démarrage des serveurs Apache et MySQL
- Configuration du port MySQL (par défaut : 8889)

### Étape 1.2 – Création de la base de données

```sql
CREATE DATABASE school1;
USE school1;
```

### Étape 1.3 – Création de la table Etudiant

```sql
CREATE TABLE Etudiant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    ville VARCHAR(50),
    sexe VARCHAR(10)
);
```

### Étape 1.4 – Insertion des données initiales

```sql
INSERT INTO Etudiant (nom, prenom, ville, sexe) VALUES 
('Alaoui', 'yahya', 'Rabat', 'homme'),
('Elhezzam', 'Rania', 'Rabat', 'femme'),
('Berrada', 'Hamza', 'Fes', 'homme');
```

### Résultat : Structure de la table initiale

<img src="screenshots/table_etudiant.png" width="800" alt="Table Etudiant initiale">

*La table contient 3 enregistrements de test pour valider le fonctionnement du web service.*

---

## Partie 2 – Développement du Web Service PHP

### Étape 2.1 – Structure du projet PHP

```
/Applications/MAMP/htdocs/lab8_dev/
├── classes/
│   └── Etudiant.php
├── connexion/
│   └── Connexion.php
├── dao/
│   └── IDao.php
├── service/
│   └── EtudiantService.php
└── ws/
    ├── createEtudiant.php
    └── loadEtudiant.php
```

### Étape 2.2 – Points d'entrée du web service

Deux web services ont été développés :

| Fichier | Méthode | Fonction |
|---------|---------|----------|
| `createEtudiant.php` | POST | Ajout d'un nouvel étudiant |
| `loadEtudiant.php` | GET | Récupération de tous les étudiants |

### Étape 2.3 – Test du service d'ajout (POST)

<img src="screenshots/test_creat.png" width="600" alt="Test POST avec REST Client">

**Requête envoyée :**
- URL : `http://localhost:8888/lab8_dev/ws/createEtudiant.php`
- Méthode : POST
- Body : x-www-form-urlencoded
- Paramètres : `nom=Dupont`, `prenom=Lina`, `ville=Paris`, `sexe=femme`

### Étape 2.4 – Réponse du service d'ajout

<img src="screenshots/result_creat.png" width="600" alt="Réponse JSON après ajout">

**Réponse JSON :**
```json
{
  "id": 1,
  "nom": "Alaoui",
  "prenom": "yahya",
  "ville": "Rabat",
  "sexe": "homme"
}
```

Le service retourne la liste complète mise à jour après chaque ajout.

### Étape 2.5 – Test du service de lecture (GET)

<img src="screenshots/test_load.png" width="600" alt="Test GET avec REST Client">

**Requête :** `GET http://localhost:8888/lab8_dev/ws/loadEtudiant.php`

### Étape 2.6 – Réponse du service de lecture

<img src="screenshots/result_load.png" width="600" alt="Réponse JSON liste étudiants">

**Réponse JSON complète :**
```json
[
  {"id": 1, "nom": "Alaoui", "prenom": "yahya", "ville": "Rabat", "sexe": "homme"},
  {"id": 2, "nom": "Elhezzam", "prenom": "Rania", "ville": "Rabat", "sexe": "femme"},
  {"id": 3, "nom": "Berrada", "preprenom": "Hamza", "ville": "Fes", "sexe": "homme"},
  {"id": 4, "nom": "Dupont", "prenom": "Lina", "ville": "Paris", "sexe": "femme"}
]
```

**Validation :** Les 4 étudiants sont correctement retournés au format JSON.

---

## Partie 3 – Application Android

### Étape 3.1 – Structure du projet Android

```
app/
├── manifests/
│   └── AndroidManifest.xml
├── java/com.example.lab8_dev/
│   ├── beans/
│   │   └── Etudiant.java
│   └── AddEtudiant.java
├── res/
│   ├── layout/
│   │   └── activity_add_etudiant.xml
│   ├── values/
│   │   └── strings.xml
│   └── xml/
│       └── network_security_config.xml
└── build.gradle
```

### Étape 3.2 – Interface d'ajout d'un étudiant

<img src="screenshots/ajout.png" width="300" alt="Interface d'ajout">

**Champs du formulaire :**
- Nom (EditText)
- Prénom (EditText)
- Ville (Spinner avec liste prédéfinie)
- Sexe (RadioGroup : Homme/Femme)
- Bouton "Ajouter"

### Étape 3.3 – Test d'ajout d'un étudiant

Un nouvel étudiant a été ajouté via l'application :

- **Nom** : hiba
- **Prénom** : himde
- **Ville** : Marrakech
- **Sexe** : Femme

<img src="screenshots/ajout.png" width="300" alt="Formulaire rempli">

### Étape 3.4 – Vérification dans la base de données

<img src="screenshots/data_result.png" width="800" alt="Données après ajout">

**État de la table après ajout :**

| id | nom     | prenom | ville     | sexe  |
|----|---------|--------|-----------|-------|
| 1  | Alaoui  | yahya  | Rabat     | homme |
| 2  | Elhezzam| Rania  | Rabat     | femme |
| 3  | Berrada | Hamza  | Fes       | homme |
| 4  | Dupont  | Lina   | Paris     | femme |
| 5  | hiba    | himde  | Marrakech | Femme |

### Étape 3.5 – Vérification via l'interface phpMyAdmin

<img src="screenshots/verifie_ajout.png" width="800" alt="phpMyAdmin après ajout">

*La table contient maintenant 5 étudiants, confirmant le bon fonctionnement de l'ajout.*

### Étape 3.6 – Analyse des logs de l'application

Les logs suivants ont été capturés lors de l'exécution de l'application, confirmant le bon fonctionnement des requêtes Volley et du parsing Gson.

#### Logcat - Réponse brute du serveur
<img src="screenshots/verif_logcat.png" width="600" alt="Logcat - Réponse JSON">

#### Logcat - Parsing des étudiants (1/2)
<img src="screenshots/verif_logcat2.png" width="600" alt="Logcat - Liste étudiants">

#### Logcat - Parsing des étudiants (2/2)
<img src="screenshots/verif_logcat3.png" width="600" alt="Logcat - Suite liste">

**Extrait des logs :**
```
D/RESPONSE: [{"id":1,"nom":"Alaoui","prenom":"yahya","ville":"Rabat","sexe":"homme"},
             {"id":2,"nom":"Elhezzam","prenom":"Rania","ville":"Rabat","sexe":"femme"},
             {"id":3,"nom":"Berrada","prenom":"Hamza","ville":"Fes","sexe":"homme"},
             {"id":4,"nom":"Dupont","prenom":"Lina","ville":"Paris","sexe":"femme"}]

D/ETUDIANT: 1 - Alaoui yahya (Rabat, homme)
D/ETUDIANT: 2 - Elhezzam Rania (Rabat, femme)
D/ETUDIANT: 3 - Berrada Hamza (Fes, homme)
D/ETUDIANT: 4 - Dupont Lina (Paris, femme)
```


---

## Points techniques abordés

- **PDO MySQL** : Connexion sécurisée à la base de données
- **Web service RESTful** : Points d'entrée GET et POST
- **JSON** : Format d'échange de données
- **Volley** : Bibliothèque de requêtes réseau pour Android
- **Gson** : Parsing JSON en objets Java
- **Threading** : Gestion asynchrone des requêtes réseau
- **Material Design** : Interface utilisateur moderne
- **Logcat** : Débogage et validation des échanges

