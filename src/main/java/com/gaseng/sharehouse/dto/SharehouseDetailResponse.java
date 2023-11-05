package com.gaseng.sharehouse.dto;

import java.util.List;

public record SharehouseDetailResponse (
        Long id,
        Long memId,
        Long checkId,
        String title,
        String description,
        String address,
        String addressDetail,
        String poster,
        List<String> images
) {

}
