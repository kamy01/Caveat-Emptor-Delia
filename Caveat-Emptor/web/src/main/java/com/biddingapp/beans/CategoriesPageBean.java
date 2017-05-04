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

import model.CategoryDto;
import services.category.CategoryService;
import utils.Constants;
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
	}

	public void save() {

		FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.LOGIN_ERROR,
				Constants.INVALID_CREDENTIALS),
				success = new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.WELCOME, category.getName());

		try {

			try {
				category.getId();
				service.updateCategory(category);
				FacesContext.getCurrentInstance().addMessage(null, success);
			} catch (NullPointerException e) {

				CategoryDto parent = (CategoryDto) treeBean.getSelectedNode().getData();

				category.setParentId(parent.getId());
				service.insertNewCategory(category);
			}

		} catch (CategoryException e) {
			FacesContext.getCurrentInstance().addMessage(null, error);

		}

		treeBean.init();
	}

	public void remove() {

		CategoryDto category = (CategoryDto) treeBean.getSelectedNode().getData();
		try {
			service.removeCategory(category);
		} catch (CategoryException e) {
			FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.LOGIN_ERROR,
					Constants.INVALID_CREDENTIALS);
			FacesContext.getCurrentInstance().addMessage(null, error);
		}
		treeBean.init();
	}

	public void searchForCategory(AjaxBehaviorEvent e) {

		if (!searchValue.isEmpty()) {

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlg1').show();");

			searchResults = new ArrayList<>();

			List<CategoryDto> categories = treeBean.getCategories();

			for (CategoryDto category : categories) {

				if (category.getName().toLowerCase().equals(searchValue.toLowerCase())) {

					searchResults.add(category);
				}
			}
			
			searchValue = null;
		}
	}

	public void onNodeSelect(NodeSelectEvent event) {

		if (isNodeSelected(treeBean)) {

			category = (CategoryDto) treeBean.getSelectedNode().getData();

		}

		List<String> path = getPath(category);
		breadCrumb.updateBreadCrumb(path);

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
		treeBean.getSelectedNode().setSelected(false);
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
