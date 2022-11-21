package com.example.mowers.adapter;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.port.MowerController;
import com.example.mowers.port.MowerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class MowerControllerImpl implements MowerController {

    final private MowerService mowerService;
    final private ModelMapper modelMapper;

    @Override
    public ResponseEntity<String> createMower(MowerDto mowerRequest) {
        Mower mower = modelMapper.map(mowerRequest, Mower.class);
        Optional<Mower> mowerResult = mowerService.createMower(mower);

        if (mowerResult.isPresent()) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mowerResult.get().getId()).toUri();
            log.info("Created Mower with ID {} and URL {} ", mowerResult.get().getId(), location);
            return ResponseEntity.created(location).build();
        } else {
            log.warn("The mower {} could not be created ", mowerRequest);
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<MowerDto> getMower(String id) {
        Optional<Mower> mower = mowerService.getMower(id);
        if (mower.isPresent()) {
            MowerDto mowerResult = modelMapper.map(mower.get(), MowerDto.class);
            log.info("Get Mower {} ", mowerResult);
            return new ResponseEntity<>(mowerResult, HttpStatus.OK);
        } else {
            log.info("Mower with ID {} not found ", id);
            return ResponseEntity.notFound().build();
        }
    }
}
