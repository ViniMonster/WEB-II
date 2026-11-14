package com.pizzaria.repository;

import com.pizzaria.model.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /** Relação 1:N - todos os pedidos de um cliente. */
    List<Pedido> findByClienteId(Long clienteId);
}
