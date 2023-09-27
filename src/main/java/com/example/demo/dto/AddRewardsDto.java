package com.example.demo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddRewardsDto {
	
	
	private String payer;
	private Long points;
	private LocalDateTime timestamp;
	
	public AddRewardsDto() {
	
	}
	
	public AddRewardsDto(String payer, Long points) {
		super();
		this.payer = payer;
		this.points = points;
	}

	public AddRewardsDto(String payer, Long points, LocalDateTime timestampDetails) {
		super();
		this.payer = payer;
		this.points = points;
		this.timestamp = timestampDetails;
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

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
	
}
