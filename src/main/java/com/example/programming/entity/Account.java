package com.example.programming.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	//識別ID
	@Id
	private Integer id;
	
	private String name;
	
	private String pass;
	
	private String mail;
}
