package com.yonder.es;

public class StoryBattleResultVo {
	private int StoryId;
	private String star1Rate;
	private String star2Rate;
	private String star0Rate;
	private String storyWinPlayerRate;
	private String storyLostPlayerRate;
	private String storyLostWinPlayerRate;
	public int getStoryId() {
		return StoryId;
	}
	public void setStoryId(int storyId) {
		StoryId = storyId;
	}
	public String getStar1Rate() {
		return star1Rate;
	}
	public void setStar1Rate(String star1Rate) {
		this.star1Rate = star1Rate;
	}
	public String getStar2Rate() {
		return star2Rate;
	}
	public void setStar2Rate(String star2Rate) {
		this.star2Rate = star2Rate;
	}
	public String getStar0Rate() {
		return star0Rate;
	}
	public void setStar0Rate(String star0Rate) {
		this.star0Rate = star0Rate;
	}
	public String getStoryWinPlayerRate() {
		return storyWinPlayerRate;
	}
	public void setStoryWinPlayerRate(String storyWinPlayerRate) {
		this.storyWinPlayerRate = storyWinPlayerRate;
	}
	public String getStoryLostPlayerRate() {
		return storyLostPlayerRate;
	}
	public void setStoryLostPlayerRate(String storyLostPlayerRate) {
		this.storyLostPlayerRate = storyLostPlayerRate;
	}
	public String getStoryLostWinPlayerRate() {
		return storyLostWinPlayerRate;
	}
	public void setStoryLostWinPlayerRate(String storyLostWinPlayerRate) {
		this.storyLostWinPlayerRate = storyLostWinPlayerRate;
	}
}
