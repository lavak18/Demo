@Bean
public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
}

public class RequestData {
    private String key;
    private String value;
    // getters and setters
}

public class ResponseData {
    // Define the structure of the response data
}

@Service
public class ApiService {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<ResponseData> callEndpoint(RequestData requestData) {
        return webClientBuilder
            .build()
            .post()
            .uri("YOUR_ENDPOINT_URL")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestData)
            .retrieve()
            .bodyToMono(ResponseData.class);
    }
}

@Service
public class DataProcessorService {

    private final ApiService apiService;

    @Autowired
    public DataProcessorService(ApiService apiService) {
        this.apiService = apiService;
    }

    public Flux<ResponseData> processMultipleRequests(List<RequestData> requestDataList) {
        return Flux.fromIterable(requestDataList)
            .flatMap(apiService::callEndpoint);
    }
}


@Service
public class YourApplicationService {

    private final DataProcessorService dataProcessorService;

    @Autowired
    public YourApplicationService(DataProcessorService dataProcessorService) {
        this.dataProcessorService = dataProcessorService;
    }

    public void processAndSaveData(List<RequestData> requestDataList) {
        dataProcessorService.processMultipleRequests(requestDataList)
            .doOnNext(responseData -> {
                // Save each output as needed
                // For example, you can use a repository here
            })
            .subscribe();
    }
}




