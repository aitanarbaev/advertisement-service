package com.anarbaev.advertisementservice.controller;

import com.anarbaev.advertisementservice.model.Advertisement;
import com.anarbaev.advertisementservice.model.View;
import com.anarbaev.advertisementservice.repository.AdvertisementRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class AdvertisementController {

    @Autowired
    AdvertisementRepository advertisementRepository;

    // Метод создания нового объявления
    @PostMapping(value = "/advertisements", produces = "application/JSON;charset=UTF-8")
    public long createNote(@Valid @RequestBody Advertisement advertisement) {
        advertisement.setMainPhoto(advertisement.getPhotos()[0]);
        return advertisementRepository.save(advertisement).getId();
    }

    // Метод получчения конкретного объявления
    @GetMapping("/advertisements/{id}")
    public MappingJacksonValue getNoteById(@PathVariable(value = "id") Long advertisementId,
                                           @RequestParam(value = "fields", required = false,
                                                   defaultValue = "no params") String[] fields) {
        MappingJacksonValue bodyContainer = new MappingJacksonValue(advertisementRepository.getOne(advertisementId));
        bodyContainer.setSerializationView(View.MainFields.class);
        List<String> searchFor = Arrays.asList("description", "photos");
        if (Arrays.asList(fields).containsAll(searchFor))
            bodyContainer.setSerializationView(View.AllFields.class);
        else if (fields[0].matches(searchFor.get(0))) {
            bodyContainer.setSerializationView(View.MainFieldsWithDesc.class);
        } else if (fields[0].matches(searchFor.get(1)))
            bodyContainer.setSerializationView(View.MainFieldsWithPhotos.class);

        return bodyContainer;
    }

    // Метод получения списка всех объявлений
    @GetMapping("/advertisements/getall")
    @JsonView(View.AllFields.class)
    public List<Advertisement> getAllNotes(@PageableDefault(size = 10) Pageable pageable) {
        Page<Advertisement> pagedResult = advertisementRepository.findAll(pageable);
        return pagedResult.getContent();
    }
}

