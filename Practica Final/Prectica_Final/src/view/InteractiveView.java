package view;

import java.util.ArrayList;
import java.util.Scanner;
import controller.Controller;

import model.Task;

public class InteractiveView extends BaseView{
    Scanner scan = new Scanner(System.in);
    Controller controller;

    public void setController( Controller c){
        this.controller=c;
    }

    @Override
    public void init() {
        int opcion;
        do{
            System.out.println("\n|--------------------------------------|");
            System.out.println("\n|-----------| MENU TAREAS |------------|");
            System.out.println("\n|--------------------------------------|");
            System.out.println("\n| 1) Añadir una tarea                  |");
            System.out.println("\n| 2) Listado de las tareas             |");
            System.out.println("\n| 3) Modificar una tarea               |");
            System.out.println("\n| 4) Importar/Exportar tareas          |");
            System.out.println("\n| 5) Salir del programa                |");
            System.out.println("\n|--------------------------------------|");
            System.out.printf("Introduzca una opcion: ");
            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion) {
                case 1:
                    addTarea();
                    break;
                case 2:
                    listadoTarea();
                    break;
                case 3:
                    subMenuModificarTarea();
                    break;
                case 4:
                    subMenuExportarImportar();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("ERROR. OPCION NO VALIDA");
                    break;
            }


        }while(opcion!=5);

        
    }

    @Override
    public void showMessage(String msg) {
        System.out.printf("%s\n",msg);
       
    }

    @Override
    public void showErrorMessage(String msg) {
        System.out.printf("ERROR. %s\n",msg);
    }

    @Override
    public void end() {
        showMessage("Saliendo...");
    }

    private void addTarea() {
        System.out.printf("Introduzca el identificador unico de la tarea: ");
        int id = scan.nextInt();
        scan.nextLine();

        System.out.printf("Introduzca el titulo de la tarea: ");
        String title = scan.nextLine();

        System.out.println("Introduzca la fecha de la tarea.");
        System.out.printf("Introduzca el anio: ");
        int year= scan.nextInt();
        scan.nextLine();
        System.out.printf("Introduzca el mes: ");
        int mes= scan.nextInt();
        scan.nextLine();
        System.out.printf("Introduzca el dia: ");
        int dia= scan.nextInt();
        scan.nextLine();
        String date= String.format("%2d/%2d/%4d",dia,mes,year);

        System.out.printf("Introduzca el contenido de la tarea: ");
        String content = scan.nextLine();

        int priority;
        do{
            System.out.printf("Introduzca la prioridad de la tarea(1-5)(5 maxima prioridad): ");
            priority = scan.nextInt();
            scan.nextLine();
        }while((priority<1) || (priority>5));
        
        System.out.printf("Introduzca la duracion estimada de la tarea en minutos: ");
        int estimatedDuration = scan.nextInt();
        scan.nextLine();

        String c ;
        boolean completed=false;
        do{
            System.out.printf("Introduzca si la tarea esta completada(y/n): ");
            c = scan.nextLine();
            if(c.equals("y")){
                completed = true;
            }else if(c.equals("n")){
                completed = false;
            }
        }while((!c.equals("y"))&&(!c.equals("n")));

        if(controller.addTarea(id,title,date,content,priority,estimatedDuration,completed)){
            showMessage("Se ha añadido la tarea correctamente.");
        }else{
            showErrorMessage("Ya hay una tarea con ese identificador.");
        }
    }

    private void listadoTarea() {
        int opcion;
        do{
            System.out.println("\n|------------------------------------------|");
            System.out.println("\n|-----------| SUBMENU LISTADO |------------|");
            System.out.println("\n|------------------------------------------|");
            System.out.println("\n| 1) Ordenado por prioridad (Sin completar)|");
            System.out.println("\n| 2) Listado completo                      |");
            System.out.println("\n| 3) Salir del submenu                     |");
            System.out.println("\n|------------------------------------------|");
            System.out.printf("Introduzca una opcion: ");
            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion) {
                case 1:
                    showListadoPrioridad();
                break;
                case 2:
                    showListadoCompleto();
                break;
                case 3:
                    System.out.println("Saliendo del submenu...");
                break;
                default:
                    System.out.println("ERROR. OPCION NO VALIDA");
                break;
            }
        }while(opcion!=3);
    }

    private void showListadoPrioridad() {
        ArrayList<String> listado = controller.getListadoPrioridad();
        for (String taskActual : listado) {
            System.out.println(taskActual);
        }  
    }

    private void showListadoCompleto() {
        ArrayList<String> listado = controller.showListadoCompleto();
        for (String taskActual : listado) {
            System.out.println(taskActual);
        }    
    }
    

    private void subMenuModificarTarea() {
        int opcion;
        do{
            System.out.println("\n|------------------------------------------|");
            System.out.println("\n|----------| SUBMENU MODIFICAR |-----------|");
            System.out.println("\n|------------------------------------------|");
            System.out.println("\n| 1) Marcar como completa / incompleta     |");
            System.out.println("\n| 2) Modifiar informacion                  |");
            System.out.println("\n| 3) Eliminar tarea                        |");
            System.out.println("\n| 4) Salir del submenu                     |");
            System.out.println("\n|------------------------------------------|");
            System.out.printf("Introduzca una opcion: ");
            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion) {
                case 1:
                    modificarCompleted();
                break;
                case 2:
                    modificarInformacion();
                break;
                case 3:
                    removeTarea();
                break;
                case 4:
                    System.out.println("Saliendo del submenu...");
                break;
                default:
                    System.out.println("ERROR. OPCION NO VALIDA");
                break;
            }
        }while(opcion!=4);
    }

    private void modificarCompleted() {
        System.out.printf("Introduzca el identificador de la tarea que desee modificar: ");
        int id = scan.nextInt();
        scan.nextLine();

        if(controller.modificarCompleted(id)==1){
            showMessage("Tarea marcada como completada");
        }else if(controller.modificarCompleted(id)==2){
            showMessage("Tarea marcada como no completada");
        }else if(controller.modificarCompleted(id)==0){
            showErrorMessage("El identificador no pertenece a ninguna tarea.");
        }
    }

    private void modificarInformacion() {
        System.out.printf("Introduzca el identificador de la tarea que desee modificar: ");
        int id = scan.nextInt();
        scan.nextLine();

        System.out.printf("Introduzca el nuevo titulo de la tarea: ");
        String title = scan.nextLine();

        System.out.println("Introduzca la fecha de la tarea.");
        System.out.printf("Introduzca el anio: ");
        int year= scan.nextInt();
        scan.nextLine();
        System.out.printf("Introduzca el mes: ");
        int mes= scan.nextInt();
        scan.nextLine();
        System.out.printf("Introduzca el dia: ");
        int dia= scan.nextInt();
        scan.nextLine();
        String date= String.format("%2d/%2d/%4d",dia,mes,year);

        System.out.printf("Introduzca el nuevo contenido de la tarea: ");
        String content = scan.nextLine();

        int priority;
        do{
            System.out.printf("Introduzca la nueva prioridad de la tarea(1-5)(5 maxima prioridad): ");
            priority = scan.nextInt();
            scan.nextLine();
        }while((priority<1) || (priority>5));
        
        System.out.printf("Introduzca la nueva duracion estimada de la tarea en minutos: ");
        int estimatedDuration = scan.nextInt();
        scan.nextLine();


        String c ;
        boolean completed=false;
        do{
            System.out.printf("Introduzca si la tarea esta completada(y/n): ");
            c = scan.nextLine();
            if(c.equals("y")){
                completed = true;
            }else if(c.equals("n")){
                completed = false;
            }
        }while((!c.equals("y"))&&(!c.equals("n")));


        if(controller.modificarTarea(id,title,date,content,priority,estimatedDuration,completed)){
            showMessage("Tarea modificada correctamente.");
        }else{
            showErrorMessage("El identificador no pertenece a ninguna tarea.");
        }
    }

    private void removeTarea() {
        System.out.printf("Introduzca el identificador de la tarea que desee eliminar: ");
        int id = scan.nextInt();
        scan.nextLine();

        if(controller.removeTarea(id)){
            showMessage("Tarea eliminada correctamente.");
        }else{
            showErrorMessage("El identificador no pertenece a ninguna tarea.");
        }
    }

    private void subMenuExportarImportar() {
        int opcion;
        do{
            System.out.println("\n|--------------------------------------|");
            System.out.println("\n|---------| SUBMENU IMP/EXP |----------|");
            System.out.println("\n|--------------------------------------|");
            System.out.println("\n| 1) Importar                          |");
            System.out.println("\n| 2) Exportar                          |");
            System.out.println("\n| 3) Salir del submenu                 |");
            System.out.println("\n|--------------------------------------|");
            System.out.printf("Introduzca una opcion: ");
            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion) {
                case 1:
                    importTask();
                break;
                case 2:
                    exportTask();
                break;
                case 3:
                    System.out.println("Saliendo del submenu...");
                break;
                default:
                    System.out.println("ERROR. OPCION NO VALIDA");
                break;
            }
        }while(opcion!=3);
    }


    private void importTask() {
        System.out.printf("Introduzca el formato de importacion (csv-json)");
        String formato = scan.nextLine();
        if(formato.equals("csv")||formato.equals("json")){
            if(controller.setExporter(formato,"i")){
                showMessage("Importacion exitosa");
            }else{
                showErrorMessage("La importacion no ha sido exitosa");
            }
        }else{
            System.out.println("ERROR. FORMATO NO VALIDO");
        }
    }

    private void exportTask() {
        System.out.printf("Introduzca el formato de exportacion (csv-json)");
        String formato = scan.nextLine();
        if(formato.equals("csv")||formato.equals("json")){
            if(controller.setExporter(formato,"e")){
                showMessage("Exportacion exitosa");
            }else{
                showErrorMessage("La exportacion no ha sido exitosa");
            }
        }else{
            System.out.println("ERROR. FORMATO NO VALIDO");
        }
    }




}
