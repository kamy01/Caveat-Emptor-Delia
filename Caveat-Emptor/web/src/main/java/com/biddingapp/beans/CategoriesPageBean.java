package com.biddingapp.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import model.CategoryDto;
import services.category.CategoryService;
import utils.constants.EnumCategories;
import utils.exceptions.CategoryException;

@ManagedBean(name = "categoriesPageBean")
@SessionScoped
public class CategoriesPageBean {

	private String searchValue;
	private List<CategoryDto> searchResults;
	private CategoryDto category;
	@EJB
	CategoryService service;

	@ManagedProperty(value = "#{treeBasicView}")
	private CategoriesTreeBean treeBean;

	@ManagedProperty(value = "#{breadCrumb}")
	private BreadCrumbBean breadCrumb;

	@PostConstruct
	public void init() {

		category = new CategoryDto();
		treeBean.init();
	}

	public void save() {

		FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_WARN,
				EnumCategories.CATEGRY_ERROR_TITLE.getConstant(), EnumCategories.ERROR_CATEGORY_INSERT.getConstant()),
				success = new FacesMessage(FacesMessage.SEVERITY_INFO, EnumCategories.CATEGORY_ADDED.getConstant(),
						category.getName());

		try {

			try {
				category.getId();
				service.updateCategory(category);
				FacesContext.getCurrentInstance().addMessage(null, success);
			} catch (NullPointerException e) {

				try {
					CategoryDto parent = (CategoryDto) treeBean.getSelectedNode().getData();
					category.setParentId(parent.getId());
				} catch (NullPointerException n) {
					category.setParentId(0L);
				}

				service.insertNewCategory(category);
			}

		} catch (CategoryException e) {
			FacesContext.getCurrentInstance().addMessage(null, error);

		}

		treeBean.init();
		expandNode(category);
	}

	public void remove() {

		CategoryDto category = (CategoryDto) treeBean.getSelectedNode().getData();
		try {
			service.removeCategory(category);
		} catch (CategoryException e) {
			FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_WARN,
					EnumCategories.CATEGRY_ERROR_TITLE.getConstant(),
					EnumCategories.ERROR_CATEGORY_REMOVE.getConstant());
			FacesContext.getCurrentInstance().addMessage(null, error);
		}
		treeBean.init();
	}

	public void searchForCategory(AjaxBehaviorEvent e) {

		if (!searchValue.isEmpty()) {

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg1').show();");

			searchForNode();
			searchValue = null;
		}
	}

	private void searchForNode() {

		searchResults = new ArrayList<>();

		List<CategoryDto> categories = treeBean.getCategories();

		for (CategoryDto category : categories) {

			if (category.getName().toLowerCase().contains(searchValue.toLowerCase())) {

				searchResults.add(category);
			}
		}
	}

	public void onNodeSelect(NodeSelectEvent event) {

		if (isNodeSelected(treeBean)) {

			category = (CategoryDto) treeBean.getSelectedNode().getData();

		}

		updateBreadCrumbForSelectedCategory(category);

	}

	private void updateBreadCrumbForSelectedCategory(CategoryDto category) {

		List<String> path = getPath(category);
		breadCrumb.updateBreadCrumb(path);
	}

	private void expandParent(TreeNode child) {

		if (child.getParent() != null) {
			child.getParent().setExpanded(true);
			expandParent(child.getParent());
		}
	}

	private void expandNode(CategoryDto category) {
		List<TreeNode> nodes = treeBean.getAllChildren(treeBean.getRoot());

		for (TreeNode node : nodes) {
			if (((CategoryDto) node.getData()).getName().toLowerCase().equals(category.getName().toLowerCase())) {

				unselectAllNodes();
				selectNode(node);
				break;
			}
		}
	}

	private void unselectAllNodes() {

		List<TreeNode> allNodes = treeBean.getAllChildren(treeBean.getRoot());
		for(TreeNode node: allNodes)
		{
			if(node.isSelected())
			{
				node.setSelected(false);
			}
		}
		
	}

	private void selectNode(TreeNode node) {
		treeBean.setSelectedNode(node);
		updateBreadCrumbForSelectedCategory((CategoryDto) node.getData());
		expandParent(node);
		node.setSelected(true);
		node.setExpanded(false);
	}

	private boolean isNodeSelected(CategoriesTreeBean tree) {

		return tree.getSelectedNode() != null ? true : false;
	}

	private List<String> getPath(CategoryDto category) {

		List<String> path = new ArrayList<String>();

		path.add(category.getName());
		if (category.getParentId() != null) {
			CategoryDto parent = null;

			try {
				parent = service.findCategoryById(category.getParentId());

			} catch (CategoryException e) {
				e.printStackTrace();
			}

			if (parent != null) {
				path.addAll(getPath(parent));
			}
		}

		return path;

	}

	public void expandAction(CategoryDto category) {
		expandNode(category);
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onNodeExpand(NodeExpandEvent event) {
		event.getTreeNode().toString();
	}

	public CategoriesTreeBean getTreeBean() {
		return treeBean;
	}

	public void setTreeBean(CategoriesTreeBean treeBean) {
		this.treeBean = treeBean;
	}

	public void createNew() {

	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public void reset() {

		category = new CategoryDto();
	}

	public BreadCrumbBean getBreadCrumb() {
		return breadCrumb;
	}

	public void setBreadCrumb(BreadCrumbBean breadCrumb) {
		this.breadCrumb = breadCrumb;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public List<CategoryDto> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<CategoryDto> searchResults) {
		this.searchResults = searchResults;
	}

}
