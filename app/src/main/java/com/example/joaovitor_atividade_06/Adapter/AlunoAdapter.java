package com.example.joaovitor_atividade_06.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.joaovitor_atividade_06.View.CadastroActivity;
import com.example.joaovitor_atividade_06.Model.Aluno;
import com.example.joaovitor_atividade_06.R;
import com.example.joaovitor_atividade_06.Repository.AlunoRepository;

import java.util.List;

public class AlunoAdapter extends BaseAdapter
{
    private LayoutInflater inflater;
    private List<Aluno> alunos;
    private AlunoRepository repository;

    public AlunoAdapter(LayoutInflater inflater, List<Aluno> alunos, AlunoRepository repository)
    {
        this.inflater = inflater;
        this.alunos = alunos;
        this.repository = repository;
    }

    @Override
    public int getCount()
    {
        return alunos.size();
    }

    @Override
    public Object getItem(int position)
    {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Aluno aluno = alunos.get(position);

        convertView = inflater.inflate(R.layout.item_aluno, null);

        TextView nome = convertView.findViewById(R.id.Nome);
        TextView idade = convertView.findViewById(R.id.idade);
        TextView nota1 = convertView.findViewById(R.id.nota1);
        TextView nota2 = convertView.findViewById(R.id.nota2);
        Button btnEditar = convertView.findViewById(R.id.btnEditar);

        nome.setText(aluno.getNome().toString().toUpperCase());
        idade.setText(String.valueOf(aluno.getIdade()).toString());
        nota1.setText(aluno.getNota1().toString());
        nota2.setText(aluno.getNota2().toString());

        btnEditar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(inflater.getContext(), CadastroActivity.class);
                intent.putExtra("aluno", aluno);
                inflater.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}