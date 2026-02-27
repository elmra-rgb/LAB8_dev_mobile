<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, PUT, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include_once __DIR__ . '/../service/EtudiantService.php';
    
    $id = $_POST['id'] ?? 0;
    $nom = $_POST['nom'] ?? '';
    $prenom = $_POST['prenom'] ?? '';
    $ville = $_POST['ville'] ?? '';
    $sexe = $_POST['sexe'] ?? '';
    
    $es = new EtudiantService();
    $es->update(new Etudiant($id, $nom, $prenom, $ville, $sexe));

    header('Content-Type: application/json');
    echo json_encode($es->findAllApi());
}
?>