package mx.softixx.cis.common.pageable.payload;

public record SortOrderResponse(String sortBy, String sortDir, String reverseSort, SortData data) {
	
	public static record SortData(String sortUp, String sortDown, String sortCss) {}
	
}