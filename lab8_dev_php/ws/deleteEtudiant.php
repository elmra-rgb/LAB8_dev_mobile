<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, DELETE, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");

if ($_SERVER["REQUEST_METHOD"] == "POST" || $_SERVER["REQUEST_METHOD"] == "DELETE") {
    include_once __DIR__ . '/../service/EtudiantService.php';
    
    $id = $_POST['id'] ?? $_GET['id'] ?? 0;
    
    $es = new EtudiantService();
    $etudiant = $es->findById($id);
    
    if ($etudiant) {
        $es->delete($etudiant);
    }

    header('Content-Type: application/json');
    echo json_encode($es->findAllApi());
}
?>