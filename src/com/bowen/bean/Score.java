package com.bowen.bean;

import java.util.Date;

public class Score {
 private int id;
 private String subName;
 private Date scoreDate;
 private double score;
 public Score(){ 
 }
 
public Score(int id, String subName, Date scoreDate, double score) {
	super();
	this.id = id;
	this.subName = subName;
	this.scoreDate = scoreDate;
	this.score = score;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getSubName() {
	return subName;
}
public void setSubName(String subName) {
	this.subName = subName;
}
public Date getScoreDate() {
	return scoreDate;
}
public void setScoreDate(Date scoreDate) {
	this.scoreDate = scoreDate;
}
public double getScore() {
	return score;
}
public void setScore(double score) {
	this.score = score;
}
 

 
 
}
