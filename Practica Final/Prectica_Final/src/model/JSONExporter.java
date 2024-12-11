package model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONExporter implements IExporter{

    Path ruta = Paths.get(System.getProperty("user.home"), "Desktop", "task.json");
    File f = ruta.toFile();

    @Override
    public ArrayList<Task> importarTask() {
        if (f.isFile() && f.exists()) {
            try {
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
            Type tipoDeLista = new TypeToken<ArrayList<Task>>() {}.getType();
            return gson.fromJson(json, tipoDeLista);
        } catch (IOException ex) {
            System.err.println("Error:" + ex.getMessage());
            return null;
        }
        }else{
            return null;
        }
    }

    @Override
    public boolean exportTask(ArrayList<Task> tasks) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(tasks);
            Files.write(f.toPath(), json.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException ex) {
            System.err.println("Error:" + ex.getMessage());
            return false;
        }
    }

}
