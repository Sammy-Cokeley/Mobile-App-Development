package com.c323proj10.sammycokeley.ui.all_videos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllVideosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllVideosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is all videos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}