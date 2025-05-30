
# 🖥️ Easy Way – Application de Bureau JavaFX pour la Gestion de Transport

## 🧭 Aperçu

Easy Way est une application desktop développée en **JavaFX** avec interface **FXML**, connectée à une base de données **MySQL**. Ce projet a été conçu dans le cadre du module **PIDEV 3A** à *Esprit School of Engineering*, et vise à fournir un outil complet pour la gestion des opérations de transport en environnement local : trajets, véhicules, utilisateurs, réclamations, covoiturage, et événements.

---

## 📑 Table des Matières

- [Fonctionnalités](#-fonctionnalités)
- [Architecture Technique](#-architecture-technique)
- [Installation](#-installation)
- [Utilisation](#-utilisation)
- [Contribuer](#-contribuer)
- [Licence](#-licence)
- [Crédits](#-crédits)

---

## ✅ Fonctionnalités

### 🔐 Gestion des utilisateurs

- Authentification locale
- Hashage des mots de passe
- Réinitialisation de mot de passe
- Validation d’email
- Captcha local simple

### 🗺️ Gestion des trajets

- Affichage cartographique via **WebView + OpenStreetMap**
- Suivi manuel des trajets
- Réservations avec simulation de paiement
- Gestion des lignes par l’admin

### 🚗 Gestion des véhicules

- Ajout, modification, suppression
- Affectation à des lignes

### 📨 Gestion des réclamations

- Formulaire de réclamation
- Notification email (via JavaMail)
- Visualisation statistique avec JavaFX Charts

### 🚘 Gestion du covoiturage

- Réservation de sièges
- Notification par email
- Paiement simulé

### 📢 Gestion des événements

- Affichage des grèves, incidents, retards
- Statistiques en graphiques
- Recherche dynamique

---

## 🧰 Architecture Technique

- **Langage** : Java 17+
- **Interface** : JavaFX avec FXML
- **Base de données** : MySQL
- **Connexion** : JDBC
- **Emailing** : JavaMail
- **Visualisation** : JavaFX Charts
- **Sécurité** : BCrypt, validation d’email

---

## ⚙️ Installation

1. **Pré-requis :**
    - Java JDK 17 ou plus
    - Maven ou JavaFX SDK
    - XAMPP pour MySQL

2. **Cloner le projet :**
   ```bash
   git clone https://github.com/<votre-utilisateur>/easy-way-desktop.git
   cd easy-way-desktop
   ```

3. **Configurer la base de données :**
    - Créer la base `easy_way` via phpMyAdmin
    - Importer les tables via le script SQL fourni

4. **Configurer les connexions :**
    - Modifier les infos de connexion MySQL dans `Database.java`
    - Paramétrer l’envoi de mails dans `MailService.java`

5. **Compiler et exécuter :**
    - Avec Maven : `mvn javafx:run`
    - Ou via votre IDE (IntelliJ / Eclipse / NetBeans)

---

## ▶️ Utilisation

- Lancer l’application
- Se connecter avec un compte admin ou utilisateur
- Accéder aux modules via le tableau de bord

---

## 🤝 Contribuer

1. Fork le projet
2. Crée ta branche : `git checkout -b feature/ma-feature`
3. Commit : `git commit -m "Ajoute ma fonctionnalité"`
4. Push : `git push origin feature/ma-feature`
5. Crée une Pull Request

---

## 📜 Licence

Ce projet est sous licence **MIT** – voir le fichier [LICENSE](LICENSE) pour plus d’informations.

---

## 🙏 Crédits

Développé dans le cadre du module **PIDEV 3A** à [Esprit School of Engineering](https://esprit.tn)  
Encadré par : *Mouhamed Yassin Ben Dhaya*
