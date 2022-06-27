# Mini-Web-Framework
Mini-Web-Framework written in Java as part of my learning process about Java reflection.

My original idea was to implement two functionalities:
 1. Route mapping
 2. Dependency Injection

Due to lack of time, I only got to finish Route maping. In order to test it, make your Controller class in package "bussineslogic/controllers", and just like in popular
 frameworks, add annotations for controller with path argument (above class declaration, example "@Controller(path="/users")") and annotations for http method 
 ("@GET" or "@POST") with optional path annotation ("@Path(path = "example/path")") above method declaration. You can see an example in my code.
