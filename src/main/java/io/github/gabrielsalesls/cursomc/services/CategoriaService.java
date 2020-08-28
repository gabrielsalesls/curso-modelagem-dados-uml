package io.github.gabrielsalesls.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.gabrielsalesls.cursomc.domain.Categoria;
import io.github.gabrielsalesls.cursomc.repositories.CategoriaRepository;
import io.github.gabrielsalesls.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id 
				+ ", Tipo: " + Categoria.class.getName()));
	}
	
}
