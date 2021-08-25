package br.com.luizcarlospjr.devdojospringwebfluxdemo.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends ReactiveCrudRepository<Anime, Integer> {

}
