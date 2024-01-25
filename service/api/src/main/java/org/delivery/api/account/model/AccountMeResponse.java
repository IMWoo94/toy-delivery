package org.delivery.api.account.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
// @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountMeResponse {

	private String email;
	private String name;
	private LocalDateTime registeredAt;
}
