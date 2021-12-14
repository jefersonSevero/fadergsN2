package br.jef.heapsort;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static void inserir(Context context, Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.insert("usuarios", null, valores);

    }
    public static void excluir(Context context){
        ContentValues valores = new ContentValues();

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.delete("usuarios", null, null);

    }



    public static List<Usuario> getUsuarios(Context context){
        List<Usuario> lista = new ArrayList<>();

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM usuarios", null);

        if(cursor.getCount() > 0){

            cursor.moveToFirst();

            do{
                Usuario usu = new Usuario();
                usu.setId(cursor.getInt(0));
                usu.setNome(cursor.getString(1));
                lista.add(usu);
            }while (cursor.moveToNext());
        }
        return lista;
    }

}
