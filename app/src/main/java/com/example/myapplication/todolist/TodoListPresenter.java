package com.example.myapplication.todolist;

import android.support.annotation.NonNull;

import com.example.myapplication.model.TodoItem;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

class TodoListPresenter implements TodoListContract.Presenter {

    private TodoListContract.View view;

    TodoListPresenter(TodoListContract.View view) {
        this.view = view;
       // setupChangeListener();
    }

    private void setupChangeListener() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<TodoItem> items = realm.where(TodoItem.class).findAll();
        items.addChangeListener(new RealmChangeListener<RealmResults<TodoItem>>() {
            @Override
            public void onChange(@NonNull RealmResults<TodoItem> todoItems) {
                view.showTodoItems(todoItems);
            }
        });
    }


}
