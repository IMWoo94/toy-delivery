package org.delivery.storeadmin.domain.user.controller.model;

import java.time.LocalDateTime;

import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreUserResponse {

	private UserResponse user;
	private StoreResponse store;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class UserResponse {
		private Long id;
		private String email;
		private StoreUserStatus status;
		private StoreUserRole role;
		private LocalDateTime registeredAt;
		private LocalDateTime unregisteredAt;
		private LocalDateTime lastLoginAt;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class StoreResponse {
		private Long id;
		private String name;
	}
}
