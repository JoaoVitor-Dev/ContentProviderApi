package com.example.joaovitor_atividade_06.View;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.joaovitor_atividade_06.Adapter.AlunoAdapter;
import com.example.joaovitor_atividade_06.Provider.DadosProvider;
import com.example.joaovitor_atividade_06.MeuObservador;
import com.example.joaovitor_atividade_06.Model.Aluno;
import com.example.joaovitor_atividade_06.R;
import com.example.joaovitor_atividade_06.Repository.AlunoRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private Button btnNovo;
    private DadosProvider dadosProvider;
    private ListView listView;
    private AlunoRepository repository;
    private AlunoAdapter adapter;

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

        setup();

        CarregarAlunos();

        btnNovo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });
    }

    private void setup()
    {
        btnNovo = findViewById(R.id.btnNovo);
        listView = findViewById(R.id.listaAlunos);
    }

    private void CarregarAlunos()
    {
        ContentResolver contentResolver = getContentResolver();
        AlunoRepository alunoRepository = new AlunoRepository(contentResolver);

        ArrayList<Aluno> alunos = (ArrayList<Aluno>) alunoRepository.obterTodos();

        if (alunos.size() <= 0){
            Toast.makeText(this, "Nenhum aluno cadastrado no app!", Toast.LENGTH_LONG).show();
        }

        if (alunos != null){
            adapter = new AlunoAdapter(getLayoutInflater(), alunos, repository);
            listView.setAdapter(adapter);
        }
    }

}