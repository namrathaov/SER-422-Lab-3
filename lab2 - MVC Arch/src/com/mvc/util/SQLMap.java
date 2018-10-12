package com.mvc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.mvc.bean.LoginBean;
import com.mvc.bean.StoryBean;
import java.sql.PreparedStatement;

public class SQLMap {
	Connection con;
	Statement stmt;
	PreparedStatement stmt1;
	ResultSet rs,result;
	public boolean validateUser(LoginBean bean) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT username,password FROM Users WHERE username=?");  
				System.out.println("Getting username");
				stmt1.setString(1, bean.getUserName());
				rs=stmt1.executeQuery();
				while(rs.next())  {
					System.out.println("Username"+rs.getString(1)+"Password "+rs.getString(2));
					if(rs.getString(2).equals(bean.getPassword()) )
					{
						return true;
					}
				}
				con.close();

			}

		}
		catch(Exception e){
			System.out.println(e);
		}

		return false;
	}

	public boolean userExists(String username) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT count(username) FROM Users WHERE username=?");  
				stmt1.setString(1, username);
				rs=stmt1.executeQuery();  
				if(rs.next())
				{
					if(rs.getInt(1)==1) {
					System.out.println("User exists");
					con.close();
					return true;
					}
				}
				
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	public String createuser(LoginBean loginBean) {
		PreparedStatement stmt1;
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				System.out.print("Connection");
				stmt=con.createStatement();  
				stmt1=con.prepareStatement("INSERT INTO USERS(username,password,role) VALUES(?,?,?)");
				stmt1.setString(1,loginBean.getUserName());
				stmt1.setString(2,loginBean.getPassword());
				stmt1.setString(3,loginBean.getRole());
				int create=stmt1.executeUpdate();
				if(create>0)
				{
					con.close();
					return "SUCCESS";
				}
				
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return "ERROR";
	}

	public String getUserType(LoginBean bean) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT role FROM Users WHERE username=?");
				stmt1.setString(1, bean.getUserName());
				rs=stmt1.executeQuery();  
				if(rs.next())
				return rs.getString(1);
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}

		return null;
	}

	public void createStory(String username, StoryBean storyBean) {
		System.out.println("Created new story : author " + username + "\ntitle" + storyBean.getStoryTitle() + "\ncontent"
				+ storyBean.getStory() + "\nvisibility" + storyBean.getVisibility());
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				System.out.print("Connection");
				stmt1=con.prepareStatement("INSERT INTO Stories(title,content,visibility,username)"
						+ " VALUES(?,?,?,?)");  
				stmt1.setString(1, storyBean.getStoryTitle());
				stmt1.setString(2,storyBean.getStory());
				stmt1.setString(3,storyBean.getVisibility());
				stmt1.setString(4, username);
				stmt1.executeUpdate();  
				con.commit();
				con.close();
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public ArrayList<Integer> getPublicStory() {
		ArrayList<Integer> publicStories = new ArrayList<Integer>();
		try{  
			System.out.print("get Public story");
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT storyID FROM Stories WHERE visibility=?",ResultSet.TYPE_SCROLL_INSENSITIVE, 
						  ResultSet.CONCUR_READ_ONLY);  
				stmt1.setString(1, "Public");
				rs=stmt1.executeQuery();
				
				int resultSize=1;
				rs.next();
				while(rs.next())
				{
					publicStories.add(rs.getInt(resultSize));
					resultSize+=1;
				}

			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return publicStories;
	}

	/*public ArrayList<Story> getPublicStories() {
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
	}*/

	public String getTitle(Integer storyID) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT title FROM Stories WHERE storyID=?");  
				stmt1.setInt(1, storyID);
				rs=stmt1.executeQuery();
				System.out.println("Story title " +rs.getString(1));
				return rs.getString(1);
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}

	/*public ArrayList<String> getTitles(ArrayList<Integer> storyList) {
		Integer storyID;
		Iterator<Integer> storyIDIter = storyList.iterator();
		Iterator<Story> storyTitleIter = stories.iterator();
		String publicStories = new String();
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
*/
	public ArrayList<Integer> getAllStories() {
	//TODO
		ArrayList<Integer> allStories = new ArrayList<Integer>();
		try{  
			System.out.println("Inside all stories");
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT storyID FROM Stories");  
				rs=stmt1.executeQuery();
				int i=1;
				rs.next();
				while(rs.next())
				{
					allStories.add(rs.getInt(i));
					i+=1;
				}
				return allStories;
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return new ArrayList<Integer>();
	}
	public String getStoryTitle(Integer storyID) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT title FROM Stories WHERE storyID=?");  
				stmt1.setInt(1, storyID);
				rs=stmt1.executeQuery();
				System.out.println("Story title " +rs.getString(1));
				return rs.getString(1);
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}

	public String getStoryContent(Integer storyID) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT content FROM Stories WHERE storyID=?");  
				stmt1.setInt(1, storyID);
				rs=stmt1.executeQuery();
				System.out.println("Story content " +rs.getString(1));
				return rs.getString(1);
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}

	public String getStoryAuthor(Integer storyID) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT username FROM Stories WHERE storyID=?");  
				stmt1.setInt(1, storyID);
				rs=stmt1.executeQuery();
				System.out.println("Story author " +rs.getString(1));
				return rs.getString(1);
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}

	public String getStoryVisibility(Integer storyID) {

		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT visibility FROM Stories WHERE storyID=?");  
				stmt1.setInt(1, storyID);
				rs=stmt1.executeQuery();
				System.out.println("Story title " +rs.getString(1));
				return rs.getString(1);
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	public boolean updateStory(Integer storyID, String title, String content, String visibility,String username) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				System.out.print("Connection");
				stmt=con.createStatement();  
				int insert=stmt.executeUpdate("UPDATE Stories SET title="+title+",content"+content+",visibility="+visibility+",username="+username
						+"WHERE storyID="+storyID);
				con.commit();
				if(insert>0) {
					con.close();
					return true;
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	public boolean deleteStory(Integer storyID) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				System.out.print("Connection");
				stmt1=con.prepareStatement("Delete from Stories WHERE storyID=?");
				stmt1.setInt(1, storyID);
				int insert=stmt1.executeUpdate();
				con.commit();
				if(insert>0) {
					con.close();
					return true;
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	public ArrayList<Integer> getAuthorStories(String author) {
		ArrayList<Integer> publicStories = new ArrayList<Integer>();
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT storyID FROM Stories WHERE username=?");  
				stmt1.setString(1, author);
				rs=stmt1.executeQuery();  
				int resultSize=1;
				System.out.println("Inside author stories");
				rs.next();	
				while(rs.next())
				{
					publicStories.add(rs.getInt(resultSize));
					resultSize+=1;
				}
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return publicStories;
	}

	/*public Integer getStoryID(Story story) {
		return story.storyID;
	}*/

	public ArrayList<Integer> getFavourites(String user) {
		ArrayList<Integer> favList = new ArrayList<Integer>();
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT storyID FROM Users WHERE username=?");  
				stmt1.setString(1, user);
				rs=stmt1.executeQuery();  
				int resultSize=0;
				System.out.println("Stories count " +rs.getFetchSize());
				if(rs==null)
				{
					return new ArrayList<Integer>();
				}
				while(rs.getFetchSize()<=resultSize)
				{
					favList.add(rs.getInt(resultSize));
					resultSize+=1;
				}
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return favList;
	}

	public boolean isFavourite(String user, Integer storyID) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				stmt1=con.prepareStatement("SELECT 1 FROM Dual WHERE exists (SELECT storyID from "
						+ "Users where username=? and storyID=?)");
				stmt1.setString(1, user);
				stmt1.setInt(2, storyID);
				rs=stmt1.executeQuery();  
				if(rs.getInt(1)==1)
				{
					return true;
				}
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	public boolean addFavourite(String user, Integer storyID) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				System.out.print("Connection");
				stmt1=con.prepareStatement("INSERT INTO Favourites VALUES(?,?)"); 
				stmt1.setString(1, user);
				stmt1.setInt(2, storyID);
				System.out.println("Adding fav" + storyID);
				int insert=stmt1.executeUpdate();
				con.commit();
				if(insert>0) {
					con.close();
					return true;
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	public boolean removeFavourite(String user, Integer storyID) {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			//TODO define db in properties file
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/NewsWebsite?autoReconnect=true&useSSL=false","root","root");  
			if(con.isValid(0)) {
				System.out.print("Connection");
				stmt1=con.prepareStatement("DELETE FROM Favourites WHERE username=? AND storyID=?");  
				stmt1.setString(1, user);
				stmt1.setInt(2, storyID);
				System.out.println("Deleting fav" + storyID);
				int insert=stmt1.executeUpdate();
				con.commit();
				if(insert>0) {
					con.close();
					return true;
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return false;
	}
}