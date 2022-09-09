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
                    //Funciones.mostrarArchivo(archivo); 1751
                    Registro rg1 = new Registro(874,874,Funciones.hacerStringde10("Verga"),Funciones.hacerStringde10("Pepe"), -1,true);
                    Registro rg2 = new Registro(877,874,Funciones.hacerStringde10("Concha"),Funciones.hacerStringde10("Juana"), -1,true);
                    Registro rg4 = new Registro(878,874,Funciones.hacerStringde10("Conchita"),Funciones.hacerStringde10("Juanita"), -1,true);
                    Registro rg5 = new Registro(875,875,Funciones.hacerStringde10("Martinez"),Funciones.hacerStringde10("Loquita"), -1,true);
                    Funciones.alta(archivo,rg1);
                    Funciones.alta(archivo,rg2);
                    Funciones.alta(archivo,rg4);
                    Funciones.alta(archivo,rg5);
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