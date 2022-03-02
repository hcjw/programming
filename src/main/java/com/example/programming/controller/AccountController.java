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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.programming.entity.Account;
import com.example.programming.entity.Question;
import com.example.programming.form.AccountForm;
import com.example.programming.service.AccountService;
import com.example.programming.service.QuestionService;

@Controller
public class AccountController {
	@Autowired
	AccountService accountService;
	@Autowired
	QuestionService questionService;

	// ログイン画面表示
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	// メインの画面表示
	@PostMapping("/questionToLogin")
	public String postQuestion(Model model, @RequestParam String name, String pass,
			RedirectAttributes redirectAttributes) {

		// 入力パスワードと一致するレコードを取得
		Optional<Account> accountOpt = accountService.findByPass(pass);
		// 一致するレコードがない場合
		if (accountOpt.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMsg", "ログインできませんでした!");
			redirectAttributes.addFlashAttribute("errorMsgIs", "一致するアカウントが見つかりません");
			return "redirect:/login";
		}

		// データベースの個々の値を取得
		Account accountRecord = accountOpt.get();
		Integer dbUserId = accountRecord.getId();
		String dbName = accountRecord.getName();
		String dbPass = accountRecord.getPass();

		// ログイン成功(ユーザー名とパスワード一致した場合)
		if (dbName.equals(name) && dbPass.equals(pass)) {

			// ログインユーザーの識別idをmodelに保存
			model.addAttribute("dbUserId", dbUserId);
			model.addAttribute("selectMsg", "左の欄から問題を選択して下さい。");

			// 問題の一覧取得
			Iterable<Question> questions = questionService.findAll();

			model.addAttribute("questions", questions);

			return "question";

		} else {
			redirectAttributes.addFlashAttribute("errorMsg", "ログインできませんでした!");
			redirectAttributes.addFlashAttribute("errorMsgIs", "一致するアカウントが見つかりません");
		}
		return "redirect:/login";
	}

	// 新規登録の画面表示
	@GetMapping("/create")
	public String getCreate() {
		return "create";
	}

	// 新規登録の結果画面表示
	@PostMapping("/createResult")
	public String postCreate(Model model, @RequestParam String name, String pass, String mail,
			@Validated AccountForm accountForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		// バリデーションエラーなし、重複のパスワードなし
		if (!bindingResult.hasErrors() && !accountService.isByPass(pass)) {
			// modelに追加
			model.addAttribute("name", name);
			model.addAttribute("pass", pass);
			model.addAttribute("mail", mail);
			// データベースに保存
			AccountForm accountFormInsert = new AccountForm(null, name, pass, mail);
			Account account = new Account(null, accountFormInsert.getName(), accountFormInsert.getPass(),
					accountFormInsert.getMail());
			accountService.insert(account);

			return "/createResult";
		}

		// 重複パスワードが存在する場合
		if (accountService.isByPass(pass)) {
			redirectAttributes.addFlashAttribute("errorOverLapping", "入力されたパスワードは既に使用されています！");
		}

		// バリデーション（入力値の正規表現エラー）
		if (bindingResult.hasErrors()) {
			// エラー処理
			List<String> errorList = new ArrayList<>();

			for (ObjectError error : bindingResult.getFieldErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			redirectAttributes.addFlashAttribute("errorList", errorList);
		}
		return "redirect:/create";
	}
}
