package com.vv.androidreview.mvp.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 *  Utility adapter class to help you add header or footer into a normal {@link RecyclerView}.
 *  <br><br>
 *  Usage:<br>
 *  1. Initialize the wrapper by passing your {@link android.support.v7.widget.RecyclerView.Adapter} in <br>
 *  2. Initialize your header or footer view <br>
 *  3. Add your header by calling {@link #addHeader(View)}, and add your footer by calling
 *  {@link #addFooter(View)} <br>
 */
public class RecyclerViewHeaderFooterWrapper<T extends RecyclerView.Adapter> extends RecyclerView.Adapter {

    public static final int TYPE_MANAGER_OTHER = 0;
    public static final int TYPE_MANAGER_LINEAR = 1;
    public static final int TYPE_MANAGER_GRID = 2;
    public static final int TYPE_MANAGER_STAGGERED_GRID = 3;

    /**
     * Return value of {@link #getItemViewType(int)}, stand for header, should be avoid duplicating
     * with normal return value from {@link #getItemViewType(int)}.
     */
    public static final int TYPE_HEADER = 9998;

    /**
     * Return value of {@link #getItemViewType(int)}, stand for footer, should be avoid duplicating
     * with normal return value from {@link #getItemViewType(int)}.
     */
    public static final int TYPE_FOOTER = 9999;

    private boolean isLinearLayoutHorizontal = false;

    private List<View> mHeaders = new ArrayList<View>();
    private List<View> mFooters = new ArrayList<View>();

    private int mManagerType;

    private LayoutManager layoutManager;

    public int getHeadSize() {
        return mHeaders.size();
    }

    public int getFootSize() {
        return mFooters.size();
    }

    public int getManagerType() {
        return mManagerType;
    }

    public void notifyDataSetChangedHF() {
        notifyDataSetChanged();
    }

    public void notifyItemChangedHF(int position) {
        notifyItemChanged(getRealPosition(position));
    }

    public void notifyItemMovedHF(int fromPosition, int toPosition) {
        notifyItemMovedHF(getRealPosition(fromPosition), getRealPosition(toPosition));
    }

    public void notifyItemRangeChangedHF(int positionStart, int itemCount) {
        notifyItemRangeChanged(getRealPosition(positionStart), itemCount);
    }

    public void notifyItemRangeRemovedHF(int positionStart, int itemCount) {
        notifyItemRangeRemoved(getRealPosition(positionStart), itemCount);
    }

    public void notifyItemRemovedHF(int position) {
        notifyItemRemoved(getRealPosition(position));
    }

    public void notifyItemInsertedHF(int position) {
        notifyItemInserted(getRealPosition(position));
    }

    public void notifyItemRangeInsertedHF(int positionStart, int itemCount) {
        notifyItemRangeInserted(getRealPosition(positionStart), itemCount);
    }

    @Override
    public final long getItemId(int position) {
        return getItemIdHF(getRealPosition(position));
    }

    public long getItemIdHF(int position) {
        return mAdapter.getItemId(position);
    }

    public ViewHolder onCreateViewHolderHF(ViewGroup viewGroup, int type) {
        return mAdapter.onCreateViewHolder(viewGroup, type);
    }

    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        // if our position is one of our items (this comes from
        // getItemViewType(int position) below)
        if (type != TYPE_HEADER && type != TYPE_FOOTER) {
            ViewHolder vh = onCreateViewHolderHF(viewGroup, type);
            return vh;
            // else we have a header/footer
        } else {
            // create a new framelayout, or inflate from a resource
            FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
            // make sure it fills the space

            if(isLinearLayoutHorizontal){
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                        .LayoutParams.WRAP_CONTENT));
            }else{
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                        .LayoutParams.WRAP_CONTENT));
            }

            return new HeaderFooterViewHolder(frameLayout);
        }
    }

    @Override
    public final void onBindViewHolder(final ViewHolder vh, int position) {
        // check what type of view our position is
        if (isHeader(position)) {
            View v = mHeaders.get(position);
            // add our view to a header view and display it
            prepareHeaderFooter((HeaderFooterViewHolder) vh, v);
        } else if (isFooter(position)) {
            View v = mFooters.get(position - getItemCountHF() - mHeaders.size());
            // add our view to a footer view and display it
            prepareHeaderFooter((HeaderFooterViewHolder) vh, v);
        } else {
            vh.itemView.setOnClickListener(new MyOnClickListener(vh));
            vh.itemView.setOnLongClickListener(new MyOnLongClickListener(vh));
            // it's one of our items, display as required
            onBindViewHolderHF(vh, getRealPosition(position));
        }
    }

    public int getRealPosition(int position) {
        return position - mHeaders.size();
    }

    public void onBindViewHolderHF(ViewHolder vh, int position) {
        mAdapter.onBindViewHolder(vh, position);
    }

    private void prepareHeaderFooter(HeaderFooterViewHolder vh, View view) {

        // if it's a staggered grid, span the whole layout
        if (mManagerType == TYPE_MANAGER_STAGGERED_GRID) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams
					(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setFullSpan(true);
            vh.itemView.setLayoutParams(layoutParams);
        }

        // if the view already belongs to another layout, remove it
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        // empty out our FrameLayout and replace with our header/footer
        vh.base.removeAllViews();
        vh.base.addView(view);

    }

    public boolean isHeader(int position) {
        return (position < mHeaders.size());
    }

    public boolean isFooter(int position) {
        return (position >= mHeaders.size() + getItemCountHF());
    }

    @Override
    public final int getItemCount() {
        return mHeaders.size() + getItemCountHF() + mFooters.size();
    }

    public int getItemCountHF() {
        return mAdapter.getItemCount();
    }

    @Override
    public final int getItemViewType(int position) {
        // check what type our position is, based on the assumption that the
        // order is headers > items > footers
        if (isHeader(position)) {
            return TYPE_HEADER;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int type = getItemViewTypeHF(getRealPosition(position));
        if (type == TYPE_HEADER || type == TYPE_FOOTER) {
            throw new IllegalArgumentException("Item type cannot equal " + TYPE_HEADER + " or " + TYPE_FOOTER);
        }
        return type;
    }

    public int getItemViewTypeHF(int position) {
        return mAdapter.getItemViewType(position);
    }

    // add a header to the adapter
    public void addHeader(View header) {
        if (!mHeaders.contains(header)) {
            mHeaders.add(header);
            // animate
            notifyItemInserted(mHeaders.size() - 1);
        }
    }

    // remove a header from the adapter
    public void removeHeader(View header) {
        if (mHeaders.contains(header)) {
            // animate
            notifyItemRemoved(mHeaders.indexOf(header));
            mHeaders.remove(header);
        }
    }

    // add a footer to the adapter
    public void addFooter(View footer) {
        if (!mFooters.contains(footer)) {
            mFooters.add(footer);
            // animate
            notifyItemInserted(mHeaders.size() + getItemCountHF() + mFooters.size() - 1);
        }
    }

    // remove a footer from the adapter
    public void removeFooter(View footer) {
        if (mFooters.contains(footer)) {
            // animate
            notifyItemRemoved(mHeaders.size() + getItemCountHF() + mFooters.indexOf(footer));
            mFooters.remove(footer);
        }
    }

    // our header/footer RecyclerView.ViewHolder is just a FrameLayout
    public static class HeaderFooterViewHolder extends ViewHolder {
        FrameLayout base;

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            base = (FrameLayout) itemView;
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        Log.d("eeee", "setOnItemClickListener " + this.onItemClickListener);
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    private class MyOnClickListener implements OnClickListener {
        private ViewHolder vh;

        public MyOnClickListener(ViewHolder vh) {
            super();
            this.vh = vh;
        }

        @Override
        public void onClick(View v) {
            int position = getRealPosition(vh.getLayoutPosition());
            if (RecyclerViewHeaderFooterWrapper.this.onItemClickListener != null) {
                RecyclerViewHeaderFooterWrapper.this.onItemClickListener.onItemClick(RecyclerViewHeaderFooterWrapper.this, vh, position);
            }
            onItemClick(vh, position);
        }
    }

    private class MyOnLongClickListener implements OnLongClickListener {
        private ViewHolder vh;

        public MyOnLongClickListener(ViewHolder vh) {
            super();
            this.vh = vh;
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getRealPosition(vh.getLayoutPosition());
            if (RecyclerViewHeaderFooterWrapper.this.onItemLongClickListener != null) {
                RecyclerViewHeaderFooterWrapper.this.onItemLongClickListener.onItemLongClick(RecyclerViewHeaderFooterWrapper.this, vh,
						position);
            }
            onItemLongClick(vh, position);
            return true;
        }

    }

    protected void onItemClick(ViewHolder vh, int position) {

    }

    protected void onItemLongClick(ViewHolder vh, int position) {

    }

    public static interface OnItemClickListener {
        void onItemClick(RecyclerViewHeaderFooterWrapper adapter, ViewHolder vh, int position);
    }

    public static interface OnItemLongClickListener {
        void onItemLongClick(RecyclerViewHeaderFooterWrapper adapter, ViewHolder vh, int position);
    }

    private T mAdapter;

    public RecyclerViewHeaderFooterWrapper(T adapter) {
        super();
        this.mAdapter = adapter;
        adapter.registerAdapterDataObserver(adapterDataObserver);

    }
    public RecyclerViewHeaderFooterWrapper(T adapter, LayoutManager layoutManager) {
        super();
        this.mAdapter = adapter;
        this.layoutManager = layoutManager;
        adapter.registerAdapterDataObserver(adapterDataObserver);
        if(layoutManager instanceof GridLayoutManager){
            mManagerType = TYPE_MANAGER_GRID;
//            ((GridLayoutManager) layoutManager).setSpanSizeLookup(mSpanSizeLookup);
        }else if(layoutManager instanceof LinearLayoutManager){
            mManagerType = TYPE_MANAGER_LINEAR;
            if(((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL){
                isLinearLayoutHorizontal = true;
            }
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            mManagerType = TYPE_MANAGER_STAGGERED_GRID;
            ((StaggeredGridLayoutManager) layoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        }else{
            mManagerType = TYPE_MANAGER_OTHER;
        }


    }

    public int getGridSpan(int position){
        if(isHeader(position) || isFooter(position)){
            return getSpan();
        }
        position -= mHeaders.size();
//        if(mIntermediary.getItem(position) instanceof IGridItem){
//            return ((IGridItem) mIntermediary.getItem(position)).getGridSpan();
//        }
        return 1;
    }

    private int getSpan(){
        if(layoutManager instanceof GridLayoutManager){
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return 1;
    }


    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null &&
                lp instanceof StaggeredGridLayoutManager.LayoutParams &&
                holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER||getItemViewType(position) == TYPE_FOOTER ?
                            gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    private GridLayoutManager.SpanSizeLookup mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup(){
        @Override
        public int getSpanSize(int position) {
            return getGridSpan(position);
        }
    };
    private AdapterDataObserver adapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart + getHeadSize(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart + getHeadSize(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart + getHeadSize(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemMoved(fromPosition + getHeadSize(), toPosition + getHeadSize());
        }
    };

    public T getRealAdapter(){
        return mAdapter;
    }
}
