package com.example.programming.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.programming.entity.Question;
import com.example.programming.repository.QuestionRepository;

@SpringBootTest
class QuestionServiceImplTest {
	@Autowired
	QuestionRepository repository;

	@Test
	void testFindAll() {
		System.out.println("--- 全件取得開始 ---");

		Iterable<Question> questions = repository.findAll();

		for (Question question : questions) {
			System.out.println(question);
		}

		System.out.println("--- 全件取得完了 ---");
	}

	@Test
	void testFindById() {
		System.out.println("--- idをキーに問題の取得開始 ---");

		Optional<Question> questionOpt = repository.findById(3);
		Question account1 = questionOpt.get();

		System.out.println(account1);

		System.out.println("--- 問題の取得完了 ---");
	}

//	@Test
//	void testInsert() {
//		System.out.println("--- 新規問題の保存開始 ---");
//
//		Question question = new Question(null, 1, "条件分岐1", "テスト", "テスト");
//		Question question1 = repository.save(question);
//
//		System.out.println(question1);
//
//		System.out.println("--- 新規問題の保存完了 ---");
//	}

//	@Test
//	void testUpdate() {
//		System.out.println("--- 問題の更新開始 ---");
//		
//		Question question = new Question(64, 1, "配列処理1", "サンプル", "サンプル");
//		Question question1 = repository.save(question);
//		
//		System.out.println(question1);
//		
//		System.out.println("--- 問題の更新完了 ---");
//	}

//	@Test
//	void testDeleteById() {
//		System.out.println("--- 問題の削除開始 ---");
//		
//		repository.deleteById(65);
//		
//		System.out.println("--- 問題の削除完了 ---");
//	}

	@Test
	void testFindByIdRandom() {
		System.out.println("--- ランダムに一件取得開始 ---");
		// ランダムでidの値を取得
		Integer randId = repository.getRandomId();

		// idからレコードを一件を取得
		Optional<Question> question = repository.findById(randId);
		System.out.println(question);

		System.out.println("--- ランダムに一件取得 ---");
	}

	@Test
	void testCheckAnswer() {
		System.out.println("--- 解答の判定開始 ---");

		Boolean check = false;
		// 対象の問題を取得
		Optional<Question> optQuestion = repository.findById(3);
		// 値の存在チェック
		if (optQuestion.isPresent()) {
			Question question = optQuestion.get();
			// クイズの解答チェック
			if (question.getAnswer().equals("int")) {
				check = true;
			}
		}
		System.out.println(check);
		assertTrue(check);

		System.out.println("--- 解答の判定完了 ---");
	}

	@Test
	void testFindAllByUserid() {
		System.out.println("--- 同一passの問題を全件取得開始 ---");

		Iterable<Question> questions = repository.getAllByUserid(15);

		for (Question question : questions) {
			System.out.println(question);
		}

		System.out.println("--- 同一passの問題を全件取得完了 ---");
	}

}
