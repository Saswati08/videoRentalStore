package com.casumo.videorental.repository;

import com.casumo.videorental.exception.InvalidInputException;
import com.casumo.videorental.model.Rent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RentRentVideoRepository implements IRentVideoRepository {
    private Map<UUID, Rent> rentVideoInfoMap;
    RentRentVideoRepository() {
        rentVideoInfoMap = new HashMap<>();
    }
    @Override
    public void addRent(Rent rent) {
       rentVideoInfoMap.put(rent.getRentIdentifier(), rent);
    }

    @Override
    public Rent getRent(UUID rentIdentifier) {
        if(Boolean.FALSE.equals(rentVideoInfoMap.containsKey(rentIdentifier))) {
            throw new InvalidInputException("Given rent identifier does not exist in system");
        }
        return rentVideoInfoMap.get(rentIdentifier);
    }

    @Override
    public Map<UUID, Rent> getRentInfoList() {
        return rentVideoInfoMap;
    }
}
