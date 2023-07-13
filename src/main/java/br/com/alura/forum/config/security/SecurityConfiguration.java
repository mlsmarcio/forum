package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService; 

	// CONFIGURAÇÕES DE AUTENTICAÇÃO, CONTROLE DE ACESSO, LOGIN
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	// CONFIGURAÇÕES DE AUTORIZAÇÃO, URL, PERFIL DE ACESSO
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable()  //tipo de ataque racker 
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // NÃO USARÁ SESSÃO
		
	}

	// CONFIGURAÇÕES DE RECURSOS STATICOS, REQUISIÇÕES PARA ARQUIVOS, JS, CSS, IMG
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
//	public static void main (String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123"));
//	}
}
