package com.example.pyladiesindicacoes.handler;

import com.example.pyladiesindicacoes.repository.ProfissionalRepository;
import com.example.pyladiesindicacoes.model.Profissional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.awt.*;

@Component
public class ProfissionalHandler {

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalHandler(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public Mono<ServerResponse> listar(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profissionalRepository.findAll(), Profissional.class);
    }

    public Mono<ServerResponse> buscarPorArea(ServerRequest request)
    {String area = request.pathVariable("area");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profissionalRepository.findByArea(area), Profissional.class);
    }

    public Mono<ServerResponse> buscarPorId(ServerRequest request)
    {String id = request.pathVariable("id");
        return profissionalRepository.findById(id)
                .flatMap(p -> ServerResponse.ok().bodyValue(p))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> salvar(ServerRequest request)
    {
        return request.bodyToMono(Profissional.class)
                        .flatMap(profissionalRepository::save)
                .flatMap(p -> ServerResponse.ok().bodyValue(p));
    }

    public Mono<ServerResponse> deletar(ServerRequest request) {
        String id =
                request.pathVariable("id");
        return profissionalRepository.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
