package br.com.luizcarlospjr.devdojospringwebfluxdemo.controller;

import br.com.luizcarlospjr.devdojospringwebfluxdemo.data.Anime;
import br.com.luizcarlospjr.devdojospringwebfluxdemo.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/animes")
@RequiredArgsConstructor
@Slf4j
public class AnimeController {

    private final AnimeService service;

    @GetMapping
    public Flux<Anime> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Anime> create(@Valid @RequestBody Anime anime) {
        return service.create(anime);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@Valid @RequestBody Anime anime) {
        return service.update(anime);
    }

    @GetMapping("{id}")
    public Mono<Anime> findById(@PathVariable("id") Integer id) {
        return service.findById(id);
    }

}
