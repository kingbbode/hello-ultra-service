package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Message;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.utility.JsonConverter;
import com.example.utility.ResponseMessage;
import com.example.utility.Answers;
import com.example.utility.DataParser;

@RestController
public class MainController {

	//Component 등록이 되어야 Autowired 먹는다.
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	@GetMapping("/keyboard")
	public Map<String, Object> keyboard() {
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("type", "text");
		return jsonObject;
	}

	@PostMapping("/message")
	public Object message(@RequestBody Message message) throws ParseException, IOException {
		
		log.debug("메세지받음");
		//버튼으로 돌려줘야 하는 서비스인지 처리
		if("#회원가입".equals(message.getContent())){
			log.debug("버튼메시지 입력받음");
			message.setButton(true);
		}
		if("프로야구순위".equals(message.getContent())){
			System.out.println("태워보자");
			DataParser.test();
		}
		
		log.debug(message.toString());
		
		
		//회원가입.. 꼭 컨트롤러에서밖에 못하냐!!!
		if (message.isNickName()) {
			User user = new User(message);
			//user.toSave(message.getUser_key(), message.getContent().substring(1));
			 userRepository.save(user);
			return ResponseMessage.response(message, "감사합니다! 이제 hello-utlra의 서비스를 이용하실 수 있습니다.");
		}
		
		log.debug(JsonConverter.makeObject(message, Answers.answer(message)).toString());
		return ResponseMessage.response(message, Answers.answer(message));
	}
}
