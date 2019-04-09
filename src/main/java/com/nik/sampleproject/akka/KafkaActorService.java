package com.nik.sampleproject.akka;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaActorService {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	String kafkaTopic = "sample-project";
	
	public String kafkaSendMessage(String msg) {
    	String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	String msgWithTimestamp = "[" + timestamp + "] " + msg;
    	
        ListenableFuture<SendResult<String, String>> future =
        		kafkaTemplate.send(kafkaTopic, msgWithTimestamp);
         
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message: {" + msgWithTimestamp + 
                  "}; Offset: [" + result.getRecordMetadata().offset() + "]");
            }
            
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message: {"
                  + msgWithTimestamp + "}; Due to: " + ex.getMessage());
            }
        });
        
        // TODO: Find a better use for this...
        return "Sent Kafka message via Akka...";
    }
}