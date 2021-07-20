package com.example.salvo;

import com.example.salvo.Models.*;
import com.example.salvo.Repositorys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class SalvoApplication  extends SpringBootServletInitializer {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}


	public SalvoApplication() {
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository PlayerRepo, GameRepository GameRepo, GamePlayerRepository GmplyrRepo, ShipRepository ShipRepo, SalvoRepository SalRepo, ScoreRepository ScRepo){
		return (args) -> {


			Player player1 = new Player("jj@gmail.com", passwordEncoder().encode("24"));
			Player player2 = new Player("jj12312@gmail.com",passwordEncoder().encode("42"));
			Player player3 = new Player("121312@gmail.com",passwordEncoder().encode("kb"));
			Player player4 = new Player("jjLOpez@gmail.com", passwordEncoder().encode("mole"));
			Player player5 = new Player("EnzoPerez@gmail.com", passwordEncoder().encode("1234"));
			Player player6 = new Player("MarceloGallardo@gmail.com",passwordEncoder().encode ("541"));
			PlayerRepo.save(player1);
			PlayerRepo.save(player2);
			PlayerRepo.save(player3);
			PlayerRepo.save(player4);
			PlayerRepo.save(player5);
			PlayerRepo.save(player6);

			var game1 = new Game(new Date());
			var game2 = new Game(new Date());
			var game3 = new Game(new Date());
			var game4 = new Game(new Date());
			var game5 = new Game(new Date());
			var game6 = new Game(new Date());
			var game7 = new Game(new Date());
			GameRepo.save(game1);
			GameRepo.save(game2);
			GameRepo.save(game3);
			GameRepo.save(game4);
			GameRepo.save(game5);
			GameRepo.save(game6);
			GameRepo.save(game7);


			Score sc1 = new Score(1.0, new Date(),player1, game1);
			Score sc2 = new Score(0.0, new Date(),player2, game1);
			Score sc3 = new Score(0.0, new Date(),player3, game2);
			Score sc4 = new Score(1.0, new Date(),player4, game2);
			Score sc5 = new Score(0.5, new Date(),player5, game3);
			Score sc6 = new Score(0.5, new Date(),player6, game3);
			ScRepo.save(sc1);
			ScRepo.save(sc2);
			ScRepo.save(sc3);
			ScRepo.save(sc4);
			ScRepo.save(sc5);
			ScRepo.save(sc6);


			var gamePlayer1 = new GamePlayer(game1, player1, new Date());
			var gamePlayer2 = new GamePlayer(game1, player2, new Date());
			var gamePlayer3 = new GamePlayer(game2, player3, new Date());
			var gamePlayer4 = new GamePlayer(game2, player4, new Date());
			var gamePlayer5 = new GamePlayer(game3, player5, new Date());
			var gamePlayer6 = new GamePlayer(game3, player6, new Date());
			var gamePlayer7 = new GamePlayer(game4, player3, new Date());
			var gamePlayer8 = new GamePlayer(game4, player5, new Date());
			var gamePlayer9 = new GamePlayer(game5, player6, new Date());
			var gamePlayer10 = new GamePlayer(game5, player4, new Date());
			var gamePlayer11 = new GamePlayer(game6, player5, new Date());
			var gamePlayer12 = new GamePlayer(game6, player6, new Date());
			var gamePlayer13 = new GamePlayer(game7, player1, new Date());

			GmplyrRepo.save(gamePlayer1);
			GmplyrRepo.save(gamePlayer2);
			GmplyrRepo.save(gamePlayer3);
			GmplyrRepo.save(gamePlayer4);
			GmplyrRepo.save(gamePlayer5);
			GmplyrRepo.save(gamePlayer6);
			GmplyrRepo.save(gamePlayer7);
			GmplyrRepo.save(gamePlayer8);
			GmplyrRepo.save(gamePlayer9);
			GmplyrRepo.save(gamePlayer10);
			GmplyrRepo.save(gamePlayer11);
			GmplyrRepo.save(gamePlayer12);
			GmplyrRepo.save(gamePlayer13);


			Ship s1 = new Ship("Batlleship", Arrays.asList("A10", "B10", "C10"), gamePlayer1 );
			Ship s2 = new Ship("Batlleship", Arrays.asList("A1", "A2", "A3"), gamePlayer2);
			Ship s3 = new Ship("Cruise", Arrays.asList("D4", "D5" ,"D6"),  gamePlayer1);
			Ship s4 = new Ship("Cruise", Arrays.asList("F3","F4", "F5"),  gamePlayer2);
			Ship s5 = new Ship("Submarine", Arrays.asList("F1", "E1","D1" ),  gamePlayer1);
			Ship s6 = new Ship("Submarine", Arrays.asList("C7", "D7" , "E7"),  gamePlayer2);
			ShipRepo.save(s1);
			ShipRepo.save(s2);
			ShipRepo.save(s3);
			ShipRepo.save(s4);
			ShipRepo.save(s5);
			ShipRepo.save(s6);

			Salvo sal1 = new Salvo( 1, Arrays.asList("A1","B1"), gamePlayer1);
			Salvo sal2 = new Salvo( 1, Arrays.asList("A10","A5"), gamePlayer2);
			Salvo sal3 = new Salvo( 2, Arrays.asList("B3","B4"), gamePlayer1);
			Salvo sal4 = new Salvo( 2, Arrays.asList("E1","E2"), gamePlayer2);
			Salvo sal5 = new Salvo( 3, Arrays.asList("D7","D8"), gamePlayer1);
			Salvo sal6 = new Salvo( 3, Arrays.asList("C6","B6"), gamePlayer2);
			Salvo sal7 = new Salvo( 1, Arrays.asList("A1","B1"), gamePlayer6);
			Salvo sal8 = new Salvo( 2, Arrays.asList("A10","A5"), gamePlayer5);
			Salvo sal9 = new Salvo( 3, Arrays.asList("B3","B4"), gamePlayer6);
			Salvo sal10 = new Salvo( 4, Arrays.asList("E1","E2"), gamePlayer5);
			Salvo sal11 = new Salvo( 5, Arrays.asList("D7","D8"), gamePlayer6);
			Salvo sal12 = new Salvo( 6, Arrays.asList("C6","B6"), gamePlayer5);
			Salvo sal13 = new Salvo( 1, Arrays.asList("A1","B1"), gamePlayer6);
			Salvo sal14 = new Salvo( 2, Arrays.asList("A10","A5"), gamePlayer3);
			Salvo sal15 = new Salvo( 3, Arrays.asList("B3","B4"), gamePlayer6);
			Salvo sal16  = new Salvo( 4, Arrays.asList("E1","E2"), gamePlayer3);
			Salvo sal17 = new Salvo( 5, Arrays.asList("D7","D8"), gamePlayer6);
			Salvo sal18 = new Salvo( 6, Arrays.asList("C6","B6"), gamePlayer3);
			SalRepo.save(sal1);
			SalRepo.save(sal2);
			SalRepo.save(sal3);
			SalRepo.save(sal4);
			SalRepo.save(sal5);
			SalRepo.save(sal6);
			SalRepo.save(sal7);
			SalRepo.save(sal8);
			SalRepo.save(sal9);
			SalRepo.save(sal10);
			SalRepo.save(sal11);
			SalRepo.save(sal12);
			SalRepo.save(sal13);
			SalRepo.save(sal14);
			SalRepo.save(sal15);
			SalRepo.save(sal16);
			SalRepo.save(sal17);
			SalRepo.save(sal18);





		};

	}}
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {


	@Autowired
	PlayerRepository  prepo;



	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(name-> {
			Player player = prepo.findByUserName(name);
			if (player != null) {
				return new User(player.getUserName(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + name);
			}

		});

	}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.authorizeRequests()
				.antMatchers("/web/game.html", "/api/game_view/**", "/h2-console/**","/rest/**").hasAuthority("USER")
				.antMatchers("/**").permitAll();
			//	.and();
		http.formLogin()
				.usernameParameter("name")
				.passwordParameter("pwd")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		http.headers().frameOptions().disable();

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}


}}