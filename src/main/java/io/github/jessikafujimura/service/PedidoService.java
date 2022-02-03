package io.github.jessikafujimura.service;

import io.github.jessikafujimura.api.dto.PedidoDTO;
import io.github.jessikafujimura.domain.entity.Pedido;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);
}
