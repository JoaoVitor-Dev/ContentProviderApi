package com.example.joaovitor_atividade_06.Model;

public class Aluno
{
    private int id;
    private String nome;
    private int idade;
    private Double nota1;
    private Double nota2;

    public Aluno(String nome, int idade, Double nota1, Double nota2)
    {
        this.nome = nome;
        this.idade = idade;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public int getIdade()
    {
        return idade;
    }

    public void setIdade(int idade)
    {
        this.idade = idade;
    }

    public Double getNota1()
    {
        return nota1;
    }

    public void setNota1(Double nota1)
    {
        this.nota1 = nota1;
    }

    public Double getNota2()
    {
        return nota2;
    }

    public void setNota2(Double nota2)
    {
        this.nota2 = nota2;
    }

    @Override
    public String toString()
    {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", nota1=" + nota1 +
                ", nota2=" + nota2 +
                '}';
    }
}
