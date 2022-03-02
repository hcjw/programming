package com.example.programming.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.programming.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {
	// passの重複チェック
	boolean existsByPass(String pass);

	// passと一致したレコードを一件取得
	Optional<Account> findByPass(String pass);
}
