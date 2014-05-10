// Generated code from Butter Knife. Do not modify!
package com.itbox.grzl.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itbox.grzl.activity.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 16908306, "field 'mTabHost'");
    target.mTabHost = (android.support.v4.app.FragmentTabHost) view;
  }

  public static void reset(com.itbox.grzl.activity.MainActivity target) {
    target.mTabHost = null;
  }
}
