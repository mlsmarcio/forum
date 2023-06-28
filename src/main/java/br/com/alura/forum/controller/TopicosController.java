package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso) {
		
		if (nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDto.converter(topicos);

		} else {
			// CRIANDO A QUERY MANUALMENTE SEM USAR O PADRÃO DO SPRING
			List<Topico> topicos = topicoRepository.topicosPorNomeDoCurso(nomeCurso);
			return TopicoDto.converter(topicos);
		}
		
//		// UTILIZANDO UMA LISTA COMO RETORNO
//		Topico topico = new Topico("Duvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
//		return TopicoDto.converter(Arrays.asList(topico, topico, topico));
		
		// LISTANDO TODOS OS TOPICOS
//		List<Topico> topicos = topicoRepository.findAll();
//		return TopicoDto.converter(topicos);

		// LISTANDO COM FILTRO (BUSCA O ATRIBUTO nome NO RELACIONAMENTO Curso. Utiliza-se o _ caso exista um atributo com nome CursoNome 
//		List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
//		List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
//		return TopicoDto.converter(topicos);
		
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
}
