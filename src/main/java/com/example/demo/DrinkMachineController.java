package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DrinkMachineController {
	
	private final DrinkMachineService drinkMachineService;
	
	public DrinkMachineController(DrinkMachineService drinkMachineService) {
		this.drinkMachineService = drinkMachineService;
	}
	
	@RequestMapping("/")
	public String index(DrinkMachineForm drinkMachineForm) {
		return "entry";
	}

	@PostMapping("/insert")
    public String insert(Model model,
    		@Validated DrinkMachineForm drinkMachineForm,
    		BindingResult result) {
		
        if (result.hasErrors()) {
			return "entry";
		}
		
		Item item = new Item();
		item.setName(drinkMachineForm.getName());
		item.setCount(drinkMachineForm.getCount());
		item.setUnitPrice(drinkMachineForm.getUnitPrice());
		item.setIsPr(drinkMachineForm.getIsPr());
		item.setRecordDate(LocalDateTime.now());
		
		drinkMachineService.insert(item);
		
		List<Item> list = drinkMachineService.getAll();
		model.addAttribute("resultList", list);
		
		return "insert";
    }
	
	@GetMapping("/read")
	public String read(Model model) {
		List<Item> list = drinkMachineService.getAll();

		model.addAttribute("resultList", list);

		return "read";
	}

	/** 
	 * name = "code"に紐づく値を引数codeに設定している
	 */
	@PostMapping("/delete")
    public String delete(Model model, 
    		             @RequestParam(name = "code") int code) {

		drinkMachineService.delete(code);
		
		List<Item> list = drinkMachineService.getAll();
		model.addAttribute("resultList", list);
		
		return "read";
    }
	
	/** 
	 * URLマッピングで指定するURLに「{」と「}」で囲まれた部分がパラメータ名になり、
	 * @PathVariableアノテーションのvalue属性にパラメータ名を指定することで
	 * URLの部分文字列を取得することができる
	 */
	@GetMapping("/edit/{id:.+}")
	String edit(@PathVariable("id") int code,
			    Model model) {
		
        System.out.println("userId = " + code);
		
		Item item = drinkMachineService.selectByCode(code);
		
    	// 画面返却ようのFormに値を設定する
    	DrinkMachineForm drinkMachineForm = new DrinkMachineForm();
    	drinkMachineForm.setCode(item.getCode());
    	drinkMachineForm.setName(item.getName());
    	drinkMachineForm.setUnitPrice(item.getUnitPrice());
    	drinkMachineForm.setCount(item.getCount());
    	drinkMachineForm.setIsPr(item.getIsPr());
    	
		model.addAttribute("drinkMachineForm", drinkMachineForm);
		
		return "edit";
	}
	
	@PostMapping("edit")
	public String update(@ModelAttribute @Validated DrinkMachineForm drinkMachineForm,
			             BindingResult result,
			             Model model) {
		
		// バリデーションエラーの場合
		if (result.hasErrors()) {
			// 編集画面に遷移
			return "edit";
		}
		
		drinkMachineService.update(drinkMachineForm);
		
		// 一覧画面にリダイレクト
		return "redirect:/read";
	}
}