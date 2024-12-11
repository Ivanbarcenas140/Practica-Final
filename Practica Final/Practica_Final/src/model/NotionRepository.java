package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import notion.api.v1.NotionClient;
import notion.api.v1.http.OkHttp5Client;
import notion.api.v1.logging.Slf4jLogger;
import notion.api.v1.model.databases.QueryResults;
import notion.api.v1.model.pages.Page;
import notion.api.v1.model.pages.PageParent;
import notion.api.v1.model.pages.PageProperty;
import notion.api.v1.model.pages.PageProperty.RichText;
import notion.api.v1.model.pages.PageProperty.RichText.Text;
import notion.api.v1.request.databases.QueryDatabaseRequest;
import notion.api.v1.request.pages.CreatePageRequest;
import notion.api.v1.request.pages.UpdatePageRequest;

public class NotionRepository implements IRepository{

    private final NotionClient client;
    private final String databaseId;
    private final String titleColumnName = "Identifier";

    public NotionRepository(String API_KEY, String DATABASE_ID) {
        this.client = new NotionClient(API_KEY);
        client.setHttpClient(new OkHttp5Client(60000,60000,6000));
        client.setLogger(new Slf4jLogger());
        System.setProperty("notion.api.v1.logging.StdoutLogger", "debug");
        this.databaseId=DATABASE_ID;
    }

    @Override
    public Task addTask(Task t) {
        int id = t.getIdentifier();
        String identifier = String.format("%d", id);
        Map<String, PageProperty> propiedades = Map.of(
            "Identifier",createTitleProperty(identifier),
            "Titulo",createRichTextProperty(t.getTitle()),
            "Fecha",createRichTextProperty(t.getDate()),
            "Contenido",createRichTextProperty(t.getContent()),
            "Prioridad",createNumberProperty(t.getPriority()),
            "Duración (min)",createNumberProperty(t.getEstimatedDuration()),
            "Completado",createCheckboxProperty(t.isCompleted())
        );

        PageParent parent = PageParent.database(databaseId);
        CreatePageRequest request = new CreatePageRequest(parent, propiedades);
        Page response = client.createPage(request);
        System.out.println("Página creada con ID (interno Notion)" + response.getId());
        return t;
    }

    @Override
    public void removeTask(Task t) {
        try {
            String identifier = String.format("d",t.getIdentifier());
            String pageId = findPageIdByIdentifier(identifier, titleColumnName);
            if (pageId == null) {
                System.out.println("No se encontró un registro con el Identifier: " + t.getIdentifier());
                return;
            }
            UpdatePageRequest updateRequest = new UpdatePageRequest(pageId, Collections.emptyMap(), true);
            client.updatePage(updateRequest);
            System.out.println("Página archivada con ID (interno Notion)" + pageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyTask(Task t) {
        try {
            String identifier = String.format("d",t.getIdentifier());
            String pageId = findPageIdByIdentifier(identifier,titleColumnName);
            if (pageId == null) {
                System.out.println("No se encontró un registro con el Identifier: " + t.getIdentifier());
                return;
            }
            Map<String, PageProperty> updatedProperties = Map.of(
                "Titulo",createRichTextProperty(t.getTitle()),
                "Fecha",createRichTextProperty(t.getDate()),
                "Contenido",createRichTextProperty(t.getContent()),
                "Prioridad",createNumberProperty(t.getPriority()),
                "Duración (min)",createNumberProperty(t.getEstimatedDuration()),
                "Completado",createCheckboxProperty(t.isCompleted())
            );
            UpdatePageRequest updateRequest = new UpdatePageRequest(pageId, updatedProperties);
            client.updatePage(updateRequest);

            System.out.println("Página actualizada con ID (interno Notion)" + pageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Task> getAllTask() {
        ArrayList<Task> listado = new ArrayList<>();
        try {
            QueryDatabaseRequest queryRequest = new QueryDatabaseRequest(databaseId);
            QueryResults queryResults = client.queryDatabase(queryRequest);
            for (Page page : queryResults.getResults()) {
                Map<String, PageProperty> properties = page.getProperties();
                Task task = mapPageToTask(page.getId(),properties);
                if (task != null) {
                    listado.add(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listado;
    }

    private String findPageIdByIdentifier(String identifier,String titleColumnName) {
        try {
            QueryDatabaseRequest queryRequest = new QueryDatabaseRequest(databaseId);
            QueryResults queryResults = client.queryDatabase(queryRequest);

            for (Page page : queryResults.getResults()) {
                Map<String, PageProperty> properties = page.getProperties();
                if (properties.containsKey(titleColumnName) &&
                        properties.get(titleColumnName).getTitle().get(0).getText().getContent().equals(identifier)) {
                    return page.getId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PageProperty createTitleProperty(String title) {
        RichText idText = new RichText();
        idText.setText(new Text(title));
        PageProperty idProperty = new PageProperty();
        idProperty.setTitle(Collections.singletonList(idText));
        return idProperty;
    }

    private PageProperty createRichTextProperty(String text) {
        RichText richText = new RichText();
        richText.setText(new Text(text));
        PageProperty property = new PageProperty();
        property.setRichText(Collections.singletonList(richText));
        return property;
    }

    private PageProperty createNumberProperty(Integer number) {
        PageProperty property = new PageProperty();
        property.setNumber(number);
        return property;
    }

    private PageProperty createCheckboxProperty(boolean checked) {
        PageProperty property = new PageProperty();
        property.setCheckbox(checked);
        return property;
    }

    private Task mapPageToTask(String pageId,Map<String, PageProperty> properties) {
        try {
            String id = properties.get("Identifier").getTitle().get(0).getText().getContent();
            int identifier = Integer.parseInt(id);
            String title= properties.get("Titulo").getRichText().get(0).getText().getContent();
            String date = properties.get("Fecha").getRichText().get(0).getText().getContent();
            String content = properties.get("Contenido").getRichText().get(0).getText().getContent();
            int priority = properties.get("Prioridad").getNumber().intValue();
            int estimatedDuration =  properties.get("Duración (min)").getNumber().intValue();
            boolean completed =  properties.get("Completado").getCheckbox();
            Task task = new Task(identifier, priority, estimatedDuration, title, content, date, completed);
            return task;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
