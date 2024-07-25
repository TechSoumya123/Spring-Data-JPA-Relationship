package com.developer.soumya.serviceImpl;

import com.developer.soumya.dto.requestDto.CityRequestDto;
import com.developer.soumya.model.City;
import com.developer.soumya.repository.CityRepository;
import com.developer.soumya.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City addCity(CityRequestDto cityRequestDto) {
        City city = new City();
        city.setName(cityRequestDto.getName());
        return cityRepository.save(city);
    }

    @Override
    public City getCity(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new IllegalArgumentException("City not found with this id" + cityId));
    }

    @Override
    public List<City> getCities() {
        return new ArrayList<>(cityRepository.findAll());
    }



    @Override
    public City deleteCity(Long cityId) {
        City city = getCity(cityId);
        cityRepository.delete(city);
        return city;
    }

    @Transactional
    @Override
    public City editCity(Long cityId, CityRequestDto cityRequestDto) {
        City editCity = getCity(cityId);
        editCity.setName(cityRequestDto.getName());
        return editCity;
    }
}
