package fetch.backend.exercise.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fetch.backend.exercise.dto.RewardsDto;
import fetch.backend.exercise.dto.SpendPointsDto;
import fetch.backend.exercise.service.api.RewardService;

@RestController
@RequestMapping(value = "/")
public class RewardController {

	@Autowired
	private RewardService rewardsService;
	
	@PostMapping(value = "/add")
	public void addPoints(@RequestBody RewardsDto rewardsDto) {
		rewardsService.addPoints(rewardsDto);
	}
	
	@PostMapping(value = "/spendPoints")
	public ResponseEntity<Object> spendPoints(@RequestBody SpendPointsDto spendPointsDto) {
		return rewardsService.spendPoints(spendPointsDto);
	}
	
	@GetMapping(path = "/balance")
	public Map<String, Long> getBalance() {
		return rewardsService.getBalancePointsPerPayer();
	}

}
