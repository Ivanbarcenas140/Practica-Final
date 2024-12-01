package model;

import java.util.ArrayList;

public interface IExporter {
    public ArrayList<Task> importarTask();
    public boolean exportTask(ArrayList<Task> tasks);
}
