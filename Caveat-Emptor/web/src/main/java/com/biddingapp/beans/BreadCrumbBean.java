package com.biddingapp.beans;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

@ManagedBean(name = "breadCrumb")
@SessionScoped
public class BreadCrumbBean {

	private MenuModel model;

	@PostConstruct
	public void init() {
		createMenu();	
	}
	
	
	private void createMenu() {
		
		model = new DefaultMenuModel();
		DefaultMenuItem item = new DefaultMenuItem("home");
		model.addElement(item);
		
	}
	
	void updateBreadCrumb(List<String> path)
	{
		model = new DefaultMenuModel();
		DefaultMenuItem item;
		
		Collections.reverse(path);
		
		for(String location: path)
		{
			item = new DefaultMenuItem(location);
			model.addElement(item);
		}
	}
	

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

}
