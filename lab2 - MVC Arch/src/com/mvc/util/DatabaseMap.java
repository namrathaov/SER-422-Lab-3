package com.mvc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.mvc.bean.LoginBean;
import com.mvc.bean.StoryBean;

public class DatabaseMap {

	public static ArrayList<User> users;
	public static ArrayList<Story> stories;
	public static HashMap<String, ArrayList<Integer>> favourites;
	public static Integer storyCount;

	public DatabaseMap() {
		if (users == null) {
			users = new ArrayList<User>();
		}
		if (stories == null) {
			stories = new ArrayList<Story>();
		}
		if (storyCount == null) {
			storyCount = 0;
		}
		if (favourites == null) {
			favourites = new HashMap<String, ArrayList<Integer>>();
		}
	}

	public boolean validateUser(LoginBean bean) {
		String username = bean.getUserName();
		String password = bean.getPassword();
		String role = bean.getRole();
		Iterator<User> userIter = users.iterator();
		while (userIter.hasNext()) {
			User u = userIter.next();
			if (u.username.equals(username)) {
				if (u.password.equals(password)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;

	}

	public boolean userExists(String username) {
		Iterator<User> userIter = users.iterator();
		while (userIter.hasNext()) {
			User u = userIter.next();
			if (u.username.equals(username)) {
				return true;
			}
		}
		return false;
	}

	public String createuser(LoginBean bean) {
		String username = bean.getUserName();
		if (userExists(username)) {
			return "User exists";
		} else {
			User user = new User();
			user.username = username;
			user.password = bean.getPassword();
			user.role = bean.getRole();
			users.add(user);
			return "SUCCESS";
		}
	}
	
	public String getUserType(LoginBean bean) {
		String username = bean.getUserName();
		Iterator<User> userIter = users.iterator();
		while (userIter.hasNext()) {
			User u = userIter.next();
			if (u.username.equals(username)) {
				return u.role;
			}
		}
		return null;
	}

	public void createStory(String username, StoryBean storyBean) {
		Story story = new Story();
		story.author = username;
		story.title = storyBean.getStoryTitle();
		story.content = storyBean.getStory();
		story.visibility = storyBean.getVisibility();
		story.storyID = DatabaseMap.storyCount;
		DatabaseMap.storyCount += 1;
		stories.add(story);
		System.out.println("Created new story : author " + story.author + "\ntitle" + story.title + "\ncontent"
				+ story.content + "\nvisibility" + story.visibility + "\nID" + story.storyID);
	}

	public ArrayList<Integer> getPublicStory() {
		Story story;
		Iterator<Story> storyIter = stories.iterator();
		System.out.println("Stories count " + stories.size());
		ArrayList<Integer> publicStories = new ArrayList<Integer>();
		while (storyIter.hasNext()) {
			story = storyIter.next();
			if (story.visibility.equals("Public")) {
				publicStories.add(story.storyID);
			}
		}
		return publicStories;
	}
	
	public ArrayList<Story> getPublicStories() {
		Story story;
		Iterator<Story> storyIter = stories.iterator();
		ArrayList<Story> publicStories = new ArrayList<Story>();
		while (storyIter.hasNext()) {
			story = storyIter.next();
			if (story.visibility.equals("Public")) {
				publicStories.add(story);
			}
		}
		return publicStories;
	}
	
	public String getTitle(Integer storyID) {
		Story story;
		Iterator<Story> storyTitleIter = stories.iterator();
		while (storyTitleIter.hasNext()) {
			story = storyTitleIter.next();
			if (storyID == story.storyID) {
				return story.title;
			}
		}
		return null;
	}

	public ArrayList<String> getTitles(ArrayList<Integer> storyList) {
		Integer storyID;
		Story story;
		Iterator<Integer> storyIDIter = storyList.iterator();
		Iterator<Story> storyTitleIter = stories.iterator();
		ArrayList<String> publicStories = new ArrayList<String>();
		while (storyIDIter.hasNext()) {
			storyID = storyIDIter.next();
			while (storyTitleIter.hasNext()) {
				story = storyTitleIter.next();
				if (storyID == story.storyID) {
					publicStories.add(story.title);
				}
			}

		}
		return publicStories;
	}

	public ArrayList<Story> getStories(ArrayList<Integer> storyList) {
		Integer storyID;
		Story story;
		Iterator<Integer> storyIDIter = storyList.iterator();
		Iterator<Story> storyTitleIter = stories.iterator();
		ArrayList<Story> publicStories = new ArrayList<Story>();
		while (storyIDIter.hasNext()) {
			storyID = storyIDIter.next();
			while (storyTitleIter.hasNext()) {
				story = storyTitleIter.next();
				if (storyID == story.storyID) {
					publicStories.add(story);
				}
			}

		}
		return publicStories;
	}
	
	public ArrayList<Integer> getAllStories() {
		ArrayList<Integer> allStories = new ArrayList<Integer>();
		Iterator<Story> all = stories.iterator();
		Story s;
		Integer storyID;
		while(all.hasNext()) {
			s= all.next();
			allStories.add(s.storyID);
		}
		return allStories;
	}

	public String getStoryTitle(Integer storyID) {

		Story story = null;
		Iterator<Story> storyTitleIter = stories.iterator();
		while (storyTitleIter.hasNext()) {
			story = storyTitleIter.next();
			if (storyID == story.storyID) {
				return (story.title);
			}
		}
		return "Invalid story ID";
	}

	public String getStoryContent(Integer storyID) {
		Story story = null;
		Iterator<Story> storyTitleIter = stories.iterator();
		while (storyTitleIter.hasNext()) {
			story = storyTitleIter.next();
			if (storyID == story.storyID) {
				return (story.content);
			}
		}
		return "Invalid story ID";
	}
	
	public String getStoryAuthor(Integer storyID) {
		Story story = null;
		Iterator<Story> storyTitleIter = stories.iterator();
		while (storyTitleIter.hasNext()) {
			story = storyTitleIter.next();
			if (storyID == story.storyID) {
				return (story.author);
			}
		}
		return "Invalid story ID";
	}

	public String getStoryVisibility(Integer storyID) {

		Story story = null;
		Iterator<Story> storyTitleIter = stories.iterator();
		while (storyTitleIter.hasNext()) {
			story = storyTitleIter.next();
			if (storyID == story.storyID) {
				return (story.visibility);
			}
		}
		return "Invalid story ID";
	}

	public boolean updateStory(Integer storyID, String title, String content, String visibility,String username) {
		Story story;
		Iterator<Story> storyIterator = stories.iterator();
		int index = 0;
		while (storyIterator.hasNext()) {
			story = storyIterator.next();
			if (storyID == story.storyID) {
				story.title = title;
				story.content = content;
				story.visibility = visibility;
				stories.set(index, story);
				return true;
			}
			index += 1;
		}

		return false;

	}

	public boolean deleteStory(Integer storyID) {
		Story story;
		Iterator<Story> storyIterator = stories.iterator();
		int index = 0;
		while (storyIterator.hasNext()) {
			story = storyIterator.next();
			if (storyID.equals(story.storyID)) {
				stories.remove(index);
				return true;
			}
			index += 1;
		}
		return false;
	}

	public ArrayList<Integer> getAuthorStories(String author) {
		Integer storyID;
		Story story;
		Iterator<Story> storyIter = stories.iterator();
		ArrayList<Integer> publicStories = new ArrayList<Integer>();
		while (storyIter.hasNext()) {
			story = storyIter.next();
			if (author.equals(story.author)) {
				publicStories.add(story.storyID);
			}
		}

		return publicStories;
	}

	public Integer getStoryID(Story story) {
		return story.storyID;
	}

	public ArrayList<Integer> getFavourites(String user) {
		ArrayList<Integer> favList = favourites.get(user);
		if(favList==null) {
			return new ArrayList<Integer>();
		}
		return favList;
	}
	
	public boolean isFavourite(String user, Integer storyID) {
		ArrayList<Integer> favList = favourites.get(user);
		if(favList == null) { 
			//System.out.println("Fav list is null");
			return false; 
		}
		Iterator<Integer> favIter = favList.iterator();
		Integer fav;
		while (favIter.hasNext()) {
			fav = favIter.next();
			//System.out.println("Checking " + fav + " " + storyID);
			if (fav.equals(storyID)) {
				return true;
			}
		}
		return false;
	}

	public boolean addFavourite(String user, Integer storyID) {
		ArrayList<Integer> favList = favourites.get(user);
		if (favList == null) {
			favList = new ArrayList<Integer>();
			favourites.put(user, favList);
		}
		Iterator<Integer> favIter = favList.iterator();
		Integer fav;
		while (favIter.hasNext()) {
			fav = favIter.next();
			if (fav.equals(storyID)) {
				return true;
			}
		}
		System.out.println("Adding fav" + storyID);
		favList.add(storyID);
		return true;
	}

	public boolean removeFavourite(String user, Integer storyID) {
		ArrayList<Integer> favList = favourites.get(user);
		Iterator<Integer> favIter = favList.iterator();
		Integer fav;
		int index = 0;
		while (favIter.hasNext()) {
			fav = favIter.next();
			if (fav.equals(storyID)) {
				favList.remove(index);
				return true;
			}
			index += 1;
		}
		return false;
	}

}
