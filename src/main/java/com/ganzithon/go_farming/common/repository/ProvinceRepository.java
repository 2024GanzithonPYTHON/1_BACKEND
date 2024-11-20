package com.ganzithon.go_farming.common.repository;

import com.ganzithon.go_farming.common.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

    List<Province> findAll();
    Optional<Province> findByName(String name);

}
