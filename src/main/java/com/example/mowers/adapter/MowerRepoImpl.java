package com.example.mowers.adapter;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.core.dto.MowerDto;
import com.example.mowers.port.MowerRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MowerRepoImpl implements MowerRepo {

    private Map<String, Mower> mowerMap = new HashMap<>();

    @Override
    public Mower createMower(MowerDto mowerRequest) {
        Mower newMower = new Mower(mowerRequest);

        mowerMap.put(newMower.getId(), newMower);
        return newMower;
    }

    @Override
    public Optional<Mower> getMower(String mowerId) {
        return Optional.ofNullable(mowerMap.get(mowerId));
    }
}
