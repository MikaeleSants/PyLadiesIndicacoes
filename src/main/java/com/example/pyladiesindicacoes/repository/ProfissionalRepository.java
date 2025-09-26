package com.example.pyladiesindicacoes.repository;

import com.example.pyladiesindicacoes.model.Profissional;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

public interface ProfissionalRepository extends ReactiveMongoRepository <Profissional, String>{
    Flux<Profissional> findByArea(String area);
}
