package org.tain.domain.enums;

public enum SocialType {

	FACEBOOK("facebook"),
	GOOGLE("google"),
	KAKAO("kakao");
	
	private final String ROLE_PREFIX = "ROLE_";
	private String value;
	
	SocialType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getRoleType() {
		return this.ROLE_PREFIX + this.value.toUpperCase();
	}
	
	public boolean isEquals(String authority) {
		return this.getRoleType().equalsIgnoreCase(authority);
	}
}
