package com.example.joaovitor_atividade_06.View;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.joaovitor_atividade_06.Model.Aluno;
import com.example.joaovitor_atividade_06.R;
import com.example.joaovitor_atividade_06.Repository.AlunoRepository;

public class CadastroActivity extends AppCompatActivity
{
    private Button btnVoltar, btnSalvar, btnDeletar;
    private EditText edtNome, edtIdade, edtNota1, edtNota2;
    private AlunoRepository repository;
    private Aluno alunoRecebido;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        setup();

        ContentResolver contentResolver = getContentResolver();
        repository = new AlunoRepository(contentResolver);

        alunoRecebido = (Aluno) getIntent().getSerializableExtra("aluno");

        if (alunoRecebido != null)
        {
            preencherDados(alunoRecebido);
            definirModoEdicao();
        }

        btnVoltar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                voltar();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                salvar();
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                deletar();
            }
        });
    }

    private void preencherDados(Aluno aluno)
    {
        edtNome.setText(aluno.getNome().toString());
        edtIdade.setText(String.valueOf(aluno.getIdade()).toString());
        edtNota1.setText(String.valueOf(aluno.getNota1()).toString());
        edtNota2.setText(String.valueOf(aluno.getNota2()).toString());
    }

    private void setup()
    {
        btnDeletar = findViewById(R.id.btnDeletar);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);
        edtNome = findViewById(R.id.edtNome);
        edtIdade = findViewById(R.id.edtIdade);
        edtNota1 = findViewById(R.id.edtNota1);
        edtNota2 = findViewById(R.id.edtNota2);
    }

    private void voltar()
    {
        startActivity(new Intent(CadastroActivity.this, MainActivity.class));
    }

    private void definirModoEdicao()
    {
        btnDeletar.setVisibility(View.VISIBLE);
    }

    private void salvar()
    {
        String nome = edtNome.getText().toString().trim();
        String idadeText = edtIdade.getText().toString().trim();
        String nota1Text = edtNota1.getText().toString().trim();
        String nota2Text = edtNota2.getText().toString().trim();

        if (nome.isEmpty())
        {
            Toast.makeText(this, "Por favor, preencha o nome do aluno.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (idadeText.isEmpty())
        {
            Toast.makeText(this, "Por favor, preencha a idade do aluno.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nota1Text.isEmpty())
        {
            Toast.makeText(this, "Por favor, preencha a primeira nota.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nota2Text.isEmpty())
        {
            Toast.makeText(this, "Por favor, preencha a segunda nota.", Toast.LENGTH_SHORT).show();
            return;
        }

        int idade = Integer.parseInt(idadeText);
        Double nota1 = Double.parseDouble(nota1Text);
        Double nota2 = Double.parseDouble(nota2Text);

        if (alunoRecebido != null)
        {
            alunoRecebido.setNome(nome);
            alunoRecebido.setIdade(idade);
            alunoRecebido.setNota1(nota1);
            alunoRecebido.setNota2(nota2);
            repository.atualizar(alunoRecebido);
            Toast.makeText(this, "Cadastro atualizado", Toast.LENGTH_SHORT).show();
        } else
        {
            repository.inserir(new Aluno(nome, idade, nota1, nota2));
            Toast.makeText(this, "Aluno cadastrado", Toast.LENGTH_SHORT).show();
        }
        voltar();
    }

    private void deletar()
    {
        repository.excluir(alunoRecebido.getId());
        Toast.makeText(this, "Aluno excluído", Toast.LENGTH_SHORT).show();
        voltar();
    }
}