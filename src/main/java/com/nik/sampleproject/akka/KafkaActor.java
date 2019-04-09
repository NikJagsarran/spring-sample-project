package com.nik.sampleproject.akka;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import akka.actor.UntypedAbstractActor;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class KafkaActor extends UntypedAbstractActor {
	@Autowired
	private KafkaActorService kafkaActorService;
	 
    public KafkaActor() {}
 
    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof String) {
            getSender().tell(kafkaActorService.kafkaSendMessage((String) message), getSelf());
        } else {
            unhandled(message);
        }
    }
}
