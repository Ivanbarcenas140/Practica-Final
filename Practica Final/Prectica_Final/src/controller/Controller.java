package controller;

import model.CSVExporter;
import model.IExporter;
import model.JSONExporter;
import model.Model;
import view.BaseView;

public class Controller {

    Model model;
    BaseView view;

    public Controller(Model m,BaseView v){
        this.model=m;
        this.view=v;
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
            view.showMessage("Se han guardado los datos correctamente.Saliendo...");
        }else{
            view.showErrorMessage("No se ha podido guardar el nuevo estado.Saliendo...");
        }
    }

    public boolean addTarea(int id, String title, String date, String content, int priority, int estimatedDuration,boolean completed) {
        return model.addTarea(id,title,date,content,priority,estimatedDuration,completed);

    }

    public boolean removeTarea(int id) {
        return model.removeTarea(id);
    }

    public boolean comprobarTarea(int id) {
        return model.comprobarTarea(id);
    }

    public boolean setExporter(String formato,String accion) {
        IExporter exporter;
        switch (formato) {
            case "json":
                exporter= new JSONExporter();
                model.setExporter(exporter);
                if(accion.equals("e")){
                    return model.exportTasksJSON();
                }else{
                    return model.importTasksJSON();
                }
                break;
            case "csv":
                exporter = new CSVExporter();
                model.setExporter(exporter);
                if(accion.equals("e")){
                    return model.exportTasksCSV();
                }else{
                    return model.importTasksCSV();
                }
                break;
            default:
                break;
        }
    }
}
