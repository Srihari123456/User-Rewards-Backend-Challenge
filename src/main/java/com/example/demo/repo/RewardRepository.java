package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.RewardEntity;
import com.example.demo.dto.AddRewardsDto;

public interface RewardRepository extends JpaRepository<RewardEntity, Integer> {
	@Query(value = "SELECT new com.example.demo.dto.AddRewardsDto(re.payer, SUM(re.points)) FROM RewardEntity re GROUP BY re.payer")
	List<AddRewardsDto> getRewardsDetails();
	
	@Query(value = "Select re from RewardEntity re where re.activeInd = true order by re.timestampDetails") 
	List<RewardEntity> getActiveRewardsDetails();
	
}
