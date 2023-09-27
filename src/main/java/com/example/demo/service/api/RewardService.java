package com.example.demo.service.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.AddRewardsDto;
import com.example.demo.dto.SpendPointsDto;

public interface RewardService {
	public Map<String, Long> getBalancePointsPerPayer();
	
	public void addPoints(AddRewardsDto addRewardsDto);
	
	public ResponseEntity<Object> spendPoints(SpendPointsDto spendPointsDto);
}
