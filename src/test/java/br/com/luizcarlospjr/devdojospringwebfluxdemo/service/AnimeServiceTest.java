package br.com.luizcarlospjr.devdojospringwebfluxdemo.service;

import br.com.luizcarlospjr.devdojospringwebfluxdemo.data.Anime;
import br.com.luizcarlospjr.devdojospringwebfluxdemo.data.AnimeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    private static final Integer ID = 1;

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeRepository repository;

    @BeforeAll
    public static void blockHoundSetUp() {
        BlockHound.install();
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void findAll() {
        Anime anime = new Anime();

        when(repository.findAll()).thenReturn(Flux.just(anime));

        StepVerifier.create(service.findAll())
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();

        verify(repository).findAll();
    }

    @Test
    void findById() {
        Anime anime = new Anime();

        when(repository.findById(ID)).thenReturn(Mono.just(anime));

        StepVerifier.create(service.findById(ID))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();

        verify(repository).findById(ID);
    }

    @Test
    void findById_notFound() {
        when(repository.findById(anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(service.findById(ID))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();

        verify(repository).findById(anyInt());
    }

    @Test
    void isBlockHoundWorking() {
        FutureTask<String> task = new FutureTask<>(() -> {
            Thread.sleep(0);
            return "";
        });

        Schedulers.parallel().schedule(task);

        try {
            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("Should fail");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

}