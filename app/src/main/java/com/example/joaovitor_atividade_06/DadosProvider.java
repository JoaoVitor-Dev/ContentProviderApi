package com.example.joaovitor_atividade_06;

import android.content.*;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DadosProvider extends ContentProvider
{
    private static final int BANCO_VERSAO = 1;
    private static final String BANCO_NOME = "banco";
    private static final String BANCO_TABELA = "aluno";
    private static final String ID = "_id";
    private static final String NOME = "nome";
    private static final String IDADE = "idade";
    private static final String NOTA1  = "nota1";
    private static final String NOTA2  = "nota2";

    private static final String CRIA_TABELA =
            "CREATE TABLE " + BANCO_TABELA +
                    "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOME + " TEXT, " + IDADE + " INTEGER, " +
                    NOTA1 + " REAL, " + NOTA2 + " REAL" + ")";

    private static final String DELETA_TABELA = "DROP TABLE IF EXISTS "
            + BANCO_TABELA;

    private static final int CODIGO_URI_ITEM = 101;

    private SQLiteDatabase banco;
    private static final String NOME_PROVIDER = "com.exemple.joaovitor_atividade_06";
    protected static final Uri URI_CONTEUDO = Uri.parse("content://"+NOME_PROVIDER+"/dados");
    private static final int CODIGO_URI_TODOS = 100;
    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(NOME_PROVIDER, "dados", CODIGO_URI_TODOS);
        URI_MATCHER.addURI(NOME_PROVIDER, "dados/#", CODIGO_URI_ITEM);
    }

    @Override
    public boolean onCreate()
    {
        Context context = getContext();
        BancoHelper helper = new BancoHelper(context);
        banco = helper.getWritableDatabase();

        if (banco != null){
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
        sqb.setTables(BANCO_TABELA);
        Cursor cursor = sqb.query(banco, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        switch (URI_MATCHER.match(uri)){
            case CODIGO_URI_TODOS:
                return "vnd.android.cursor.dir/dados";
            default:
                throw new IllegalArgumentException("erro na uri");
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        long id = banco.insert(BANCO_TABELA, null, values);
        if (id > 0){
            Uri uriId = ContentUris.withAppendedId(URI_CONTEUDO, id);
            getContext().getContentResolver().notifyChange(uriId, null);
            return uriId;
        }
        throw new SQLException("Erro ao inserir dados");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int registrosDeletados;
        switch (URI_MATCHER.match(uri)) {
            case CODIGO_URI_TODOS:
                registrosDeletados = banco.delete(BANCO_TABELA, selection, selectionArgs);
                break;
            case CODIGO_URI_ITEM:
                String id = uri.getLastPathSegment();
                registrosDeletados = banco.delete(BANCO_TABELA, ID + " = ?", new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("Erro na URI para delete: " + uri);
        }
        if (registrosDeletados > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return registrosDeletados;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int registrosAlterados;
        switch (URI_MATCHER.match(uri)) {
            case CODIGO_URI_TODOS:
                registrosAlterados = banco.update(BANCO_TABELA, values, selection, selectionArgs);
                break;
            case CODIGO_URI_ITEM:
                String id = uri.getLastPathSegment();
                registrosAlterados = banco.update(BANCO_TABELA, values, ID + " = ?", new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("Erro na URI para update: " + uri);
        }
        if (registrosAlterados > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return registrosAlterados;
    }

    private class BancoHelper extends SQLiteOpenHelper
    {
        BancoHelper(Context context){
            super(context, BANCO_NOME, null, BANCO_VERSAO);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CRIA_TABELA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DELETA_TABELA);
            onCreate(sqLiteDatabase);
        }
    }
}
