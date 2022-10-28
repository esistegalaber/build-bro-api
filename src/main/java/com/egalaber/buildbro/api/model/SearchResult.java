package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The results of a query
 *
 * @param <R> the class of the results returned from the search
 */
public abstract class SearchResult<R> implements Serializable {
    private List<R> data = new ArrayList<>();
    private Integer page;
    private Long totalElements;
    private Integer totalPages;
    private Boolean hasNext;
    private Boolean hasPrevious;

    /**
     * Default constructor
     * needed for JSON
     * libraries
     */
    public SearchResult() {
    }

//    /**
//     * A Constructor for child classes
//     *
//     * @param sr the SearchResult that the
//     *           child is "cloning"
//     */
//    protected SearchResult(SearchResult sr) {
//        this.results = sr.results;
//        this.page = sr.page;
//        this.totalPages = sr.totalPages;
//        this.totalElements = sr.totalElements;
//        this.hasNext = sr.hasNext;
//        this.hasPrevious = sr.hasPrevious;
//    }

//    /**
//     * Returns a SearchResult based on a query Page and a closure that transforms the
//     * Page"s results into the relevant SearchResult result Class.
//     *
//     * @param result    a query Page containing all info needed to build a SearchResult
//     * @param transform a closure which can transform the Page"s results class to the
//     *                  SearchResult"s results class
//     * @return a SearchResult based on the provided Page
//     */
//    static <P, R> SearchResult fromPageResult(Page<P> result, Closure<R> transform) {
//        new SearchResult(
//                results:result.getContent().collect(transform),
//                page:result.getNumber(),
//                totalPages:result.getTotalPages(),
//                totalElements:result.getTotalElements(),
//                hasNext:result.hasNext(),
//                hasPrevious:result.hasPrevious()
//        )
//    }


    public List<R> getData() {
        return data;
    }

    public void setData(List<R> data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean getHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(Boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}
