package com.example.programming.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.programming.entity.Account;
import com.example.programming.repository.AccountRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountRepository repository;

	// idをキーに一件取得
	@Override
	public Optional<Account> findById(Integer id) {
		return repository.findById(id);
	}

	// passをキーに一件取得
	@Override
	public Optional<Account> findByPass(String pass) {
		return repository.findByPass(pass);
	}

	// 保存
	@Override
	public void insert(Account account) {
		repository.save(account);
	}

	// 更新
	@Override
	public void update(Account account) {
		repository.save(account);
	}

	// 削除
	@Override
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	// passの重複チェック
	@Override
	public boolean isByPass(String pass) {
		return repository.existsByPass(pass);
	}
}
