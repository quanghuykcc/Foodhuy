package com.devlin.foodhuy.activities;

import android.view.Menu;
import android.view.MenuItem;

import com.devlin.core.model.entities.Bundle;
import com.devlin.core.view.BaseActivity;
import com.devlin.core.view.Constants;
import com.devlin.core.viewmodel.CommentViewModel;
import com.devlin.foodhuy.App;
import com.devlin.foodhuy.R;
import com.devlin.foodhuy.BR;
import com.devlin.foodhuy.databinding.ActivityCommentBinding;

public class CommentActivity extends BaseActivity<ActivityCommentBinding, CommentViewModel> {

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        App.sharedComponent().inject(this);

        super.onCreate(savedInstanceState);

        setBindingContentView(R.layout.activity_comment, BR.viewModel);

        setToolbar(R.id.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle("Viết bình luận");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_comment_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_comment) {
            Bundle bundle = new Bundle(Constants.ACTION_ADD_COMMENT);
            mViewModel.getEventBus().post(bundle);

            return true;
        }

        else if (id == android.R.id.home) {
            onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
