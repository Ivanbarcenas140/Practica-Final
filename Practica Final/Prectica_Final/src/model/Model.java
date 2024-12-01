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

    private ArrayList<Task> tasks = new ArrayList<>();
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
                this.tasks = (ArrayList<Task>) ois.readObject();
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
            oos.writeObject(tasks);
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
        if(tasks.contains(tareaActual)){
            return false;
        }else{
            tasks.add(tareaActual);
            return true;
        }

    }

    public boolean removeTarea(int id) {
        for (Task task : tasks) {
            if(task.getIdentifier()==id){
                tasks.remove(task);
                return true;
            }
        }
        return false;
    }

    public boolean comprobarTarea(int id) {
        for (Task task : tasks) {
            if(task.getIdentifier()==id){
                return true;
            }
        }
        return false;
    }

    public boolean exportTasksCSV() {
       return exporter.exportTask(tasks);
    }

    public boolean importTasksCSV() {
        //Añadimos las tareas que importamos, no las sustituimos.
        ArrayList<Task> tasksImportadas= exporter.importarTask();
        if(tasksImportadas!=null){
            for (Task taskImportada : tasksImportadas) {
                if(!tasks.contains(taskImportada)){
                    tasks.add(taskImportada);
                }
            }
            return true;
        }else{
            return false;
        }
    }





    

}
