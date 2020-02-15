package com.example.demo;


import com.example.demo.redis.MessageGateway;
import com.example.demo.redis.Status;
import com.example.demo.redis.TransactionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@RestController
class RedisEample {

	@Autowired
	MessageGateway messageGateway;

	@GetMapping()
	public List<TransactionData> hello() {
		List<TransactionData> lTdata = new ArrayList<>();

		for(int i = 0; i < 100; i++) {
			lTdata.add( new TransactionData(String.valueOf(new Random().nextInt()), Status.COMPLETED));
		}
		messageGateway.sendMessage(lTdata);
		return lTdata;
	}
}




