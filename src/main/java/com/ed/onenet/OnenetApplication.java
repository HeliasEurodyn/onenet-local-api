package com.ed.onenet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.server.ResponseStatusException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;


@SpringBootApplication
public class OnenetApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnenetApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(){
		return  new RestTemplate();
	}

//	@Bean
//	public RestTemplate restTemplate() throws Exception {
//		SSLContext sslContext = SSLContext.getInstance("TLS");
//		sslContext.init(null, new TrustManager[]{new X509TrustManager() {
//			@Override
//			public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
//			}
//
//			@Override
//			public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
//			}
//
//			@Override
//			public X509Certificate[] getAcceptedIssuers() {
//				return new X509Certificate[0];
//			}
//		}}, new SecureRandom());
//
//		// Set the default SSL context to bypass SSL verification
//		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//		HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
//
//		return new RestTemplate();
//	}



}
