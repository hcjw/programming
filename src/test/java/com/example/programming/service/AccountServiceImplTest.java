package com.example.programming.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.programming.entity.Account;
import com.example.programming.repository.AccountRepository;

@SpringBootTest
class AccountServiceImplTest {

	@Autowired
	AccountRepository repository;

	@Test
	void testFindById() {
		System.out.println("--- idをキーにアカウントの取得開始 ---");

		Optional<Account> questionOpt = repository.findById(15);
		Account account1 = questionOpt.get();

		System.out.println(account1);

		System.out.println("--- アカウントの取得完了 ---");
	}

	@Test
	void testFindByPass() {
		System.out.println("--- passをキーにアカウントの取得開始 ---");

		Optional<Account> questionOpt = repository.findByPass("12345678");
		Account account1 = questionOpt.get();

		System.out.println(account1);

		System.out.println("--- アカウントの取得完了 ---");
	}

//	@Test
//	void testInsert() {
//		System.out.println("--- 新規アカウントの保存開始 ---");
//
//		Account account = new Account(null, "iost", "テスト", "テスト");
//		Account account1 = repository.save(account);
//
//		System.out.println(account1);
//
//		System.out.println("--- 新規アカウントの保存完了 ---");
//	}

//	@Test
//	void testUpdate() {
//		System.out.println("--- アカウントの更新開始 ---");
//
//		Account account = new Account(24, "algo", "サンプル", "サンプル");
//		Account account1 = repository.save(account);
//
//		System.out.println(account1);
//
//		System.out.println("--- アカウントの更新完了 ---");
//	}

//	@Test
//	void testDeleteById() {
//		System.out.println("--- アカウントの削除開始 ---");
//		
//		repository.deleteById(24);
//		
//		System.out.println("--- アカウントの削除完了 ---");
//	}

	@Test
	void testIsByPass() {
		System.out.println("--- passの重複チェック ---");

		boolean isByPass = repository.existsByPass("12345678");
		System.out.println(isByPass);
	}

}
