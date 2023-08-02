package br.com.alura.forum.config.security;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}")
	private String expiration;

	@Value("${forum.jwt.secret}")
	private String secret;
	
	public String gerarToken(Authentication authentication) {
		
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date(); 
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration.trim())); 
		
		return Jwts.builder()
				.setIssuer("API do forum da Alura")			// APLICAÇÃO QUE ESTÁ GERANDO O TOKEN 
				.setSubject(logado.getId().toString())		// IDENTIFICA O USUÁRIO  
				.setIssuedAt(hoje)							// DAT DA GERAÇÃO DO TOKEN
				.setExpiration(dataExpiracao)				// TEMPO PARA EXPIRAR
				.signWith(SignatureAlgorithm.HS256, secret)	// ALGORITIMO DE CRIPTOGRAFIA E SENHA
				.compact();									// COMPACTAR
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}			
}
