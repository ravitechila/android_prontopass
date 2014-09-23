package com.protopass.pojo;

public class QuestionPaper {

	String question,ans1,ans3,correctAns2,ans4;
	String description;
	
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAns1() {
		return ans1;
	}
	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}
	public String getAns3() {
		return ans3;
	}
	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}
	public String getCorrectAns2() {
		return correctAns2;
	}
	public void setCorrectAns2(String correctAns2) {
		this.correctAns2 = correctAns2;
	}
	public String getAns4() {
		return ans4;
	}
	public void setAns4(String ans4) {
		this.ans4 = ans4;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		
		return "Question = "+question +" Ans 1 = "+ans1+" Ans2 = "+correctAns2;
	}
	
}
