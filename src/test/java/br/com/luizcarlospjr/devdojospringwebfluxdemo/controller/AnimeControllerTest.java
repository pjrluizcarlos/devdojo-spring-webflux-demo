package br.com.luizcarlospjr.devdojospringwebfluxdemo.controller;

import br.com.luizcarlospjr.devdojospringwebfluxdemo.WebfluxTest;
import br.com.luizcarlospjr.devdojospringwebfluxdemo.data.Anime;
import br.com.luizcarlospjr.devdojospringwebfluxdemo.service.AnimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AnimeControllerTest extends WebfluxTest {

    private static final Integer ID = 1;
    private static final String NAME = "Tensei Shitara Slime Datta Ken";

    @InjectMocks
    private AnimeController controller;

    @Mock
    private AnimeService service;

    @Test
    @DisplayName("Given there is anime; When call to find all; Should return anime")
    void givenThereIsAnime_whenCallToFindAllAnimes_shouldReturnAnime() {
        Anime entity = new Anime();

        when(service.findAll()).thenReturn(Flux.just(entity));

        StepVerifier.create(controller.findAll())
                .expectSubscription()
                .expectNext(entity)
                .verifyComplete();

        verify(service).findAll();
    }

    @Test
    @DisplayName("Given there is anime with ID; When call to find by ID; Should return anime with ID")
    void givenThereIsAnimeWithId_whenCallToFindById_shouldReturnAnimeWithId() {
        Anime anime = new Anime();

        when(service.findById(anyInt())).thenReturn(Mono.just(anime));

        StepVerifier.create(controller.findById(ID))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();

        verify(service).findById(ID);
    }

    @Test
    @DisplayName("Given there is anime with ID; When call to delete by ID; Should delete anime with ID")
    void givenThereIsAnimeWithId_whenCallToDeleteAnimeById_shouldDeleteAnimeWithId() {
        when(service.deleteById(anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(controller.deleteById(ID))
                .expectSubscription()
                .verifyComplete();

        verify(service).deleteById(ID);
    }

    @Test
    @DisplayName("Given valid anime to update; When call for anime update; Should update anime")
    void givenValidAnimeToUpdate_whenCallForAnimeUpdate_shouldUpdateAnime() {
        Anime completeModel = buildAnime();
        Anime modelWithoutId = completeModel.withId(null);

        when(service.update(any(Anime.class))).thenReturn(Mono.empty());

        StepVerifier.create(controller.update(ID, modelWithoutId))
                .expectSubscription()
                .verifyComplete();

        verify(service).update(completeModel);
    }

    @Test
    @DisplayName("Given valid anime to create; When call for anime creation; Should create and return created anime")
    void givenValidAnimeToUpdate_whenCallForAnimeCreation_shouldCreateAndReturnCreatedAnime() {
        Anime completeModel = buildAnime();
        Anime modelWithoutId = completeModel.withId(null);

        when(service.create(any(Anime.class))).thenReturn(Mono.just(completeModel));

        StepVerifier.create(controller.create(modelWithoutId))
                .expectSubscription()
                .expectNext(completeModel)
                .verifyComplete();

        verify(service).create(modelWithoutId);
    }

    private Anime buildAnime() {
        return Anime.builder()
                .id(ID)
                .name(NAME)
                .build();
    }

}