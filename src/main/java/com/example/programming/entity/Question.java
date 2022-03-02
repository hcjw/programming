package com.example.programming.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
	//識別ID
	@Id
	private Integer id;
	
	private Integer userid;
	
	private String title;
	
	private String question;
	
	private String answer;
}
