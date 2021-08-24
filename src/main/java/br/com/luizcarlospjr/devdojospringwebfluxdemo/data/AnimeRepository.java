package br.com.luizcarlospjr.devdojospringwebfluxdemo.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AnimeRepository extends ReactiveCrudRepository<Anime, Integer> {

}
