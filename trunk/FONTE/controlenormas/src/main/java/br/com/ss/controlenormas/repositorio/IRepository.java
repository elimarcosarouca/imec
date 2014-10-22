package br.com.ss.controlenormas.repositorio;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface para repositorio.
 * @author robson.ramos
 * @param <T>
 * @param <ID>
 */
public interface IRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

}
