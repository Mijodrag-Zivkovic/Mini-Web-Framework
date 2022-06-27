package framework.request;

import framework.request.enums.HttpMethod;

import java.util.HashMap;

public class Request {

    private HttpMethod method;
    private String location;
    private Header header;
    private HashMap<String, String> parameters;

    public Request() {
        this(HttpMethod.GET, "/");
    }

    public Request(HttpMethod method, String location) {
        this(method, location, new Header(), new HashMap<String, String>());
    }

    public Request(HttpMethod httpMethod, String location, Header header, HashMap<String, String> parameters) {
        this.method = httpMethod;
        this.location = location;
        this.header = header;
        this.parameters = parameters;
    }

    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    public HashMap<String, String> getParameters() {
        return new HashMap<String, String>(this.parameters);
    }

    public boolean isMethod(HttpMethod httpMethod) {
        return this.getMethod().equals(httpMethod);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getLocation() {
        return location;
    }

    public Header getHeader() {
        return header;
    }
}
