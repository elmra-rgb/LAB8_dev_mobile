package com.example.lab8_deve.beans;


public class Etudiant {
    private int identifiant;
    private String nom;
    private String prenom;
    private String localite;
    private String genre;

    // Constructeur vide
    public Etudiant() {}

    // Constructeur avec paramètres
    public Etudiant(int identifiant, String nom, String prenom, String localite, String genre) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.localite = localite;
        this.genre = genre;
    }

    // Getters et Setters
    public int getIdentifiant() { return identifiant; }
    public void setIdentifiant(int identifiant) { this.identifiant = identifiant; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getLocalite() { return localite; }
    public void setLocalite(String localite) { this.localite = localite; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    @Override
    public String toString() {
        return identifiant + " - " + nom + " " + prenom + " (" + localite + ", " + genre + ")";
    }
}