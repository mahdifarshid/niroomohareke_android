package com.application.mahabad.niroomohareke.Utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Pagination
 * Created by Suleiman19 on 10/15/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */
public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;
    GridLayoutManager gridLayoutManager;

    private int visibleItemCount,totalItemCount,firstVisibleItemPosition;
    /**
     * Supporting only LinearLayoutManager for now.
     *
     * @param layoutManager
     */
    public PaginationScrollListener(LinearLayoutManager layoutManager,GridLayoutManager grid) {
        this.layoutManager = layoutManager;
        this.gridLayoutManager=grid;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


         visibleItemCount = layoutManager.getChildCount();
         totalItemCount = layoutManager.getItemCount();
         firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();


        if(firstVisibleItemPosition==-1){
             visibleItemCount = gridLayoutManager.getChildCount();
             totalItemCount = gridLayoutManager.getItemCount();
             firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
        }

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }

    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
