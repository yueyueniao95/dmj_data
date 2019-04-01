package com.infinite.dmj_data.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinite.dmj_data.service.SunshineService;

@RestController
@RequestMapping("/sunshine")
public class SunshineController {
	
	@Autowired
	private SunshineService sunshineService;
	
	@GetMapping("/test")
	public void getData() throws ClientProtocolException, IOException{
		this.sunshineService.getData();
	}

}
