package br.com.luizcarlospjr.devdojospringwebfluxdemo.service;

import br.com.luizcarlospjr.devdojospringwebfluxdemo.WebfluxTest;
import br.com.luizcarlospjr.devdojospringwebfluxdemo.data.Anime;
import br.com.luizcarlospjr.devdojospringwebfluxdemo.data.AnimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Slf4j
class AnimeServiceTest extends WebfluxTest {

    private static final Integer ID = 1;

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeRepository repository;

    @Test
    @DisplayName("Given there is anime; When call to find all; Should return anime")
    void givenThereIsAnime_whenCallToFindAllAnimes_shouldReturnAnime() {
        Anime entity = new Anime();

        when(repository.findAll()).thenReturn(Flux.just(entity));

        StepVerifier.create(service.findAll())
                .expectSubscription()
                .expectNext(entity)
                .verifyComplete();

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Given anime to create; When call for anime creation; Should create and return created anime")
    void givenAnimeToCreate_whenCallForAnimeCreation_shouldCreateAndReturnAnimeCreated() {
        Anime entityToSave = new Anime();
        Anime entitySaved = new Anime();

        when(repository.save(any(Anime.class))).thenReturn(Mono.just(entitySaved));

        StepVerifier.create(service.create(entityToSave))
                .expectSubscription()
                .expectNext(entitySaved)
                .verifyComplete();

        verify(repository).save(entityToSave);
    }

    @Test
    @DisplayName("Given anime with valid ID to update; When call for anime update; Should update anime")
    void givenAnimeWithValidIdToUpdate_whenCallForAnimeUpdate_shouldUpdateAnime() {
        Anime entityToUpdate = Anime.builder().id(ID).build();
        Anime validEntity = new Anime();
        Anime entityUpdated = new Anime();

        when(repository.findById(anyInt())).thenReturn(Mono.just(validEntity));
        when(repository.save(any(Anime.class))).thenReturn(Mono.just(entityUpdated));

        StepVerifier.create(service.update(entityToUpdate))
                .expectSubscription()
                .verifyComplete();

        verify(repository).findById(ID);
        verify(repository).save(entityToUpdate);
    }

    @Test
    @DisplayName("Given anime with invalid ID to update; When call for anime update; Should throw not found exception")
    void givenAnimeWithInvalidIdToUpdate_whenCallForAnimeUpdate_shouldThrowNotFoundException() {
        Anime entityToUpdate = Anime.builder().id(ID).build();

        when(repository.findById(anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(service.update(entityToUpdate))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();

        verify(repository).findById(ID);
        verify(repository, never()).save(any(Anime.class));
    }

    @Test
    @DisplayName("Given there is anime with ID; When call to find by ID; Should return anime with ID")
    void givenThereIsAnimeWithId_whenCallToFindById_shouldReturnAnimeWithId() {
        Anime anime = new Anime();

        when(repository.findById(anyInt())).thenReturn(Mono.just(anime));

        StepVerifier.create(service.findById(ID))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();

        verify(repository).findById(ID);
    }

    @Test
    @DisplayName("Given there is no anime with ID; When call to find by ID; Should throw not found exception")
    void givenThereIsNoAnimeWithId_whenCallToFindById_shouldThrowNotFoundException() {
        when(repository.findById(anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(service.findById(ID))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();

        verify(repository).findById(anyInt());
    }

    @Test
    @DisplayName("Given there is anime with ID; When call to delete by ID; Should delete anime with ID")
    void givenThereIsAnimeWithId_whenCallToDeleteAnimeById_shouldDeleteAnimeWithId() {
        Anime validEntity = new Anime();

        when(repository.findById(anyInt())).thenReturn(Mono.just(validEntity));
        when(repository.delete(any(Anime.class))).thenReturn(Mono.empty());

        StepVerifier.create(service.deleteById(ID))
                .expectSubscription()
                .verifyComplete();

        verify(repository).findById(ID);
        verify(repository).delete(validEntity);
    }

    @Test
    @DisplayName("Given there is no anime with ID; When call to delete by ID; Should throw not found exception")
    void givenThereIsNoAnimeWithId_whenCallToDeleteAnimeById_shouldThrowNotFoundException() {
        when(repository.findById(anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(service.deleteById(ID))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();

        verify(repository).findById(anyInt());
        verify(repository, never()).delete(any(Anime.class));
    }

}