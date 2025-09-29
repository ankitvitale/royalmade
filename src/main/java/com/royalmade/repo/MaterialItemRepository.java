package com.royalmade.repo;

import com.royalmade.entity.MaterialItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialItemRepository extends JpaRepository<MaterialItem,Long> {
}
