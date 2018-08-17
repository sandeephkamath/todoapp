package com.example.myapplication.todoadd;

public interface TodoAddContract {

    interface View {
        String getTitleText();

        String getDescriptionText();

        String getTagText();

        void finish();
    }

    interface Presenter {
        void addTodoItem();
    }
}
