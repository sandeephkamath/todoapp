package com.example.myapplication.todolist;

import com.example.myapplication.model.TodoItem;

import io.realm.RealmResults;

public interface TodoListContract {

    interface View {
        void showNoItemsView();

        void showTodoItems(RealmResults<TodoItem> items);
    }

    interface Presenter {

    }
}
