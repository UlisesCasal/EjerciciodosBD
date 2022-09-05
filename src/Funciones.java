import java.io.IOException;
import java.io.RandomAccessFile;

public class Funciones {

    public static void crearVacio(RandomAccessFile archivo) throws IOException {
        //En caso que el archivo no exista, lo crea con todos en nulo
        for (int i = 0; i < 877 ; i++) {
            Registro registroNulo = new Registro(i, 0, "..........", "..........", -1, false);
            registroNulo.escribirRegistro(archivo);
        }

    }

    public static void mostrarArchivo(RandomAccessFile archivo) throws IOException {
        int eof = Math.toIntExact(archivo.length() / 37);
        Registro leer;
        for (int i = 0; i < eof; i++) {
            leer = Registro.devolverRegistro(archivo, i);
            System.out.println(leer.armarString());
        }

    }

    public Integer funcionHash(Integer numero) {
        return numero % 877;
    }

}
