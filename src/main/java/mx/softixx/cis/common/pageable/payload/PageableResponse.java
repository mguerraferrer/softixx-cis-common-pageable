package mx.softixx.cis.common.pageable.payload;

import java.util.List;

public record PageableResponse<T, U>(PageResponse<T> pageResponse, List<U> sourceList) {}