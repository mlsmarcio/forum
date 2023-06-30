package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso) {
		
		System.out.println(nomeCurso);
		
		// FILTRANDO PELO TITULO - PRECISA CRIAR O MÉTODO NO REPOSITORY APENAS A ASSINATURA
		// List<Topico> topicos = topicoRepository.findByTitulo(nomeTitulo);
		
		List<Topico> topicos;
		
		if (nomeCurso == null) {
			topicos = topicoRepository.findAll();
		} else {
			topicos = topicoRepository.obterPorNomeDoCurso(nomeCurso); // findByCurso_Nome(nomeCurso);
		}
		return TopicoDto.converter(topicos);
	}
	
	// Retorna um ResponseEntity para devolver o código 201 + a uri + a representação do que foi criado
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
		
	}
}
