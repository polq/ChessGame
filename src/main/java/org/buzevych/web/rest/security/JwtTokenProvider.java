package org.buzevych.web.rest.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.buzevych.web.rest.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component class that has main method that defines JWT token interaction. It has methods to create
 * a token from a user username and defined role, to get token from an HTTP request header, to get
 * Authentication from a token and to validate token.
 */
@Component
@Slf4j
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

    String finalToke =
        Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, JWT_TOKEN_SALT)
            .compact();
    log.info("Token {} for {} user has been successfully created", finalToke, username);
    return finalToke;
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader(HTTP_HEADER_AUTHORIZATION_VALUE);
    if (bearerToken != null && bearerToken.startsWith(TOKEN_START)) {
      String pureToken = bearerToken.substring(TOKEN_START.length());
      log.info("Token '{}' has been obtained from and HTTP request", pureToken);
      return pureToken;
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_TOKEN_SALT).parseClaimsJws(token);
      boolean valid = !claims.getBody().getExpiration().before(new Date());
      log.info("Token {} is valid - {}", token, valid);
      return valid;
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
