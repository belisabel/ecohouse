package com.EcoHouse.user.service;

import com.EcoHouse.user.model.BrandAdmin;
import com.EcoHouse.user.model.User;

import java.util.Optional;

public interface BrandAdminService {


    Optional<BrandAdmin> findByEmail(String email);

}
