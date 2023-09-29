import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ApiCaller {

    private final WebClient webClient;

    public ApiCaller() {
        this.webClient = WebClient.create(); // Create a WebClient instance
    }

    public Flux<String> hitApiMultipleTimes(Iterable<String> values, String baseUrl) {
        return Flux.fromIterable(values) // Create a Flux from the Iterable
                .flatMap(value -> callApi(baseUrl + value)) // FlatMap to make concurrent API calls
                .doNext(response -> {
                    // Perform operations on each response (e.g., logging, processing, etc.)
                    System.out.println("Received response: " + response);
                });
    }

    private Mono<String> callApi(String apiUrl) {
        // Make an API call using WebClient and return the response as a Mono
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class);
    }

    public static void main(String[] args) {
        Iterable<String> values = List.of("value1", "value2", "value3"); // Example values
        String baseUrl = "https://api.example.com/data/"; // Example API base URL

        ApiCaller apiCaller = new ApiCaller();
        Flux<String> responses = apiCaller.hitApiMultipleTimes(values, baseUrl);

        // Subscribe to the Flux to initiate the API calls and handle responses
        responses.subscribe();
    }
}
