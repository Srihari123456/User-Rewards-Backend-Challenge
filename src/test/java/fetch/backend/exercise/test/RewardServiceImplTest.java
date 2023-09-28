package fetch.backend.exercise.test;
import fetch.backend.exercise.domain.RewardEntity;
import fetch.backend.exercise.dto.RewardsDto;
import fetch.backend.exercise.dto.SpendPointsDto;
import fetch.backend.exercise.repo.RewardRepository;
import fetch.backend.exercise.service.api.RewardServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewardServiceImplTest {

    @Mock
    private RewardRepository rewardRepository;

    @InjectMocks
    private RewardServiceImpl rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBalancePointsPerPayer() {
        // Mock data
        List<RewardsDto> rewards = new ArrayList<>();
        rewards.add(new RewardsDto("Payer1", 100L));
        rewards.add(new RewardsDto("Payer2", 200L));

        // Mock the repository call
        when(rewardRepository.getRewardsDetails()).thenReturn(rewards);

        // Test the service method
        Map<String, Long> responseMap = rewardService.getBalancePointsPerPayer();

        // Assertions
        assertNotNull(responseMap);
        assertEquals(2, responseMap.size());
        assertTrue(responseMap.containsKey("Payer1"));
        assertTrue(responseMap.containsKey("Payer2"));
        assertEquals(100L, responseMap.get("Payer1"));
        assertEquals(200L, responseMap.get("Payer2"));
    }
    
    @Test
    @DisplayName("Insufficient Points")
    void spendPointsWithoutbalance() {
    	long pointsToSpend = 100; // Set points to spend
        SpendPointsDto spendPointsDto = new SpendPointsDto();
        spendPointsDto.setPoints(pointsToSpend);

        List<RewardEntity> rewards = new ArrayList<>();
        rewards.add(new RewardEntity("Payer1", 0L, null));
        rewards.add(new RewardEntity("Payer2", 1L,null));

        // Mock the repository call
        when(rewardRepository.getRedeemableRewardsDetails()).thenReturn(rewards);
        ResponseEntity<Object> response = rewardService.spendPoints(spendPointsDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The user doesn’t have enough points", response.getBody());
    
    }

    @Test
    void addPoints() {
        // Mock data
        RewardsDto rewardsDto = new RewardsDto("Payer1", 100L);

        // Test the service method
        rewardService.addPoints(rewardsDto);

        // Verify that save was called on the repository
        verify(rewardRepository, times(1)).save(any());
    }

    @Test
    void spendPoints() {
        // Mock data
        SpendPointsDto spendPointsDto = new SpendPointsDto(150L);

        List<RewardsDto> rewardsDtoList = new ArrayList<>();
        rewardsDtoList.add(new RewardsDto("Payer1", -50L));
        rewardsDtoList.add(new RewardsDto("Payer2", 0L));

        List<RewardEntity> rewards = new ArrayList<>();
        rewards.add(new RewardEntity("Payer1", 100L, null));
        rewards.add(new RewardEntity("Payer2", 200L,null));

        // Mock the repository call
        when(rewardRepository.getRedeemableRewardsDetails()).thenReturn(rewards);

        // Test the service method
        ResponseEntity<Object> responseEntity = rewardService.spendPoints(spendPointsDto);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        List<RewardsDto> responseDtoList = (List<RewardsDto>) responseEntity.getBody();
        assertNotNull(responseDtoList);
        assertEquals(2, responseDtoList.size());
        assertTrue(responseDtoList.stream().anyMatch(dto -> dto.getPayer().equals("Payer1")));
        assertTrue(responseDtoList.stream().anyMatch(dto -> dto.getPayer().equals("Payer2")));

        // Verify that saveAll was called on the repository
        verify(rewardRepository, times(1)).saveAll(rewards);
    }
}
