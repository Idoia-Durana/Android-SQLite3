
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

//Se diseña para interactuar con la Base de datos SQLite
public class daoContacto {
    SQLiteDatabase cx; //objeto que representa la conexion a BBDD
    ArrayList<Contacto> lista= new ArrayList<Contacto>(); // almacena la lista de contactos recuperados de la BBDD
    Context ct;
    Contacto c;
    String nombreBD = "BDContactos";

    //Sentencia SQL  para crear la tabla de contactos en a base de datos
    String tabla = "create table if not exists contacto(id integer primary key autoincrement, nombre text,telefono text,email text, edad integer)";

    public daoContacto(Context c) {
        this.ct = c;
        cx = c.openOrCreateDatabase(nombreBD, c.MODE_PRIVATE, null); //abre o crea la base de datos usando "nombreDB"
        cx.execSQL(tabla);//ejecuta la sentencia SQL para crear la tabla de contactos en la BBDD
    }

    public boolean insertar(Contacto c) {

        ContentValues contenedor = new ContentValues(); //el concepto Content Values se usa para insertar valores del contacto en la BBDD
        contenedor.put("nombre", c.getNombre());
        contenedor.put("telefono", c.getTelefono());
        contenedor.put("email", c.getEmail());
        contenedor.put("edad", c.getEdad());
        return (cx.insert("contacto", null, contenedor)) > 0;
    }
        public boolean eliminar(int id){

            return(cx.delete("contacto","id="+id,null))>0; //recibe el ID del contacto a eliminar

        }
    public ArrayList<Contacto> verTodos() {
        lista.clear();
        Cursor cursor = cx.rawQuery("select * from contacto", null); //usa objeto cursosr para recorrer los resultados de la consulta
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                lista.add(new Contacto(cursor.getInt(0), //crea objetos 'contacto' a partir de los datos del 'cursor' y los agrega a 'lista'
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)));
            } while (cursor.moveToNext());
        }
        return lista; //devuelve lista con todos los contactos
    }
        public boolean editar (Contacto c){
            ContentValues contenedor = new ContentValues(); //recibe objeto 'contacto' con todos los datos actualizados
            contenedor.put("nombre", c.getNombre());
            contenedor.put("telefono", c.getTelefono());
            contenedor.put("email", c.getEmail());
            contenedor.put("edad", c.getEdad());
            return (cx.update("contacto",contenedor,"id="+ c.getId(),null)) > 0; //actualiza los valores del contacto en la BBDD usando el ID del contacto
        }



        public Contacto verUno(int posicion) { //recupera un único contacto de la base de datos en la posición específica y lo devuelve como objeto 'Contacto'
            Cursor cursor = cx.rawQuery("select*from contacto",null);
            cursor.moveToPosition(posicion);
            c = new Contacto(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4));

            return c;
        }

    }
