package com.gaseng.sharehouse.dto;

import java.util.List;

public record SharehouseResponse (
		Long id,
		String name,
		String createdAt,
		String title,
		String description,
		String address,
		String addressDetail,
		String poster,
		List<String> images
) {

}
