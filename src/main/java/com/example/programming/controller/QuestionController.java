package com.example.programming.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.programming.entity.Account;
import com.example.programming.entity.Question;
import com.example.programming.form.AccountForm;
import com.example.programming.form.QuestionForm;
import com.example.programming.service.AccountService;
import com.example.programming.service.QuestionService;

@Controller
public class QuestionController {
	@Autowired
	AccountService accountService;
	@Autowired
	QuestionService questionService;

	// メイン画面表示
	@PostMapping("/question")
	public String postQuestionToQuestionResult(Model model, @RequestParam Integer dbUserId) {

		// ログインユーザーの識別idをmodelに保存
		model.addAttribute("dbUserId", dbUserId);

		// 問題の一覧取得
		Iterable<Question> questions = questionService.findAll();

		model.addAttribute("questions", questions);
		model.addAttribute("selectMsg", "左の欄から問題を選択して下さい。");

		return "question";
	}

	// 解答結果画面を表示
	@PostMapping("/questionResult")
	public String postQuestionToQuestion(Model model, @RequestParam String answer, String dbAnswer, Integer dbUserId) {

		// ログインユーザーの識別idをmodelに保存
		model.addAttribute("dbUserId", dbUserId);

		// 問題の一覧取得
		Iterable<Question> questions = questionService.findAll();
		model.addAttribute("questions", questions);

		model.addAttribute("selectMsg", "左の欄から問題を選択して下さい。");

		// 解答の入力値がない場合、メイン画面に戻る
		if (answer.equals("")) {

			model.addAttribute("answerErrorMsg", "解答を入力して下さい。");

			return "question";
		}

		// 解答が合っている場合
		if (answer.equals(dbAnswer)) {
			model.addAttribute("judgeMsg", "おめでとう！正解です！");
		} else {
			model.addAttribute("judgeMsg", "残念、不正解です");
		}
		return "questionResult";
	}

	// アカウント画面表示
	@PostMapping("/account")
	public String postAccount(Model model, @RequestParam Integer dbUserId) {

		// ユーザー情報を取得
		Optional<Account> accountOpt = accountService.findById(dbUserId);
		// アカウントがなければログイン画面へ戻る
		if (accountOpt.isEmpty()) {
			return "login";
		} else {
			Account accountRecord = accountOpt.get();
			String dbName = accountRecord.getName();
			String dbPass = accountRecord.getPass();
			String dbMail = accountRecord.getMail();

			model.addAttribute("dbUserId", dbUserId);
			model.addAttribute("dbName", dbName);
			model.addAttribute("dbPass", dbPass);
			model.addAttribute("dbMail", dbMail);
		}

		// ユーザーの問題情報を取得
		Iterable<Question> myQuestions = questionService.findAllByUserid(dbUserId);
		model.addAttribute("myQuestions", myQuestions);

		return "account";
	}

	// アカウントの更新画面表示
	@PostMapping("/editAccount")
	public String postEditAccount(Model model, @RequestParam Integer dbUserId) {

		Optional<Account> accountOpt = accountService.findById(dbUserId);
		// アカウントがなければログイン画面へ戻る
		if (accountOpt.isEmpty()) {
			return "login";
		} else {
			Account accountRecord = accountOpt.get();
			String dbName = accountRecord.getName();
			String dbPass = accountRecord.getPass();
			String dbMail = accountRecord.getMail();

			model.addAttribute("dbUserId", dbUserId);
			model.addAttribute("dbName", dbName);
			model.addAttribute("dbPass", dbPass);
			model.addAttribute("dbMail", dbMail);
		}
		return "editAccount";
	}

	// アカウントの削除画面表示
	@PostMapping("/delete")
	public String postDelete(Model model, @RequestParam Integer dbUserId) {

		Optional<Account> accountOpt = accountService.findById(dbUserId);
		// アカウントがなければログイン画面へ戻る
		if (accountOpt.isEmpty()) {
			return "login";
		} else {
			Account accountRecord = accountOpt.get();
			String dbName = accountRecord.getName();
			String dbPass = accountRecord.getPass();
			String dbMail = accountRecord.getMail();

			model.addAttribute("dbUserId", dbUserId);
			model.addAttribute("dbName", dbName);
			model.addAttribute("dbPass", dbPass);
			model.addAttribute("dbMail", dbMail);

			return "delete";
		}
	}

