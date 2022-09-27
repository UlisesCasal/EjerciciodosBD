import java.io.IOException;
import java.io.RandomAccessFile;

public record Registro(Integer indice, Integer codigoCliente, String apellido, String nombre, Integer enlace, boolean estado) {
    
    static final int tamanioRegistro = 37;

    public  String armarString() {
        return this.indice + ", " +  this.codigoCliente + ", " + this.apellido + ", " + this.nombre + ", " + this.enlace + ", " + this.estado;
    }
    public static Registro modificarRegistro(Integer indiceE, Integer codigoClienteE, String apellidoE, String nombreE, Integer enlaceE, boolean estadoE) {
        Registro registroNuevo = new Registro(indiceE, codigoClienteE, apellidoE, nombreE, enlaceE, estadoE);
        return registroNuevo;
    }

    public Registro  cambiarIndice(int indice){
        Registro registroNuevo = new Registro(indice, this.codigoCliente, this.apellido, this.nombre, this.enlace, this.estado);
        return registroNuevo;
    }

    // Escribir el contenido de un registro en un archivo pasado por parametro(temporalmente APPEND)
    public void escribirRegistro(RandomAccessFile archivo) throws IOException {
        archivo.seek(archivo.length());
        archivo.writeInt(this.indice);
        archivo.writeInt(this.codigoCliente);
        archivo.writeUTF(this.apellido);
        archivo.writeUTF(this.nombre);
        archivo.writeInt(this.enlace);
        archivo.writeBoolean(this.estado);
    }


    public void sobreescribirRegistro(RandomAccessFile archivo, int posicion) throws IOException {
        archivo.seek((long) posicion *tamanioRegistro);
        archivo.writeInt(this.indice);
        archivo.writeInt(this.codigoCliente);
        archivo.writeUTF(this.apellido);
        archivo.writeUTF(this.nombre);
        archivo.writeInt(this.enlace);
        archivo.writeBoolean(this.estado);
    }

    // Escribir en un registro el contenido de un archivo en una linea especificada por parametro
    public static Registro devolverRegistro(RandomAccessFile archivo, Integer posicion) throws IOException {
        archivo.seek((long) posicion *tamanioRegistro);
        Integer index = archivo.readInt();
        Integer code = archivo.readInt();
        String ape = archivo.readUTF();
        String nom = archivo.readUTF();
        Integer enl = archivo.readInt();
        boolean est = archivo.readBoolean();
        Registro rg = new Registro(index,code,ape,nom,enl, est);
        return rg;
    }

}
