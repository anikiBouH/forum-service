package telran.java45.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import telran.java45.accounting.dao.UserAccountRepository;
import telran.java45.accounting.model.UserAccount;

@Component
@RequiredArgsConstructor
@Order(10)
public class AuthentificationFilter implements Filter {

	final UserAccountRepository userAccountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			String[] credentials;
			try {
				credentials = getCredential(request.getHeader("Authorization"));
			} catch (Exception e) {
				response.sendError(401, "Token is not valid");
				return;
			}
			UserAccount userAccount = userAccountRepository.findById(credentials[0]).orElse(null);
			if (userAccount == null || !BCrypt.checkpw(credentials[1], userAccount.getPassword())) {
				response.sendError(401, "Login or password is not valid");
				return;
			} 
			request = new WrappedRequest(request, credentials[0]);
			
		}
		chain.doFilter(request, response);

	}

	private boolean checkEndPoint(String method, String path) {
		return !((("POST".equalsIgnoreCase(method) && path.matches("/account/register/?")) || path.matches("/forum/posts\\S*")));
	}

	private String[] getCredential(String token) {
		String[] basicAuthStrings = token.split(" ");
		String decode = new String(Base64.getDecoder().decode(basicAuthStrings[1]));
		return decode.split(":");
	}
	
	private static class WrappedRequest extends HttpServletRequestWrapper{
		
		String login;
		
		public WrappedRequest(HttpServletRequest request, String login) {
			super(request);
			this.login = login;
		}
		
		@Override
		public Principal getUserPrincipal() {
			return () -> login;
		}
		
	}

}
