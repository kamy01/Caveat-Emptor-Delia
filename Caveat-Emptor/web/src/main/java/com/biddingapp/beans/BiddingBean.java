package com.biddingapp.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import model.BidDto;
import model.CategoryDto;
import model.ItemDto;
import services.bidding.BidService;
import services.item.ItemService;
import utils.exceptions.BidException;
import utils.exceptions.ItemException;

@ManagedBean(name = "biddingBean")
@ViewScoped
public class BiddingBean implements Serializable {

	private static final long serialVersionUID = 4382587578494080638L;

	@EJB
	BidService service;

	@EJB
	ItemService itemService;

	@ManagedProperty(value = "#{userLogin}")
	private UserLoginBean login;

	@ManagedProperty(value = "#{treeBasicView}")
	private CategoriesTreeBean treeBean;

	@ManagedProperty(value = "#{itemBean}")
	private ItemBean itemBean;

	private CategoryDto selectedCategory;
	private List<ItemDto> selectedItems;
	private BidDto currentBid;
	private ItemDto selectedItem;
	private boolean newBid;

	private Long selectedMaxBid;
	private int selectedBidsNo;

	@PostConstruct
	private void init() {

		selectedCategory = new CategoryDto();
		selectedItems = new ArrayList<ItemDto>();

		selectedItem = new ItemDto();
		selectedItem.setOpeningDate(new Date());
		selectedItem.setClosingDate(new Date());

		currentBid = new BidDto();

		treeBean.init();
	}

	public void choseItem(ItemDto item) {

		selectedItem = item;
		selectedBidsNo = itemBean.getNumberOfBids().get(item.getId());
		selectedMaxBid = itemBean.getMaxBids().get(item.getId());

		try {
			currentBid = service.getBidForUser(item, login.getUser());
			newBid = false;
		} catch (BidException e) {
			currentBid = new BidDto();
			newBid = true;
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Selected", item.getName()));

	}

	public void onNodeSelect(NodeSelectEvent event) {

		if (isNodeSelected(treeBean)) {

			TreeNode selectedNode = event.getTreeNode();
			selectedCategory = (CategoryDto) selectedNode.getData();

			List<CategoryDto> categories = treeBean.getChildrenForCategory(selectedNode);
			categories.add(selectedCategory);

			try {
				selectedItems = itemService.getItemsForCategories(getIdList(categories));
			} catch (ItemException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void addBid() {

		currentBid.setUser(login.getUser());
		currentBid.setItem(selectedItem);
		try {
			if (newBid) {
				service.addBid(currentBid);
			} else {
				service.editBid(currentBid);
			}
		} catch (BidException e) {
			System.out.println(e.getMessage());
		}

		itemBean.init();
		init();
	}

	public void removeBid() {
		try {
			service.removeBid(currentBid);
		} catch (BidException e) {
			System.out.println(e.getMessage());
		}

		itemBean.init();
		init();
	}

	private List<Long> getIdList(List<CategoryDto> childrenList) {

		List<Long> ids = new ArrayList<Long>();
		for (CategoryDto category : childrenList) {
			ids.add(category.getId());
		}

		return ids;

	}

	private boolean isNodeSelected(CategoriesTreeBean tree) {

		return tree.getSelectedNode() != null ? true : false;
	}

	public BidService getService() {
		return service;
	}

	public void setService(BidService service) {
		this.service = service;
	}

	public UserLoginBean getLogin() {
		return login;
	}

	public void setLogin(UserLoginBean login) {
		this.login = login;
	}

	public CategoriesTreeBean getTreeBean() {
		return treeBean;
	}

	public void setTreeBean(CategoriesTreeBean treeBean) {
		this.treeBean = treeBean;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public CategoryDto getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(CategoryDto selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<ItemDto> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<ItemDto> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public ItemDto getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ItemDto selectedItem) {
		this.selectedItem = selectedItem;
	}

	public Long getSelectedMaxBid() {
		return selectedMaxBid;
	}

	public void setSelectedMaxBid(Long selectedMaxBid) {
		this.selectedMaxBid = selectedMaxBid;
	}

	public int getSelectedBidsNo() {
		return selectedBidsNo;
	}

	public void setSelectedBidsNo(int selectedBidsNo) {
		this.selectedBidsNo = selectedBidsNo;
	}

	public ItemBean getItemBean() {
		return itemBean;
	}

	public void setItemBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}

	public BidDto getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(BidDto currentBid) {
		this.currentBid = currentBid;
	}

	public boolean isNewBid() {
		return newBid;
	}

	public void setNewBid(boolean newBid) {
		this.newBid = newBid;
	}

}
