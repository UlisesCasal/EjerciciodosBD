import java.io.IOException;
import java.io.RandomAccessFile;

public record Registro(Integer indice, String codigoCliente, String apellido, String nombre, Integer enlace) {

    private String armarString() {
        return this.indice + ", " +  this.codigoCliente + ", " + this.apellido + ", " + this.nombre + ", " + this.enlace;
    }

    public void escribirRegistro(RandomAccessFile archivo) throws IOException {
        String linea = this.armarString();
        archivo.writeChars(linea);
    }
}
