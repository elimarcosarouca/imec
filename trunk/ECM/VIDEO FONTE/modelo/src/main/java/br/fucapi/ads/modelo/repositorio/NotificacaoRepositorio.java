package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.fucapi.ads.modelo.dominio.Notificacao;

public interface NotificacaoRepositorio extends
		JpaRepository<Notificacao, Integer> {

	@Query("select u from Notificacao u where u.dataLeitura is null and u.login = :login")
	List<Notificacao> findByDataLeituraIsNullAndLogin(
			@Param("login") String login);

	@Query("select u from Notificacao u where u.login = :login")
	List<Notificacao> findByLogin(@Param("login") String login);

}
