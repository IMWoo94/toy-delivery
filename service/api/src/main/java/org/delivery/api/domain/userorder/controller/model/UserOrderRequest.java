package org.delivery.api.domain.userorder.controller.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderRequest {

	// 주문
	// 특정 사용자가, 특정 메뉴를 주문
	// 특정 사용자 = 로그인 세션에 들어 있는 사용자
	// 특정 메뉴 ID 만 전달

	@NotNull
	private List<OrderMenu> storeMenuIdList;
}
