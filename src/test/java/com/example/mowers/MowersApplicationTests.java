package com.example.mowers;

import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.core.dto.PlateauDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MowersApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void givenApp_whenCreatingPlateau_thenGetPlateau() {
        PlateauDto plateauDto = new PlateauDto(10, 20);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<Object>(plateauDto, headers);

        ResponseEntity<String> resultCreation = restTemplate.exchange(
                "/plateau",
                HttpMethod.POST,
                request,
                String.class);
        assertEquals(HttpStatus.CREATED, resultCreation.getStatusCode());

        String getPath = resultCreation.getHeaders().getLocation().getPath();
        assertNotNull(getPath);
        assertTrue(getPath.contains("/plateau/"));

        ResponseEntity<PlateauDto> resultGet = restTemplate.exchange(
                getPath,
                HttpMethod.GET,
                null,
                PlateauDto.class);
        assertEquals(HttpStatus.OK, resultGet.getStatusCode());
        assertEquals(plateauDto, resultGet.getBody());
    }

    @Test
    public void givenApp_whenCreatingPlateau_andCreatingMower_thenGetMower() {
        PlateauDto plateauDto = new PlateauDto(10, 20);
        HttpHeaders headersPlateau = new HttpHeaders();
        headersPlateau.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> resultPlateauCreation = restTemplate.exchange(
                "/plateau",
                HttpMethod.POST,
                new HttpEntity<Object>(plateauDto, headersPlateau),
                String.class);
        assertEquals(HttpStatus.CREATED, resultPlateauCreation.getStatusCode());
        String getPlateauPath = resultPlateauCreation.getHeaders().getLocation().getPath();
        String plateauId = getPlateauPath.substring(getPlateauPath.lastIndexOf("/") + 1);

        MowerDto mowerDto = new MowerDto(plateauId, new Point(10, 20), Orientation.N);
        HttpHeaders headersMower = new HttpHeaders();
        headersMower.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> resultMowerCreation = restTemplate.exchange(
                "/mower",
                HttpMethod.POST,
                new HttpEntity<Object>(mowerDto, headersMower),
                String.class);
        assertEquals(HttpStatus.CREATED, resultMowerCreation.getStatusCode());

        String getMowerPath = resultMowerCreation.getHeaders().getLocation().getPath();
        assertNotNull(getMowerPath);
        assertTrue(getMowerPath.contains("/mower/"));

        ResponseEntity<MowerDto> resultGet = restTemplate.exchange(
                getMowerPath,
                HttpMethod.GET,
                null,
                MowerDto.class);
        assertEquals(HttpStatus.OK, resultGet.getStatusCode());
        assertEquals(mowerDto, resultGet.getBody());
    }
}
