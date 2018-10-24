package com.matrangola.gadgets.data.repository;

import com.matrangola.gadgets.data.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
