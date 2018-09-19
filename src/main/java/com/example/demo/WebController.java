package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author michael
 */
@RestController("/api/")
public class WebController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;
    
    @PostMapping("/api/produce/{message}")
    public void produce(@PathVariable String message) {
	rabbitTemplate.convertAndSend(new MichaelsMessage(message));
    }
    
    @PostMapping("/api/produce/random/{count}")
    public void setRandom(@PathVariable int count) {
	for (int i=0 ; i < count; i++) {
	    UUID uuid = UUID.randomUUID();
	    if( uuid.hashCode() % 12 == 0) { // because twelve is that largest one syllable number
		rabbitTemplate.convertAndSend(new MichaelsMessage("MichaelIsMetal"));
	    } else {
		rabbitTemplate.convertAndSend(new MichaelsMessage(uuid.toString()));
	    }
	}
    }
    
    @PostMapping("/api/consume")
    public String consume() {
        //this cast feel particularly shameful, but i couldn't find a good example this seemingly simple method
	//and the commented line complained about a smartconverter, but wouldn't let me use :
	//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/messaging/converter/StringMessageConverter.html	
	//MichaelsMessage ret = rabbitTemplate.receiveAndConvert(new ParameterizedTypeReference<MichaelsMessage>() {});
        MichaelsMessage ret = (MichaelsMessage) rabbitTemplate.receiveAndConvert();
        if(null == ret) {
            return null;
        }
        return ret.toString();
    }
    
    @PostMapping("/api/consume/*")
    public List<String> consumeAll() {
	List<String> ret = new ArrayList<>();
	String buf = consume();
	while( null != buf) {
	    ret.add(buf);
	    buf = consume();
	}
	return ret;
    }
    
    @GetMapping("/api/count")
    public Object count() {
        return amqpAdmin.getQueueProperties(Static.QUEUENAME).get("QUEUE_MESSAGE_COUNT");
    }
}
