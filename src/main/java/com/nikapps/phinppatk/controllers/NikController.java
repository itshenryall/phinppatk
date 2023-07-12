package com.nikapps.phinppatk.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikapps.phinppatk.entity.NikEntity;
import com.nikapps.phinppatk.entity.PpatkResultEntity;
import com.nikapps.phinppatk.repo.NikRepo;
import com.nikapps.phinppatk.repo.PpatkResultRepo;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Api Documentation for Phinppatk API", description = "management APIs")
@RestController
@RequestMapping("/api")
public class NikController {

	@Autowired
	NikRepo nikRepo;
	
	@Autowired
	PpatkResultRepo ppatkResultRepo;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/nik")
	public JSONObject getNikByCreatedDate(@RequestParam Date startDate, @RequestParam Date endDate,Model model, 
		   PpatkResultEntity ppatkResultEntity) throws JsonMappingException, JsonProcessingException {
		
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();		
		List<NikEntity> ne = new ArrayList<NikEntity>();
		nikRepo.findBydateCreatedBetween(startDate, endDate).forEach(ne::add);
		
		
		for (int j = 0; j < ne.size(); j++) {
			
			//generate token
		    String uri = "http://192.168.153.2/api/auth"; 
		    String notEncoded ="phintraco:p7i5t7a5";
		    String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
		    System.out.println("encodeUserPass "+encodedAuth);
			    RestTemplate setAuth = new RestTemplate();
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_JSON);
			    headers.set("client_id", "phintraco");
			    headers.set("Authorization", "Basic "+encodedAuth);
	  
				HttpEntity<String> result = new HttpEntity<String>(headers);
				String token = setAuth.postForObject(uri, result, String.class);
				
			
			Object objv=JSONValue.parse(token); 
			System.out.println(objv);
			JSONObject jsonObject = (JSONObject) objv; 
			String access_token = (String) jsonObject.get("access_token"); 

			
			//dapat token
			System.out.println("dapat tokenya? "+access_token);
			JSONObject obj11 = new JSONObject();
			obj11.put("nik", ne.get(j).getIdentificationNumber());
			obj11.put("version", ne.get(j).getVersion());
			obj11.put("status", ne.get(j).getRegistrationStatus());
			obj11.put("registration_id", ne.get(j).getRegistrationId());
			
			String dataNik = ne.get(j).getIdentificationNumber();
			String idRegis = ne.get(j).getRegistrationId();
			System.out.println("loop data nik "+dataNik);
			
			String uriPpatk = "http://192.168.153.3/api/v1/data/nik/"+dataNik;
						
			RestTemplate restTemplatex = new RestTemplate();
		    HttpHeaders headersx = new HttpHeaders();
		    
		    headersx.setContentType(MediaType.APPLICATION_JSON);
			/* headersx.set("Authorization", "Bearer "+access_token); */
		    headersx.setBearerAuth(access_token);
		    System.out.println("initokennya "+access_token);
			HttpEntity<String> resultx = new HttpEntity<String>(headersx);
			System.out.println("sama? "+resultx);
			final String uuid = UUID.randomUUID().toString().replace("-", "");
		    System.out.println("uuid = " + uuid);
			
			try{
			    PpatkResultEntity regisppatk = ppatkResultRepo.findByRegistrationId(idRegis);	    
			    //kalau data kosong di table ppatk resurt maka insert
			    if (regisppatk == null) {
			    	ResponseEntity<String> exchange = restTemplatex.exchange(uriPpatk, HttpMethod.GET, resultx, String.class);
					ObjectMapper mapper = new ObjectMapper();
					   boolean statusData = true;
					   JsonNode jsonvalue = mapper.readTree(exchange.getBody());
					   System.out.println("jsonvalue"+jsonvalue);
					   PpatkResultEntity r = ppatkResultRepo.save(new PpatkResultEntity(uuid.toString(), ne.get(j).getVersion() ,jsonvalue.toString(), ne.get(j).getRegistrationId(), statusData));
					   new ResponseEntity<>(r, HttpStatus.CREATED);			  
					   System.out.println("status "+r);
			    	System.out.println("ini keinsert ke table dengan response found ");
				}else {
					new ResponseEntity<>(regisppatk, HttpStatus.FOUND);
					System.out.println("ini ada datanya jadi ga keinsert (response found) "+regisppatk.getRegistrationId());
				}
			    //if data nik response: not found & null
			} catch(HttpStatusCodeException e){
				PpatkResultEntity regisppatk = ppatkResultRepo.findByRegistrationId(idRegis);	    
				if (regisppatk == null) {
					boolean statusData = false;
				    String response = e.getResponseBodyAsString();
				    PpatkResultEntity r = ppatkResultRepo.save(new PpatkResultEntity(uuid.toString(), ne.get(j).getVersion() ,response.toString(), ne.get(j).getRegistrationId(), statusData));
					new ResponseEntity<>(r, HttpStatus.CREATED);
					System.out.println("status "+r);
					System.out.println("ini keinsert ke table dengan response not found ");
					
				}else {
					new ResponseEntity<>(regisppatk, HttpStatus.FOUND);
					System.out.println("ini ada datanya jadi ga ke insert (response not found) "+regisppatk.getRegistrationId());
				}
			}
			list.add(obj11);
			System.out.println("list "+list.size());
		}
		obj.put("Success",true);
		obj.put("data", list);	
		return obj;
	}
	
}
