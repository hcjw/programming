package com.example.programming.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.programming.entity.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
	// ランダムに一件取得
	@Query("SELECT id FROM question ORDER BY RANDOM() limit 1")
	Integer getRandomId();

	// 指定のuseridをキーに全て取得
	@Query("SELECT * FROM question WHERE userid = :userid")
	Iterable<Question> getAllByUserid(@Param("userid") Integer userid);
}
