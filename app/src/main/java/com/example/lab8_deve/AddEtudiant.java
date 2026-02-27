package com.example.lab8_deve;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.lab8_deve.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {

    // Déclaration des composants UI
    private EditText champIdentite;      // correspond à @+id/saisie_identite
    private EditText champPrenom;        // correspond à @+id/saisie_prenom
    private Spinner spinnerLocalite;      // correspond à @+id/choix_localite
    private RadioGroup groupeGenre;       // correspond à @+id/groupe_genre
    private RadioButton radioMasculin;    // correspond à @+id/choix_masculin
    private RadioButton radioFeminin;     // correspond à @+id/choix_feminin
    private Button boutonEnvoi;           // correspond à @+id/bouton_envoi

    private RequestQueue fileRequetes;

    private static final String URL_INSERTION = "http://10.0.2.2:8888/lab8_deve/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        initialiserComposants();
        fileRequetes = Volley.newRequestQueue(this);
        boutonEnvoi.setOnClickListener(this);
    }

    private void initialiserComposants() {
        // Ces IDs doivent correspondre EXACTEMENT à ceux dans le XML
        champIdentite = findViewById(R.id.saisie_identite);
        champPrenom = findViewById(R.id.saisie_prenom);
        spinnerLocalite = findViewById(R.id.choix_localite);
        groupeGenre = findViewById(R.id.groupe_genre);
        radioMasculin = findViewById(R.id.choix_masculin);
        radioFeminin = findViewById(R.id.choix_feminin);
        boutonEnvoi = findViewById(R.id.bouton_envoi);
    }

    @Override
    public void onClick(View v) {
        if (v == boutonEnvoi) {
            envoyerDonnees();
        }
    }

    private void envoyerDonnees() {
        String identite = champIdentite.getText().toString().trim();
        String prenom = champPrenom.getText().toString().trim();

        if (identite.isEmpty() || prenom.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest requete = new StringRequest(
                Request.Method.POST,
                URL_INSERTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SUCCESS", "Réponse: " + response);
                        traiterReponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "Erreur Volley: " + error.toString());
                        Toast.makeText(AddEtudiant.this,
                                "Erreur de connexion au serveur",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                String genre = radioMasculin.isChecked() ? "homme" : "femme";
                Map<String, String> params = new HashMap<>();
                params.put("nom", champIdentite.getText().toString().trim());
                params.put("prenom", champPrenom.getText().toString().trim());
                params.put("ville", spinnerLocalite.getSelectedItem().toString());
                params.put("sexe", genre);
                return params;
            }
        };

        fileRequetes.add(requete);
    }

    private void traiterReponse(String response) {
        try {
            Type typeListe = new TypeToken<Collection<Etudiant>>(){}.getType();
            Collection<Etudiant> etudiants = new Gson().fromJson(response, typeListe);

            for (Etudiant e : etudiants) {
                Log.d("ETUDIANT", e.toString());
            }

            Toast.makeText(this,
                    "Étudiant ajouté avec succès!",
                    Toast.LENGTH_SHORT).show();

            // Réinitialiser le formulaire
            champIdentite.setText("");
            champPrenom.setText("");
            spinnerLocalite.setSelection(0);
            radioMasculin.setChecked(true);

        } catch (Exception e) {
            Log.e("PARSING_ERROR", "Erreur de parsing: " + e.getMessage());
        }
    }
}