

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    daoContacto dao; //objeto para interactar con la BBDD y realiza operaciones de crear,leer,actualizar y borrar(Crud)
    Adaptador adapter; //Adaptador para manejar la lista de contactos en la interfaz de usuario.
    ArrayList<Contacto>lista; // Lista de contactos que se mostrarán en la interfaz de usuario.

    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //se inicia cuando la activity comienzxa
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Establece el diseño de la actividad
        dao= new daoContacto(this);  //se instancia el objeto DaoContacto
        lista = dao.verTodos(); //se recupera la lista de contactos de la BBDD usando el método 'verTodos' del objeto 'dao'
        adapter =new Adaptador(this,lista,dao); //se crea un adaptador con la lista de contactos y se asigna al 'listView' de la interfaz de usaurio
        ListView list = (ListView)findViewById(R.id.lista); // se accede al componente lisView en la interfaz grafica a traves del findViewById
        ImageButton agregar=(ImageButton)findViewById(R.id.agregar); // se accede al componente ImageButton en la interfaz grafica a traves del findViewById
        list.setAdapter(adapter); // se establece un adaptador para el listView, proporciona datos S AL listview y  controla como se muestra  los datos en la lista
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        agregar.setOnClickListener(new View.OnClickListener() { //cuando el botón agregar se presiona, se ejecutará el código dentro del método onClick.
            @Override
            public void onClick(View v) {
                final Dialog dialogo = new Dialog(MainActivity.this); //se crea objeto 'Dialog' que se usa para mostrar una ventana emergente en la que se agregar nuevo contacto
                //propiedades del diálogo
                dialogo.setTitle("Nuevo registro");
                dialogo.setCancelable(true); //al poder cancerlarse puede cerrar al pulsar fuera del recuadro
                dialogo.setContentView(R.layout.dialogo); //define y muestra la interfaz de usuario de una actividad en Android
                dialogo.show();
                //se asignan las variables locales:
                final EditText nombre = (EditText)dialogo.findViewById(R.id.nombre);
                final EditText tel = (EditText)dialogo.findViewById(R.id.telefono);
                final EditText email = (EditText)dialogo.findViewById(R.id.email);
                final EditText edad = (EditText)dialogo.findViewById(R.id.edad);
                Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Agregar"); // texto qeu se muestra en el fragment
                Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);

                ////cuando se clicka en el boton agregar se crea un nuevo Object contacto
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            c=new Contacto(nombre.getText().toString(),
                                    tel.getText().toString(),
                                    email.getText().toString(),
                                    Integer.parseInt(edad.getText().toString()));
                            //se inserta un nuevo objeto contacto en la BBDD esando el método insertar del Objeto Dao
                            dao.insertar(c);
                            lista=dao.verTodos(); //se actualiza la lista  llamado al método vertTodos
                            adapter.notifyDataSetChanged(); //se notifica al 'adapter' qu los datos se han modificado
                            dialogo.dismiss(); //se cierra el dialogo llamando al método dismiss

                        }catch(Exception e){
                            Toast.makeText(getApplication(),"ERROR",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //cuando se da click en el boton 'cancelar' se cierra el dialogo
                        dialogo.dismiss();
                    }
                });
            }
        });


}
}
