package com.egalaber.buildbro.api.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

abstract public class BaseSearch implements Serializable {
    Integer pageSize;
    Integer page;
    String sortAttribute;
    String sortDirection;

    BaseSearch() {
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSortAttribute() {
        return sortAttribute;
    }

    public void setSortAttribute(String sortAttribute) {
        this.sortAttribute = sortAttribute;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public PageRequest page() {
        int thePage = page != null ? page : 0;
        int thePageSize = pageSize != null ? pageSize : 10;
        String theSortAttribute = sortAttribute != null ? sortAttribute : defaultSortAttribute();
        Sort.Direction theDirection = sortDirection != null ? Sort.Direction.fromString(sortDirection) : Sort.Direction.DESC;
        return PageRequest.of(thePage, thePageSize, Sort.by(theDirection, theSortAttribute));
    }

    abstract protected String defaultSortAttribute();
}
