package com.devlin.core.viewmodel;

import android.databinding.Bindable;
import android.widget.Toast;

import com.devlin.core.BR;
import com.devlin.core.model.entities.Comment;
import com.devlin.core.model.entities.Restaurant;
import com.devlin.core.model.responses.APIResponse;
import com.devlin.core.model.services.clouds.ICommentService;
import com.devlin.core.model.services.storages.RestaurantModel;
import com.devlin.core.view.INavigator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 08-Aug-16.
 */
public class CommentViewModel extends BaseViewModel {

    //region Properties

    private String mContent;

    private Restaurant mRestaurant;

    private ICommentService mCommentService;

    //endregion

    //region Getter and Setter

    @Bindable
    public Restaurant getRestaurant() {
        return mRestaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;

        notifyPropertyChanged(BR.restaurant);
    }

    @Bindable
    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;

        notifyPropertyChanged(BR.content);
    }

    //endregion

    //region Constructors

    public CommentViewModel(INavigator navigator, ICommentService commentService) {
        super(navigator);

        mCommentService = commentService;

    }

    //endregion

    //region Override Methods

    @Override
    public void onCreate() {
        super.onCreate();

        register();

        mContent = "";
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregister();

        mRestaurant = null;
        mContent = null;
    }

    @Override
    public INavigator getNavigator() {
        return super.getNavigator();
    }

    //endregion

    //region Subcribe Methods

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void event(Restaurant restaurant) {
        setRestaurant(restaurant);
    }

    //endregion

    //region Public methods

    public void addCommentCommand() {
        if (validate()) {
            final Comment comment = new Comment();
            comment.setContent(mContent);
            comment.setCommenterId(getNavigator().getApplication().getLoginUser().getId());
            comment.setTitle("");
            comment.setRestaurantId(mRestaurant.getId());

            mCommentService.add(comment).enqueue(new Callback<APIResponse<Comment>>() {
                @Override
                public void onResponse(Call<APIResponse<Comment>> call, Response<APIResponse<Comment>> response) {
                    if (response != null && response.isSuccessful()
                            && response.body() != null
                            && response.body().isSuccess()) {
                        post(comment);
                    }
                    showToast("Viết bình luận thất bại, vui lòng kiểm tra lại kết nối mạng", Toast.LENGTH_LONG);

                }

                @Override
                public void onFailure(Call<APIResponse<Comment>> call, Throwable t) {
                    showToast("Viết bình luận thất bại, vui lòng kiểm tra lại kết nối mạng", Toast.LENGTH_LONG);
                }
            });

            getNavigator().goBack();
        }
    }

    public boolean validate() {
        if (mContent != null && !mContent.equals("")) {
            return true;
        }

        showToast("Hãy điền nội dung bình luận trước khi Gửi", Toast.LENGTH_LONG);
        return false;
    }

    //endregion

}
