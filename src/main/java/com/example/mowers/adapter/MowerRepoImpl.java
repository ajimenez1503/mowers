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

    private Map<String, Mower> mowerMap = new HashMap<String, Mower>();

    @Override
    public Mower createMower(MowerDto mower) {
        Mower mowerToSave = new Mower(mower);

        mowerMap.put(mowerToSave.getId(), mowerToSave);
        return mowerToSave;
    }

    @Override
    public Optional<Mower> getMower(String id) {
        return Optional.ofNullable(mowerMap.get(id));
    }
}
