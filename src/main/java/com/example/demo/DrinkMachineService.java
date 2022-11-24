package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DrinkMachineService {

	private final DrinkMachineDao dao;
	
	public DrinkMachineService(DrinkMachineDao dao) {
		this.dao = dao;
	}   
	
    public List<Item> getAll(){
    	return dao.getAll();
    }
    
    public void insert(Item item) {
    	dao.insertItem(item);
    }
    
    public void delete(int code) {
    	dao.deleteItem(code);
    }
    
    public Item selectByCode(int code) {
    		
    	// idを指定して本の情報を取得する
    	Item item = dao.selectByCode(code);
    		
    	return item;
    }
    
    public void update(DrinkMachineForm form) {
    	
    	Item item = new Item();
    	
    	item.setCode(form.getCode());
    	item.setName(form.getName());
    	item.setUnitPrice(form.getUnitPrice());
    	item.setCount(form.getCount());
    	item.setIsPr(form.getIsPr());
    	
    	dao.update(item);
    }
	
}