package com.fyshadows.collegemate;

import java.util.List;
import android.app.Application;

public interface FetchCommentsListener {
    public void onFetchCommentsComplete(List<CommentList> data);
    public void onFetchCommentsFailure(String msg);
}
