package br.jef.heapsort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btCadastrar;
    private Button btLimpar;
    private Button btOrdenar;
    private EditText etNome;
    private ListView lvDesordenado;
    private ListView lvOrdenado;
    private List<Usuario> listaUsuarios;
    private List<Usuario> listaOrdenado;
    private ArrayAdapter adapter1;
    private ArrayAdapter adapter2;
    private RadioButton rbHeap;
    private RadioButton rbBubble;
    private EditText etIteracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etIteracao = findViewById(R.id.etIteracao);
        btCadastrar = findViewById(R.id.btCadastrar);
        btLimpar = findViewById(R.id.btLimparLista);
        btOrdenar = findViewById(R.id.btOrdenar);
        etNome = findViewById(R.id.etNome);
        lvDesordenado = findViewById(R.id.lvDesordenado);
        lvOrdenado = findViewById(R.id.lvOrdenado);
        carregarListaDesordenada();
        //bubbleSort();

        //carregarListaOrdenada();
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
                carregarListaDesordenada();
              //  carregarListaOrdenada();
            }
        });

        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpar();
                carregarListaDesordenada();
               // bubbleSort();
                heapSort();
            }
        });

        btOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heapSort();
                }
        });


    }

    private void cadastrar() {
        String nome = etNome.getText().toString().toUpperCase();
        if (nome.isEmpty()) {
            Toast.makeText(this, "Informe o nome do usuário!", Toast.LENGTH_LONG).show();
        } else {
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            UsuarioDAO.inserir(this, usuario);
            etNome.setText("");
        }
    }

    //Lista Ordenada
    private void limpar() {
        if (listaUsuarios.size() == 0) {
            Toast.makeText(this, "Lista já está vazia!", Toast.LENGTH_LONG).show();
        } else {
            UsuarioDAO.excluir(this);
        }
    }


    private void carregarListaDesordenada() {
        listaUsuarios = UsuarioDAO.getUsuarios(this);

        if (listaUsuarios.size() == 0) {
            lvDesordenado.setEnabled(false);
        } else {
            lvDesordenado.setEnabled(true);
        }

        adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaUsuarios);
        lvDesordenado.setAdapter(adapter1);
    }


    //Lista Ordenada
    private void heapSort() {
        listaOrdenado = UsuarioDAO.getUsuarios(this);
        int iteracoes = 0;

        if (listaOrdenado.size() == 0) {

            lvOrdenado.setEnabled(false);
        } else {
            lvOrdenado.setEnabled(true);
        }

        int aux = listaOrdenado.size();
        String [] lista = new String [aux];

        for (int i = 0; i < aux; i++){
            lista [i] = listaOrdenado.get(i).getNome();
        }


        //UTILIZANDO O MAXHEAP
        //FAZER COM QUE NENHUM FILHO SEJA MAIOR QUE O PAI
        for(int i = 1; i < lista.length; i++){

            //VERIFICAR SE FILHO É MAIOR QUE O PAI
            if (lista [i].compareTo(lista[(i-1)/2]) > 0){
                int j = i;

                //TROCAR FILHO E PAI ENQUANTO PAI FOR MENOR
                while(lista [j].compareTo(lista[(j-1)/2]) > 0){
                    String aux2 = lista[j];
                    lista[j] = lista[(j-1)/2];
                    lista[(j-1)/2] = aux2;
                    j = (j-1)/2;
                }
            }
        }

        //processo do HEAPSORT
        for(int i = lista.length - 1; i > 0; i--){

            String aux2 = lista[0];
            lista[0] = lista[i];
            lista[i] = aux2;

            int j = 0;
            int index;

            do{
                index = (2 * j + 1);

                if(index < (i-1) && lista[index].compareTo(lista[index+1]) < 0){
                    index ++;
                }

                if(index < i && lista[j].compareTo(lista[index]) < 0){
                    aux2 = lista[j];
                    lista[j] = lista[index];
                    lista[index] = aux2;
                }

                j = index;
                iteracoes++;

            }while(index < i);
        }
        etIteracao.setText("Total de iterações: " + iteracoes);
        adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        lvOrdenado.setAdapter(adapter2);
    }

    private void bubbleSort(){
        listaOrdenado = UsuarioDAO.getUsuarios(this);
        int aux = listaOrdenado.size();
        String [] lista = new String [aux];
        String aux2;
        int iteracoes = 0;
        boolean ordenado;

        for (int i = 0; i < aux; i++){
            lista [i] = listaOrdenado.get(i).getNome();
        }

        for(int i = 0;i < aux; i++) {
            ordenado = true;

            for (int j = 0; j < aux-1; j++) {
                iteracoes++;
                if (lista[j].compareTo(lista[j + 1]) > 0) {
                    aux2 = lista[j];
                    lista[j] = lista[j + 1];
                    lista[j + 1] = aux2;
                    ordenado = false;
                }
            }
            if(ordenado){
                break;
            }
        }
        etIteracao.setText("" + iteracoes);
        adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        lvOrdenado.setAdapter(adapter1);
    }

}


