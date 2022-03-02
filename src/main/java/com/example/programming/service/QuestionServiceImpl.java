package com.example.programming.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.programming.entity.Question;
import com.example.programming.repository.QuestionRepository;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	QuestionRepository repository;
	//問題を全件取得
	@Override
	public Iterable<Question> findAll() {
		return repository.findAll();
	}
	//idをキーに問題を一件取得
	@Override
	public Optional<Question> findById(Integer id) {
		return repository.findById(id);
	}
	//問題を一件追加
	@Override
	public void insert(Question question) {
		repository.save(question);
	}
	//問題を一件更新
	@Override
	public void update(Question question) {
		repository.save(question);
	}
	//idをキーに問題を一件削除
	@Override
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
	//ランダムに問題を一件取得
	@Override
	public Optional<Question> findByIdRandom() {
		// ランダムでidの値を取得
		Integer randId = repository.getRandomId();
		// 問題があった場合
		if (randId == null) {
			// 空のOptionalインスタンスを返す
			return Optional.empty();
		}
		// idからレコードを一件を取得
		return repository.findById(randId);
	}
	//解答が正解かどうかの判定
	@Override
	public Boolean checkAnswer(Integer id, String answer) {
		Boolean check = false;
		// 対象の問題を取得
		Optional<Question> optQuestion = repository.findById(id);
		// 値の存在チェック
		if (optQuestion.isPresent()) {
			Question question = optQuestion.get();
			// クイズの解答チェック
			if (question.getAnswer().equals(answer)) {
				check = true;
			}
		}
		return check;
	}
	//useridの全ての問題を取得
	@Override
	public Iterable<Question> findAllByUserid(Integer userid) {
		return repository.getAllByUserid(userid);
	}
}
