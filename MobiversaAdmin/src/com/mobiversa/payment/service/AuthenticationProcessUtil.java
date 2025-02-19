package com.mobiversa.payment.util;

import java.util.HashSet;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mobiversa.common.bo.AgentUserRole;

public class AuthenticationProcessUtil {
	
	public static boolean isUserAuthenticatedForPayoutIpnTrigger() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(AgentUserRole.AGENT_USER.name()));
	}
	
	public static String getCurrentUserAuthentication() {
		return new HashSet<>(SecurityContextHolder.getContext().getAuthentication().getAuthorities()).toString();
	}

}
