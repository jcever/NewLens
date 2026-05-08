1. what is mvn:
mvn is the command-line tool for Maven, which is Java's main build system. It does for Java what npm does for JavaScript.
    what it does:
    1. Dependency management
        when you run mvn, it downloads the dependencies form Maven Central repository and puts them on the classpath so your code can import them.
    2. Compilation
        runs javac (Java compiler) on your .java files and produces .class (bytecode class files that JVM can execute) files in target/classes/.
    3. Testing - runs Junit tests
    4. Packaging 
        bundles everything into a .jar (Java Archive a zip file that bundles compiled .class files plus metadata into a unit) file in target/.
    5. Running
        with the Spring Boot plugin, mvn spring-boot:run starts your app.
pom.xml - Project Object Model, build configuration file = package.json(npm) or pyproject.toml (Python)
    Maven's instruction file, it tells Maven what your project is, what it depends on and how to build it.

2. What does @SpringBootApplication actually do, in three sub-annotations?

It's a meta-annotation bundling three:

- @SpringBootConfiguration — marks the class as a configuration source (a place where beans can be defined). It's a specialization of @Configuration.
- @EnableAutoConfiguration — tells Spring Boot to inspect the classpath and auto-wire reasonable defaults. Because spring-boot-starter-web is on the classpath, Spring sees Tomcat +
  Spring MVC and starts a web server on port 8080. Without this, you'd configure all that by hand.
- @ComponentScan — recursively scans the package of the annotated class (com.newlens.backend) and below for stereotype annotations (@RestController, @Service, @Repository,
  @Component, @Configuration) and registers each as a bean in the application context.

This is why your NewLensApplication lives at the root of the package tree — moving it deeper would shrink the scan area and Spring wouldn't find your controllers.

2. If you had two classes both annotated @RestController with @RequestMapping("/health"), what happens at startup?

The application fails to start. Spring builds a handler map at startup; when it tries to register the second method for GET /health, it detects the ambiguous mapping and throws a    
BeanCreationException wrapping an IllegalStateException with a message like:

Ambiguous mapping. Cannot map 'controllerB' method ...                              
to {GET /health}: There is already 'controllerA' bean method ... mapped.

This is a fail-fast design: ambiguous routing would be a silent bug at runtime, so Spring refuses to boot. (Note: this is at the route+method level. Two controllers can share a      
class-level @RequestMapping("/health") if their @GetMapping/@PostMapping paths differ.)

3. Which file decides which port the app runs on, and where would you change it?

backend/src/main/resources/application.yml, specifically:

server:                                                                                                                                                                               
port: 8080

Other ways to override (in increasing priority):
- application.yml
- environment variable: SERVER_PORT=9090
- JVM flag: -Dserver.port=9090
- command-line arg: --server.port=9090

Spring Boot's property resolution lets command-line args win over env vars, which win over the YAML file. Useful in deploys where the platform sets PORT for you.

4. Why is HealthResponse a record and not a regular class?

Because it's a DTO — a pure data carrier with no behavior. Records (Java 14+) give you for free what you'd otherwise hand-write or generate with Lombok:

- a canonical constructor
- accessor methods (status(), not getStatus())
- equals, hashCode, toString based on the fields
- immutable by design — final fields, no setters

For a DTO that just gets serialized to JSON and sent over the wire, you want exactly those properties: small, immutable, value-equal. Using a regular class would mean ~30 lines of
boilerplate for the same behavior, plus a real risk that someone adds a setter and breaks immutability.

When not to use a record: JPA @Entity classes (Hibernate needs a no-arg constructor and mutable fields for proxying), or anything that legitimately needs inheritance.

5. When you hit curl /health, what does Spring use to convert the Java object to JSON, and where does that converter come from?

Spring uses an HttpMessageConverter — specifically MappingJackson2HttpMessageConverter, which wraps Jackson (ObjectMapper).

Provenance chain:

1. Your pom.xml declares spring-boot-starter-web.
2. spring-boot-starter-web transitively pulls in spring-boot-starter-json.
3. spring-boot-starter-json pulls in jackson-databind (the Jackson core).
4. Spring Boot's auto-configuration sees Jackson on the classpath and registers MappingJackson2HttpMessageConverter automatically.
5. When your controller method returns HealthResponse, the DispatcherServlet picks the converter that matches the response's Accept header (defaults to application/json) and asks it
   to write the object to the response body.                                           

What MockMvc does
    MockMvc is a Spring helper that simulates HTTP without actually opening a network socket. It builds a fake HttpServletRequest, hands it to the Dispatcher Servlet, and lets you assert on the response.