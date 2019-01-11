package com.lfchaim.springmongocustomerclient.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.lfchaim.springmongocustomerclient.util.CustomerUtil;

import reactor.core.publisher.Mono;

@Service
public class CustomerService {

	private static final String LOG_FILE = "log_customer.txt";
	
	private WebClient webClient;

	public CustomerService(WebClient.Builder webClientBuilder) {
		this.webClient = WebClient.builder().baseUrl("http://localhost:8080").defaultCookie("cookieKey", "cookieValue")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080")).build();
	}

	public void generateCustomer(long quantity) {
		CustomerUtil util = new CustomerUtil();
		Calendar c1 = Calendar.getInstance();
		ExecutorService executor = Executors.newFixedThreadPool(1000);
		for( long i = 0; i < quantity; i++ ) {
			String name = util.getFullName();
			Map<String, Object> map = new LinkedHashMap<String,Object>();
			map.put("name", name);
			map.put("email", util.getEmail(name)+"@com.com");
			Map<String, String> mapAddress = new LinkedHashMap<String,String>();
			mapAddress.put("address","Avenida Paulista");
			mapAddress.put("number","2300");
			mapAddress.put("complement","1 andar");
			mapAddress.put("zipCode","01311-000");
			List<Map> listAddr = new ArrayList<>();
			listAddr.add(mapAddress);
			map.put("address",listAddr);
			String json = util.jsonFromMap(map);
			executor.submit(() -> {
		        try {
		        	//Mono<ClientResponse> resp = WebClient.create().post().uri("http://localhost:8080/customer").accept(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(map)).exchange();
		        	Mono<ClientResponse> resp = WebClient.create().post().uri("http://localhost:8080/customer").accept(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(map)).exchange();
		        	
		        	System.out.println(resp.block().toString());
		        	System.out.println("Registro enviado para: "+json);
		        } catch( Exception e ) {
		        	e.printStackTrace();
		        	Thread.currentThread().interrupt();
		        }
	        });
		}
		Calendar c2 = Calendar.getInstance();
		util.writeLog(LOG_FILE, "Tempo total: "+(c2.getTimeInMillis()-c1.getTimeInMillis())+" ms - Registos: "+quantity);
	}
}
