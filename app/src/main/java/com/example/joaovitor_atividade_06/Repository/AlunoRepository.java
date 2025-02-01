package com.example.joaovitor_atividade_06.Repository;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.example.joaovitor_atividade_06.Model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {
    private static final Uri URI_ALUNOS = Uri.parse("content://com.exemple.joaovitor_atividade_06/dados");

    private final ContentResolver contentResolver;

    public AlunoRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public List<Aluno> obterTodos() {
        List<Aluno> alunos = new ArrayList<>();

        Cursor cursor = contentResolver.query(URI_ALUNOS, null, null, null, null);

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex("_id");
            int nomeIndex = cursor.getColumnIndex("nome");
            int idadeIndex = cursor.getColumnIndex("idade");
            int nota1Index = cursor.getColumnIndex("nota1");
            int nota2Index = cursor.getColumnIndex("nota2");

            if (idIndex != -1 && nomeIndex != -1 && idadeIndex != -1 &&
                    nota1Index != -1 && nota2Index != -1) {

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(idIndex);
                    String nome = cursor.getString(nomeIndex);
                    int idade = cursor.getInt(idadeIndex);
                    Double nota1 = cursor.getDouble(nota1Index);
                    Double nota2 = cursor.getDouble(nota2Index);

                    Aluno aluno = new Aluno(nome, idade, nota1, nota2);
                    aluno.setId(id);
                    alunos.add(aluno);
                }
            }
            cursor.close();
        }
        return alunos;
    }

    public Uri inserir(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("idade", aluno.getIdade());
        values.put("nota1", aluno.getNota1());
        values.put("nota2", aluno.getNota2());

        return contentResolver.insert(URI_ALUNOS, values);
    }

    public int atualizar(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("idade", aluno.getIdade());
        values.put("nota1", aluno.getNota1());
        values.put("nota2", aluno.getNota2());

        Uri alunoUri = Uri.withAppendedPath(URI_ALUNOS, String.valueOf(aluno.getId()));
        return contentResolver.update(alunoUri, values, null, null);
    }

    public int excluir(int alunoId) {
        Uri alunoUri = Uri.withAppendedPath(URI_ALUNOS, String.valueOf(alunoId));
        return contentResolver.delete(alunoUri, null, null);
    }
}
