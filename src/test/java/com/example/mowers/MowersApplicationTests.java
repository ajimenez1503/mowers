package com.example.mowers;

import com.example.mowers.core.domain.Orientation;
import com.example.mowers.core.dto.PlateauDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

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
                request,
                PlateauDto.class);
        assertEquals(HttpStatus.OK, resultGet.getStatusCode());
        assertEquals(plateauDto, resultGet.getBody());
    }
}
