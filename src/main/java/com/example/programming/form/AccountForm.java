package com.example.programming.form;

import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountForm {
	//識別ID
	@Id
	private Integer id;
	//ユーザーネーム（英数字１〜１０文字）
	@Pattern(regexp = "^[0-9a-zA-Z]{1,10}$", message = "ユーザー名は半角英数字1~10文字で入力してください！")
	private String name;
	//パスワード（英数字、記号８〜２０文字）
	@Pattern(regexp = "^[0-9a-zA-Z!-/:-@\\[-`{-~]{8,20}$", message = "パスワードは半角英数字記号8~20文字入力してください！")
	private String pass;
	//メールアドレス（英数字、記号１〜256文字）
	@Pattern(regexp = "^[0-9a-zA-Z!-/:-@\\[-`{-~]{1,256}$", message = "メールアドレスは半角英数字記号1~256文字で入力してください！")
	private String mail;
}
