package com.example.joaovitor_atividade_06;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    private Button btnSair;
    private DadosProvider dadosProvider;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MeuObservador observador = new MeuObservador(new Handler(), this);
        getContentResolver().registerContentObserver(
                DadosProvider.URI_CONTEUDO,
                true,
                observador
        );

        inserirAlunoExemplo();

        btnSair = findViewById(R.id.btnSair);

        btnSair.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    private void inserirAlunoExemplo() {
        ContentValues values = new ContentValues();
        values.put("nome", "João Vitor");
        values.put("idade", 20);
        values.put("nota1", 8.5);
        values.put("nota2", 9.0);

        Uri uri = Uri.parse("content://com.exemple.joaovitor_atividade_06/dados");
        Uri resultUri = getContentResolver().insert(uri, values);

        if (resultUri == null) {
            Toast.makeText(this, "Erro ao inserir o aluno.", Toast.LENGTH_LONG).show();
        } 
    }


}