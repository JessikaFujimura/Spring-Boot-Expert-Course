package io.github.jessikafujimura.domain.repository;

import io.github.jessikafujimura.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
