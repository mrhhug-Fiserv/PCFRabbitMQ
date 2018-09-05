package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author michael
 */
@RestController("/api/")
public class WebController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    tye myType = new ParameterizedTypeReference<String>();
    
    @PutMapping("/api/produce/{message}")
    public void produce(@PathVariable String message) {
	rabbitTemplate.convertAndSend(message);
    }
    
    @PutMapping("/api/produce/random/{count}")
    public void setRandom(@PathVariable int count) {
	for (int i=0 ; i < count; i++) {
	    UUID uuid = UUID.randomUUID();
	    if( uuid.hashCode() % 12 == 0) { // because twelve is that largest one syllable number
		rabbitTemplate.convertAndSend("MichaelIsMetal");
	    } else {
		rabbitTemplate.convertAndSend(uuid.toString());
	    }
	}
    }
    
    @GetMapping("/api/consume")
    public String consume() {
	return rabbitTemplate.receiveAndConvert(ParameterizedTypeReference.forType(String.class));
    }
    
    @GetMapping("/api/consume/*")
    public List<String> consumeAll() {
	List<String> ret = new ArrayList<>();
	String buf = consume();
	while( null != buf) {
	    ret.add(buf);
	    buf = consume();
	}
	return ret;
    }
}