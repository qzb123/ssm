package com.jsx.model;

import org.springframework.web.multipart.MultipartFile;

public class User {
	//@NotNull
    private int id;
	//@NotNull
    private String username;
	//@NotNull
    private String password;
    private String image;  
    private MultipartFile pictureFile;
 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public MultipartFile getPictureFile() {
		return pictureFile;
	}
	public void setPictureFile(MultipartFile pictureFile) {
		this.pictureFile = pictureFile;
	}
}
