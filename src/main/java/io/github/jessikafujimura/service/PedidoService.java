package io.github.jessikafujimura.service;

import io.github.jessikafujimura.api.dto.PedidoDTO;
import io.github.jessikafujimura.domain.entity.Pedido;
import io.github.jessikafujimura.domain.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);

    Optional<Pedido> obterPedidoDetalhado(Integer id);

    void atualizarStatus(Integer id, StatusPedido status);
}
