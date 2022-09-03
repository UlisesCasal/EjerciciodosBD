import java.io.*;
import java.util.Scanner;

public class Main {
    static RandomAccessFile archivo = null;
    public static void main(String[] args) throws IOException {

                FileOutputStream fos = null;

                try {
                    //crea el archivo:
                    archivo = new RandomAccessFile("datos.bin", "rw");

                    //asigno registros:
                    Registro registro1 = new Registro(0,0, "aaaaaaaaaa", "bbbbbbbbbb", 0);
                    registro1.escribirRegistro(archivo);

                    //Recupero registro del archivo
                    Registro leer = Registro.devolverRegistro(archivo, 0);
                    System.out.println(leer.armarString());  //se muestra en pantalla


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