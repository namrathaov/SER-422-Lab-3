package com.mvc.bean;

public class StoryBean {
	private Integer storyID;
	private String storyTitle;
	private String story;
	private String visibility;
	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getStoryID() {
		return storyID;
	}

	public void setStoryTitle(Integer storyID) {
		this.storyID = storyID;
	}

	public String getStoryTitle() {
		return storyTitle;
	}

	public void setStoryTitle(String storyTitle) {
		this.storyTitle = storyTitle;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
}
