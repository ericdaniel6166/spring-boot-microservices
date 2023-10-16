package com.example.springbootmicroservicesframework.config.specification;

import com.example.springbootmicroservicesframework.dto.CursorPageRequest;
import com.example.springbootmicroservicesframework.utils.Const;
import com.example.springbootmicroservicesframework.utils.DateTimeUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageSpecification<T> implements Specification<T> {

    final transient CursorPageRequest cursorPageRequest;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        var predicate = applyPaginationFilter(root, criteriaBuilder);
        List<Order> orderList = new ArrayList<>();
        switch (Sort.Direction.fromString(cursorPageRequest.getSortDirection())) {
            case ASC -> orderList.add(criteriaBuilder.asc(root.get(cursorPageRequest.getSortColumn())));
            case DESC -> orderList.add(criteriaBuilder.desc(root.get(cursorPageRequest.getSortColumn())));
            default -> {
            } //can't go here because of validation
        }
        if (!StringUtils.equals(cursorPageRequest.getSortColumn(), Const.ID)) {
            Order defaultOrder = criteriaBuilder.asc(root.get(Const.ID));
            orderList.add(defaultOrder);
        }
        query.orderBy(orderList);
        return predicate;
    }

    private Predicate applyPaginationFilter(Root<T> root, CriteriaBuilder criteriaBuilder) {
        String searchValue = cursorPageRequest.getSearchValue();
        if (StringUtils.isBlank(String.valueOf(searchValue))) {
            return criteriaBuilder.conjunction();
        }
        if (LocalDateTime.class.isAssignableFrom(root.get(cursorPageRequest.getSortColumn()).getJavaType())) {
            return cursorPageRequest.hasPrevPageCursor()
                    ? criteriaBuilder.lessThan(root.get(cursorPageRequest.getSortColumn()), DateTimeUtils.toLocalDateTime(searchValue, Const.DEFAULT_DATE_TIME_FORMATTER))
                    : criteriaBuilder.greaterThan(root.get(cursorPageRequest.getSortColumn()), DateTimeUtils.toLocalDateTime(searchValue, Const.DEFAULT_DATE_TIME_FORMATTER));
        }
        return cursorPageRequest.hasPrevPageCursor()
                ? criteriaBuilder.lessThan(root.get(cursorPageRequest.getSortColumn()), searchValue)
                : criteriaBuilder.greaterThan(root.get(cursorPageRequest.getSortColumn()), searchValue);
    }
}