	// アカウントの更新処理
	@PostMapping("/accountUpdate")
	public String postAccountUpdate(Model model, @RequestParam String name, String pass, String mail, Integer dbUserId,
			@Validated AccountForm accountForm, BindingResult bindingResult) {

		// ユーザー情報取得
		Optional<Account> accountOpt = accountService.findById(dbUserId);
		// アカウントがなければログイン画面へ戻る
		if (accountOpt.isEmpty()) {
			return "login";
		}
		// entityへ追加
		Account accountRecord = accountOpt.get();
		String dbName = accountRecord.getName();
		String dbPass = accountRecord.getPass();
		String dbMail = accountRecord.getMail();

		// バリデーションエラーなし、重複のパスワードなし、もしくはデータベースのパスワードと同じ場合
		if (!bindingResult.hasErrors() && (!accountService.isByPass(pass) || dbPass.equals(pass))) {

			Account account = new Account(dbUserId, name, pass, mail);
			// データベース更新
			accountService.update(account);

			// ユーザーの問題情報を取得
			Iterable<Question> myQuestions = questionService.findAllByUserid(dbUserId);
			model.addAttribute("myQuestions", myQuestions);

			// 更新したユーザーのアカウント情報を保存
			model.addAttribute("dbUserId", dbUserId);
			model.addAttribute("dbName", name);
			model.addAttribute("dbPass", pass);
			model.addAttribute("dbMail", mail);

			model.addAttribute("updateCompleteMsg", "アカウントの更新が完了しました");

			return "account";
		}

		// ユーザー自身のパスワード以外で重複がある場合
		if (!dbPass.equals(pass) && accountService.isByPass(pass)) {
			model.addAttribute("errorOverLapping", "入力されたパスワードは既に使用されています！");
		}

		// バリデーション（入力値の正規表現エラー）
		if (bindingResult.hasErrors()) {
			// エラー処理
			List<String> errorList = new ArrayList<>();

			for (ObjectError error : bindingResult.getFieldErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("errorList", errorList);
		}

		// 既存のアカウント情報を保存
		model.addAttribute("dbUserId", dbUserId);
		model.addAttribute("dbName", dbName);
		model.addAttribute("dbPass", dbPass);
		model.addAttribute("dbMail", dbMail);

		// エラーの場合、編集画面に戻る
		return "editAccount";
	}

	// アカウントの削除処理
	@PostMapping("/accountDelete")
	public String postAccountDelete(@RequestParam Integer dbUserId) {

		// データベースからアカウントのレコードを削除
		accountService.deleteById(dbUserId);

		// データベースからユーザーの全問題のレコードを削除
		// useridで全問題を取得
		Iterable<Question> questions = questionService.findAllByUserid(dbUserId);
		List<Integer> qList = new ArrayList<>();
		// Listに識別idを追加
		for (Question q : questions) {
			qList.add(q.getId());
		}
		// 識別idをキーに全レコードを削除
		for (Integer qId : qList) {
			questionService.deleteById(qId);
		}

		return "login";
	}

	// 問題の新規作成処理
	@PostMapping("/questionCreate")
	public String postQuestionCreate(Model model, @RequestParam String title, String question, String answer,
			Integer dbUserId, @Validated QuestionForm questionForm, BindingResult bindingResult) {

		// バリデーション（入力値のエラー）
		if (bindingResult.hasErrors()) {
			// バリデーションエラーの表示
			List<String> errorList = new ArrayList<>();

			for (ObjectError error : bindingResult.getFieldErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("errorListCreate", errorList);

		} else { // 新規登録処理
			Question questionEntity = new Question(null, dbUserId, title, question, answer);
			// データベースに問題を新規追加
			questionService.insert(questionEntity);

			model.addAttribute("createMsg", "問題を追加しました。");
		}

		// ユーザーの問題情報を取得
		Iterable<Question> myQuestions = questionService.findAllByUserid(dbUserId);
		model.addAttribute("myQuestions", myQuestions);

		// ユーザー情報を取得
		Optional<Account> accountOpt = accountService.findById(dbUserId);
		// アカウントがなければログイン画面へ戻る
		if (accountOpt.isEmpty()) {
			return "login";
		} else {
			Account accountRecord = accountOpt.get();
			String dbName = accountRecord.getName();
			String dbPass = accountRecord.getPass();
			String dbMail = accountRecord.getMail();

			model.addAttribute("dbUserId", dbUserId);
			model.addAttribute("dbName", dbName);
			model.addAttribute("dbPass", dbPass);
			model.addAttribute("dbMail", dbMail);
		}

		return "account";
	}

	// 問題の更新処理
	@PostMapping("/questionUpdate")
	public String postQuestionUpdate(Model model, @RequestParam String title, String question, String answer,
			Integer id, Integer dbUserId, @Validated QuestionForm questionForm, BindingResult bindingResult) {

		// hiddenで受け取ったquestionデータベースの識別idに値がある場合(削除する問題を選択した状態)更新処理
		if (id != null) {
			// バリデーションエラーがない場合
			if (!bindingResult.hasErrors()) {

				Question questionEntity = new Question(id, dbUserId, title, question, answer);
				// データベースに問題を更新
				questionService.update(questionEntity);

				model.addAttribute("updateMsg", "問題を更新しました。");
			}
		}

		// バリデーション（入力値のエラー）
		if (bindingResult.hasErrors()) {

			if (id == null) { // 問題の選択していない場合

				model.addAttribute("errorMsg", "左の欄から更新する問題を選択してください。");

			} else {
				// バリデーションエラーを表示
				List<String> errorList = new ArrayList<>();

				for (ObjectError error : bindingResult.getFieldErrors()) {
					errorList.add(error.getDefaultMessage());
				}
				model.addAttribute("errorListUpdate", errorList);
			}
		}

		// ユーザーの問題情報を取得
		Iterable<Question> myQuestions = questionService.findAllByUserid(dbUserId);

		model.addAttribute("myQuestions", myQuestions);

		// ユーザー情報を取得
		Optional<Account> accountOpt = accountService.findById(dbUserId);
		// アカウントがなければログイン画面へ戻る
		if (accountOpt.isEmpty()) {
			return "login";
		} else {
			Account accountRecord = accountOpt.get();
			String dbName = accountRecord.getName();
			String dbPass = accountRecord.getPass();
			String dbMail = accountRecord.getMail();

			model.addAttribute("dbUserId", dbUserId);
			model.addAttribute("dbName", dbName);
			model.addAttribute("dbPass", dbPass);
			model.addAttribute("dbMail", dbMail);
		}

		return "account";
	}

	// 問題の削除処理
	@PostMapping("/questionDelete")
	public String postQuestionDelete(Model model, @RequestParam String title, String question, String answer,
			Integer id, Integer dbUserId) {

		// hiddenで受け取ったquestionデータベースの識別idに値がある場合(削除する問題を選択した状態)削除処理
		if (id != null) {
			// データベースに問題を削除
			questionService.deleteById(id);

			model.addAttribute("deleteMsg", "問題を削除しました。");

		} else {

			model.addAttribute("errorMsg", "左の欄から削除する問題を選択してください。");

		}

		// ユーザーの問題情報を取得
		Iterable<Question> myQuestions = questionService.findAllByUserid(dbUserId);

		model.addAttribute("myQuestions", myQuestions);

		// ユーザー情報を取得
		Optional<Account> accountOpt = accountService.findById(dbUserId);
		// アカウントがなければログイン画面へ戻る
		if (accountOpt.isEmpty()) {
			return "login";
		} else {
			Account accountRecord = accountOpt.get();
			String dbName = accountRecord.getName();
			String dbPass = accountRecord.getPass();
			String dbMail = accountRecord.getMail();

			model.addAttribute("dbUserId", dbUserId);
			model.addAttribute("dbName", dbName);
			model.addAttribute("dbPass", dbPass);
			model.addAttribute("dbMail", dbMail);
		}

		return "account";
	}

	// ランダム処理
	@PostMapping("/questionRandom")
	public String postQuestionRandom(Model model, @RequestParam Integer dbUserId) {

		// 問題をランダムに一件取得
		Optional<Question> questionOpt = questionService.findByIdRandom();
		if (questionOpt.isEmpty()) {
			model.addAttribute("errorMsg", "問題が登録されていません");
		} else {
			// データベースの個々の値を取得
			Question questionRecord = questionOpt.get();
			String dbTitle = questionRecord.getTitle();
			String dbQuestion = questionRecord.getQuestion();
			String dbAnswer = questionRecord.getAnswer();
			// 問題の情報をmodelに保存
			model.addAttribute("dbTitle", dbTitle);
			model.addAttribute("dbQuestion", dbQuestion);
			model.addAttribute("dbAnswer", dbAnswer);
		}

		// ログインユーザーの識別idをmodelに保存
		model.addAttribute("dbUserId", dbUserId);

		// 問題の一覧取得
		Iterable<Question> questions = questionService.findAll();
		model.addAttribute("questions", questions);

		return "question";
	}

	// 検索処理
	@PostMapping("/questionSearch")
	public String postSerchQuestion(Model model, @RequestParam String search, Integer dbUserId) {
		List<Question> qList = new ArrayList<>();

		// 問題の一覧取得
		Iterable<Question> questions = questionService.findAll();
		// 入力値とデータベースのタイトルを比較し、入力値が含まれていた場合リストに追加
		for (Question question : questions) {
			if (question.getTitle().contains(search) || search.equals("")) {
				qList.add(question);
			}
		}
		// 検索結果がない場合はメイン画面に戻る
		int count = 0;

		for (Question question : qList) {
			if (question != null) {
				count++;
			}
		}
		if (count == 0) {
			// エラーメッセージ
			model.addAttribute("searchMsg", "一致する問題がありません。");
			// ログインユーザーの識別idをmodelに保存
			model.addAttribute("dbUserId", dbUserId);

		} else {
			// 検索結果をmodelに保存
			model.addAttribute("qList", qList);
			// ログインユーザーの識別idをmodelに保存
			model.addAttribute("dbUserId", dbUserId);
		}

		return "question";
	}
}
