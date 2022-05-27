package com.example.validatingforminput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class WebController implements WebMvcConfigurer {
	@Autowired //Springによって自動生成され、データを処理するために使用します
	private UserRepository userRepository;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}

	@GetMapping("/")
	public String showForm(PersonForm personForm) {
		return "form";
	}

	@PostMapping("/")
	public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult,@RequestParam String name, @RequestParam int age) {

		if (bindingResult.hasErrors()) {
			return "form";
		}

		PersonForm n =new PersonForm();
		n.setName(name);
		n.setAge(age);
		userRepository.save(n);

		return "redirect:/results";
	}

	@GetMapping("/table")
	public @ResponseBody Iterable<PersonForm> showPersonTable(){
		return userRepository.findAll();
	}

}