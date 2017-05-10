package com.biddingapp.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "itemsDropdown")
@ViewScoped
public class ItemsDropdown implements Serializable {

	private static final long serialVersionUID = -5068240643017583300L;
	private String itemType;
	private List<String> itemTypes;

	@PostConstruct
	public void init() {
		itemTypes = new ArrayList<String>();
		itemTypes.add("buy");
		itemTypes.add("sell");
	}

	public void onItemTypeChange() {
		FacesMessage msg;
		if (itemType != null && !itemType.equals("")) {
			msg = new FacesMessage("Selected", itemType);
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected.");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public List<String> getItemTypes() {
		return itemTypes;
	}

	public void setItemTypes(List<String> itemTypes) {
		this.itemTypes = itemTypes;
	}
}
