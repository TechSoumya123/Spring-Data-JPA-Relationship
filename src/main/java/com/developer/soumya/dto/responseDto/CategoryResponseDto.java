package com.developer.soumya.dto.responseDto;

import lombok.Data;

import java.util.List;
@Data
public class CategoryResponseDto {

    private Long id;
    private String name;
    private List<String> bookNames;
}
