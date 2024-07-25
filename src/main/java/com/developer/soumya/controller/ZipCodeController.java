package com.developer.soumya.controller;

import com.developer.soumya.dto.requestDto.ZipCodeRequestDto;
import com.developer.soumya.model.ZipCode;
import com.developer.soumya.service.ZipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zipcode")
public class ZipCodeController {

    private final ZipCodeService zipcodeService;

    @Autowired
    public ZipCodeController(ZipCodeService zipcodeService) {
        this.zipcodeService = zipcodeService;
    }

    @PostMapping("/add")
    public ResponseEntity<ZipCode> addZipcode(@RequestBody final ZipCodeRequestDto zipcodeRequestDto) {
        ZipCode zipcode = zipcodeService.addZipCode(zipcodeRequestDto);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ZipCode> getZipcode(@PathVariable final Long id) {
        ZipCode zipcode = zipcodeService.getZipCodeById(id);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ZipCode>> getZipcodes() {
        List<ZipCode> zipcodes = zipcodeService.getZipCodes();
        return new ResponseEntity<>(zipcodes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ZipCode> deleteZipcode(@PathVariable final Long id) {
        ZipCode zipcode = zipcodeService.deleteZipCode(id);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<ZipCode> editZipcode(@RequestBody final ZipCodeRequestDto zipcodeRequestDto,
                                               @PathVariable final Long id) {
        ZipCode zipcode = zipcodeService.editZipCode(id, zipcodeRequestDto);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }

    @PostMapping("/addCity/{cityId}/toZipcode/{zipcodeId}")
    public ResponseEntity<ZipCode> addCity(@PathVariable final Long cityId,
                                           @PathVariable final Long zipcodeId) {
        ZipCode zipcode = zipcodeService.addCityToZipCode(zipcodeId, cityId);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }

    @PostMapping("/deleteCity/{zipcodeId}")
    public ResponseEntity<ZipCode> deleteCity(@PathVariable final Long zipcodeId) {
        ZipCode zipcode = zipcodeService.removeCityToZipCode(zipcodeId);
        return new ResponseEntity<>(zipcode, HttpStatus.OK);
    }
}
