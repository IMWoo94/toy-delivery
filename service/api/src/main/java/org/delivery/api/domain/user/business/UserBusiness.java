package org.delivery.api.domain.user.business;

import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserBusiness {

	private final UserService userService;
	private final UserConverter userConverter;
}
