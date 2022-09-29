import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthPasswordFieldUI;

public class Funciones {

    static final int inicioZO = 877;
    static int elementosZO = 0;

    String pausa;
    Scanner sc =  new Scanner(System.in);

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
        
        archivo.seek(0);
        Integer i=0;
        Integer eof= (int) (archivo.length()/Registro.tamanioRegistro)+elementosZO;
        Registro leer;
        while(i< eof){
            leer = Registro.devolverRegistro(archivo, i);
            System.out.println(leer.armarString());   
            i++;
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
        } else {
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

            
            

            posicionEscribir = ((int) (archivo.length()) / Registro.tamanioRegistro)+elementosZO;
            
            if (posicionEscribir == -1) {
                registro=Registro.modificarRegistro(posicionEscribir,registro.codigoCliente(),
                                            registro.apellido(),registro.nombre(),-1,registro.estado());
                registro.escribirRegistro(archivo);
                elementosZO ++;
            }
            else {
                registro=Registro.modificarRegistro(posicionEscribir,registro.codigoCliente(),
                                            registro.apellido(),registro.nombre(),-1,registro.estado());
                registro.sobreescribirRegistro(archivo,posicionEscribir);
                
            }

            registroActual = Registro.modificarRegistro(registroActual.indice(),registroActual.codigoCliente(),
                                            registroActual.apellido(),registroActual.nombre(),posicionEscribir,registroActual.estado());
            registroActual.sobreescribirRegistro(archivo,posicionAModificar);

        }

    }

//Busco registro y devuelvo posicion
public static int buscarRegistro(RandomAccessFile archivo, int codigoCliente) throws IOException{
    
    int buscarRegistro=-1;
    int indice = funcionHash(codigoCliente);

    archivo.seek((long) indice * Registro.tamanioRegistro);
    Integer index = archivo.readInt();
    Integer code = archivo.readInt();
    String ape = archivo.readUTF();
    String nom = archivo.readUTF();
    Integer enl = archivo.readInt();
    boolean est = archivo.readBoolean();
    if (codigoCliente==code && est){
        buscarRegistro=index;
    }else {
        
        while(est && enl!=-1){
            archivo.seek((long) enl * Registro.tamanioRegistro);
            index = archivo.readInt();
            code = archivo.readInt();
            ape = archivo.readUTF();
            nom = archivo.readUTF();
            enl = archivo.readInt();
            est = archivo.readBoolean();

            if (codigoCliente==code && est){
                buscarRegistro=index;
            }

        }
        
    }


    
    return buscarRegistro;

}




public static boolean baja(RandomAccessFile archivo, int codigoCliente) throws IOException{
    

    int posicionRegistroAEliminar= funcionHash(codigoCliente);
    

    
    Registro registroActual = Registro.devolverRegistro(archivo, posicionRegistroAEliminar);
    
    int posicionAnterior=posicionRegistroAEliminar;
    int posicionActual= posicionRegistroAEliminar; 
    int posicionSiguiente= registroActual.enlace();
    
    
  
        boolean encontro=false;
        boolean zo=false;
    do{
        if(registroActual.codigoCliente()==codigoCliente){
            encontro=true;
        } else{
            zo=true;
            posicionAnterior=posicionActual;
            posicionActual=posicionSiguiente;
            if(posicionActual!=-1){
                registroActual = Registro.devolverRegistro(archivo, posicionActual);
            }
            posicionSiguiente=registroActual.enlace();
        } 
        
    }while(posicionActual!=-1 && !encontro);



    
   

//si el elemento a eliminar es el primero de la lista enlazada

if ( encontro && posicionSiguiente==-1){
    Registro registroAnterior = Registro.devolverRegistro(archivo, posicionAnterior);
    registroAnterior = new Registro(registroAnterior.indice(), registroAnterior.codigoCliente(), registroAnterior.apellido(), registroAnterior.nombre(), -1, registroAnterior.estado());
    registroAnterior.sobreescribirRegistro(archivo, posicionAnterior);

    registroActual = new Registro(posicionActual, 0, "..........", "..........", -1, false);
    registroActual.sobreescribirRegistro(archivo, posicionActual);
}else if(encontro && posicionSiguiente != -1){
        do{

            registroActual = Registro.devolverRegistro(archivo, posicionActual);
            Registro registroSiguiente = Registro.devolverRegistro(archivo, posicionSiguiente);
            posicionAnterior=posicionActual;
            posicionActual=posicionSiguiente;
            posicionSiguiente=registroSiguiente.enlace();
            registroSiguiente = new Registro(registroActual.indice(), registroSiguiente.codigoCliente(), registroSiguiente.apellido(), registroSiguiente.nombre(), registroActual.enlace(), registroSiguiente.estado());
            registroSiguiente.sobreescribirRegistro(archivo, posicionAnterior);
            
            
           
        }while(posicionSiguiente!=-1);

        Registro registroAnterior = Registro.devolverRegistro(archivo, posicionAnterior);
        registroAnterior = new Registro(registroAnterior.indice(), registroAnterior.codigoCliente(), registroAnterior.apellido(), registroAnterior.nombre(), -1, registroAnterior.estado());
        registroAnterior.sobreescribirRegistro(archivo, posicionAnterior);

        registroActual = new Registro(posicionActual, 0, "..........", "..........", -1, false);
        registroActual.sobreescribirRegistro(archivo, posicionActual);

    }



    if((encontro && zo) && (elementosZO > 0)){
        elementosZO--;


    }

    return  encontro;



    
}







   
}

        


//END.
