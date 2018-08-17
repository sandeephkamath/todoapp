package com.example.myapplication.todoadd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoAddActivity extends AppCompatActivity implements TodoAddContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.title)
    EditText title;

    @BindView(R.id.description)
    EditText description;

    @BindView(R.id.tag)
    EditText tag;

    @OnClick(R.id.add)
    void onAddClick() {
        presenter.addTodoItem();
    }

    TodoAddContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_add);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        presenter = new TodoAddPresenter(this);
    }


    @Override
    public String getTitleText() {
        return title.getText().toString();
    }

    @Override
    public String getDescriptionText() {
        return description.getText().toString();
    }

    @Override
    public String getTagText() {
        return tag.getText().toString();
    }

}
