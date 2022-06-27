package framework;

import framework.annotations.Controller;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Set;

public class DiscoveryMechanism {

    Reflections reflections;
    Set<Class<?>> annotated;
    ArrayList<Class> controllerClasses;
    public DiscoveryMechanism() {
        reflections = new Reflections("framework");
        annotated = reflections.getTypesAnnotatedWith(Controller.class);
        controllerClasses = new ArrayList<>();
        for (Class<?> controller : annotated) {
            System.out.println(controller.getName());
            controllerClasses.add(controller);
        }

    }

    public ArrayList<Class> getControllerClasses() {
        return controllerClasses;
    }
}
