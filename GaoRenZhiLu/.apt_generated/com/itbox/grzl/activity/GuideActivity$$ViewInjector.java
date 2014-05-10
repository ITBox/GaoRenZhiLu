// Generated code from Butter Knife. Do not modify!
package com.itbox.grzl.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GuideActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itbox.grzl.activity.GuideActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034124, "field 'mViewPager'");
    target.mViewPager = (android.support.v4.view.ViewPager) view;
  }

  public static void reset(com.itbox.grzl.activity.GuideActivity target) {
    target.mViewPager = null;
  }
}
