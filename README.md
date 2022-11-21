# mowers

## Goal

- We need to develop an application that helps in controlling brand-new mowers.
    - The mowers are able to cut the grass and to inspect the terrain with their cameras to identify problems in the
      green areas.
- A green grass plateau must be navigated by the mowers, so they can cut the grass

## Details

- The plateau is rectangular and is divided up into a grid to simplify navigation.
- A mower’s position and location are represented by:
    - Combination of X and Y coordinates
    - A letter representing one of the four cardinal compass points (N, E, S, W).
- Control a mower by sending a string of letters.
    - The possible letters are “L”, “R” and ”M”.
        - “L” and “R” make the mower spin 90 degrees left or right respectively, without moving from its current spot.
        - “M” means to move forward one grid point and maintain the same Heading.

## API

### Plateau

- POST `/plateau`
    - Header: `Content-Type: application/json`
    - Body:
      ```json
      { 
          "X" :10,
          "Y" :22,
          "orientation": "N"
      }
       ```
    - Response
        - Status: 201 CREATED
            - Location: `http://localhost:8080/plateau/b9406067-4d3e-4210-93d1-5db05e9b939d`
    - Example
      ```
        curl --location --request POST 'http://localhost:8080/plateau' \
            --header 'Content-Type: application/json' \
            --data-raw '{
                "X": 10,
                "Y": 22,
                "orientation": "N"
            }'
      ```

- GET `/plateau/{id}`
    - Response
        - Status: 200 Ok
            - Body:
                ```json
                  { 
                      "X" :10,
                      "Y" :22,
                      "orientation": "N"
                  }
                ```
        - Status: 404 Not Found
    - Example
      ```
        curl --location --request GET 'http://localhost:8080/plateau/b9406067-4d3e-4210-93d1-5db05e9b939d'
      ```

## Build, test and running

- Build and test

```
mvn clean install
```

- Run

```
mvn spring-boot:run
```

## Assumptions

## TODO

- [ ] Testing
- [ ] Documentation
- [ ] Static analysis code
- [ ] Create a docker image
- [ ] CI (GitActions)
    - [ ] Build and testing
    - [ ] Run static analysis
    - [ ] Push docker image