// Generated code from Butter Knife. Do not modify!
package com.itbox.grzl.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class QuickAskActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itbox.grzl.activity.QuickAskActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034212, "field 'mTitleTv'");
    target.mTitleTv = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131034136, "field 'mTitleEt'");
    target.mTitleEt = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131034137, "field 'mContentEt'");
    target.mContentEt = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131034140, "method 'onClick'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.itbox.grzl.activity.QuickAskActivity target) {
    target.mTitleTv = null;
    target.mTitleEt = null;
    target.mContentEt = null;
  }
}
