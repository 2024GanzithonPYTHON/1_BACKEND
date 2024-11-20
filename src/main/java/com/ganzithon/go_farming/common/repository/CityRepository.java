package com.ganzithon.go_farming.common.repository;

import com.ganzithon.go_farming.common.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByProvinceId(Long provinceId);
    Optional<City> findByName(String name);

}
