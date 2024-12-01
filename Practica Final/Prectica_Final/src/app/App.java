package app;

import controller.Controller;
import view.BaseView;
import view.InteractiveView;
import model.Model;
import model.BinaryRepository;
import model.NotionRepository;
import model.IRepository;

public class App {
    public static void main(String[] args) throws Exception {
        IRepository repository;
        BaseView view;

        if(args.length == 4){
            repository = new NotionRepository();
            view = new InteractiveView();
        }else{
            repository= new BinaryRepository();
            view = new InteractiveView();
        }

        Model model = new Model(repository);
        Controller c = new Controller(model,view);

        c.start();
    }
}
