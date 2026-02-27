<?php
class Connexion {
    private $connexion;

    public function __construct() {
        try {
            // Sur MAMP, le port MySQL est 8889
            $this->connexion = new PDO("mysql:host=localhost:8889;dbname=school1;charset=utf8", "root", "root");
            $this->connexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            die('Erreur : ' . $e->getMessage());
        }
    }

    public function getConnexion() {
        return $this->connexion;
    }
}
?>