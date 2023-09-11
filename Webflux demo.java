import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebFluxExample {

    public static void main(String[] args) {
        // Create a Flux of data that you want to send to the endpoint
        Flux<String> inputData = Flux.just("data1", "data2", "data3");

        // Use flatMap to call the endpoint and save each output
        Flux<String> savedOutputs = inputData.flatMap(data -> {
            // Create a Mono for making the HTTP call to the endpoint
            Mono<String> resultMono = callEndpoint(data);
            
            // You can save the output here or perform any other operation
            // For this example, we'll just return the resultMono
            return resultMono;
        });

        // You can further process or subscribe to the savedOutputs Flux if needed
        savedOutputs.subscribe(output -> {
            // Handle each output as it arrives
            System.out.println("Received output: " + output);
        });

        // The above code won't block and will execute asynchronously.
    }

    // Simulate calling an HTTP endpoint and returning a Mono with the result
    public static Mono<String> callEndpoint(String data) {
        // Simulate an HTTP call and return the result as a Mono
        return Mono.just("Result for data: " + data);
    }
}
