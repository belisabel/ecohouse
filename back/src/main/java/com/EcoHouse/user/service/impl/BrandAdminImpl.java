package com.EcoHouse.user.service.impl;

import com.EcoHouse.user.model.BrandAdmin;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.model.UserType;
import com.EcoHouse.user.repository.BrandAdminRepository;
import com.EcoHouse.user.service.BrandAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BrandAdminImpl implements BrandAdminService {


    private final BrandAdminRepository brandAdminRepository;







    @Override
    public Optional<BrandAdmin> findByEmail(String email) {
        return brandAdminRepository.findByEmail(email);
    }



}
