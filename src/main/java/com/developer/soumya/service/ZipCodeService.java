package com.developer.soumya.service;

import com.developer.soumya.dto.requestDto.ZipCodeRequestDto;
import com.developer.soumya.model.ZipCode;

import java.util.List;

public interface ZipCodeService {

    public ZipCode addZipCode(ZipCodeRequestDto zipCodeRequestDto);

    public List<ZipCode> getZipCodes();

    public ZipCode getZipCodeById(Long zipCodeId);

    public ZipCode deleteZipCode(Long zipCodeId);

    public ZipCode editZipCode(Long zipCodeId, ZipCodeRequestDto zipCodeRequestDto);

    public ZipCode addCityToZipCode(Long zipCodeId,Long cityId);

    public ZipCode removeCityToZipCode(Long zipCodeId);
}
