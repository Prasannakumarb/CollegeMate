package com.fyshadows.collegemate;

import java.util.List;
import android.app.Application;

public interface FetchDataListener {
    public void onFetchComplete(List<DiscussionList> data);
    public void onFetchFailure(String msg);
}
