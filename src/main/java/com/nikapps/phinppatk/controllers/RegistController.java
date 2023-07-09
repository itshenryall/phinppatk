package com.nikapps.phinppatk.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nikapps.phinppatk.entity.RegistEntity;
import com.nikapps.phinppatk.repo.RegistRepo;

@RestController
@RequestMapping("/apix") 
public class RegistController {
	
	@Autowired
	RegistRepo registrepo;

	 @GetMapping("/regist")
	  public ResponseEntity<List<RegistEntity>> getAllTutorials() {

	      List<RegistEntity> re = registrepo.findAll();
	      if (re.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(re, HttpStatus.OK);
	 }
}

