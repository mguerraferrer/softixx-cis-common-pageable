package mx.softixx.cis.common.pageable.payload;

import org.springframework.data.domain.Page;

/**
 * This record class is used to obtain a {@code PageResponse} instance 
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 * @param <T>
 * @see {@link PageResponse}
 */
public record PageDataRequest<T> (Page<T> page, PageData pageData) {}