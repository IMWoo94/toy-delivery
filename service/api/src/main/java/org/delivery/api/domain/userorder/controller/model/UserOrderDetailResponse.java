package org.delivery.api.domain.userorder.controller.model;

import java.util.List;

import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderDetailResponse {

	private UserOrderResponse userOrderResponse;
	private StoreResponse storeResponse;
	private List<StoreMenuResponse> storeMenuResponseList;

}
