package com.example.programming.service;

import java.util.Optional;

import com.example.programming.entity.Question;

public interface QuestionService {
	// 全件取得
	Iterable<Question> findAll();

	// 指定のuseridカラムをキーに全て取得
	Iterable<Question> findAllByUserid(Integer userid);

	// idをキーに一件取得
	Optional<Question> findById(Integer id);

	// ランダムにidを一件取得
	Optional<Question> findByIdRandom();

	// 正解/不正解を判定
	Boolean checkAnswer(Integer id, String answer);

	// 登録
	void insert(Question question);

	// 更新
	void update(Question question);

	// 削除
	void deleteById(Integer id);

}
