package com.nikapps.phinppatk.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.nikapps.phinppatk.entity.PpatkResultEntity;

@Repository
public interface PpatkResultRepo extends CrudRepository<PpatkResultEntity, Long> {

	@Query(value="select * from profits_backoffice.ppatk_result where registration_id = :registrationId", nativeQuery = true)
	PpatkResultEntity findByRegistrationId(String registrationId);
}
