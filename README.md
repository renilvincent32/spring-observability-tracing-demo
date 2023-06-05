# spring-observability-tracing-demo
Contains a sample code base where a client calls a service, generates spans and traces for each call and exports them to OpenZipkin server using Micrometer tracing

## How to run the application ?
1. Checkout the code
2. Run ```BackendApplication.java``` - This would bootstrap ```zipkin``` and ```postgres``` docker images - via Spring Boot Docker Compose support
3. Run ```FrontEndApplication.java``` - This would make multiple calls to backend inventory service via ```CommandLineRunner```
4. Get the trace ID from the logs

<img width="1170" alt="image" src="https://github.com/renilvincent32/spring-observability-tracing-demo/assets/96111257/fdb14c3a-d09a-440c-9bf8-f55138815e09">

5. Copy the trace ID
6. Open [Zipkin](http://localhost:9411)
7. Paste the trace ID in seach box
8. You will see the spans related to the trace, along with metadata info such as Tags. Please see below.

<img width="1512" alt="image" src="https://github.com/renilvincent32/spring-observability-tracing-demo/assets/96111257/ac27ebaa-1aa4-47e3-9855-30d955c179ad">

