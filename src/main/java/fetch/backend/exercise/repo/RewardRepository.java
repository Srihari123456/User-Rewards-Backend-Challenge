package fetch.backend.exercise.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fetch.backend.exercise.domain.RewardEntity;
import fetch.backend.exercise.dto.RewardsDto;

public interface RewardRepository extends JpaRepository<RewardEntity, Integer> {
	@Query(value = "SELECT new fetch.backend.exercise.dto.RewardsDto(re.payer, SUM(re.points)) FROM RewardEntity re GROUP BY re.payer")
	List<RewardsDto> getRewardsDetails();
	
	@Query(value = "Select re from RewardEntity re order by re.timestampDetails") 
	List<RewardEntity> getRedeemableRewardsDetails();
	
}
