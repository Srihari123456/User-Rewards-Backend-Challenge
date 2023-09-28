package fetch.backend.exercise.service.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import fetch.backend.exercise.dto.RewardsDto;
import fetch.backend.exercise.dto.SpendPointsDto;

public interface RewardService {
	public Map<String, Long> getBalancePointsPerPayer();
	
	public void addPoints(RewardsDto rewardsDto);
	
	public ResponseEntity<Object> spendPoints(SpendPointsDto spendPointsDto);
}
