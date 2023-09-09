package org.techtown.qself;

import android.view.View;

public interface OnQuestionClickListener {
    public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position);
}
