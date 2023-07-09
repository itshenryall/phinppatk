package com.nikapps.phinppatk.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nikapps.phinppatk.entity.PpatkResultEntity;

@Repository
public interface PpatkResultRepo extends JpaRepository<PpatkResultEntity, Long> {

}
