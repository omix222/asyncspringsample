package com.example.asyncsample;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
@Async
@Service
public class AsyncServiceA {
	public String asyncService(DeferredResult<String> deferredResult) {
		String result = "";
		result = LocalDateTime.now().toString();
		System.out.println("AsyncServiceA 1: "+result);
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("AsyncServiceA 2: "+ LocalDateTime.now());
		deferredResult.setResult(result+"A"); 
		return result;
	}
}
