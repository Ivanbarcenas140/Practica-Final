package model;

import java.io.Serializable;
import java.util.Locale;

public class Task implements Serializable{
    private int identifier,priority,estimatedDuration;
    private String title,content,date;
    private boolean completed;
    
    public Task(int identifier, int priority, int estimatedDuration, String title, String content, String date,boolean completed) {
        this.identifier = identifier;
        this.priority = priority;
        this.estimatedDuration = estimatedDuration;
        this.title = title;
        this.content = content;
        this.date = date;
        this.completed = completed;
    }


    public static Task getPersonFromDelimitedString(String linea, String delimitador) {
        String[] chunks = linea.split(delimitador);
        if(chunks.length!=7){
         //rellenar(hay excepcion)
         return null;
        }
 
        try{
            int identifier = Integer.parseInt(chunks[0]);
            String title = chunks[1];
            String date = chunks[2];
            String content = chunks[3];
            int priority = Integer.parseInt(chunks[4]);
            int estimatedDuration = Integer.parseInt(chunks[5]);
            boolean completed =  Boolean.parseBoolean(chunks[6]);
            Task t = new Task(identifier, priority, estimatedDuration, title, content, date, completed);
            return t;
        }catch(Exception e){
         //rellenar
         return null;
        }
    }

    public String getInstanceAsDelimitedString(String delimitador) {
        return String.format(Locale.ENGLISH, "%d"+delimitador+"%s"+delimitador+"%s"+delimitador+"%s"+delimitador+"%d"+delimitador+"%d"+delimitador+"%b",identifier,title,date,content,priority,estimatedDuration,completed);
    }
 
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + identifier;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (identifier != other.identifier)
            return false;
        return true;
    }

    @Override
    public String toString(){
        return String.format("%d | %s | %s | %s | %d | %d | %b |",identifier,title,date,content,priority,estimatedDuration,completed);
    }


    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


}
