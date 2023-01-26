package mx.softixx.cis.common.pageable.util;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.Assert;

import lombok.val;
import mx.softixx.cis.common.core.data.StringUtils;
import mx.softixx.cis.common.pageable.payload.SortOrderResponse;
import mx.softixx.cis.common.pageable.payload.SortRequest;
import mx.softixx.cis.common.pageable.payload.SortOrderResponse.SortData;

public final class SortUtils {
	
	private SortUtils() {
	}
	
	public static final String SORT_ORDER_UP_CSS = "fa-solid fa-arrow-up-short-wide";
	public static final String SORT_ORDER_DOWN_CSS = "fa-solid fa-arrow-down-short-wide";
	
	private static final String SORT_BY_ASSERT_MESSAGE = "pageable.text.sort.by.required";
	private static final String SORT_REQUEST_ASSERT_MESSAGE = "pageable.text.sort.request.required";
	private static final String SORT_REQUEST_LIST_ASSERT_MESSAGE = "pageable.text.sort.request.list.required";
	
	public static Sort sort(SortRequest sortRequest) {
		Assert.notNull(sortRequest, SORT_REQUEST_ASSERT_MESSAGE);
		return sort(sortRequest.sortBy(), sortRequest.sortDir());
	}
	
	public static Sort sort(String sortBy, String sortDir) {
		Assert.notNull(sortBy, SORT_BY_ASSERT_MESSAGE);
		
		val direction = determineDirection(sortDir);
		return Sort.by(direction, sortBy);
	}
	
	public static Sort sort(List<SortRequest> sortRequests) {
		Assert.notNull(sortRequests, SORT_REQUEST_ASSERT_MESSAGE);
		Assert.noNullElements(sortRequests, SORT_REQUEST_LIST_ASSERT_MESSAGE);
		
		val orders = sortRequests.stream().map(SortUtils::mapToOrder).filter(Objects::nonNull).toList();
		if (!orders.isEmpty()) {
			return Sort.by(orders);
		}
		
		return Sort.unsorted();
	}
	
	public static SortOrderResponse sortOrder(Sort sort) {
		if (sort != null) {
			val order = sort.stream().findFirst().orElse(null);
			if (order != null) {
				var sortBy = order.getProperty();
				val sortDir = order.getDirection().name().toLowerCase();
				val reverseSort = reverseSort(order);
				val sortData = sortData(order);
				
				return new SortOrderResponse(sortBy, sortDir, reverseSort, sortData);
			}
		}
		return null;
	}
	
	private static String reverseSort(Order order) {
		val reverseSort = order.getDirection().equals(Direction.ASC) ? Direction.DESC : Direction.ASC;
		return reverseSort.name().toLowerCase();
	}
	
	private static SortData sortData(Order order) {
		val sortCss = order.getDirection().equals(Direction.ASC) ? SORT_ORDER_UP_CSS : SORT_ORDER_DOWN_CSS;
		return new SortData(SORT_ORDER_UP_CSS, SORT_ORDER_UP_CSS, sortCss);
	}
	
	private static Order mapToOrder(SortRequest sortRequest) {
		val direction = determineDirection(sortRequest);
		if (StringUtils.hasValue(sortRequest.sortBy())) {
			return new Order(direction, sortRequest.sortBy());
		}
		return null;
	}
	
	private static Direction determineDirection(SortRequest sortRequest) {
		return determineDirection(sortRequest.sortDir());
	}
	
	private static Direction determineDirection(String sortDir) {
		if (StringUtils.hasValue(sortDir)) {
			return Sort.DEFAULT_DIRECTION;
		}
		
		return Direction.fromOptionalString(sortDir).orElse(Sort.DEFAULT_DIRECTION);
	}
	
}