package br.com.luizcarlospjr.devdojospringwebfluxdemo.service;

import br.com.luizcarlospjr.devdojospringwebfluxdemo.data.Anime;
import br.com.luizcarlospjr.devdojospringwebfluxdemo.data.AnimeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repository;

    public Flux<Anime> findAll() {
        return repository.findAll();
    }

    public Mono<Anime> findById(@NonNull Integer id) {
        return repository.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    public Mono<Anime> create(Anime anime) {
        return repository.save(anime);
    }

    private <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public Mono<Void> update(Anime anime) {
        return findById(anime.getId())
                .map(animeFound -> anime)
                .flatMap(repository::save)
                .then();
    }

}
