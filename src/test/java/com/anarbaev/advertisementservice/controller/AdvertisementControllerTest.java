package com.anarbaev.advertisementservice.controller;

import com.anarbaev.advertisementservice.model.Advertisement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdvertisementController.class)
class AdvertisementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertisementController advertisementController;


    @Test
    void createNote() throws Exception {
        Advertisement advertisement = new Advertisement();
        advertisement.setId((long) 0);
        advertisement.setDescription("lorem ipsum");
        advertisement.setPhotos(new String[]{"https://26.img.avito.st/640x480/7903334526.jpg", "https://84.img.avito.st/640x480/7892250684.jpg"});
        advertisement.setPrice(11844690);
        advertisement.setTitle(" dolor sit amet");

        ObjectMapper mapper = new ObjectMapper();
        String inputJson = mapper.writeValueAsString(advertisement);
        given(advertisementController.createNote(advertisement)).willReturn(advertisement.getId());
        mockMvc.perform(post("/advertisements").contentType(APPLICATION_JSON).content(inputJson))
                .andExpect(status().isOk())
                .andExpect(content().string(advertisement.getId().toString()));

    }

    @Test
    void getNoteById() throws Exception {
        Advertisement advertisement = new Advertisement();
        advertisement.setId((long) 0);
        advertisement.setDescription("lorem ipsum");
        advertisement.setPhotos(new String[]{"https://26.img.avito.st/640x480/7903334526.jpg", "https://84.img.avito.st/640x480/7892250684.jpg"});
        advertisement.setPrice(11844690);
        advertisement.setTitle(" dolor sit amet");
        advertisement.setMainPhoto("https://26.img.avito.st/640x480/7903334526.jpg");

        MappingJacksonValue bodyContainer = new MappingJacksonValue(advertisement);
        given(advertisementController.getNoteById((long) 1, new String[]{"description", "photos"})).willReturn(bodyContainer);
        mockMvc.perform(get("/advertisements/1?fields=description, photos").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"title\":\" dolor sit amet\",\"price\":1.184469E7,\"mainPhoto\":\"https://26.img.avito.st/640x480/7903334526.jpg\",\"description\":\"lorem ipsum\",\"photos\":[\"https://26.img.avito.st/640x480/7903334526.jpg\",\"https://84.img.avito.st/640x480/7892250684.jpg\"]}"));
    }

    @Test
    public void getAll() throws Exception {
        Advertisement advertisement = new Advertisement();
        advertisement.setId((long) 0);
        advertisement.setDescription("lorem ipsum");
        advertisement.setPhotos(new String[]{"https://26.img.avito.st/640x480/7903334526.jpg", "https://84.img.avito.st/640x480/7892250684.jpg"});
        advertisement.setPrice(11844690);
        advertisement.setTitle(" dolor sit amet");
        advertisement.setMainPhoto("https://26.img.avito.st/640x480/7903334526.jpg");

        Pageable pageable = PageRequest.of(0, 1);
        List<Advertisement> allAdvertisements = singletonList(advertisement);

        given(advertisementController.getAllNotes(pageable)).willReturn(allAdvertisements);

        mockMvc.perform(get("/advertisements/getall?page=0&size=1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().string("[{\"title\":\" dolor sit amet\",\"price\":1.184469E7,\"mainPhoto\":\"https://26.img.avito.st/640x480/7903334526.jpg\",\"description\":\"lorem ipsum\",\"photos\":[\"https://26.img.avito.st/640x480/7903334526.jpg\",\"https://84.img.avito.st/640x480/7892250684.jpg\"]}]"));
    }
}