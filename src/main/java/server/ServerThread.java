package server;

import framework.ImprovisedStorage;
import framework.annotations.Controller;
import framework.annotations.GET;
import framework.annotations.POST;
import framework.annotations.Path;
import framework.bussineslogic.controllers.UserController;
import framework.bussineslogic.model.User;
import framework.request.enums.HttpMethod;
import framework.response.JsonResponse;
import framework.response.Response;
import framework.request.Header;
import framework.request.Helper;
import framework.request.Request;
import framework.request.exceptions.RequestNotValidException;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServerThread implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Map<String, Method> requestsMap;

    public ServerThread(Socket socket, ArrayList<Class> controllers){
        this.socket = socket;

        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        requestsMap = new HashMap<>();
        for(Class controller : controllers){
            Controller contr = (Controller)controller.getAnnotation(Controller.class);
            String mainRoute = contr.path();
            for(Method method : controller.getDeclaredMethods())
            {
                if(method.isAnnotationPresent(GET.class) || method.isAnnotationPresent(POST.class))
                {
                    String httpMethod;
                    String route="";
                    if(method.isAnnotationPresent(GET.class))
                        httpMethod = "GET";
                    else httpMethod = "POST";
                    if(method.isAnnotationPresent(Path.class)){
                        Path path = (Path) method.getAnnotation(Path.class);
                        route = path.path();
                    }
                    System.out.println(httpMethod+" "+mainRoute+route);
                    requestsMap.put(httpMethod+" "+mainRoute+route,method);
                }else continue;
            }
        }
    }

    public void run(){
        try {

            Request request = this.generateRequest();
            if(request == null) {
                in.close();
                out.close();
                socket.close();
                return;
            }


            // Response example
//            Map<String, Object> responseMap = new HashMap<>();
//            responseMap.put("route_location", request.getLocation());
//            responseMap.put("route_method", request.getMethod().toString());
//            responseMap.put("parameters", request.getParameters());
            //System.out.println(request.getLocation());
            Method m = requestsMap.get(request.getMethod().toString()+" "+request.getLocation());
            System.out.println(request.getMethod().toString()+" "+request.getLocation());
            Object data;
            try {
                data = m.invoke(m.getDeclaringClass().getDeclaredConstructor().newInstance());
                JsonResponse response = new JsonResponse(data);
                out.println(response.render());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            //JsonResponse response = new JsonResponse(data);

            //out.println(response.render());

            in.close();
            out.close();
            socket.close();

        } catch (IOException | RequestNotValidException e) {
            e.printStackTrace();
        }
    }

    private Request generateRequest() throws IOException, RequestNotValidException {
        String command = in.readLine();
        if(command == null) {
            return null;
        }

        String[] actionRow = command.split(" ");
        HttpMethod method = HttpMethod.valueOf(actionRow[0]);
        String route = actionRow[1];
        Header header = new Header();
        HashMap<String, String> parameters = Helper.getParametersFromRoute(route);

        do {
            command = in.readLine();
            String[] headerRow = command.split(": ");
            if(headerRow.length == 2) {
                header.add(headerRow[0], headerRow[1]);
            }
        } while(!command.trim().equals(""));

        if(method.equals(HttpMethod.POST)) {
            int contentLength = Integer.parseInt(header.get("content-length"));
            char[] buff = new char[contentLength];
            in.read(buff, 0, contentLength);
            String parametersString = new String(buff);

            HashMap<String, String> postParameters = Helper.getParametersFromString(parametersString);
            for (String parameterName : postParameters.keySet()) {
                parameters.put(parameterName, postParameters.get(parameterName));
            }
        }

        Request request = new Request(method, route, header, parameters);

        return request;
    }
}
