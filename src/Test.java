import com.jakewharton.fliptables.FlipTable;
import com.jakewharton.fliptables.FlipTableConverters;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

    public static void guardarListaDePersonas(File file, List<Persona> lista) {
        try {
            FileOutputStream ficheroSalida = new FileOutputStream(file);
            ObjectOutputStream ObjetoSalida = new ObjectOutputStream(ficheroSalida);
            ObjetoSalida.writeObject(lista);
            ObjetoSalida.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static List<Persona> obtenerListaDePersona(File file) {
        List<Persona> lista = new ArrayList<>();
        try {
            FileInputStream ficheroEntrada = new FileInputStream(file);
            ObjectInputStream ObjetoEntrada = new ObjectInputStream(ficheroEntrada);
            lista = (List<Persona>) ObjetoEntrada.readObject();
            ObjetoEntrada.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }


    public static void main(String[] args) {
        File file = new File("C:\\Users\\kevin\\IdeaProjects\\POO_Actividad6\\src\\BasePersona.txt");
        int opcion = 0;
        List<Persona> objetivo = obtenerListaDePersona(file);
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Registro de personas");
            System.out.println("====================\n");
            System.out.println("1. Agregar persona");
            System.out.println("2. Mostrar personas");
            System.out.println("3. Buscar persona");
            System.out.println("4. Eliminar persona");
            System.out.println("5. Modificar persona");
            System.out.println("6. Salir");
            System.out.println("\nIngrese una opcion:\n ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Registro de personas");
                    System.out.println("====================\n");
                    System.out.println ("Ingrese los siguientes datos:\n");
                    sc.nextLine ();
                    System.out.println("Ingrese el nombre de la persona: ");
                    String nombre = sc.nextLine();
                    System.out.println("Ingrese el apellido de la persona: ");
                    String apellido = sc.nextLine();
                    System.out.println("Ingrese la cedula de la persona: ");
                    String cedula = sc.nextLine();
                    sc.nextLine();

                    objetivo.add(new Persona(nombre, apellido, cedula));
                    guardarListaDePersonas(file, objetivo);
                    break;
                case 2:
                    System.out.println("Mostrar personas");
                    System.out.println("====================\n");
                    System.out.println(FlipTableConverters.fromIterable(objetivo, Persona.class));
                    break;

                case 3:
                    System.out.println ("Buscar Persona");
                    System.out.println ("===============\n");
                    System.out.println ("Ingrese la cedula de la persona");
                    String buscado=sc.next ();
                    String mensaje="No se encontro a la persona\n";
                    Persona x=null;
                    for (Persona persona1:objetivo){
                        if(persona1.getCedula ().equals (buscado)){
                            mensaje="Persona encontrada\n";
                            x=persona1;
                        }
                    }
                    System.out.println ("\n"+mensaje);
                    String []headers={"Nombre","Cedula","Apellido"};
                    if(x!=null){
                        String [][] data={{x.getNombre (),x.getCedula (),String.valueOf (x.getApellido ())}};
                        System.out.println (FlipTable.of(headers,data));
                    }
                    break;
                case 4:
                    System.out.println ("Eliminar persona");
                    System.out.println ("================\n");
                    System.out.println ("Ingrese la cedula de la persona");
                    String eliminar=sc.next ();
                    String mensaje2="No se encontro a la persona\n";
                    for (int i=0;i< objetivo.size ();i++){

                        if(objetivo.get (i).getCedula().equals(eliminar)){
                            objetivo.remove (i);
                            mensaje2="Persona eliminada\n";
                        }
                    }
                    guardarListaDePersonas (file,objetivo);
                    System.out.println (mensaje2);
                    break;
                case 5:
                    System.out.println ("Modificar Persona");
                    System.out.println ("=================\n");
                    System.out.println ("Ingrese la cedula de la persona");
                    String modificar=sc.next ();
                    String mensaje3="No se encontro a la persona\n";
                    Persona persona=null;
                    for(Persona o:objetivo){
                        if(o.getCedula ().equals (modificar)){
                            persona=o;
                            mensaje3="Persona encontrada";
                        }
                    }
                    System.out.println (mensaje3);
                    int opcion2=0;
                    if(persona!=null){
                        do{
                            System.out.println ("1-Modificar Nombre");
                            System.out.println ("2-Modificar Cedula");
                            System.out.println ("3-Modificar Apellido");
                            System.out.println ("4-Cancelar");
                            opcion2=sc.nextInt ();
                            switch (opcion2){
                                case 1:
                                    sc.nextLine ();
                                    System.out.println ("Nombre actual: "+persona.getNombre ());
                                    System.out.println("Ingrese el nuevo nombre: ");
                                    persona.setNombre (sc.nextLine ());
                                    break;
                                case 2:
                                    sc.nextLine();
                                    System.out.println ("Cedula actual: "+persona.getCedula ());
                                    System.out.println("Ingrese la nueva cedula de la persona: ");
                                    persona.setCedula (sc.nextLine ());
                                    break;
                                case 3:
                                    sc.nextLine();
                                    System.out.println ("Apellido actual: "+persona.getApellido ());
                                    System.out.println("Ingrese el nuevo apellido de la persona: ");
                                    persona.setApellido (sc.nextLine ());
                                    break;
                                case 4:
                                    System.out.println ("\nOpcion cancelada\n");
                                    guardarListaDePersonas (file,objetivo);
                                    break;
                                default:
                                    System.out.println ("\nOpcion invalida\n");
                            }
                        }while (opcion2!=4);
                    }
                    break;
                case 6:
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }while (opcion != 6) ;
    }
}