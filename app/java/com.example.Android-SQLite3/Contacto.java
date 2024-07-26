
public class Contacto {
    int id;
    String nombre;
    String telefono;
    String email;
    int edad;

    public Contacto(String nombre, String telefono, String email, int edad) {  //Este constructor se utiliza para crear un nuevo contacto con la información proporcionada
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.edad = edad;
    }

    public Contacto() { //se usa para inicializar el objeto "contacto" sin proporcionar daros iniciales
    }

    public Contacto(int id, String nombre, String telefono, String email, int edad) { //constructor que inicializa a todos los campos de la clase incluido el ID
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.edad = edad;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
