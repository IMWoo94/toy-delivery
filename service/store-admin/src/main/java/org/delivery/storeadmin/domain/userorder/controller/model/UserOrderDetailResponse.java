package org.delivery.storeadmin.domain.userorder.controller.model;

import java.util.List;

import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDetailResponse {

	private UserOrderResponse userOrderResponse;
	private List<StoreMenuResponse> storeMenuResponseList;
}
