package com.example.myapplication.todolist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.TodoItem;
import com.example.myapplication.todoadd.TodoAddActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class TodoListActivity extends AppCompatActivity implements TodoListContract.View {

    @BindView(R.id.todo_list)
    RecyclerView todoList;

    @BindView(R.id.no_items_text)
    TextView noItemsView;

    TodoListAdapter adapter;

    TodoListPresenter presenter;
    Realm realm;

    RealmResults<TodoItem> todoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        presenter = new TodoListPresenter(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFAB();
        setupTodoList();
    }

    private void setupTodoList() {
        todoItems = realm.where(TodoItem.class).findAll();
        adapter = new TodoListAdapter(todoItems, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        todoList.setLayoutManager(layoutManager);
        todoList.setAdapter(adapter);
        todoList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        TouchHelperCallback touchHelperCallback = new TouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(touchHelperCallback);
        touchHelper.attachToRecyclerView(todoList);

        todoItems.addChangeListener(new RealmChangeListener<RealmResults<TodoItem>>() {
            @Override
            public void onChange(@NonNull RealmResults<TodoItem> items) {
                if (items.size() <= 0) {
                    showNoItemsView();
                } else {
                    todoList.setVisibility(View.VISIBLE);
                    noItemsView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void showNoItemsView() {
        todoList.setVisibility(View.GONE);
        noItemsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTodoItems(RealmResults<TodoItem> items) {
        //adapter.updateItems(items);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTodoAddActivity();
            }
        });
    }

    private void startTodoAddActivity() {
        Intent intent = new Intent(this, TodoAddActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        setSearchListener(menu);
        return true;
    }

    private void setSearchListener(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        if (null != searchView && searchManager != null) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                updateData(newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                updateData(query);
                return true;
            }
        };
        if (searchView != null) {
            searchView.setOnQueryTextListener(queryTextListener);
        }
    }

    private void updateData(String tag) {
        if (TextUtils.isEmpty(tag)) {
            todoItems = realm.where(TodoItem.class).findAll();
        } else {
            todoItems = realm.where(TodoItem.class).equalTo(TodoItem.TAG, tag).findAll();
        }
        adapter.updateData(todoItems);
    }


    private class TouchHelperCallback extends ItemTouchHelper.SimpleCallback {

        TouchHelperCallback() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            deleteTodoItem(viewHolder);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }

    private void deleteTodoItem(RecyclerView.ViewHolder viewHolder) {
        RealmResults<TodoItem> result = realm.where(TodoItem.class).equalTo(TodoItem.Id, viewHolder.getItemId()).findAll();
        realm.beginTransaction();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }


}
