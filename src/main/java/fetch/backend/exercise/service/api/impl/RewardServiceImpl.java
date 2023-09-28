package fetch.backend.exercise.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fetch.backend.exercise.domain.RewardEntity;
import fetch.backend.exercise.dto.RewardsDto;
import fetch.backend.exercise.dto.SpendPointsDto;
import fetch.backend.exercise.repo.RewardRepository;
import fetch.backend.exercise.service.api.RewardService;

@Service
public class RewardServiceImpl implements RewardService{

	private static final Logger LOGGER = LoggerFactory.getLogger(RewardServiceImpl.class);
	
	@Autowired
	public RewardRepository rewardRepository;
	
	// Checks the points available for each of the Payer
	@Override
	public Map<String, Long> getBalancePointsPerPayer() {
		LOGGER.info("Calculating the points available under each Payer");
		Map<String, Long> responseMap = new HashMap<>();
		List<RewardsDto>rewards = rewardRepository.getRewardsDetails();
		for(RewardsDto rename: rewards) {
			responseMap.put(rename.getPayer(), rename.getPoints());
		}
		return responseMap;
	}

	// Saves the newly received points from the Payer
	@Override
	public void addPoints(RewardsDto rewardsDto) {
		LOGGER.info("Adding the newly received points from {}",rewardsDto.getPayer());
		rewardRepository.save(new RewardEntity(rewardsDto.getPayer(),
											   rewardsDto.getPoints(), 
											   rewardsDto.getTimestamp()));
	}

	// Calculates the total available points for a user
	private Long getAvailablePoints(List<RewardEntity>rewards) {
		var availablePoints = 0l;
		for(RewardEntity reward: rewards) {
			availablePoints += reward.getPoints();
		}
		return availablePoints;
	}
	
	@Override
	public ResponseEntity<Object> spendPoints(SpendPointsDto spendPointsDto) {
		var rewards = rewardRepository.getRedeemableRewardsDetails();
		
		var pointsToSpend = spendPointsDto.getPoints();
		var rewardsDtoList = new ArrayList<RewardsDto>();
		
		var availablePoints = getAvailablePoints(rewards);
		
		// Checks if the user has sufficient points to spend
		if(availablePoints < pointsToSpend) {
			LOGGER.info("User doesnt have enough points");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user doesn’t have enough points");
		}
		
		// Deducts points based on timestamp
		
		for(RewardEntity reward: rewards) {
			var redeemableRewardPoints = reward.getPoints();
			var remainingRewardPoints = Math.max(redeemableRewardPoints - pointsToSpend, 0);
			reward.setPoints(remainingRewardPoints);
			
			pointsToSpend = Math.max(0, pointsToSpend - redeemableRewardPoints);
			rewardsDtoList.add(new RewardsDto(reward.getPayer(), remainingRewardPoints - redeemableRewardPoints));
			LOGGER.info("Deducting points from {}",reward.getPayer());
			
			if(pointsToSpend <= 0)
				break;
		}
		rewardRepository.saveAll(rewards);
		return ResponseEntity.status(HttpStatus.OK).body(rewardsDtoList);
	}

}
