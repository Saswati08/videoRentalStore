package com.casumo.videorental.repository;

import com.casumo.videorental.model.Rent;

import java.util.Map;
import java.util.UUID;

public interface IRentVideoRepository {
    void addRent(Rent rent);
    Rent getRent(UUID rentIdentifier);
    Map<UUID, Rent> getRentInfoList();
}
