import java.io.*;
import java.util.Scanner;

public class Main {
    static RandomAccessFile archivo = null;
    public static void main(String[] args) throws IOException {

                FileOutputStream fos = null;

                try {
                    //crea el archivo:
                    archivo = new RandomAccessFile("datos.bin", "rw");
                    if (archivo.length() == 0) {
                        Funciones.crearVacio(archivo);
                        System.out.println("Archivo creado!!!");
                    }
                    Funciones.mostrarArchivo(archivo);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }

    }
}