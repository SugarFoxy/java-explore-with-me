package ru.practicum.explore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.explore.dto.HitDto;
import ru.practicum.explore.dto.StatsDto;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Service
public abstract class BaseClient {
    WebClient client = WebClient.create("http://localhost:9090");

    protected List<StatsDto> get(String path, MultiValueMap<String, String> params) {
        Flux<StatsDto> flux = client
                .get()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .queryParams(params)
                        .build())
                .retrieve()
                .bodyToFlux(StatsDto.class);
        return flux.collect(Collectors.toList())
                .share().block();
    }

    protected <T> void post(String path, T body) {
        final Mono<ClientResponse> postResponse =
                client
                        .post()
                        .uri(path)
                        .body(Mono.just(body), HitDto.class)
                        .accept(APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(ClientResponse.class);
        postResponse
                .map(ClientResponse::statusCode).subscribe(httpStatus -> log.info(httpStatus.toString()));
    }
}