package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Model {

    private IExporter exporter;
    private IRepository repository;
    File ficheroSerializado;
    

    public Model(IRepository r){
        this.repository=r;
        ficheroSerializado = Paths.get(System.getProperty("user.home"), "Desktop", "model.bin").toFile();
    }

    public void setExporter(IExporter e){
        this.exporter=e;
    }

    public boolean loadData(){
        if (ficheroSerializado.exists() && ficheroSerializado.isFile()) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(ficheroSerializado));
                ArrayList<Task> listado = (ArrayList<Task>) ois.readObject();
                for (Task task : listado) {
                    repository.addTask(task);
                }
            } catch (IOException | ClassNotFoundException ex) {
                // Dejamos el error para la depuración, por el canal err.
               //rellenar
                return false;
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException ex) {
                        // Dejamos el error para la depuración, por el canal err.
                        //rellenar
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean saveData(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(ficheroSerializado));
            ArrayList<Task> listado = repository.getAllTask();
            oos.writeObject(listado);
            return true;
        } catch (IOException ex) {
            // Dejamos el error para la depuración, por el canal err.
            //rellenar
            return false;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    // Dejamos el error para la depuración, por el canal err.
                    //rellenar
                    return false;
                }
            }
        }
    }

    public boolean addTarea(int id, String title, String date, String content, int priority, int estimatedDuration,boolean completed) {
        Task tareaActual= new Task(id, priority, estimatedDuration, title, content, date, completed);
        if(repository.addTask(tareaActual)!=null){
            return true;
        }else{
            return false;
        }
    }


    public String showListadoPrioridad() {
        ArrayList<Task> tasksPrioridad = repository.getAllTask();
        for(int i=5; i>=0; i--){
            for (Task task : tasksPrioridad) {
                if((task.getPriority()==i)&&(!task.isCompleted())){
                    return task.toString();
                }
            }
        }
        return null; 
    }

    public String showListadoCompleto() {
       ArrayList<Task> listado = repository.getAllTask();
       for (Task task : listado) {
            return task.toString();
        }
        return null;
    }

    public int modificarCompleted(int id) {
        ArrayList<Task> listado = repository.getAllTask();
        for (Task task : listado) {
            if(task.getIdentifier()==id){
                if(task.isCompleted()){
                    Task taskActualizada = new Task(id, task.getPriority(), task.getEstimatedDuration(), task.getTitle(), task.getContent(), task.getDate(), false);
                    repository.modifyTask(taskActualizada);
                    return 2;
                }else{
                    Task taskActualizada = new Task(id, task.getPriority(), task.getEstimatedDuration(), task.getTitle(), task.getContent(), task.getDate(), true);
                    repository.modifyTask(taskActualizada);
                    return 1;
                }
            }
        }
        return 0;
        
    }

    public boolean modificarTarea(int id, String title, String date, String content, int priority, int estimatedDuration, boolean completed) {
        ArrayList<Task> listado = repository.getAllTask();
        for (Task task : listado) {
            if(task.getIdentifier()==id){
                Task taskActualizada = new Task(id, priority, estimatedDuration, title, content, date, completed);
                repository.modifyTask(taskActualizada);
                return true;
            }
        }
        return false;
    }

    public boolean removeTarea(int id) {
        ArrayList<Task> listado = repository.getAllTask();
        for (Task task : listado) {
            if(task.getIdentifier()==id){
                repository.removeTask(task);
                return true;
            }
        }
        return false;
    }

    public boolean exportTasksCSV() {
        ArrayList<Task> listado = repository.getAllTask();
        return exporter.exportTask(listado);
     }
 
     public boolean importTasksCSV() {
        //Añadimos las tareas que importamos, no las sustituimos.
        ArrayList<Task> tasksImportadas= exporter.importarTask();
        if(tasksImportadas!=null){
            for (Task taskImportada : tasksImportadas) {
                repository.addTask(taskImportada);
            }
            return true;
        }else{
            return false;
        }
     }

    public boolean exportTasksJSON() {
        ArrayList<Task> listado = repository.getAllTask();
        return exporter.exportTask(listado);
    }

    public boolean importTasksJSON() {
        ArrayList<Task> tasksImportadas= exporter.importarTask();
        if (tasksImportadas != null) {
            for (Task taskImportada : tasksImportadas) {
                repository.addTask(taskImportada);
            }
            return true;
        } else {
            return false;
        }
    }





    

}
