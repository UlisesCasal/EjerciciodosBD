import java.io.*;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthPasswordFieldUI;

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
                    archivo.close();



                    Scanner sc = new Scanner(System.in);
                    String opcion;
                    int codigoCliente;
                    String apellido,nombre,pausa;
                    do {
                        //System.out.println("Presione enter para continuar...");
                        //String pausa = sc.nextLine();
                        System.out.println("-------------------Elegi opcion-----------------------");
                        System.out.println("1.Alta de registro");
                        System.out.println("2.Baja de registro");
                        System.out.println("3.Actualizacion de registro");
                        System.out.println("4.Buscar registro");
                        System.out.println("5.Mostrar archivo");
                        System.out.println("0. Salir");
                        opcion = sc.nextLine();
                        Funciones.limpiar();
                        switch (opcion) {
                        case "1":
                            //Realiza alta:
                            Funciones.limpiar();
                            System.out.println("Ingresa el codigo de cliente: ");
                            codigoCliente = sc.nextInt();
                            

                            archivo = new RandomAccessFile("datos.bin", "rw");

                            if (Funciones.buscarRegistro(archivo, codigoCliente)<0){
                                pausa = sc.nextLine();
                                System.out.println("Ingresa el apellido del cliente");
                                apellido = sc.nextLine();
                                System.out.println("Ingresa el nombre del cliente");
                                nombre = sc.nextLine();
                                Registro reg = new Registro(Funciones.funcionHash(codigoCliente),codigoCliente,
                                Funciones.hacerStringde10(apellido),Funciones.hacerStringde10(nombre), -1,true);
                                Funciones.alta(archivo,reg);
                            }else {
                                
                                System.out.println("El codigo de cliente ya existe...");
                                pausa = sc.nextLine();
                            }
                            archivo.close();
                            
                            System.out.println("Presione ENTER para continuar...");
                            pausa = sc.nextLine();
                            break;
                        case "2":
                            //Realiza baja
                            Funciones.limpiar();
                            System.out.println("Ingresa el codigo de cliente: ");
                            codigoCliente = sc.nextInt();
                            
                            archivo = new RandomAccessFile("datos.bin", "rw");
                            if(Funciones.baja(archivo,codigoCliente)){
                                System.out.println("Se elimino el registro correctamente");
                            }else{
                                System.out.println("No ingresaste un codigo existente...");
                            }
                            archivo.close();
                            pausa = sc.nextLine();
                            break;
                        case "3":
                            Funciones.limpiar();
                            boolean opcionValida=false;
                            System.out.println("Ingresa el codigo de cliente: ");
                            codigoCliente = sc.nextInt();

                            archivo = new RandomAccessFile("datos.bin", "rw");
                            int posicionAModificar=Funciones.buscarRegistro(archivo, codigoCliente);
                            if (posicionAModificar>=0){

                                while(!opcionValida){
                                    System.out.println("-------------------Elegi opcion-----------------------");
                                    System.out.println("1.Cambiar nombre");
                                    System.out.println("2.Cambiar apellido");
                                    System.out.println("0.Cancelar");
                                    Registro registroAModificar;
                                    opcion = sc.nextLine();
                                    opcion = sc.nextLine();
                                    switch (opcion) {
                                        case "1":
                                        //cambiarNombre
                                        System.out.println("Ingresa el nombre del cliente");
                                        nombre = sc.nextLine();
                                        registroAModificar = Registro.devolverRegistro(archivo, posicionAModificar);
                                        registroAModificar = new Registro(registroAModificar.indice(),registroAModificar.codigoCliente(),
                                            Funciones.hacerStringde10(registroAModificar.apellido()),Funciones.hacerStringde10(nombre), registroAModificar.enlace(),true);

                                        registroAModificar.sobreescribirRegistro(archivo, posicionAModificar);
                                        opcionValida=true;
                                        break;
                                        case "2":
                                        //cambiar apellido
                                        System.out.println("Ingresa el apellido del cliente");
                                        apellido = sc.nextLine();
                                        registroAModificar = Registro.devolverRegistro(archivo, posicionAModificar);
                                        registroAModificar = new Registro(registroAModificar.indice(),registroAModificar.codigoCliente(),
                                            Funciones.hacerStringde10(apellido),Funciones.hacerStringde10(registroAModificar.nombre()), registroAModificar.enlace(),true);

                                        registroAModificar.sobreescribirRegistro(archivo, posicionAModificar);
                                        opcionValida=true;
                                        break;
                                        case "0":
                                        opcionValida=true;
                                        //salir
                                    }
                                }
                                System.out.println("Presione ENTER para continuar...");
                                pausa = sc.nextLine();
                                Funciones.limpiar();
                            }else {
                                
                                System.out.println("El codigo de cliente no existe...");
                                pausa = sc.nextLine();
                            }
                            
                            archivo.close();
                            
                            break;
                        case "4":
                            Funciones.limpiar();
                            System.out.println("Ingresa el codigo de cliente: ");
                            codigoCliente = sc.nextInt();
                            archivo = new RandomAccessFile("datos.bin", "rw");
                            int indice=Funciones.buscarRegistro(archivo, codigoCliente);
                            if(indice>=0) {
                                Registro registroBuscado = Registro.devolverRegistro(archivo,indice);
                                System.out.printf("Registro : ");
                                System.out.println(registroBuscado.armarString());
                                pausa= sc.nextLine();
                            }else{
                                System.out.println("El codigo de cliente buscado no existe");
                                sc.nextLine();
                            }

                            archivo.close();

                            System.out.println("Presione ENTER para continuar...");
                            pausa= sc.nextLine();
                            Funciones.limpiar();
                            break;
                        case "5":
                            Funciones.limpiar();
                            archivo = new RandomAccessFile("datos.bin", "rw");
                            Funciones.mostrarArchivo(archivo);
                            archivo.close();
                            System.out.println("Presione ENTER para continuar...");
                            pausa = sc.nextLine();
                            break;
                        case "0":
                            Funciones.limpiar();
                            System.out.println("Excelente servicio, nos vemos");
                            //pausa = sc.nextLine();
                            break;
                        default:
                            System.out.println("Opcion incorrecta");

                            break;
                        }
                    }while(!(opcion.equals("0")));
    


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