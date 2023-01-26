package mx.softixx.cis.common.pageable.payload;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.util.Assert;

import lombok.Data;
import lombok.val;
import mx.softixx.cis.common.pageable.util.SortUtils;

/**
 * @author KUN
 *
 * @param <T>
 */
@Data
public class PageResponse<T> {
	private Long totalElements;
	private int totalPages;
	private int currentPage;
	private int pageNum;
	private int pageSize;
	private int numberOfElements;
	private Boolean hasContent;
	private Boolean hasPrevious;
	private Boolean hasNext;
	private boolean first;
	private boolean last;
	private SortOrderResponse sort;
	private String requestMapping;
	private PageInfoResponse pageInfo;
	private List<Integer> pages;
	private List<Integer> recordsPerPages;

	/**
	 * @param pageDataRequest
	 */
	public PageResponse(PageDataRequest<T> pageDataRequest, String requestMapping) {
		val page = pageDataRequest.page();
		Assert.notNull(page, "pageable.text.page.required");

		val pageData = pageDataRequest.pageData();
		Assert.notNull(pageData, "pageable.text.page.data.required");

		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.currentPage = page.getNumber();
		this.pageNum = page.getNumber() == 0 ? 1 : page.getNumber() + 1;
		this.pageSize = page.getSize();
		this.numberOfElements = page.getNumberOfElements();
		this.hasContent = page.hasContent();
		this.hasPrevious = page.hasPrevious();
		this.hasNext = page.hasNext();
		this.first = page.isFirst();
		this.last = page.isLast();
		this.sort = SortUtils.sortOrder(page.getSort());
		this.requestMapping = requestMapping;
		this.pageInfo = pageInfo();
		this.pages = pagesToDisplay(pageData);
		this.recordsPerPages = pageData.recordsPerPages();
	}

	private PageInfoResponse pageInfo() {
		if (Boolean.TRUE.equals(this.hasContent)) {
			val start = (this.pageNum - 1) * this.pageSize + 1;
			var end = start + this.pageSize - 1;

			if (end > this.totalElements) {
				end = this.totalElements.intValue();
			}

			if (this.totalElements <= this.pageSize) {
				return new PageInfoResponse("pageable.text.info.paginate.result", null, null, this.totalElements);
			}
			return new PageInfoResponse("pageable.text.info.showing.records", start, end, this.totalElements);
		} else {
			return PageInfoResponse.empty();
		}
	}

	private List<Integer> pagesToDisplay(PageData pageData) {
		if (Boolean.TRUE.equals(this.hasContent)) {
			if (this.totalElements <= this.pageSize) {
				return List.of(1);
			}

			val pagesToDisplay = pageData.pagesToDisplay();
			if (this.totalPages <= pagesToDisplay) {
				return IntStream.rangeClosed(1, this.totalPages).boxed().toList();
			} else {
				return calculatePages(pagesToDisplay);
			}
		}
		return Collections.emptyList();
	}

	private List<Integer> calculatePages(int pagesToDisplay) {
		if (this.pageNum >= pagesToDisplay) {
			var rangeStart = this.pageNum - 1;
			var totalNextPages = this.pageNum + pagesToDisplay - 1;
			if (totalNextPages > this.totalPages) {
				totalNextPages = this.totalPages;

				while (totalNextPages - rangeStart < pagesToDisplay - 1) {
					rangeStart--;
				}
			}
			return IntStream.rangeClosed(rangeStart, totalNextPages).limit(pagesToDisplay).boxed().toList();
		} else {
			return IntStream.rangeClosed(1, pagesToDisplay).boxed().toList();
		}
	}

}