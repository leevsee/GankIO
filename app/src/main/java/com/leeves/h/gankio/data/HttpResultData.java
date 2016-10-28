package com.leeves.h.gankio.data;

/**
 * Function：
 * Created by h on 2016/10/17.
 *
 * @author Leeves
 */

public class HttpResultData<T> {

    private boolean error;

    private T results;

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
