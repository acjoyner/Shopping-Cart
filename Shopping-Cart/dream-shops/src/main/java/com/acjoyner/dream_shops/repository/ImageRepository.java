package com.acjoyner.dream_shops.repository;

import com.acjoyner.dream_shops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
