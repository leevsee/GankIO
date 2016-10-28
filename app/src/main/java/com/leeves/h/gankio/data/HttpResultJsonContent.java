package com.leeves.h.gankio.data;

import java.util.List;

/**
 * Function：
 * Created by h on 2016/10/17.
 *
 * @author Leeves
 */

public class HttpResultJsonContent<T> {

    private List<String> category;
    private boolean error;
    private T results;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
