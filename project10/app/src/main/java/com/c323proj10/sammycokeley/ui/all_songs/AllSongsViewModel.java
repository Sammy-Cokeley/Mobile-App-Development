package com.c323proj10.sammycokeley.ui.all_songs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllSongsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllSongsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is all songs fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}