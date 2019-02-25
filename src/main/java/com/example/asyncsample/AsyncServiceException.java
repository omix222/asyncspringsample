package com.example.asyncsample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Async
@Service
public class AsyncServiceException {
	private static final Logger logger = LoggerFactory.getLogger(AsyncServiceException.class);
	
	public  CompletableFuture<String> asyncService() {
		logger.info("start AsyncServiceException.asyncService CompletableFuture");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		throw new RuntimeException("from async Exception!");
	}
}
