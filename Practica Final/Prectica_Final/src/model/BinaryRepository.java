package model;

import java.util.ArrayList;

public class BinaryRepository implements IRepository{

    private ArrayList<Task> tasks = new ArrayList<>();

    @Override
    public Task addTask(Task t) {
        if(tasks.contains(t)){
            return null;
        }else{
            tasks.add(t);
            return t;
        }
    }

    @Override
    public void removeTask(Task t) {
        for (Task task : tasks) {
            if(task.getIdentifier()==t.getIdentifier()){
                tasks.remove(task);
            }
        }
    }

    @Override
    public void modifyTask(Task t) {
        for (Task task : tasks) {
            if(task.getIdentifier()==t.getIdentifier()){
                task.setTitle(t.getTitle());
                task.setDate(t.getDate());
                task.setPriority(t.getPriority());
                task.setContent(t.getContent());
                task.setEstimatedDuration(t.getEstimatedDuration());
                task.setCompleted(t.isCompleted());
            }
        }
    }

    @Override
    public ArrayList<Task> getAllTask() {
        return tasks;
    }

}
