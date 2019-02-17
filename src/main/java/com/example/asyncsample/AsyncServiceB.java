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
public class AsyncServiceB {
	private static final Logger logger = LoggerFactory.getLogger(AsyncServiceB.class);
	public String asyncService(DeferredResult<String> deferredResult) {
		logger.info("start AsyncServiceB.asyncService deferredResult");
		String result = "";
		result = LocalDateTime.now().toString();
		logger.info("AsyncServiceB 1: "+result);
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result = LocalDateTime.now().toString();
		logger.info("AsyncServiceB 2: "+ result);
		deferredResult.setResult(result+"B"); 
		logger.info("end AsyncServiceB.asyncService deferredResult");
		return result;
	}
	
	public  CompletableFuture<String> asyncService() {
		logger.info("start AsyncServiceB.asyncService CompletableFuture");
		String result = "";
		result = LocalDateTime.now().toString();
		logger.info("AsyncServiceB 1: "+result);
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("AsyncServiceB 2: "+ LocalDateTime.now());
		logger.info("end AsyncServiceB.asyncService CompletableFuture");
		return CompletableFuture.completedFuture(result+"B");
	}
}
