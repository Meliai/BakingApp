package com.rudainc.bakingapp.async;

import com.rudainc.bakingapp.models.BakingSample;

import java.util.ArrayList;

public interface OnGetDataCompleted {
    void onGetDataCompleted(ArrayList<BakingSample> data);

    void onGetDataError(String message);
}
