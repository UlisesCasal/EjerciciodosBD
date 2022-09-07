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
                    //Funciones.mostrarArchivo(archivo);
                    Registro rg1 = new Registro(874,874,"Verga","Pepe", -1,true);
                    Registro rg2 = new Registro(877,1751,"Concha","Juana", -1,true);
                    Funciones.alta(archivo,rg1);
                    Funciones.alta(archivo,rg2);
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