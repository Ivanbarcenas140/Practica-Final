package view;

import controller.Controller;

public abstract class BaseView {

    protected Controller controller;

    public void setController(Controller c){
        this.controller=c;
    }
    public abstract void init(); 
    public abstract void showMessage(String msg);
    public abstract void showErrorMessage(String msg); 
    public abstract void end(); 

}
