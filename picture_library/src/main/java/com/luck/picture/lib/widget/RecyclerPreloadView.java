/*
 * ==================================================
 * =  PROJECT     地下铁的故事
 * =  MODULE      地下铁的故事.picture_library
 * =  FILE NAME   RecyclerPreloadView
 * =  LAST MODIFIED BY PASSIONPENGUIN [1/5/21, 9:25 PM]
 * ==================================================
 * Copyright 2021 PassionPenguin. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.luck.picture.lib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.listener.OnRecyclerViewPreloadMoreListener;

/**
 * @author：luck
 * @date：2020-04-14 18:43
 * @describe：RecyclerPreloadView
 */
public class RecyclerPreloadView extends RecyclerView {
    public static final int BOTTOM_PRELOAD = 2;
    private static final String TAG = RecyclerPreloadView.class.getSimpleName();
    private static final int BOTTOM_DEFAULT = 1;
    public boolean isInTheBottom = false;
    public boolean isEnabledLoadMore = false;
    private int mFirstVisiblePosition, mLastVisiblePosition;
    /**
     * reachBottomRow = 1;(default)
     * mean : when the lastVisibleRow is lastRow , call the onReachBottom();
     * reachBottomRow = 2;
     * mean : when the lastVisibleRow is Penultimate Row , call the onReachBottom();
     * And so on
     */
    private int reachBottomRow = BOTTOM_DEFAULT;
    private OnRecyclerViewPreloadMoreListener onRecyclerViewPreloadListener;

    public RecyclerPreloadView(@NonNull Context context) {
        super(context);
    }

    public RecyclerPreloadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public RecyclerPreloadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setReachBottomRow(int reachBottomRow) {
        if (reachBottomRow < 1)
            reachBottomRow = 1;
        this.reachBottomRow = reachBottomRow;
    }

    /**
     * Whether to load more
     */
    public boolean isEnabledLoadMore() {
        return isEnabledLoadMore;
    }

    /**
     * Whether to load more
     *
     * @param isEnabledLoadMore
     */
    public void setEnabledLoadMore(boolean isEnabledLoadMore) {
        this.isEnabledLoadMore = isEnabledLoadMore;
    }

    @Override
    public void onScrollStateChanged(int newState) {
        super.onScrollStateChanged(newState);
        if (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_DRAGGING) {
            LayoutManager layoutManager = getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager linearManager = (GridLayoutManager) layoutManager;
                mFirstVisiblePosition = linearManager.findFirstVisibleItemPosition();
                mLastVisiblePosition = linearManager.findLastVisibleItemPosition();
            }
        }
    }

    /**
     * Gets the first visible position index
     *
     * @return
     */
    public int getFirstVisiblePosition() {
        return mFirstVisiblePosition;
    }

    /**
     * Gets the last visible position index
     *
     * @return
     */
    public int getLastVisiblePosition() {
        return mLastVisiblePosition;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (onRecyclerViewPreloadListener != null) {
            if (isEnabledLoadMore) {
                LayoutManager layoutManager = getLayoutManager();
                if (layoutManager == null) {
                    throw new RuntimeException("LayoutManager is null,Please check it!");
                }
                Adapter adapter = getAdapter();
                if (adapter == null) {
                    throw new RuntimeException("Adapter is null,Please check it!");
                }
                boolean isReachBottom = false;
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    int rowCount = adapter.getItemCount() / gridLayoutManager.getSpanCount();
                    int lastVisibleRowPosition = gridLayoutManager.findLastVisibleItemPosition() / gridLayoutManager.getSpanCount();
                    isReachBottom = (lastVisibleRowPosition >= rowCount - reachBottomRow);
                }

                if (!isReachBottom) {
                    isInTheBottom = false;
                } else if (!isInTheBottom) {
                    onRecyclerViewPreloadListener.onRecyclerViewPreloadMore();
                    if (dy > 0) {
                        isInTheBottom = true;
                    }
                } else {
                    // 属于首次进入屏幕未滑动且内容未超过一屏，用于确保分页数设置过小导致内容不足二次上拉加载...
                    if (dy == 0) {
                        isInTheBottom = false;
                    }
                }
            }
        }
    }

    public void setOnRecyclerViewPreloadListener(OnRecyclerViewPreloadMoreListener onRecyclerViewPreloadListener) {
        this.onRecyclerViewPreloadListener = onRecyclerViewPreloadListener;
    }
}
