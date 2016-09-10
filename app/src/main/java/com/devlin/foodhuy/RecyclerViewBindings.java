package com.devlin.foodhuy;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;


import com.devlin.core.view.BaseRecyclerViewAdapter;
import com.devlin.foodhuy.adapters.BindingRecyclerViewAdapter;
import com.devlin.foodhuy.adapters.ClickHandler;
import com.devlin.foodhuy.adapters.LongClickHandler;
import com.devlin.foodhuy.adapters.binder.ItemBinder;

import java.util.Collection;

public class RecyclerViewBindings
{
    private static final int KEY_ITEMS = -123;
    private static final int KEY_CLICK_HANDLER = -124;
    private static final int KEY_LONG_CLICK_HANDLER = -125;

    @SuppressWarnings("unchecked")
    @BindingAdapter("items")
    public static <T> void setItems(RecyclerView recyclerView, Collection<T> items)
    {
        if (recyclerView.getAdapter() instanceof BaseRecyclerViewAdapter) {
            BaseRecyclerViewAdapter adapter = (BaseRecyclerViewAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                adapter.setData(items);
            }
            return;
        }


        BindingRecyclerViewAdapter adapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter != null)
        {
            adapter.setItems(items);
        }
        else
        {
            recyclerView.setTag(KEY_ITEMS, items);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("clickHandler")
    public static <T> void setHandler(RecyclerView recyclerView, ClickHandler<T> handler)
    {
        BindingRecyclerViewAdapter adapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter != null)
        {
            adapter.setClickHandler(handler);
        }
        else
        {
            recyclerView.setTag(KEY_CLICK_HANDLER, handler);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("longClickHandler")
    public static <T> void setHandler(RecyclerView recyclerView, LongClickHandler<T> handler)
    {
        BindingRecyclerViewAdapter adapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter != null)
        {
            adapter.setLongClickHandler(handler);
        }
        else
        {
            recyclerView.setTag(KEY_LONG_CLICK_HANDLER, handler);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("itemViewBinder")
    public static <T> void setItemViewBinder(RecyclerView recyclerView, ItemBinder<T> itemViewMapper)
    {
        Collection<T> items = (Collection<T>) recyclerView.getTag(KEY_ITEMS);
        ClickHandler<T> clickHandler = (ClickHandler<T>) recyclerView.getTag(KEY_CLICK_HANDLER);
        BindingRecyclerViewAdapter adapter = new BindingRecyclerViewAdapter(itemViewMapper, items);
        if(clickHandler != null)
        {
            adapter.setClickHandler(clickHandler);
        }
        recyclerView.setAdapter(adapter);
    }
}