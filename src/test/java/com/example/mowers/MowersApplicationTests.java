package com.example.mowers;

import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.core.dto.PlateauDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MowersApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertNotNull(restTemplate);
        MowersApplication.main(new String[]{});
    }

    @Test
    void givenApp_whenCreatingPlateau_thenGetPlateau() {
        PlateauDto plateauDto = new PlateauDto(10, 20);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<PlateauDto> resultCreation = restTemplate.exchange(
                "/plateau",
                HttpMethod.POST,
                new HttpEntity<Object>(plateauDto, headers),
                PlateauDto.class);
        assertEquals(HttpStatus.CREATED, resultCreation.getStatusCode());
        assertEquals(plateauDto, resultCreation.getBody());

        String getPath = resultCreation.getHeaders().getLocation().getPath();
        assertNotNull(getPath);
        assertTrue(getPath.contains("/plateau/"));

        ResponseEntity<PlateauDto> resultGet = restTemplate.exchange(
                getPath,
                HttpMethod.GET,
                null,
                PlateauDto.class);
        assertEquals(HttpStatus.OK, resultGet.getStatusCode());
    }

    @Test
    void givenApp_whenCreatingPlateau_andCreatingMower_thenGetMower() {
        PlateauDto plateauDto = new PlateauDto(10, 20);
        HttpHeaders headersPlateau = new HttpHeaders();
        headersPlateau.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<PlateauDto> resultPlateauCreation = restTemplate.exchange(
                "/plateau",
                HttpMethod.POST,
                new HttpEntity<Object>(plateauDto, headersPlateau),
                PlateauDto.class);
        assertEquals(HttpStatus.CREATED, resultPlateauCreation.getStatusCode());
        String getPlateauPath = resultPlateauCreation.getHeaders().getLocation().getPath();
        String plateauId = getPlateauPath.substring(getPlateauPath.lastIndexOf("/") + 1);

        MowerDto mowerDto = new MowerDto(plateauId, new Point(10, 20), Orientation.N);
        HttpHeaders headersMower = new HttpHeaders();
        headersMower.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<MowerDto> resultMowerCreation = restTemplate.exchange(
                "/mower",
                HttpMethod.POST,
                new HttpEntity<Object>(mowerDto, headersMower),
                MowerDto.class);
        assertEquals(HttpStatus.CREATED, resultMowerCreation.getStatusCode());
        assertEquals(mowerDto, resultMowerCreation.getBody());

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

    @Test
    void givenApp_whenCreatingPlateau_andCreatingMower_thenMoveMower() {
        PlateauDto plateauDto = new PlateauDto(10, 20);
        HttpHeaders headersPlateau = new HttpHeaders();
        headersPlateau.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<PlateauDto> resultPlateauCreation = restTemplate.exchange(
                "/plateau",
                HttpMethod.POST,
                new HttpEntity<Object>(plateauDto, headersPlateau),
                PlateauDto.class);
        assertEquals(HttpStatus.CREATED, resultPlateauCreation.getStatusCode());
        String getPlateauPath = resultPlateauCreation.getHeaders().getLocation().getPath();
        String plateauId = getPlateauPath.substring(getPlateauPath.lastIndexOf("/") + 1);

        MowerDto mowerDto = new MowerDto(plateauId, new Point(5, 6), Orientation.N);
        HttpHeaders headersMower = new HttpHeaders();
        headersMower.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<MowerDto> resultMowerCreation = restTemplate.exchange(
                "/mower",
                HttpMethod.POST,
                new HttpEntity<Object>(mowerDto, headersMower),
                MowerDto.class);
        assertEquals(HttpStatus.CREATED, resultMowerCreation.getStatusCode());
        String getMowerPath = resultMowerCreation.getHeaders().getLocation().getPath();

        ResponseEntity<MowerDto> resultMowerMove = restTemplate.exchange(
                getMowerPath,
                HttpMethod.PUT,
                new HttpEntity<Object>("LMLRMLM", new HttpHeaders()),
                MowerDto.class);
        assertEquals(HttpStatus.OK, resultMowerMove.getStatusCode());
        assertEquals(Orientation.S, resultMowerMove.getBody().getOrientation());
        assertEquals(3, resultMowerMove.getBody().getPosition().getX());
        assertEquals(5, resultMowerMove.getBody().getPosition().getY());
    }


    @Test
    void inputTestCase1() {
        // Plateau in "5 5"
        PlateauDto plateauDto = new PlateauDto(5, 5);
        HttpHeaders headersPlateau = new HttpHeaders();
        headersPlateau.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<PlateauDto> resultPlateauCreation = restTemplate.exchange(
                "/plateau",
                HttpMethod.POST,
                new HttpEntity<Object>(plateauDto, headersPlateau),
                PlateauDto.class);
        assertEquals(HttpStatus.CREATED, resultPlateauCreation.getStatusCode());
        String getPlateauPath = resultPlateauCreation.getHeaders().getLocation().getPath();
        String plateauId = getPlateauPath.substring(getPlateauPath.lastIndexOf("/") + 1);

        // Create first Mower in "1 2 N"
        MowerDto mowerDto1 = new MowerDto(plateauId, new Point(1, 2), Orientation.N);
        HttpHeaders headersMower1 = new HttpHeaders();
        headersMower1.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<MowerDto> resultMowerCreation1 = restTemplate.exchange(
                "/mower",
                HttpMethod.POST,
                new HttpEntity<Object>(mowerDto1, headersMower1),
                MowerDto.class);
        assertEquals(HttpStatus.CREATED, resultMowerCreation1.getStatusCode());
        String getMowerPath1 = resultMowerCreation1.getHeaders().getLocation().getPath();

        // Move first mower "LMLMLMLMM"
        ResponseEntity<MowerDto> resultMowerMove1 = restTemplate.exchange(
                getMowerPath1,
                HttpMethod.PUT,
                new HttpEntity<Object>("LMLMLMLMM", new HttpHeaders()),
                MowerDto.class);
        assertEquals(HttpStatus.OK, resultMowerMove1.getStatusCode());
        assertEquals(Orientation.N, resultMowerMove1.getBody().getOrientation());
        assertEquals(1, resultMowerMove1.getBody().getPosition().getX());
        assertEquals(3, resultMowerMove1.getBody().getPosition().getY());

        // Create first Mower in "3 3 E"
        MowerDto mowerDto2 = new MowerDto(plateauId, new Point(3, 3), Orientation.E);
        HttpHeaders headersMower2 = new HttpHeaders();
        headersMower2.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<MowerDto> resultMowerCreation2 = restTemplate.exchange(
                "/mower",
                HttpMethod.POST,
                new HttpEntity<Object>(mowerDto2, headersMower2),
                MowerDto.class);
        assertEquals(HttpStatus.CREATED, resultMowerCreation2.getStatusCode());
        String getMowerPath2 = resultMowerCreation2.getHeaders().getLocation().getPath();

        // Move first mower "MMRMMRMRRM"
        ResponseEntity<MowerDto> resultMowerMove2 = restTemplate.exchange(
                getMowerPath2,
                HttpMethod.PUT,
                new HttpEntity<Object>("MMRMMRMRRM", new HttpHeaders()),
                MowerDto.class);
        assertEquals(HttpStatus.OK, resultMowerMove2.getStatusCode());
        assertEquals(Orientation.E, resultMowerMove2.getBody().getOrientation());
        assertEquals(5, resultMowerMove2.getBody().getPosition().getX());
        assertEquals(1, resultMowerMove2.getBody().getPosition().getY());
    }

    @Test
    void givenApp_whenInvalidUpperRightXCoordinateInPlateauDto_andCreatePlateau_thenBadRequest() {
        PlateauDto plateauDto = new PlateauDto(-1, 1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<PlateauDto> resultCreation = restTemplate.exchange(
                "/plateau",
                HttpMethod.POST,
                new HttpEntity<Object>(plateauDto, headers),
                PlateauDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, resultCreation.getStatusCode());
    }

    @Test
    void givenApp_whenInvalidUpperRightYCoordinateInPlateauDto_andCreatePlateau_thenBadRequest() {
        PlateauDto plateauDto = new PlateauDto(1, -1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<PlateauDto> resultCreation = restTemplate.exchange(
                "/plateau",
                HttpMethod.POST,
                new HttpEntity<Object>(plateauDto, headers),
                PlateauDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, resultCreation.getStatusCode());
    }

    @Test
    void givenApp_whenBlankPlateauId_andGetPlateau_thenBadRequest() {
        ResponseEntity<Object> resultCreation = restTemplate.exchange(
                "/plateau/ ",
                HttpMethod.GET,
                null,
                Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, resultCreation.getStatusCode());
    }

    @Test
    void givenApp_whenInvalidPlateauIdInMowerDto_andCreateMower_thenBadRequest() {
        MowerDto mowerDto = new MowerDto("", new Point(3, 3), Orientation.E);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<MowerDto> resultCreation = restTemplate.exchange(
                "/mower",
                HttpMethod.POST,
                new HttpEntity<Object>(mowerDto, headers),
                MowerDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, resultCreation.getStatusCode());
    }

    @Test
    void givenApp_whenBlankMowerId_andGetMower_thenBadRequest() {
        ResponseEntity<Object> resultCreation = restTemplate.exchange(
                "/mower/ ",
                HttpMethod.GET,
                null,
                Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, resultCreation.getStatusCode());
    }

    @Test
    void givenApp_whenBlankMowerId_andMoveMower_thenBadRequest() {
        ResponseEntity<Object> resultCreation = restTemplate.exchange(
                "/mower/ ",
                HttpMethod.PUT,
                new HttpEntity<Object>("MMRMMRMRRM", new HttpHeaders()),
                Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, resultCreation.getStatusCode());
    }

    @Test
    void givenApp_whenInvalidCommands_andMoveMower_thenBadRequest() {
        ResponseEntity<Object> resultCreation = restTemplate.exchange(
                "/mower/ID",
                HttpMethod.PUT,
                new HttpEntity<Object>("badCommands", new HttpHeaders()),
                Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, resultCreation.getStatusCode());
    }

}
