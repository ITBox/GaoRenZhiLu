// Generated code from Butter Knife. Do not modify!
package com.itbox.grzl.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LoginActicity$$ViewInjector {
  public static void inject(Finder finder, final com.itbox.grzl.activity.LoginActicity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034133, "field 'mTVLoginRegist' and method 'userRegist'");
    target.mTVLoginRegist = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.userRegist();
        }
      });
    view = finder.findRequiredView(source, 2131034132, "field 'mTVLoginFind' and method 'userFindPass'");
    target.mTVLoginFind = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.userFindPass();
        }
      });
    view = finder.findRequiredView(source, 2131034129, "field 'mETLoginUserName'");
    target.mETLoginUserName = (com.itbox.fx.widget.CellView) view;
    view = finder.findRequiredView(source, 2131034130, "field 'mETLoginPassword'");
    target.mETLoginPassword = (com.itbox.fx.widget.CellView) view;
    view = finder.findRequiredView(source, 2131034131, "method 'userLogin'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.userLogin();
        }
      });
  }

  public static void reset(com.itbox.grzl.activity.LoginActicity target) {
    target.mTVLoginRegist = null;
    target.mTVLoginFind = null;
    target.mETLoginUserName = null;
    target.mETLoginPassword = null;
  }
}
