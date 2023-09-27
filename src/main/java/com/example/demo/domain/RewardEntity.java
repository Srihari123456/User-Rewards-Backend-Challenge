package com.example.demo.domain;



import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="REWARD_INFO")
public class RewardEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reward_id_generator")
    @SequenceGenerator(name = "reward_id_generator", sequenceName = "REWARD_SEQUENCE", allocationSize = 1)
    @Column(name = "REWARD_ID")
    private Integer rewardId;

    @Column(name = "PAYER")
    private String payer;

    @Column(name = "POINTS")
    private Long points;

    @Column(name = "TIMESTAMP_DETAILS")
    private LocalDateTime timestampDetails;

    @Column(name = "ACTIVE_IND")
    private Boolean activeInd;
    
    

	public RewardEntity() {
		
	}

	public RewardEntity(String payer, Long points, LocalDateTime timestampDetails, Boolean activeInd) {
		super();
		this.payer = payer;
		this.points = points;
		this.timestampDetails = timestampDetails;
		this.activeInd = activeInd;
	}

	public Integer getRewardId() {
		return rewardId;
	}

	public void setRewardId(Integer rewardId) {
		this.rewardId = rewardId;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public LocalDateTime getTimestampDetails() {
		return timestampDetails;
	}

	public void setTimestampDetails(LocalDateTime timestampDetails) {
		this.timestampDetails = timestampDetails;
	}

	public Boolean getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}
    
    
}
