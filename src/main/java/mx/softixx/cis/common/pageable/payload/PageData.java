package mx.softixx.cis.common.pageable.payload;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import mx.softixx.cis.common.pageable.util.PageableUtils;

/**
 * Manages the values of records per page and pages to display in pagination
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public record PageData(int recordsPerPage, int pagesToDisplay, List<Integer> recordsPerPages) {

	private static final String RECORDS_PER_PAGE_ASSERT_MESSAGE = "pageable.text.records.per.page.invalid";
	private static final String PAGINATE_PAGES_ASSERT_MESSAGE = "pageable.text.paginate.page.invalid";
	private static final String PAGEABLE_PAGINATE_PAGE_ASSERT_MESSAGE = "pageable.text.paginate.page.required";

	/**
	 * <p>
	 * Creates an instance of the <b>{@code PageData}</b> record with default values
	 * </p>
	 * <p>
	 * The default value of <b>{@code recordsPerPage}</b> is
	 * {@code PageableUtils#RECORDS_PER_PAGE}<br>
	 * The default value of <b>{@code pagesToDisplay}</b> is
	 * {@code PageableUtils#PAGES_TO_DISPLAY}<br>
	 * The default value of <b>{@code recordsPerPages}</b> is
	 * {@code PageableUtils#RECORDS_PER_PAGE_LIST}
	 * </p>
	 */
	public PageData() {
		this(PageableUtils.RECORDS_PER_PAGE, PageableUtils.PAGES_TO_DISPLAY, PageableUtils.RECORDS_PER_PAGE_LIST);
	}
	
	/**
	 * Creates an instance of the <b>{@code PageData}</b> record from a
	 * <b>{@code Pageable}</b> object. {@code Pageable#getPageSize()} will be the
	 * value for <b>{@code recordsPerPage}</b> field. The default value of the
	 * <b>{@code pagesToDisplay}</b> field will be
	 * {@code PageableUtils#PAGES_TO_DISPLAY}
	 * 
	 * @param Pageable must not be null
	 * @return {@code PageData}
	 * @throws IllegalArgumentException if pageable is null
	 * @see {@link PageableUtils#PAGES_TO_DISPLAY}
	 */
	public static PageData of(Pageable pageable) {
		Assert.notNull(pageable, "pageable.text.pageable.requeired");
		return PageData.of(pageable.getPageSize(), PageableUtils.PAGES_TO_DISPLAY);
	}
	
	/**
	 * Creates an instance of the <b>{@code PageData}</b> record from a
	 * <b>{@code Pageable}</b> object. {@code Pageable#getPageSize()} will be the
	 * value for <b>{@code recordsPerPage}</b> field. If
	 * <b>{@code pagesToDisplay}</b> is null, the default value will be
	 * {@code PageableUtils#PAGES_TO_DISPLAY}.
	 * 
	 * @param pageable       must not be null
	 * @param pagesToDisplay if specified, must be greater than 0
	 * @return {@code PageData}
	 * @throws IllegalArgumentException if pageable is null or
	 *                                  <b>{@code pagesToDisplay}</b> is less than 0
	 * @see {@link PageableUtils#PAGES_TO_DISPLAY}
	 */
	public static PageData of(Pageable pageable, Integer pagesToDisplay) {
		Assert.notNull(pageable, "pageable.text.pageable.requeired");

		if (pagesToDisplay != null) {
			Assert.state(pagesToDisplay > 0, PAGINATE_PAGES_ASSERT_MESSAGE);
		} else {
			pagesToDisplay = PageableUtils.PAGES_TO_DISPLAY;
		}

		return PageData.of(pageable.getPageSize(), pagesToDisplay);
	}

	/**
	 * Creates an instance of the <b>{@code PageData}</b> record. In case
	 * <b>{@code recordsPerPage}</b> is null, the default value will be
	 * {@code PageableUtils#RECORDS_PER_PAGE}. Otherwise, if
	 * <b>{@code pagesToDisplay}</b> is null, the default value will be
	 * {@code PageableUtils#PAGES_TO_DISPLAY}.
	 * 
	 * @param recordsPerPage if specified, must be greater than 0
	 * @param pagesToDisplay if specified, must be greater than 0
	 * @return {@code PageData}
	 * @throws IllegalArgumentException if both <b>{@code recordsPerPage}</b> and
	 *                                  <b>{@code pagesToDisplay}</b> are null or
	 *                                  less than 0
	 */
	public static PageData of(Integer recordsPerPage, Integer pagesToDisplay) {
		if (recordsPerPage == null && pagesToDisplay == null) {
			throw new IllegalArgumentException("pageable.text.records.per.page.pages.to.display.invalid");
		}

		if (recordsPerPage != null) {
			Assert.state(recordsPerPage > 0, RECORDS_PER_PAGE_ASSERT_MESSAGE);
		} else {
			recordsPerPage = PageableUtils.RECORDS_PER_PAGE;
		}

		if (pagesToDisplay != null) {
			Assert.state(pagesToDisplay > 0, PAGINATE_PAGES_ASSERT_MESSAGE);
		} else {
			pagesToDisplay = PageableUtils.PAGES_TO_DISPLAY;
		}

		return new PageData(recordsPerPage, pagesToDisplay, PageableUtils.RECORDS_PER_PAGE_LIST);
	}

	/**
	 * Creates an instance of the <b>{@code PageData}</b> record. In case
	 * <b>{@code recordsPerPage}</b> is null, the default value will be
	 * {@code PageableUtils#RECORDS_PER_PAGE}. If <b>{@code pagesToDisplay}</b> is
	 * null, the default value will be {@code PageableUtils#PAGES_TO_DISPLAY}. If
	 * <b>{@code recordsPerPages}</b> list is null or empty, the default value will
	 * be {@code PageableUtils#RECORDS_PER_PAGE_LIST}.
	 * 
	 * @param recordsPerPage  if specified, must be greater than 0
	 * @param pagesToDisplay  if specified, must be greater than 0
	 * @param recordsPerPages list of record-per-page
	 * @return {@code PageData}
	 * @throws IllegalArgumentException if all parameters are null or if
	 *                                  <b>{@code recordsPerPage}</b> and
	 *                                  <b>{@code pagesToDisplay}</b> are less than
	 *                                  0
	 */
	public static PageData of(Integer recordsPerPage, Integer pagesToDisplay, List<Integer> recordsPerPages) {
		if (recordsPerPage == null && pagesToDisplay == null
				&& (recordsPerPages == null || recordsPerPages.isEmpty())) {
			throw new IllegalArgumentException("pageable.text.parameter.empty.value");
		}

		if (recordsPerPage != null) {
			Assert.state(recordsPerPage > 0, RECORDS_PER_PAGE_ASSERT_MESSAGE);
		} else {
			recordsPerPage = PageableUtils.RECORDS_PER_PAGE;
		}

		if (pagesToDisplay != null) {
			Assert.state(pagesToDisplay > 0, PAGINATE_PAGES_ASSERT_MESSAGE);
		} else {
			pagesToDisplay = PageableUtils.PAGES_TO_DISPLAY;
		}

		recordsPerPages = sanitizeRecordsPerPage(recordsPerPages);

		return new PageData(recordsPerPage, pagesToDisplay, recordsPerPages);
	}

	/**
	 * Creates an instance of the <b>{@code PageData}</b> record with the value of
	 * the <b>{@code recordsPerPages}</b> parameter. The default value of the
	 * <b>{@code recordsPerPage}</b> field will be
	 * {@code PageableUtils#RECORDS_PER_PAGE}. The default value of the
	 * <b>{@code pagesToDisplay}</b> field will be
	 * {@code PageableUtils#PAGES_TO_DISPLAY}
	 * 
	 * @param recordsPerPages list of record-per-page
	 * @return {@code PageData}
	 * @throws IllegalArgumentException if recordsPerPages is null or empty
	 * @see {@link PageableUtils#RECORDS_PER_PAGE}
	 */
	public static PageData of(List<Integer> recordsPerPages) {
		recordsPerPages = sanitizeRecordsPerPage(recordsPerPages);

		return new PageData(PageableUtils.RECORDS_PER_PAGE, PageableUtils.PAGES_TO_DISPLAY, recordsPerPages);
	}

	/**
	 * Creates an instance of the <b>{@code PageData}</b> record with the value of
	 * the <b>{@code recordsPerPage}</b> parameter. The default value of the
	 * <b>{@code pagesToDisplay}</b> field will be
	 * {@code PageableUtils#PAGES_TO_DISPLAY}
	 * 
	 * @param recordsPerPage must not be null and must be greater than 0
	 * @return {@code PageData}
	 * @throws IllegalArgumentException if the recordsPerPage is null or less than 0
	 * @see {@link PageableUtils#PAGES_TO_DISPLAY}
	 */
	public static PageData withRecordPerPage(Integer recordsPerPage) {
		Assert.notNull(recordsPerPage, PAGEABLE_PAGINATE_PAGE_ASSERT_MESSAGE);
		Assert.state(recordsPerPage > 0, RECORDS_PER_PAGE_ASSERT_MESSAGE);

		return new PageData(recordsPerPage, PageableUtils.PAGES_TO_DISPLAY, PageableUtils.RECORDS_PER_PAGE_LIST);
	}

	/**
	 * Creates an instance of the <b>{@code PageData}</b> record with the value of
	 * the <b>{@code recordsPerPage}</b> and <b>{@code recordsPerPages}</b>
	 * parameters. The default value of the <b>{@code pagesToDisplay}</b> field will
	 * be {@code PageableUtils#PAGES_TO_DISPLAY}
	 * 
	 * @param recordsPerPage  must not be null and must be greater than 0
	 * @param recordsPerPages list of record-per-page
	 * @return {@code PageData}
	 * @throws IllegalArgumentException if the recordsPerPage is null or less than 0
	 *                                  or if recordsPerPages is null or empty
	 * @see {@link PageableUtils#PAGES_TO_DISPLAY}
	 */
	public static PageData withRecordPerPage(Integer recordsPerPage, List<Integer> recordsPerPages) {
		Assert.notNull(recordsPerPage, PAGEABLE_PAGINATE_PAGE_ASSERT_MESSAGE);
		Assert.state(recordsPerPage > 0, RECORDS_PER_PAGE_ASSERT_MESSAGE);

		recordsPerPages = sanitizeRecordsPerPage(recordsPerPages);

		return new PageData(recordsPerPage, PageableUtils.PAGES_TO_DISPLAY, recordsPerPages);
	}

	/**
	 * Creates an instance of the <b>{@code PageData}</b> record with the value of
	 * the <b>{@code pagesToDisplay}</b> parameter. The default value of the
	 * <b>{@code recordsPerPage}</b> field will be
	 * {@code PageableUtils#RECORDS_PER_PAGE}
	 * 
	 * @param pagesToDisplay must not be null and must be greater than 0
	 * @return {@code PageData}
	 * @throws IllegalArgumentException if the recordsPerPage is null or less than 0
	 * @see {@link PageableUtils#RECORDS_PER_PAGE}
	 * @see {@link PageableUtils#RECORDS_PER_PAGES}
	 */
	public static PageData withPagesToDisplay(Integer pagesToDisplay) {
		Assert.notNull(pagesToDisplay, PAGEABLE_PAGINATE_PAGE_ASSERT_MESSAGE);
		Assert.state(pagesToDisplay > 0, PAGINATE_PAGES_ASSERT_MESSAGE);

		return new PageData(PageableUtils.RECORDS_PER_PAGE, pagesToDisplay, PageableUtils.RECORDS_PER_PAGE_LIST);
	}

	/**
	 * Creates an instance of the <b>{@code PageData}</b> record with the value of
	 * the <b>{@code pagesToDisplay}</b> and <b>{@code recordsPerPages}</b>
	 * parameters. The default value of the <b>{@code recordsPerPage}</b> field will
	 * be {@code PageableUtils#RECORDS_PER_PAGE}
	 * 
	 * @param pagesToDisplay  must not be null and must be greater than 0
	 * @param recordsPerPages list of record-per-page
	 * @return {@code PageData}
	 * @throws IllegalArgumentException if the recordsPerPage is null or less than 0
	 *                                  or if recordsPerPages is null or empty
	 * @see {@link PageableUtils#RECORDS_PER_PAGE}
	 */
	public static PageData withPagesToDisplay(Integer pagesToDisplay, List<Integer> recordsPerPages) {
		Assert.notNull(pagesToDisplay, PAGEABLE_PAGINATE_PAGE_ASSERT_MESSAGE);
		Assert.state(pagesToDisplay > 0, PAGINATE_PAGES_ASSERT_MESSAGE);

		recordsPerPages = sanitizeRecordsPerPage(recordsPerPages);

		return new PageData(PageableUtils.RECORDS_PER_PAGE, pagesToDisplay, recordsPerPages);
	}

	private static final List<Integer> sanitizeRecordsPerPage(List<Integer> recordsPerPages) {
		Assert.notNull(recordsPerPages, "pageable.text.records.per.page.nullable.list");

		recordsPerPages = recordsPerPages.stream().filter(i -> i > 0).distinct().sorted().toList();
		Assert.state(!recordsPerPages.isEmpty(), "pageable.text.records.per.page.empty.list");

		return recordsPerPages;
	}

}