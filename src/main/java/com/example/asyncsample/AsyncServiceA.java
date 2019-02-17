package com.example.asyncsample;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
@Async
@Service
public class AsyncServiceA {
	private static final Logger logger = LoggerFactory.getLogger(AsyncServiceA.class);
	public String asyncService(DeferredResult<String> deferredResult) {
		logger.info("start AsyncServiceA.asyncService deferredResult");
		String result = "";
		result = LocalDateTime.now().toString();
		logger.info("AsyncServiceA 1: "+result);
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AsyncServiceA 2: "+ LocalDateTime.now());
		deferredResult.setResult(result+"A"); 
		logger.info("end AsyncServiceA.asyncService deferredResult");
		return result;
	}
	
	public  CompletableFuture<String> asyncService() {
		logger.info("start AsyncServiceA.asyncService CompletableFuture");
		String result = "";
		result = LocalDateTime.now().toString();
		logger.info("AsyncServiceA 1: "+result);
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AsyncServiceA 2: "+ LocalDateTime.now());
		logger.info("end AsyncServiceA.asyncService CompletableFuture");
		return CompletableFuture.completedFuture(result+"A");
	}
}
