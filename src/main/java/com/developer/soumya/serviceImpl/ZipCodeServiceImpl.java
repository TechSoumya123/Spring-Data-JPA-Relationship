package com.developer.soumya.serviceImpl;

import com.developer.soumya.dto.requestDto.ZipCodeRequestDto;
import com.developer.soumya.model.City;
import com.developer.soumya.model.ZipCode;
import com.developer.soumya.repository.ZipCodeRepository;
import com.developer.soumya.service.CityService;
import com.developer.soumya.service.ZipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class ZipCodeServiceImpl implements ZipCodeService {

    private final ZipCodeRepository zipCodeRepository;

    private final CityService cityService;

    @Autowired
    public ZipCodeServiceImpl(ZipCodeRepository zipCodeRepository, CityService cityService) {
        this.zipCodeRepository = zipCodeRepository;
        this.cityService = cityService;
    }

    @Transactional
    @Override
    public ZipCode addZipCode(ZipCodeRequestDto zipCodeRequestDto) {
        var zipCode = new ZipCode();
        zipCode.setName(zipCodeRequestDto.getName());
        if (zipCodeRequestDto.getCityId() == null) {
            return zipCodeRepository.save(zipCode);
        }
        City city = cityService.getCity(zipCodeRequestDto.getCityId());
        zipCode.setCity(city);
        return zipCodeRepository.save(zipCode);
    }

    @Override
    public List<ZipCode> getZipCodes() {
        List<ZipCode> list;
        list = new ArrayList<>(zipCodeRepository.findAll());
        return list;
    }

    @Override
    public ZipCode getZipCodeById(Long zipCodeId) {
        return zipCodeRepository.findById(zipCodeId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Zipcode with id " + zipCodeId + "could not found"));
    }

    @Override
    public ZipCode deleteZipCode(Long zipCodeId) {
        ZipCode zipCode = getZipCodeById(zipCodeId);
        zipCodeRepository.delete(zipCode);
        return zipCode;
    }

    @Transactional
    @Override
    public ZipCode editZipCode(Long zipCodeId, ZipCodeRequestDto zipCodeRequestDto) {
        ZipCode editZipCode = getZipCodeById(zipCodeId);
        editZipCode.setName(zipCodeRequestDto.getName());
        if (zipCodeRequestDto.getCityId() != null) {
            return editZipCode;
        }
        City city = cityService.getCity(zipCodeRequestDto.getCityId());
        editZipCode.setCity(city);
        return editZipCode;
    }

    @Transactional
    @Override
    public ZipCode addCityToZipCode(Long zipCodeId, Long cityId) {
        ZipCode zipCode = getZipCodeById(zipCodeId);
        City city = cityService.getCity(cityId);
        if (Objects.nonNull(zipCode.getCity())) {
            throw new IllegalArgumentException("zipcode already has a city");
        }
        zipCode.setCity(city);
        return zipCode;
    }

    @Transactional
    @Override
    public ZipCode removeCityToZipCode(Long zipCodeId) {

        ZipCode zipCode = getZipCodeById(zipCodeId);
        if (!Objects.nonNull(zipCode.getCity())) {
            throw new IllegalArgumentException("zipcode does not have a city");
        }
        zipCode.setCity(null);
        return zipCode;
    }
}
