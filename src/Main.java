import java.io.*;
import java.util.Scanner;

public class Main {
    static RandomAccessFile archivo = null;
    public static void main(String[] args) throws IOException {


                FileOutputStream fos = null;
                DataOutputStream salida = null;


                try {
                    //crea el archivo:
                    archivo = new RandomAccessFile("datos.bin", "rw");


                    //crea puntero al registro.

                    //asigno registros:
                    Registro registro1 = new Registro(0, "000000", "aaaaaaaaaa", "aaaaaaaaaa", 000000);
                    Registro registro2 = new Registro(1, "000001", "bbbbbbbbbb", "bbbbbbbbbb", 000000);
                    Registro registro3 = new Registro(2, "000002", "cccccccccc", "cccccccccc", 000000);
                    registro1.escribirRegistro(archivo);
                    registro2.escribirRegistro(archivo);
                    registro3.escribirRegistro(archivo);
                    String linea = "";
                    archivo.seek(0); //nos situamos al principio
                    for (int i = 0; i < 36; i++) {
                        linea += archivo.readChar();
                    }
                    System.out.println(linea);  //se muestra en pantalla



                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                        if (salida != null) {
                            salida.close();
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }

    }
}