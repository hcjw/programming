package com.example.programming.service;

import java.util.Optional;

import com.example.programming.entity.Account;

public interface AccountService {
	
	// idをキーに一件取得
	Optional<Account> findById(Integer id);

	// passをキーに一件取得
	Optional<Account> findByPass(String pass);

	// passの重複チェック
	boolean isByPass(String pass);

	// 登録
	void insert(Account account);

	// 更新
	void update(Account account);

	// 削除
	void deleteById(Integer id);
}
