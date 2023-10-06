package com.ssafy.coffeeing.modules.auth.dto;

import javax.validation.constraints.Pattern;

public record SignUpRequest(
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$") String email,
	@Pattern(regexp = "^(?=.*[^A-Z]*[A-Z])(?=.*[^a-z]*[a-z])(?=.*\\d*\\d)(?=.*[^#?!@$%^&*-]*[#?!@$%^&*-]).{8,}$\n") String password
) {
}
