package com.casumo.videorental.service.inventory;

import com.casumo.videorental.model.Rent;
import com.casumo.videorental.repository.IRentVideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetRentInfoService {
    private final IRentVideoRepository rentVideoRepository;
    public Map<UUID, Rent> getRentInfos() {
        return rentVideoRepository.getRentInfoList();
    }

}
