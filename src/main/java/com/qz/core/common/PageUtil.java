package com.qz.core.common;

/**
 * 分页工具
 * <ul>
 * <li>totalRecords: 总记录数
 * <li>totalPages: 总页数
 * <li>pageIndex: 当前页码
 * <li>pageSize: 每页数目
 * <li>start: mysql分页起始位置
 * </ul>
 * 
 * @author chance
 * @date 2017年11月3日下午7:31:18
 */
public class PageUtil {

	private int totalRecords;
	private int totalPages;
	private int pageIndex;
	private int pageSize;
	private int start;

	public int getTotalRecords() {
		return totalRecords;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * 构造函数
	 * @param pageIndex 当前页码
	 * @param pageSize 每页数目
	 */
	public PageUtil(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		//页码必须大于0
		if (this.pageIndex < 1) {
			this.pageIndex = 1;
		}

		//设置起始位置
		this.start = (this.pageIndex - 1) * this.pageSize;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
		this.totalPages = calculateTotalPages();
		
		if(pageIndex > totalPages){
			pageIndex = totalPages;
		}
	}
	
	/**
	 * 计算总页数
	 * @return
	 */
	private int calculateTotalPages(){
		int num = totalRecords / pageSize;
		if((totalRecords % pageSize) > 0){
			num++;
		}
		return num;
	}
}
