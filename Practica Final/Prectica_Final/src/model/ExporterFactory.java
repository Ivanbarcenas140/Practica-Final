package model;

public class ExporterFactory {

    public IExporter getExporter(String formato){
        IExporter exporter;
        if (formato.equals("json")){
            exporter=new JSONExporter();
            return exporter;
        }else if(formato.equals("csv")){
            exporter=new CSVExporter();
            return exporter;
        }else{
            return null;
        }
    }
}
