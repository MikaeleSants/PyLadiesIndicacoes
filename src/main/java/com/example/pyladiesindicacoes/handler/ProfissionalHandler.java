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

    public Mono<ServerResponse> buscarPorArea(ServerRequest request) {
        String area = request.pathVariable("area");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profissionalRepository.findByArea(area), Profissional.class);
    }

    public Mono<ServerResponse> listarAreas(ServerRequest request) {
        return profissionalRepository.findAll()
                .collectList()
                .map(list ->
                        list.stream()
                                .map(Profissional::area)
                                .distinct()
                                .toList()
                )
                .flatMap(areas ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(areas)
                );
    }


    public Mono<ServerResponse> buscarPorId(ServerRequest request) {
        String id = request.pathVariable("id");
        return profissionalRepository.findById(id)
                .flatMap(p -> ServerResponse.ok().bodyValue(p))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> salvar(ServerRequest request) {
        return request.bodyToMono(Profissional.class)
                .flatMap(profissionalRepository::save)
                .flatMap(p -> ServerResponse.ok().bodyValue(p));
    }

    public Mono<ServerResponse> editar(ServerRequest request) {
        String idAntigo = request.pathVariable("id");

        return request.bodyToMono(Profissional.class)
                .flatMap(novosDados ->
                        profissionalRepository.findById(idAntigo)
                                .flatMap(existing -> {
                                    java.util.function.Function<Profissional, Profissional> criarNovoRegistro = p ->
                                            new Profissional(
                                                    null,
                                                    novosDados.nome() != null ? novosDados.nome() : p.nome(),
                                                    novosDados.area() != null ? novosDados.area() : p.area(),
                                                    novosDados.contato() != null ? novosDados.contato() : p.contato(),
                                                    novosDados.camposEspecificos() != null ? novosDados.camposEspecificos() : p.camposEspecificos()
                                            );
                                    Profissional novoRegistro = criarNovoRegistro.apply(existing);
                                    return profissionalRepository.save(novoRegistro)
                                            .flatMap(saved ->
                                                    profissionalRepository.deleteById(idAntigo)
                                                            .then(ServerResponse.ok().bodyValue(saved))
                                            );
                                })
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deletar(ServerRequest request) {
        String id =
                request.pathVariable("id");
        return profissionalRepository.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}
