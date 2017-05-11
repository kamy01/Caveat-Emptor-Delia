package com.biddingapp.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.TreeNode;

import model.BidDto;
import model.CategoryDto;
import model.ItemDto;
import services.bidding.BidService;
import services.item.ItemService;
import utils.exceptions.BidException;
import utils.exceptions.ItemException;

@ManagedBean(name = "itemBean")
@ViewScoped
public class ItemBean {

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

	@PostConstruct
	public void init() {

		categoriesList = new ArrayList<CategoryDto>();
		bidsMap = new HashMap<ItemDto, List<BidDto>>();
		bidsList = new ArrayList<BidDto>();
		maxBids = new HashMap<ItemDto, Long>();
		numberOfBids = new HashMap<ItemDto, Integer>();
		
		treeBean.init();
		populateItemList();
		populateBidsList();
		populateBidsDetailsLists();
		populateCategoriesList();
	}


	private void populateBidsDetailsLists() {

		List<BidDto> bidList;
		
		for(ItemDto item: itemList)
		{
			bidList = getBidListForItem(item);
			maxBids.put(item, calculateMax(bidList));
			numberOfBids.put(item, bidList.size());
		}
		
	}

	private Long calculateMax(List<BidDto> bidList) {

		Long max = -1L;
		
		for(BidDto bid: bidList)
		{
			if (max < bid.getPrice())
			{
				max = bid.getPrice();
			}
		}
		
		return max;
	}

	private void populateBidsList() {

		try {
			bidsList = bidService.getAllBids();
		} catch (BidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<BidDto> itemBids;

		for (ItemDto item : itemList) {
			itemBids = getBidListForItem(item);

			bidsMap.put(item, itemBids);
		}

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

}
