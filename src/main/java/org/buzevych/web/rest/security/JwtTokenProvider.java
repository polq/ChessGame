package org.buzevych.web.rest.security;

import io.jsonwebtoken.*;
import org.buzevych.web.rest.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

  private final String TOKEN_START = "Bearer_";
  private final String HTTP_HEADER_AUTHORIZATION_VALUE = "Authorization";
  private final String JWT_TOKEN_SALT = "SpringBoardGameREST";
  private final int TOKEN_VALIDITY_TIME = 1000 * 60 * 60 * 24;

  private UserDetailsService userDetailsService;

  @Autowired
  public JwtTokenProvider(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  public String createToken(String username, List<Role> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", getRoleNames(roles));

    Date now = new Date();
    Date validity = new Date(now.getTime() + TOKEN_VALIDITY_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, JWT_TOKEN_SALT)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader(HTTP_HEADER_AUTHORIZATION_VALUE);
    if (bearerToken != null && bearerToken.startsWith(TOKEN_START)) {
      return bearerToken.substring(TOKEN_START.length());
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_TOKEN_SALT).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());

    } catch (JwtException | IllegalArgumentException e) {
      throw new AuthenticationServiceException("JWT token is expired or invalid");
    }
  }

  private List<String> getRoleNames(List<Role> userRoles) {
    return userRoles.stream().map(Role::getName).collect(Collectors.toList());
  }

  private String getUsername(String token) {
    return Jwts.parser().setSigningKey(JWT_TOKEN_SALT).parseClaimsJws(token).getBody().getSubject();
  }
}
