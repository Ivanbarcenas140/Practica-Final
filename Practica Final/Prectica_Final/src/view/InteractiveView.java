package view;

import java.util.Scanner;

public class InteractiveView extends BaseView{
    Scanner scan = new Scanner(System.in);

    @Override
    public void init() {
        int opcion;
        do{
            System.out.println("\n|--------------------------------------|");
            System.out.println("\n|-----------| MENU TAREAS |------------|");
            System.out.println("\n|--------------------------------------|");
            System.out.println("\n| 1) Añadir una tarea                  |");
            System.out.println("\n| 2) Eliminar una tarea                |");
            System.out.println("\n| 3) Modificar una tarea               |");
            System.out.println("\n| 4) Mostrar todas las tareas          |");
            System.out.println("\n| 5) Mostrar tareas completadas        |");
            System.out.println("\n| 6) Mostrar tareas pendientes         |");
            System.out.println("\n| 7) Importar/Exportar tareas          |");
            System.out.println("\n| 8) Salir del programa                |");
            System.out.println("\n|--------------------------------------|");
            System.out.printf("Introduzca una opcion: ");
            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion) {
                case 1:
                    addTarea();
                    break;
                case 2:
                    removeTarea();
                    break;
                case 3:
                    modificarTarea();
                    break;
                case 4:
                    
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;
                case 7:
                    subMenuExportarImportar();
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("ERROR. OPCION NO VALIDA");
                    break;
            }


        }while(opcion!=8);

        
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
        
    }

    private void addTarea() {
        System.out.printf("Introduzca el identificador unico de la tarea.");
        int id = scan.nextInt();
        scan.nextLine();

        System.out.printf("Introduzca el titulo de la tarea.");
        String title = scan.nextLine();

        System.out.println("Introduzca la fecha de la tarea.");
        System.out.printf("Introduzca el anio.");
        int year= scan.nextInt();
        scan.nextLine();
        System.out.printf("Introduzca el mes.");
        int mes= scan.nextInt();
        scan.nextLine();
        System.out.printf("Introduzca el dia.");
        int dia= scan.nextInt();
        scan.nextLine();
        String date= String.format("%2d/%2d/%4d",dia,mes,year);

        System.out.printf("Introduzca el contenido de la tarea.");
        String content = scan.nextLine();

        int priority;
        do{
            System.out.printf("Introduzca la prioridad de la tarea(1-5)(5 maxima prioridad).");
            priority = scan.nextInt();
            scan.nextLine();
        }while((priority<1) || (priority>5));
        
        System.out.printf("Introduzca la duracion estimada de la tarea en minutos.");
        int estimatedDuration = scan.nextInt();
        scan.nextLine();

        System.out.printf("Introduzca el identificador unico de la tarea.");
        boolean completed = scan.nextBoolean();
        scan.nextLine();

        if(controller.addTarea(id,title,date,content,priority,estimatedDuration,completed)){
            showMessage("Se ha añadido la tarea correctamente.");
        }else{
            showErrorMessage("No se ha podido añadir la tarea.");
        }
    }

    private void removeTarea() {
        System.out.printf("Introduzca el identificador de la tarea que desee eliminar");
        int id = scan.nextInt();
        scan.nextLine();
        if(controller.removeTarea(id)){
            showMessage("Se ha eliminado la tarea correctamente.");
        }else{
            showErrorMessage("No se ha podido eliminar la tarea.");
        }
    }

    private void modificarTarea() {
        System.out.printf("Introduzca el identificador de la tarea que desee eliminar");
        int id = scan.nextInt();
        scan.nextLine();
        if(!controller.comprobarTarea(id)){
            showErrorMessage("La tarea ya existe.");
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

            }else{

            }
        }else{
            System.out.println("ERROR. FORMATO NO VALIDO");
        }
    }

    private void exportTask() {
        System.out.printf("Introduzca el formato de exportacion (csv-json)");
        String formato = scan.nextLine();
        if(formato.equals("csv")||formato.equals("json")){
            controller.setExporter(formato,"e");
        }else{
            System.out.println("ERROR. FORMATO NO VALIDO");
        }
    }




}
