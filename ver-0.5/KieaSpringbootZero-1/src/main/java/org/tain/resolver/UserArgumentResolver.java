package org.tain.resolver;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.tain.annotation.SocialUser;
import org.tain.domain.User;
import org.tain.domain.enums.SocialType;
import org.tain.repository.UserRepository;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

	private UserRepository userRepository;
	
	public UserArgumentResolver(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(SocialUser.class) != null && parameter.getParameterType().equals(User.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
		User user = (User) session.getAttribute("user");
		return getUser(user, session);
	}

	private Object getUser(User user, HttpSession session) {
		if (user == null) {
			try {
				OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
				Map<String, Object> map = authentication.getPrincipal().getAttributes();
				User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);
				
				user = this.userRepository.findByEmail(convertUser.getEmail());
				if (user == null) {
					user = this.userRepository.save(convertUser);
				}
				
				setRooleIfNotSame(user, authentication, map);
				session.setAttribute("user", user);
			} catch (ClassCastException e) {
				return user;
			}
		}
		return user;
	}

	private User convertUser(String authority, Map<String, Object> map) {
		if (SocialType.FACEBOOK.isEquals(authority))
			return getModernUser(SocialType.FACEBOOK, map);
		else if (SocialType.GOOGLE.isEquals(authority))
			return getModernUser(SocialType.GOOGLE, map);
		else if (SocialType.KAKAO.isEquals(authority))
			return getKakaoUser(map);
		return null;
	}

	private User getModernUser(SocialType socialType, Map<String, Object> map) {
		return User.builder()
				.name(String.valueOf(map.get("name")))
				.email(String.valueOf(map.get("email")))
				.principal(String.valueOf(map.get("id")))
				.socialType(socialType)
				.createdDate(LocalDateTime.now())
				.build();
	}

	private User getKakaoUser(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		Map<String, String> propertyMap = (HashMap<String, String>) map.get("properties");
		return User.builder()
				.name(propertyMap.get("nickname"))
				.email(String.valueOf(map.get("kaccount_email")))
				.principal(String.valueOf(map.get("id")))
				.socialType(SocialType.KAKAO)
				.createdDate(LocalDateTime.now())
				.build();
	}

	private void setRooleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) {
		if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(map, "N/A", AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())));
		}
	}
}
