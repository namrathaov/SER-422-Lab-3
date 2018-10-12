package com.mvc.dao;

import java.util.ArrayList;

import com.mvc.bean.StoryBean;
import com.mvc.util.DatabaseMap;
import com.mvc.util.Story;

public class NewStory {

	DatabaseMap map;

	public NewStory() {
		map = new DatabaseMap();
	}

	public void createStory(String username, StoryBean storyBean) {
		map.createStory(username, storyBean);
	}

	public void getStory(Integer storyID, StoryBean storyBean) {
		String title = map.getStoryTitle(storyID);
		String content = map.getStoryContent(storyID);
		String visibility = map.getStoryVisibility(storyID);
		String author = map.getStoryAuthor(storyID);
		storyBean.setStoryTitle(title);
		storyBean.setStory(content);
		storyBean.setVisibility(visibility);
		storyBean.setAuthor(author);
	}

	public ArrayList<Integer> getPublicStories() {
		return map.getPublicStory();
	}

	public Integer getStoryID(Story story) {
		return map.getStoryID(story);
	}

	public boolean updateStory(Integer storyID, String title, String content, String visibility,String username) {
		return map.updateStory(storyID, title, content, visibility,username);
	}

	public boolean deleteStory(Integer storyID) {
		return map.deleteStory(storyID);
	}

	public String getStoryTitle(Integer storyID) {
		return map.getStoryTitle(storyID);
	}

	public boolean isFavourite(String user, Integer storyID) {
		return map.isFavourite(user, storyID);
		
	}
	
	public boolean addFavourite(String user, Integer storyID) {
		return map.addFavourite(user, storyID);
	}

	public boolean removeFavourite(String user, Integer storyID) {
		return map.removeFavourite(user, storyID);
	}

	public ArrayList<Integer> getFavouriteStories(String user) {
		return map.getFavourites(user);
	}
	
	public ArrayList<Integer> getAllStories() {
		return map.getAllStories();
	}
	public ArrayList<Integer> getAuthorStories(String author) {
		return map.getAuthorStories(author);
	}
}