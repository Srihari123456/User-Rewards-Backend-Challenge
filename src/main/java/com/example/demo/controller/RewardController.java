package com.example.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AddRewardsDto;
import com.example.demo.dto.SpendPointsDto;
import com.example.demo.service.api.RewardService;

@RestController
@RequestMapping(value = "/rewards")
public class RewardController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RewardController.class);
	
	@Autowired
	private RewardService rewardsService;
	
	@GetMapping(path = "/balance")
	public Map<String, Long> getBalance() {
		return rewardsService.getBalancePointsPerPayer();
	}
	
	@PostMapping(value = "/add") // check @Valid javax.validation.valid
	public void addPoints(@RequestBody AddRewardsDto addRewardsDto) {
		rewardsService.addPoints(addRewardsDto);
		LOGGER.info("In controller");
	}
	
	@PostMapping(value = "/spendPoints")
	public ResponseEntity<Object> spendPoints(@RequestBody SpendPointsDto spendPointsDto) {
		return rewardsService.spendPoints(spendPointsDto);
	}

}
