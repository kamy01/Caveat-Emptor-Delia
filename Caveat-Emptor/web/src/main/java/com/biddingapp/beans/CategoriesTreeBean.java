package com.biddingapp.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import model.CategoryDto;
import services.category.CategoryService;
import utils.exceptions.CategoryException;

@ManagedBean(name = "treeBasicView")
@SessionScoped
public class CategoriesTreeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private TreeNode root;
	private TreeNode selectedNode;

	@EJB
	CategoryService service;

	@PostConstruct
	public void init() {

		CategoryDto rootCategory = new CategoryDto();
		try {
			rootCategory = service.getRootCategory();
		} catch (CategoryException e) {
			e.printStackTrace();
		}

		root = new DefaultTreeNode(rootCategory, null);

		List<CategoryDto> children = new ArrayList<CategoryDto>();
		try {
			children = service.getChildrenList(rootCategory);
		} catch (CategoryException e) {
			e.printStackTrace();
		}

		constructTree(children, root);

	}

	private void constructTree(List<CategoryDto> categories, TreeNode parent) {

		for (CategoryDto category : categories) {

			TreeNode node = new DefaultTreeNode(category, parent);
			List<CategoryDto> children = new ArrayList<CategoryDto>();

			try {
				children = service.getChildrenList(category);
			} catch (CategoryException e) {
				e.printStackTrace();
			}
			if (children.size() > 0)
				constructTree(children, node);
		}
	}

	public void onNodeSelect(NodeSelectEvent event) {

		CategoryDto category = new CategoryDto();

		try {
			category = (CategoryDto) event.getTreeNode().getData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", category.getName());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void onNodeExpand(NodeExpandEvent event) {
		event.getTreeNode().toString();
	}

	public void onNodeCollapse(NodeCollapseEvent event) {
	}

	public void onNodeUnselect(NodeUnselectEvent event) {
	}

	public List<TreeNode> getAllChildren(TreeNode parent) {
		
		List<TreeNode> children = new ArrayList<TreeNode>();
		List<TreeNode> totalNodes = new ArrayList<TreeNode>();

		int childrenNo = parent.getChildCount();

		if (childrenNo > 0) {

			children = parent.getChildren();
			totalNodes.addAll(children);

			for (TreeNode child : children) {
				if (child.getChildCount() > 0) {
					totalNodes.addAll(getAllChildren(child));
				}
			}
		}

		return totalNodes;

	}

	public List<CategoryDto> getChildrenForCategory(TreeNode parent) {

		List<TreeNode> childrenNodes = getAllChildren(parent);
		List<CategoryDto> childrenCategories = new ArrayList<>();
		
		for(TreeNode node: childrenNodes)
		{
			childrenCategories.add((CategoryDto) node.getData());
		}
		
		return childrenCategories;
	}
	
	public List<CategoryDto> getCategories() {
		
		List<TreeNode> nodes = new ArrayList<>();

		nodes.addAll(getAllChildren(root));

		List<CategoryDto> categories = new ArrayList<CategoryDto>();

		for (TreeNode node : nodes) {
			categories.add((CategoryDto) node.getData());
		}

		return categories;
	}
}