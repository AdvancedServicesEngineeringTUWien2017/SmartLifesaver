package com.filip.lifesaverandroid.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.filip.lifesaverandroid.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract recycler view adapter to display any subclass of BaseEntity.
 * @param <K>
 */
public abstract class AbsBaseEntityRecyclerViewAdapter<K extends BaseEntity> extends RecyclerView.Adapter<AbsBindViewHolder<K>> {

    private List<K> recyclerViewItems;
    protected Context context;

    public AbsBaseEntityRecyclerViewAdapter(Context context) {
        this.context = context;
        this.recyclerViewItems = new ArrayList<>();
    }


    public AbsBaseEntityRecyclerViewAdapter(Context context, List<K> recyclerViewItems) {
        this.context = context;
        this.recyclerViewItems = recyclerViewItems;
    }

    @Override
    public void onBindViewHolder(AbsBindViewHolder<K> holder, int position) {
        K item = recyclerViewItems.get(position);
        holder.bindView(item, position);
    }

    public void addAllItems(List<K> items) {
        if(items != null) {
            recyclerViewItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public K addItem(K item) {
        recyclerViewItems.add(item);
        notifyItemInserted(recyclerViewItems.size() - 1);
        return item;
    }

    public K addItemToPosition(K item, int position) {
        recyclerViewItems.add(position, item);
        notifyItemInserted(position);
        return item;
    }

    public void removeItem(K item) {
        int pos = recyclerViewItems.indexOf(item);
        if(pos != -1) {
            recyclerViewItems.remove(pos);
            notifyItemRemoved(pos);
        }
    }


    public K getItem(int position) {
        if(position < 0 || position > getItemCount()) {
            return null;
        }
        return recyclerViewItems.get(position);
    }

    public void clear() {
        recyclerViewItems.clear();
    }

    public List<K> getContent() {
        return recyclerViewItems;
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }
}

abstract class AbsBindViewHolder<K extends BaseEntity> extends RecyclerView.ViewHolder {
    public AbsBindViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindView(K entity, int pos);
}