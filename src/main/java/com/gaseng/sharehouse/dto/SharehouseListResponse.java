package com.gaseng.sharehouse.dto;

import java.util.List;

public record SharehouseListResponse (
        Long shrId,
        String shrTitle,
        String shrDescription,
        String ShrAddress,
        String shrPoster

) {

}
