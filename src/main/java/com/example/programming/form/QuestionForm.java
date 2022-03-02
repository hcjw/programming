package com.example.programming.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionForm {
	// 識別ID
	@Id
	private Integer id;

	private Integer userid;

	@Length(min=1, max=10, message = "タイトルは1~10文字以内で入力して下さい。")
	private String title;

	@Length(min=1, max=100, message = "問題文は1~100文字以内で入力して下さい。")
	private String question;

	@Length(min=1, max=30, message = "解答は1~30文字以内で入力して下さい。")
	private String answer;
}
