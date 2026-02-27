<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");
header('Content-Type: application/json');

include_once __DIR__ . '/../service/EtudiantService.php';

$es = new EtudiantService();
echo json_encode($es->findAllApi());
?>