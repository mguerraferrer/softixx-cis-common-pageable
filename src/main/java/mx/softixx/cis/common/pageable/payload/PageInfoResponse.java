package mx.softixx.cis.common.pageable.payload;

public record PageInfoResponse(String text, Integer start, Integer end, Long totalElements) {
	
	public static PageInfoResponse empty() {
		return new PageInfoResponse("pageable.text.info.no.records", null, null, null);
	}
	
}