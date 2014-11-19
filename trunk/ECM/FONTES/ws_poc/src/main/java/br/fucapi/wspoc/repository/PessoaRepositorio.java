package br.fucapi.wspoc.repository;

import br.fucapi.wspoc.domain.Pessoa;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Pessoa.class)
public interface PessoaRepositorio {
}
