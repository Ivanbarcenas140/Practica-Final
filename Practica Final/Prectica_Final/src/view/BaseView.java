package view;

import controller.Controller;

public abstract class BaseView {

    protected Controller controller;

    public void setController(Controller c){
        this.controller=c;
    }

    //Inicia la vista y desencadena la lógica de la vista.
    public abstract void init(); 
    
    //Permite notificar de un mensaje al usuario.
    public abstract void showMessage(String msg);
    
    //Permite notificar de un mensaje de error al usuario.
    public abstract void showErrorMessage(String msg); 
    
    //Finaliza la vista ordenadamente.
    public abstract void end(); 

}
