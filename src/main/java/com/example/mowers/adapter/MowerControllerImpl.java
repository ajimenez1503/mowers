package com.example.mowers.adapter;

import com.example.mowers.core.domain.Command;
import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.port.MowerController;
import com.example.mowers.port.MowerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class MowerControllerImpl implements MowerController {

    private final MowerService mowerService;

    @Override
    public ResponseEntity<MowerDto> createMower(MowerDto mowerRequest) {
        Optional<Mower> mowerResult = mowerService.createMower(mowerRequest);

        if (mowerResult.isPresent()) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mowerResult.get().getId()).toUri();
            log.info("Created Mower with ID {} and URL {} ", mowerResult.get().getId(), location);
            return ResponseEntity.created(location).body(mowerResult.get().getDto());
        } else {
            log.warn("The mower {} is not valid ", mowerRequest);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Override
    public ResponseEntity<MowerDto> getMower(String mowerId) {
        Optional<Mower> mower = mowerService.getMower(mowerId);
        if (mower.isPresent()) {
            log.info("Get Mower {} ", mower);
            return new ResponseEntity<>(mower.get().getDto(), HttpStatus.OK);
        } else {
            log.info("Mower with ID {} not found ", mowerId);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<MowerDto> moveMower(String mowerId, String commands) {
        Optional<Mower> mower = mowerService.getMower(mowerId);
        if (mower.isPresent()) {
            List<Command> commandList = commands.codePoints().mapToObj(c -> Command.valueOf(String.valueOf((char) c))).toList();
            Optional<Mower> mowerResult = mowerService.moveMower(mower.get(), commandList);
            if (mowerResult.isPresent()) {
                log.info("Mower {} has been moved", mowerResult.get());
                return new ResponseEntity<>(mowerResult.get().getDto(), HttpStatus.OK);
            } else {
                log.info("Mower with ID {} could not be moved", mowerId);
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            log.info("Mower with ID {} not found ", mowerId);
            return ResponseEntity.notFound().build();
        }
    }
}
