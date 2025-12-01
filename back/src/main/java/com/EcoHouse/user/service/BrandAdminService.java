package com.EcoHouse.user.service;

import com.EcoHouse.user.model.BrandAdmin;
import com.EcoHouse.user.model.User;

public interface BrandAdminService {

    BrandAdmin createBrandAdmin(User user);
    BrandAdmin findByUserId(Long userId);


}
