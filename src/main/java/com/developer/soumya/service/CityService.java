package com.developer.soumya.service;

import com.developer.soumya.dto.requestDto.CityRequestDto;
import com.developer.soumya.model.City;

import java.util.List;

public interface CityService {

    public City addCity(CityRequestDto cityRequestDto);

    public City getCity(Long cityId);

    public List<City> getCities();

    public City deleteCity(Long cityId);

    public City editCity(Long cityId, CityRequestDto cityRequestDto);
}
