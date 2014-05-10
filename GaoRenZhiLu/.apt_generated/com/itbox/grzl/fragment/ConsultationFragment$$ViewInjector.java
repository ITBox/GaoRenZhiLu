// Generated code from Butter Knife. Do not modify!
package com.itbox.grzl.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ConsultationFragment$$ViewInjector {
  public static void inject(Finder finder, final com.itbox.grzl.fragment.ConsultationFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034212, "field 'mTitleTv'");
    target.mTitleTv = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131034198, "field 'mGridView'");
    target.mGridView = (android.widget.GridView) view;
    view = finder.findRequiredView(source, 2131034197, "method 'onClick'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131034196, "method 'onClick'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(com.itbox.grzl.fragment.ConsultationFragment target) {
    target.mTitleTv = null;
    target.mGridView = null;
  }
}
