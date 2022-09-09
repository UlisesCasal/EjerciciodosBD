import java.io.IOException;
import java.io.RandomAccessFile;

public class Funciones {

    static final int inicioZO = 877;
    static int elementosZO = 0;

    public static String hacerStringde10(String cadena) {
        cadena = cadena.trim();
        int agregar = 10 - cadena.length();
        for (int i = 0; i < agregar; i++) {
            cadena += " ";
        }
        return cadena;
    }

    public static void crearVacio(RandomAccessFile archivo) throws IOException {
        //En caso que el archivo no exista, lo crea con todos en nulo
        for (int i = 0; i < inicioZO; i++) {
            Registro registroNulo = new Registro(i, 0, "..........", "..........", -1, false);
            registroNulo.escribirRegistro(archivo);
        }

    }

    public static void mostrarArchivo(RandomAccessFile archivo) throws IOException {
        int eof = Math.toIntExact(archivo.length() / Registro.tamanioRegistro) + elementosZO;
        Registro leer;
        for (int i = 0; i < eof; i++) {
            leer = Registro.devolverRegistro(archivo, i);
            System.out.println(leer.armarString());
        }
    }

    public static Integer buscarLibreZO(RandomAccessFile archivo) throws IOException {
        int posicionLibre = -1;
        int posicionActual = inicioZO;
        boolean encontrado = false;
        Registro aux;
        while ((((long) posicionActual * Registro.tamanioRegistro) < archivo.length()) && (!encontrado)) {
            aux = Registro.devolverRegistro(archivo, posicionActual);
            if (aux.estado()) {
                posicionActual ++;
            }
            else {
                encontrado = true;
                posicionLibre = posicionActual;
            }
        }
        return posicionLibre;
    }

    public static Integer funcionHash(Integer numero) {
        return numero % 877;
    }

    public static void alta(RandomAccessFile archivo, Registro registro) throws IOException {
    int hashCode = funcionHash(registro.codigoCliente());

    Registro registroActual = Registro.devolverRegistro(archivo, hashCode);

    if (!registroActual.estado()) {
        registro.sobreescribirRegistro(archivo,hashCode);
    }

    else {
        int posicionRegistroActual = hashCode;
        int posicionAModificar = posicionRegistroActual;

        while ((registroActual.estado()) && (posicionRegistroActual != -1)) {
            posicionRegistroActual = registroActual.enlace();
            if (registroActual.enlace() != -1) {
                posicionAModificar = posicionRegistroActual;
                registroActual = Registro.devolverRegistro(archivo, registroActual.enlace());
            }

        }

        int posicionEscribir = buscarLibreZO(archivo);

        posicionEscribir = ((int) (archivo.length()) / Registro.tamanioRegistro) + elementosZO;
        if (posicionEscribir == -1) {
            registro.escribirRegistro(archivo);
            elementosZO ++;
        }
        else {
            registro.sobreescribirRegistro(archivo,posicionEscribir);
        }

        registroActual = Registro.modificarRegistro(registroActual.indice(),registroActual.codigoCliente(),
                                        registroActual.apellido(),registroActual.nombre(),posicionEscribir,registroActual.estado());
        registroActual.sobreescribirRegistro(archivo,posicionAModificar);

    }

    }



}//END.
