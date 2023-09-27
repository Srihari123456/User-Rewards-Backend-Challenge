package com.example.demo.service.api;

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

import com.example.demo.controller.RewardController;
import com.example.demo.domain.RewardEntity;
import com.example.demo.dto.AddRewardsDto;
import com.example.demo.dto.SpendPointsDto;
import com.example.demo.repo.RewardRepository;

@Service
public class RewardServiceImpl implements RewardService{

	private static final Logger LOGGER = LoggerFactory.getLogger(RewardController.class);
	
	@Autowired
	public RewardRepository rewardRepository;
	
	@Override
	public Map<String, Long> getBalancePointsPerPayer() {
		Map<String, Long> responseMap = new HashMap<>();
		List<AddRewardsDto>rewards = rewardRepository.getRewardsDetails();
		for(AddRewardsDto re: rewards) {
			responseMap.put(re.getPayer(), re.getPoints());
		}
		return responseMap;
	}

	@Override
	public void addPoints(AddRewardsDto addRewardsDto) {
		LOGGER.info("In service");
		rewardRepository.save(new RewardEntity(addRewardsDto.getPayer(),
											   addRewardsDto.getPoints(), 
											   addRewardsDto.getTimestamp(), 
											   Boolean.TRUE)
							 );
	}

	@Override
	public ResponseEntity<Object> spendPoints(SpendPointsDto spendPointsDto) {
		List<RewardEntity>rewards = rewardRepository.getActiveRewardsDetails();
		Long availablePoints = 0l, pointsToSpend = spendPointsDto.getPoints();
		var addRewardsDtoList = new ArrayList<AddRewardsDto>();
		
		for(RewardEntity re: rewards) {
			availablePoints += re.getPoints();
		}
		
		if(availablePoints < pointsToSpend) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user doesn’t have enough points");
		}
		
		for(RewardEntity reward: rewards) {
			Long currentAvailableRewardPoints = reward.getPoints();
			Long currentRemainingRewardPoints = Math.max(currentAvailableRewardPoints - pointsToSpend, 0);
			reward.setPoints(currentRemainingRewardPoints);
			pointsToSpend = Math.max(0, pointsToSpend - currentAvailableRewardPoints);
			addRewardsDtoList.add(new AddRewardsDto(reward.getPayer(),
					currentRemainingRewardPoints - currentAvailableRewardPoints));
			if(pointsToSpend <= 0)
				break;
		}
		rewardRepository.saveAll(rewards);
		return ResponseEntity.status(HttpStatus.OK).body(addRewardsDtoList);
	}

}
