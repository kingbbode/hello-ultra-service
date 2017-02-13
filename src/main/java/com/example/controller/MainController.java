package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Message;
import com.example.service.Answer;
import com.example.service.JsonObject;

@RestController
public class MainController {
	
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	
	@GetMapping("/keyboard")
	public Map<String, Object> keyboard(){
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		
		jsonObject = new HashMap<String, Object>();
		jsonObject.put("type","text");
		return jsonObject;
	}
	
	@PostMapping("/message")
	public Object message( @RequestBody Message message) throws ParseException{
	
		System.out.println("메세지받음");
	
		
		String userKey = message.getUserkey();
		String type = message.getType();
		String content = message.getContent();
		
		log.debug("userKey : {}",userKey);
		log.debug("type : {}",type);		
		log.debug("content : {}",content);
		
		Answer answer = new Answer();
		String makedMessage = answer.makeMessage(message);
		
		JsonObject jsonObject = new JsonObject();
		
		//버튼응답인지 텍스트응답인지 처리
		if(answer.getIsButton()){
			return jsonObject.buttonObject(makedMessage, message);
		}
		
		return jsonObject.textObject(makedMessage, message);
	}
}