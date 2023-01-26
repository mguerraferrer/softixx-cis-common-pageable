package mx.softixx.cis.common.pageable.util;

import java.util.List;

public final class PageableUtils {
	
	private PageableUtils() {
	}
	
	/**
	 * This is the default page of the pagination
	 */
	public static final int DEFAULT_PAGE = 1;
	
	/**
	 * Determines the minimun number of records to be displayed per page
	 */
	public static final int MIN_RECORDS_PER_PAGE = 5;
	
	/**
	 * Determines the number of records to be displayed per page
	 */
	public static final int RECORDS_PER_PAGE = 10;
	
	/**
	 * Determines the maximum number of records to be displayed per page
	 */
	public static final int MAX_RECORDS_PER_PAGE = 100;
	
	/**
	 * Determines the list of pages that will be visible in the pagination
	 */
	public static final int PAGES_TO_DISPLAY = 5;
	
	public static final List<Integer> RECORDS_PER_PAGE_LIST = List.of(10, 20, 30, 40, 50, 100);
	
	public static final int pageNumber(int pageNumber) {
		if (pageNumber < 1) {
			return PageableUtils.DEFAULT_PAGE;
		}
		return pageNumber;
	}
	
	public static final int recordsPerPage(int recordsPerPage) {
		if (recordsPerPage < MIN_RECORDS_PER_PAGE) {
			return MIN_RECORDS_PER_PAGE;
		} else if (recordsPerPage > MAX_RECORDS_PER_PAGE) {
			return MAX_RECORDS_PER_PAGE;
		} else {
			return recordsPerPage;
		}
	}
	
}