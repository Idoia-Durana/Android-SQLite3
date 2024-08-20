package com.example.proyecto_final_eep;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {


    ArrayList<Contacto> lista; //almacena la lista de contactos que se mostrará en la interfaz
    daoContacto dao; //referencia clase DAO
    Contacto c; //Almacena tempralmente un objeto 'contacto'
    Activity a;
    int id=0; //almacena temporalmente el ID del usuario
    public Adaptador(Activity a, ArrayList<Contacto> lista,daoContacto dao){ //recibe como parametro un activity y un objeto DAO
        this.lista=lista;
        this.a=a;
        this.dao=dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {

        return lista.size();
    }

    @Override
    public Contacto getItem(int i) {
        c = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        c=lista.get(i);
        return c.getId();
    }

    @Override
    public View getView(int posicion,View view, ViewGroup viewGroup) { //Crea y devuelve un vista que representa un elemento de la lista en la posicón especificada
        View v= view;
        if(v==null){ // comprueba si la vista es nula, si es así se carga y construye  la interfaz de usuario definida en el archivo XML correspondiente mediante el layoutInflater
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=li.inflate(R.layout.item,null);
        }
        //Una vez se ha "inflado" la vista se obtienen la referencias a los elementos individuales mostrados a continuacion:
        c=lista.get(posicion);
        TextView nombre = (TextView)v.findViewById(R.id.t_nombre);
        TextView tel = (TextView)v.findViewById(R.id.t_telefono);
        TextView email = (TextView)v.findViewById(R.id.t_email);
        TextView edad = (TextView)v.findViewById(R.id.t_edad);

        ImageButton editar =(ImageButton)v.findViewById(R.id.editar);
        ImageButton eliminar =(ImageButton)v.findViewById(R.id.eliminar);

        // se establecen los datos en los elementos de la vista , los 'setTag' son etiquetas de posición a los botones de editar y eliminar
        nombre.setText(c.getNombre());
        tel.setText(c.getTelefono());
        email.setText(c.getEmail());
        edad.setText("" + c.getEdad());
        editar.setTag(posicion);
        eliminar.setTag(posicion);


    editar.setOnClickListener(new View.OnClickListener() { //Al hacer clic en el botón de editar, se muestra un diálogo que
                                                            // permite al usuario editar los detalles del contacto seleccionado.
                                                             // Al hacer clic en "editar", se actualiza el contacto modificado en base de datos

        @Override
        public void onClick(View v) {
            int pos = Integer.parseInt(v.getTag().toString()); //se obtiene la posicion del elemento en la lista a partir de la etiqueta Tag del boton editar
            final Dialog dialogo = new Dialog(a); // Se crea un objeto Dialogo
            dialogo.setTitle("Editar registro");
            dialogo.setCancelable(true);// el usuario puede cancelar al acción hacieno click fuera del Dialog
            dialogo.setContentView(R.layout.dialogo); // Se obtienen las referencias a los elementos de la interfaz de usuario dentro de lDailog:
            dialogo.show();
           // se asignan los valores a los elementos del dialogo
            final EditText nombre = (EditText)dialogo.findViewById(R.id.nombre);
            final EditText tel = (EditText)dialogo.findViewById(R.id.telefono);
            final EditText email = (EditText)dialogo.findViewById(R.id.email);
            final EditText edad = (EditText)dialogo.findViewById(R.id.edad);
            Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);
            guardar.setText("Guardar");
            Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);
            c=lista.get(pos);
            setId(c.getId());
            nombre.setText(c.getNombre());
            tel.setText(c.getTelefono());
            email.setText(c.getEmail());
            edad.setText("" + c.getEdad());


            guardar.setOnClickListener(new View.OnClickListener() { // Al hacer clic en "Guardar", se actualiza el contacto en la base de datos y se actualiza la lista de contactos.
                @Override
                public void onClick(View v) {
                    try{
                        c=new Contacto(getId(),nombre.getText().toString(),
                                tel.getText().toString(),
                                email.getText().toString(),
                                Integer.parseInt(edad.getText().toString()));
                        dao.editar(c);
                        lista=dao.verTodos();
                        notifyDataSetChanged();
                        dialogo.dismiss();

                    }catch(Exception e){
                        Toast.makeText(a,"ERROR",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogo.dismiss();
                }
            });
        }
    });
    eliminar.setOnClickListener(new View.OnClickListener() {  //Al hacer clic en el botón de eliminar, se muestra un diálogo de
                                                                // confirmación para asegurarse de que el usuario realmente quiere eliminar el contacto seleccionado.
                                                                // Si el usuario confirma, el contacto se elimina de la base de datos
                                                                //  y se actualiza la lista de contactos.
        @Override
        public void onClick(View v) {  // cuando se da click en eliminar se llama a este método
            int pos = Integer.parseInt(v.getTag().toString()); // se obtiene posicion del elemento en la lista
            c=lista.get(pos);
            setId(c.getId()); // se obtienen los datos crrespondientes  a la posición obtenida en la lsita de contactos
            AlertDialog.Builder del = new AlertDialog.Builder(a); // muestra el mensaje al usuario  después de haber creado un Alert Dialog (un objeto)
            del.setMessage("¿Estas seguro  de eliminar contacto?");
            del.setCancelable(false);
            del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) { // se usan manejadores de clcik para los botones "si" y "No"
                   dao.eliminar(getId());// en caso click en "si" se elimina el contacto correspondiente usando el DAO actualizando la lista de contactos y  notificando al adaptador que los datos han sido modificados
                   lista=dao.verTodos();
                   notifyDataSetChanged();
                }
            });
            del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            del.show();
        }

    });

    return v; // se devuevle al vista que se ha modificado en el metodo "onClick"

    }
}