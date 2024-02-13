package org.delivery.storeadmin.domain.user.controller.model;

import org.delivery.db.storeuser.enums.StoreUserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoreUserRegisterRequest {

	@NotBlank
	private String storeName;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotNull
	private StoreUserRole role;
}
