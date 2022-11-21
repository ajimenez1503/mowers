package com.example.mowers.adapter;

import com.example.mowers.core.domain.Mower;
import com.example.mowers.port.MowerRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MowerRepoImpl implements MowerRepo {

    private Map<String, Mower> mowerMap = new HashMap<String, Mower>();

    @Override
    public Mower createMower(Mower mower) {
        Mower mowerToSave = mower.toBuilder().build();
        mowerToSave.setId(UUID.randomUUID().toString());

        mowerMap.put(mowerToSave.getId(), mowerToSave);
        return mowerToSave;
    }

    @Override
    public Optional<Mower> getMower(String id) {
        return Optional.ofNullable(mowerMap.get(id));
    }
}
