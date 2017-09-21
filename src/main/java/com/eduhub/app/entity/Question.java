package com.eduhub.app.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity(name="question")
public class Question {
	
	@Id
	@Column(name="questionId")
	private int questionId;
	
	@Column(name="question")
	private String question;
	
	@Column(name="type")
	private String type;
	
	@Column(name="optionA")
	private String optionaA;
	
	@Column(name="optionB")
	private String optionaB;
	
	@Column(name="optionC")
	private String optionaC;
	
	@Column(name="optionD")
	private String optionaD;
	
	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", question=" + question + ", type=" + type + ", optionaA="
				+ optionaA + ", optionaB=" + optionaB + ", optionaC=" + optionaC + ", optionaD=" + optionaD
				+ ", answer=" + answer + ", standard=" + standard + ", category=" + category + "]";
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOptionaA() {
		return optionaA;
	}

	public void setOptionaA(String optionaA) {
		this.optionaA = optionaA;
	}

	public String getOptionaB() {
		return optionaB;
	}

	public void setOptionaB(String optionaB) {
		this.optionaB = optionaB;
	}

	public String getOptionaC() {
		return optionaC;
	}

	public void setOptionaC(String optionaC) {
		this.optionaC = optionaC;
	}

	public String getOptionaD() {
		return optionaD;
	}

	public void setOptionaD(String optionaD) {
		this.optionaD = optionaD;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Standard getStandard() {
		return standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name="answer")
	private String answer;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="standardId")
	@NotNull
	private Standard standard;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="categoryId")
	@NotNull
	private Category category;
	
}
