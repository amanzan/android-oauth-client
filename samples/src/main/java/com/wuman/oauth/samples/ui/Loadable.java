
package com.wuman.oauth.samples.ui;


import androidx.loader.app.LoaderManager;

import com.wuman.oauth.samples.AsyncResourceLoader.Result;

public interface Loadable<T> extends LoaderManager.LoaderCallbacks<Result<T>> {

    boolean hasMore();

    boolean hasError();

    void init();

    void destroy();

    boolean isReadyToLoadMore();

    void loadMore();
}
