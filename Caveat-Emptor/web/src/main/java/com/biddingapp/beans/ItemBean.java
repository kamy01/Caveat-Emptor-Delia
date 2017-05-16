package com.biddingapp.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.TreeNode;

import model.BidDto;
import model.CategoryDto;
import model.ItemDto;
import services.bidding.BidService;
import services.item.ItemService;
import utils.ItemStatusEnum;
import utils.exceptions.BidException;
import utils.exceptions.ItemException;

@ManagedBean(name = "itemBean")
@ViewScoped
public class ItemBean implements Serializable {

	private static final long serialVersionUID = 1257300281145215446L;

	@EJB
	ItemService service;
	@EJB
	BidService bidService;

	@ManagedProperty(value = "#{userLogin}")
	private UserLoginBean login;

	@ManagedProperty(value = "#{treeBasicView}")
	private CategoriesTreeBean treeBean;

	private static List<ItemDto> itemList;
	private static List<CategoryDto> categoriesList;
	private static Map<ItemDto, List<BidDto>> bidsMap;
	private static Map<ItemDto, Long> maxBids;
	private static Map<ItemDto, Integer> numberOfBids;
	private static List<BidDto> bidsList;
	private static List<String> statusList;
	private static ItemDto item;

	@PostConstruct
	public void init() {

		initCollections();
		treeBean.init();

		itemSetup();

		populateItemList();
		populateBidsList();
		populateBidsDetailsLists();
		populateCategoriesList();
		populateStatusList();
	}

	private void itemSetup() {
		item = new ItemDto();
		item.setOpeningDate(new Date());
		item.setClosingDate(new Date());
	}

	private void initCollections() {

		categoriesList = new ArrayList<CategoryDto>();
		bidsMap = new HashMap<ItemDto, List<BidDto>>();
		bidsList = new ArrayList<BidDto>();
		maxBids = new HashMap<ItemDto, Long>();
		numberOfBids = new HashMap<ItemDto, Integer>();

	}

	private void populateStatusList() {

		statusList = new ArrayList<String>();

		statusList.add(ItemStatusEnum.ABANDONED.getStatus());
		statusList.add(ItemStatusEnum.OPEN.getStatus());
		statusList.add(ItemStatusEnum.CLOSED.getStatus());
		statusList.add(ItemStatusEnum.NOT_YET_OPENED.getStatus());
	}

	private void populateBidsDetailsLists() {

		List<BidDto> bidList;

		for (ItemDto item : itemList) {
			bidList = getBidListForItem(item);
			maxBids.put(item, calculateMax(bidList));
			numberOfBids.put(item, bidList.size());
		}

	}

	public void onNodeSelect(NodeSelectEvent event) {

		if (isNodeSelected(treeBean)) {

			item.setCategory((CategoryDto) treeBean.getSelectedNode().getData());
		}

	}

	private boolean isNodeSelected(CategoriesTreeBean tree) {

		return tree.getSelectedNode() != null ? true : false;
	}

	private Long calculateMax(List<BidDto> bidList) {

		Long max = -1L;

		for (BidDto bid : bidList) {
			if (max < bid.getPrice()) {
				max = bid.getPrice();
			}
		}

		return max;
	}

	private void populateBidsList() {

		try {
			bidsList = bidService.getAllBids();
		} catch (BidException e) {
			e.printStackTrace();
		}

		List<BidDto> itemBids;

		for (ItemDto item : itemList) {
			itemBids = getBidListForItem(item);

			bidsMap.put(item, itemBids);
		}

	}

	public String addNewItem() {

		FacesMessage success = new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", item.toString()),
				error = new FacesMessage(FacesMessage.SEVERITY_WARN, "NOT OK ", "Please add all the info!");

		if (item.getName() != null && item.getDescription() != null && item.getInitialPrice() != null
				&& item.getCategory() != null) {
			item.setStatus(ItemStatusEnum.NOT_YET_OPENED.getStatus());
			item.setOwner(login.getUser());
			FacesContext.getCurrentInstance().addMessage(null, success);
			try {
				service.addNewItem(item);
						
				initCollections();
				populateItemList();
				populateBidsList();
				populateBidsDetailsLists();
				populateCategoriesList();
				populateStatusList();
				itemSetup();
				return "caveatEmptor.xhtml?faces-redirect=true";
				
			} catch (ItemException e) {
				FacesContext.getCurrentInstance().addMessage(null, error);
			}
		}
		return null;
	}

	private List<BidDto> getBidListForItem(ItemDto item) {

		List<BidDto> bidList = new ArrayList<BidDto>();

		for (BidDto bid : bidsList) {
			if (bid.getItem().getId() == item.getId()) {
				bidList.add(bid);
			}
		}
		return bidList;
	}

	private void populateCategoriesList() {

		List<TreeNode> nodes = treeBean.getAllChildren(treeBean.getRoot());
		for (TreeNode node : nodes) {
			categoriesList.add((CategoryDto) node.getData());
		}
	}

	private void populateItemList() {

		try {
			itemList = service.getAllItemsForUser(login.getUser());
		} catch (ItemException e) {
			e.printStackTrace();
		}

	}

	public void onRowEdit(RowEditEvent event) {

		FacesMessage msg;

		try {
			service.updateItem((ItemDto) event.getObject());
			msg = new FacesMessage("Item Edited", ((ItemDto) event.getObject()).getName());
		} catch (ItemException ie) {
			msg = new FacesMessage("Error when trying to edit", ((ItemDto) event.getObject()).getName());
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", ((ItemDto) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public static List<CategoryDto> getCategoriesList() {
		return categoriesList;
	}

	public static void setCategoriesList(List<CategoryDto> categoriesList) {
		ItemBean.categoriesList = categoriesList;
	}

	public CategoriesTreeBean getTreeBean() {
		return treeBean;
	}

	public void setTreeBean(CategoriesTreeBean treeBean) {
		this.treeBean = treeBean;
	}

	public static Map<ItemDto, List<BidDto>> getBidsMap() {
		return bidsMap;
	}

	public static void setBidsMap(Map<ItemDto, List<BidDto>> bidsMap) {
		ItemBean.bidsMap = bidsMap;
	}

	public static List<BidDto> getBidsList() {
		return bidsList;
	}

	public static void setBidsList(List<BidDto> bidsList) {
		ItemBean.bidsList = bidsList;
	}

	public static Map<ItemDto, Long> getMaxBids() {
		return maxBids;
	}

	public static void setMaxBids(Map<ItemDto, Long> maxBids) {
		ItemBean.maxBids = maxBids;
	}

	public static Map<ItemDto, Integer> getNumberOfBids() {
		return numberOfBids;
	}

	public static void setNumberOfBids(Map<ItemDto, Integer> numberOfBids) {
		ItemBean.numberOfBids = numberOfBids;
	}

	public static List<String> getStatusList() {
		return statusList;
	}

	public static void setStatusList(List<String> statusList) {
		ItemBean.statusList = statusList;
	}

	public static ItemDto getItem() {
		return item;
	}

	public static void setItem(ItemDto item) {
		ItemBean.item = item;
	}

}
