package io.github.jessikafujimura.domain.repository;

import io.github.jessikafujimura.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedido extends JpaRepository<ItemPedido, Integer> {
}
