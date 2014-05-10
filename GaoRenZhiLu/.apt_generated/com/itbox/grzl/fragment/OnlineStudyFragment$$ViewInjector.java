// Generated code from Butter Knife. Do not modify!
package com.itbox.grzl.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class OnlineStudyFragment$$ViewInjector {
  public static void inject(Finder finder, final com.itbox.grzl.fragment.OnlineStudyFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034199, "field 'mGridView' and method 'onItemClick'");
    target.mGridView = (android.widget.GridView) view;
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p0, p1, p2, p3);
        }
      });
    view = finder.findRequiredView(source, 2131034212, "field 'mTitleTv'");
    target.mTitleTv = (android.widget.TextView) view;
  }

  public static void reset(com.itbox.grzl.fragment.OnlineStudyFragment target) {
    target.mGridView = null;
    target.mTitleTv = null;
  }
}
