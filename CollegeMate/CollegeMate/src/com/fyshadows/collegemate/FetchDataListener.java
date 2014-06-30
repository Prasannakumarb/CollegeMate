package com.fyshadows.collegemate;

import java.util.List;

public interface FetchDataListener {
    public void onFetchComplete(List<DiscussionList> data);
    public void onFetchFailure(String msg);
}
