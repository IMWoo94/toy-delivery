package org.delivery.api.domain.store.controller.model;

import java.math.BigDecimal;

import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponse {

	private Long id;
	private String name;
	private String address;
	private StoreStatus status;
	private StoreCategory category;
	private double star;
	private String thumbnailUrl;
	private BigDecimal minimumAmount;
	private BigDecimal minimumDeliveryAmount;
	private String phoneNumber;

}
