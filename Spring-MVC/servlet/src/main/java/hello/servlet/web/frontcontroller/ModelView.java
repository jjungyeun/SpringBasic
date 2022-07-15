package hello.servlet.web.frontcontroller;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
    private String viewName;
    private final Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName){
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void addData(String name, Object data){
        this.model.put(name, data);
    }

    public Object getData(String name){
        return this.model.get(name);
    }
}
