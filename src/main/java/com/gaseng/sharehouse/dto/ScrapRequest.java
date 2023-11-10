package com.gaseng.sharehouse.dto;

import com.gaseng.sharehouse.domain.Sharehouse;

public record ScrapRequest (
        Long id
) {
    public Sharehouse sharehouse() {
        return Sharehouse.builder()
                .build();
    }
}