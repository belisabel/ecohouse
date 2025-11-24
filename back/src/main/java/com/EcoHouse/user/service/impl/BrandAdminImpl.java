package com.EcoHouse.user.service.impl;

import com.EcoHouse.user.model.BrandAdmin;
import com.EcoHouse.user.model.User;
import com.EcoHouse.user.repository.BrandAdminRepository;
import com.EcoHouse.user.service.BrandAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BrandAdminImpl implements BrandAdminService {


    private final BrandAdminRepository brandAdminRepository;


    @Override
    public BrandAdmin createBrandAdmin(User user) {
        BrandAdmin admin = new BrandAdmin();
        admin.setUser(user);
        return brandAdminRepository.save(admin);
    }

    @Override
    public BrandAdmin findByUserId(Long userId) {
        return brandAdminRepository.findByUser_Id(userId).orElse(null);    }
}
