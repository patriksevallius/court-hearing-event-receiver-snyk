package uk.gov.justice.digital.hmpps.courthearingeventreceiver.config.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import java.util.List
import java.util.stream.Collectors

class AuthAwareTokenConverter : Converter<Jwt, AbstractAuthenticationToken> {
  override fun convert(jwt: Jwt): AbstractAuthenticationToken? {
    val clientId = jwt.claims["client_id"]
    val clientOnly = jwt.subject == clientId
    return AuthAwareAuthenticationToken(jwt, clientOnly, extractAuthorities(jwt))
  }

  private fun extractAuthorities(jwt: Jwt): Collection<GrantedAuthority>? {
    val authorities = jwt.claims.getOrDefault("authorities", List.of<Any>()) as Collection<String>
    return authorities.stream().map { role: String? ->
      SimpleGrantedAuthority(
        role
      )
    }.collect(Collectors.toUnmodifiableSet())
  }
}
