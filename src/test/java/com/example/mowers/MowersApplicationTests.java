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
        assertNotNull(restTemplate);
    }

    @Test
    void main() {
        MowersApplication.main(new String[]{});
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

    @Test
    public void givenApp_whenCreatingPlateau_andCreatingMower_thenMoveMower() {
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

        MowerDto mowerDto = new MowerDto(plateauId, new Point(5, 6), Orientation.N);
        HttpHeaders headersMower = new HttpHeaders();
        headersMower.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> resultMowerCreation = restTemplate.exchange(
                "/mower",
                HttpMethod.POST,
                new HttpEntity<Object>(mowerDto, headersMower),
                String.class);
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
    public void inputTestCase1() {
        // Plateau in "5 5"
        PlateauDto plateauDto = new PlateauDto(5, 5);
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

        // Create first Mower in "1 2 N"
        MowerDto mowerDto1 = new MowerDto(plateauId, new Point(1, 2), Orientation.N);
        HttpHeaders headersMower1 = new HttpHeaders();
        headersMower1.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> resultMowerCreation1 = restTemplate.exchange(
                "/mower",
                HttpMethod.POST,
                new HttpEntity<Object>(mowerDto1, headersMower1),
                String.class);
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
        ResponseEntity<String> resultMowerCreation2 = restTemplate.exchange(
                "/mower",
                HttpMethod.POST,
                new HttpEntity<Object>(mowerDto2, headersMower2),
                String.class);
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

}
