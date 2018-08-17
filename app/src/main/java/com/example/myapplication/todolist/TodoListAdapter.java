package com.example.myapplication.todolist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.TodoItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TodoListAdapter extends RealmRecyclerViewAdapter<TodoItem, TodoListAdapter.TodoHolder> {

    TodoListAdapter(@Nullable OrderedRealmCollection<TodoItem> data, boolean autoUpdate) {
        super(data, autoUpdate);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_holder, parent, false);
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        TodoItem item = getItem(position);
        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.tag.setText(item.getTag());
            holder.description.setText(item.getDescription());
        }
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }

    class TodoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.tag)
        TextView tag;

        private TodoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
