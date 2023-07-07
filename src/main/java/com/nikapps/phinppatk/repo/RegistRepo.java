package com.nikapps.phinppatk.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nikapps.phinppatk.entity.RegistEntity;


@Repository
public interface RegistRepo extends JpaRepository<RegistEntity, Long> {
	
}
