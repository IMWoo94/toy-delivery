package org.delivery.storeadmin.domain.userorder.controller.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.delivery.db.userorder.enums.UserOrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderResponse {

	private Long id;
	private Long userId;
	private Long storeId;
	private UserOrderStatus status;
	private BigDecimal amount;
	private LocalDateTime orderedAt;
	private LocalDateTime acceptedAt;
	private LocalDateTime cookingStartedAt;
	private LocalDateTime deliveryStartedAt;
	private LocalDateTime receivedAt;

}
