package com.nikapps.phinppatk.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nikapps.phinppatk.entity.NikEntity;
@Repository
public interface NikRepo extends JpaRepository<NikEntity, Long> {
	
	List<NikEntity> findByIdentificationNumber(String identificationNumber);
	
	@Query(value="select b.id, a.id as registration_id, a.version, a.registration_status, b.identification_number, b.date_created from profits_backoffice.registration a \r\n"
			+ "inner join profits_backoffice.registration_personal_information b\r\n"
			+ "ON b.id = a.personal_information_id\r\n"
			+ "WHERE a.registration_status IN ('VERIFIED', \r\n"
			+ "'APPROVED',\r\n"
			+ "'PENDING KSEI',\r\n"
			+ "'FAILED KSEI',\r\n"
			+ "'COMPLETE SID',\r\n"
			+ "'PROCESS RDN',\r\n"
			+ "'PENDING RDN',\r\n"
			+ "'FAILED RDN', \r\n"
			+ "'PENDING FUND', \r\n"
			+ "'FORCED COMPLETE',\r\n"
			+ "'MANUAL COMPLETE',\r\n"
			+ "'COMPLETE') and (b.date_created BETWEEN :startDate AND :endDate) limit 3;", nativeQuery = true)
	List<NikEntity> findBydateCreatedBetween(Date startDate, Date endDate);


}
