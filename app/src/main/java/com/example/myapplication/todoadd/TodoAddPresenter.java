package com.example.myapplication.todoadd;

import com.example.myapplication.model.TodoItem;

import java.util.Calendar;

import io.realm.Realm;

public class TodoAddPresenter implements TodoAddContract.Presenter {

    private TodoAddContract.View view;

    TodoAddPresenter(TodoAddContract.View view) {
        this.view = view;
    }

    @Override
    public void addTodoItem() {
        if (view != null) {
            String title = view.getTitleText();
            String description = view.getDescriptionText();
            String tag = view.getTagText();
            TodoItem todoItem = new TodoItem();
            todoItem.setDescription(description);
            todoItem.setTag(tag);
            todoItem.setTitle(title);
            todoItem.setId(Calendar.getInstance().getTimeInMillis());
            saveTodoItem(todoItem);
        }
    }

    private void saveTodoItem(TodoItem item) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(item);
        realm.commitTransaction();
        if (view != null) {
            view.finish();
        }
    }
}
