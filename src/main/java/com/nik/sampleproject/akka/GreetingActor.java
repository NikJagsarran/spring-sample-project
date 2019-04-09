package com.nik.sampleproject.akka;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import akka.actor.UntypedAbstractActor;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GreetingActor extends UntypedAbstractActor {
	@Autowired
	private GreetingService greetingService;
	 
    public GreetingActor() {
    	
    }
 
    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Greet) {
            String name = ((Greet) message).getName();
            getSender().tell(greetingService.greet(name), getSelf());
        } else {
            unhandled(message);
        }
    }
    
    public static class Greet {
    	private String name;
    	
    	public Greet(String name) {
    		this.name = name;
    	}

    	public String getName() {
    		return name;
    	}

    	public void setName(String name) {
    		this.name = name;
    	}
    }
}
