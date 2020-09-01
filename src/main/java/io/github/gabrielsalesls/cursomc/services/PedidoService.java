package io.github.gabrielsalesls.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.gabrielsalesls.cursomc.domain.Pedido;
import io.github.gabrielsalesls.cursomc.repositories.PedidoRepository;
import io.github.gabrielsalesls.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id 
				+ ", Tipo: " + Pedido.class.getName()));
	}
	
}
