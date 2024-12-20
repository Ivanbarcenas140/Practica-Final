package controller;

import java.util.ArrayList;

import model.CSVExporter;
import model.IExporter;
import model.JSONExporter;
import model.Model;
import model.Task;
import model.ExporterFactory;
import view.BaseView;

public class Controller {

    Model model;
    BaseView view;

    public Controller(Model m,BaseView v){
        this.model=m;
        this.view=v;

        v.setController(this);
    }

    public void start(){
        if(model.loadData()){
            view.showMessage("Datos cargados correctamente.");
        }else{
            view.showErrorMessage("No se encontro el fichero o no se han podido cargar los datos correctamente");
        }
        view.init();
    }

    public void end(){
        if(model.saveData()){
            view.showMessage("Se han guardado los datos correctamente.");
        }else{
            view.showErrorMessage("No se ha podido guardar el nuevo estado.");
        }
        view.end();
    }

    public boolean addTarea(int id, String title, String date, String content, int priority, int estimatedDuration,boolean completed) {
        return model.addTarea(id,title,date,content,priority,estimatedDuration,completed);

    }

    public boolean removeTarea(int id) {
        return model.removeTarea(id);
    }

    public boolean setExporter(String formato,String accion) {
        IExporter exporter;
        ExporterFactory factory = new ExporterFactory();
        if(formato.equals("json")) {
            exporter= factory.getExporter(formato);
            model.setExporter(exporter);
            if(accion.equals("e")){
                return model.exportTasksJSON();
            }else{
                return model.importTasksJSON();
            }
        }else{
            exporter= factory.getExporter(formato);
            model.setExporter(exporter);
            if(accion.equals("e")){
                return model.exportTasksCSV();
            }else{
                return model.importTasksCSV();
            }
        }
    }

    
    public ArrayList<String> getListadoPrioridad() {
        return model.getListadoPrioridad();
    }

    public ArrayList<String> showListadoCompleto() {
        return model.showListadoCompleto();
    }

    public int modificarCompleted(int id) {
        int estado = model.modificarCompleted(id);
        if(estado==1){
            return 1;
        }else if(estado == 2){
            return 2;
        }else{
            return 0;
        }
    }

    public boolean modificarTarea(int id, String title, String date, String content, int priority,int estimatedDuration, boolean completed) {
        return model.modificarTarea(id,title,date,content,priority,estimatedDuration,completed);
    }

    
}
