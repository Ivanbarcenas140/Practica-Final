package model;

import java.util.ArrayList;

public interface IRepository {
    public Task addTask(Task t);
    public void removeTask(Task t);
    public void modifyTask(Task t);
    public ArrayList<Task> exportTask(ArrayList<Task> Tasks);

}
