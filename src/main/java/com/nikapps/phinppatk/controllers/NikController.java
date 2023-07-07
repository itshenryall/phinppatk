package com.nikapps.phinppatk.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikapps.phinppatk.entity.NikEntity;
import com.nikapps.phinppatk.entity.PpatkResultEntity;
import com.nikapps.phinppatk.repo.NikRepo;
import com.nikapps.phinppatk.repo.PpatkResultRepo;
import com.nikapps.phinppatk.repo.RegistRepo;



@RestController
@RequestMapping("/api")
public class NikController {

	@Autowired
	NikRepo nikRepo;
	
	@Autowired
	PpatkResultRepo ppatkResultRepo;
	
	@Autowired
	RegistRepo registRepo;
	

	@SuppressWarnings("unchecked")
	@GetMapping("/nik")
	public JSONObject getNikByCreatedDate(@RequestParam Date startDate, @RequestParam Date endDate,
			String identificationNumber, PpatkResultEntity ppatkResultEntity) throws JsonMappingException, JsonProcessingException {
		
		JSONArray list = new JSONArray();
		JSONObject obj = new JSONObject();		
		List<NikEntity> ne = new ArrayList<NikEntity>();
		nikRepo.findBydateCreatedBetween(startDate, endDate).forEach(ne::add);
			
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
			JSONObject jsonObject = (JSONObject) objv; 
			String access_token = (String) jsonObject.get("access_token"); 
			//dapat token
			System.out.println("dapat tokenya? "+access_token);
	
		for (int j = 0; j < ne.size(); j++) {
			JSONObject obj11 = new JSONObject();
			obj11.put("nik", ne.get(j).getIdentificationNumber());
			obj11.put("version", ne.get(j).getVersion());
			obj11.put("status", ne.get(j).getRegistrationStatus());
			obj11.put("registration_id", ne.get(j).getRegistrationId());
			
			@SuppressWarnings("unused")
			String dataNik = ne.get(j).getIdentificationNumber();
			System.out.println("loop data nik "+dataNik);
			
			String uriPpatk = "http://192.168.153.3/api/v1/data/nik/3276031002940005";
						
			RestTemplate restTemplatex = new RestTemplate();
		    HttpHeaders headersx = new HttpHeaders();
		    
		    headersx.setContentType(MediaType.APPLICATION_JSON);
			/* headersx.set("Authorization", "Bearer "+access_token); */
		    headersx.setBearerAuth(access_token);
		    System.out.println("initokennya "+access_token);
		    
			HttpEntity<String> resultx = new HttpEntity<String>(headersx);
			System.out.println("sama? "+resultx);
		    
			try{
				 ResponseEntity<String> exchange = restTemplatex.exchange(uriPpatk, HttpMethod.GET, resultx, String.class);
				 ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonvalue = mapper.readTree(exchange.getBody());
					System.out.println("jsonvalue"+jsonvalue);
					PpatkResultEntity r = ppatkResultRepo.save(new PpatkResultEntity(ne.get(j).getVersion() ,jsonvalue.toString(), ne.get(j).getRegistrationId(), ne.get(j).getRegistrationStatus(), ne.get(j).getIdentificationNumber()));
					new ResponseEntity<>(r, HttpStatus.CREATED);			
					System.out.println("status "+r);
			} catch(HttpStatusCodeException e){
			     String otherResponse200 = e.getResponseBodyAsString();
			     PpatkResultEntity r = ppatkResultRepo.save(new PpatkResultEntity(ne.get(j).getVersion() ,otherResponse200.toString(), ne.get(j).getRegistrationId(), ne.get(j).getRegistrationStatus(), ne.get(j).getIdentificationNumber()));
				 new ResponseEntity<>(r, HttpStatus.CREATED);
				 System.out.println("status "+r);
			}

			list.add(obj11);
			System.out.println("list "+list);
		}
		obj.put("data", list);
		obj.put("success", true);
		return obj;
	}
	
	
	@GetMapping("/getNik")
	public String pushToken() {
		//generate token
	    String uri = "http://192.168.153.2/api/auth"; 
	    String notEncoded ="phintraco:p7i5t7a5";
	    String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
	    System.out.println("encodeUserPass "+encodedAuth);
		    RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("client_id", "phintraco");
		    headers.set("Authorization", "Basic "+encodedAuth);

		  
			HttpEntity<String> result = new HttpEntity<String>(headers);
			String token = restTemplate.postForObject(uri, result, String.class);
			
			Object obj=JSONValue.parse(token); 
			JSONObject jsonObject = (JSONObject) obj; 
			String access_token = (String) jsonObject.get("access_token"); 
			//dapat token
			System.out.println("baru di get "+access_token);
			
			
		//hit get nik
        String urix = "http://192.168.153.3/api/v1/data/nik/1050176605823007"; 
	    RestTemplate restTemplatex = new RestTemplate();
	    HttpHeaders headersx = new HttpHeaders();
	    
	    headersx.setContentType(MediaType.APPLICATION_JSON);
		/* headersx.set("Authorization", "Bearer "+access_token); */
	    headersx.setBearerAuth(access_token);
	    System.out.println("initokennya "+access_token);
	    
		HttpEntity<String> resultx = new HttpEntity<String>(headersx);
		System.out.println("sama?"+resultx);
		
        ResponseEntity<String> exchange = restTemplate.exchange(urix, HttpMethod.GET, resultx, String.class);
        
        String content = exchange.getBody();
        System.out.println("yeayyyy"+content);
		//fail
		// String get = restTemplatex.getForObject(urix ,String.class, resultx); 
		//ResponseEntity<Response> response = restTemplatex.exchange(urix, HttpMethod.GET, resultx, Response.class);
		
	    return content;
	}

	@GetMapping("/nik/createdat")
	public ResponseEntity<List<NikEntity>> getNikByCreatedDatex(@RequestParam Date startDate,
			@RequestParam Date endDate) {
		return new ResponseEntity<List<NikEntity>>(nikRepo.findBydateCreatedBetween(startDate, endDate), HttpStatus.OK);
	}
	
	@GetMapping("/getData")
	public ResponseEntity<List<NikEntity>> getData() {
		return new ResponseEntity<List<NikEntity>>(nikRepo.getData(), HttpStatus.OK);
	}
	
}
