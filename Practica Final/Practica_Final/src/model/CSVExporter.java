package model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVExporter implements IExporter{

    Path ruta = Paths.get(System.getProperty("user.home"),"Desktop","tasks.csv");
    String delimitador=",";

    @Override
    public ArrayList<Task> importarTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        try{
            List<String> lineas = Files.readAllLines(ruta);
            for ( String linea : lineas){
                Task t = Task.getPersonFromDelimitedString(linea,delimitador);
                if(t!=null){
                    tasks.add(t);
                }
            }
            return tasks;
        }catch(IOException e){
            //rellenear
            return null;
        }
    }

    @Override
    public boolean exportTask(ArrayList<Task> tasks) {
        ArrayList<String> lineas = new ArrayList<>();
        for (Task task : tasks) {
            lineas.add(task.getInstanceAsDelimitedString(delimitador));
        }
        try{
            Files.write(ruta, lineas, StandardCharsets.UTF_8);
            return true;
        }catch(IOException e){
            //rellenar
            return false;
        }
    }

}
