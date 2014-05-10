// Generated code from Butter Knife. Do not modify!
package com.itbox.grzl.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ConsultationSearchActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itbox.grzl.activity.ConsultationSearchActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034212, "field 'mTitleTt'");
    target.mTitleTt = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131034123, "field 'mListView'");
    target.mListView = (android.widget.ListView) view;
  }

  public static void reset(com.itbox.grzl.activity.ConsultationSearchActivity target) {
    target.mTitleTt = null;
    target.mListView = null;
  }
}
