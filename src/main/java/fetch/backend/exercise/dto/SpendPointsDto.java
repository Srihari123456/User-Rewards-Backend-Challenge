package fetch.backend.exercise.dto;

public class SpendPointsDto {
	private Long points;

	public SpendPointsDto() {
		super();
	}

	public SpendPointsDto(Long points) {
		super();
		this.points = points;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}
	
}
