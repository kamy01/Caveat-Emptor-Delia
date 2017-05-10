package com.biddingapp.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import model.ItemDto;
import services.item.ItemService;
import utils.exceptions.ItemException;

@ManagedBean(name = "itemBean")
@ViewScoped
public class ItemBean {

	@EJB
	ItemService service;

	@ManagedProperty(value = "#{userLogin}")
	private UserLoginBean login;

	private static List<ItemDto> itemList;

	@PostConstruct
	public void init() {
		populateItemList();
	}

	private void populateItemList() {

		try {
			itemList = service.getAllItems();
		} catch (ItemException e) {
			e.printStackTrace();
		}

	}

	public UserLoginBean getLogin() {
		return login;
	}

	public void setLogin(UserLoginBean login) {
		this.login = login;
	}

	public static List<ItemDto> getItemList() {
		return itemList;
	}

	public static void setItemList(List<ItemDto> itemList) {
		ItemBean.itemList = itemList;
	}

}
