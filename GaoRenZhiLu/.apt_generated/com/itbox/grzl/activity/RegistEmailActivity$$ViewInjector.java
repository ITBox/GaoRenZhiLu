// Generated code from Butter Knife. Do not modify!
package com.itbox.grzl.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class RegistEmailActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itbox.grzl.activity.RegistEmailActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034142, "field 'mLLEmailEmail'");
    target.mLLEmailEmail = (com.itbox.fx.widget.CellView) view;
    view = finder.findRequiredView(source, 2131034143, "field 'mLLEmailPass'");
    target.mLLEmailPass = (com.itbox.fx.widget.CellView) view;
    view = finder.findRequiredView(source, 2131034151, "field 'mSubmit' and method 'registEmailAccount'");
    target.mSubmit = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.registEmailAccount();
        }
      });
    view = finder.findRequiredView(source, 2131034211, "field 'mTVTopCancel' and method 'cancelAct'");
    target.mTVTopCancel = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.cancelAct();
        }
      });
    view = finder.findRequiredView(source, 2131034145, "field 'mLLEmailSex'");
    target.mLLEmailSex = (com.itbox.fx.widget.CellView) view;
    view = finder.findRequiredView(source, 2131034147, "field 'mLLEmailArea'");
    target.mLLEmailArea = (com.itbox.fx.widget.CellView) view;
    view = finder.findRequiredView(source, 2131034148, "field 'mLLEmailType'");
    target.mLLEmailType = (com.itbox.fx.widget.CellView) view;
    view = finder.findRequiredView(source, 2131034212, "field 'mTVTopMedium'");
    target.mTVTopMedium = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131034144, "field 'mLLEmailName'");
    target.mLLEmailName = (com.itbox.fx.widget.CellView) view;
    view = finder.findRequiredView(source, 2131034146, "field 'mLLEmailBirthday'");
    target.mLLEmailBirthday = (com.itbox.fx.widget.CellView) view;
    view = finder.findRequiredView(source, 2131034150, "method 'clickClause'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.clickClause();
        }
      });
  }

  public static void reset(com.itbox.grzl.activity.RegistEmailActivity target) {
    target.mLLEmailEmail = null;
    target.mLLEmailPass = null;
    target.mSubmit = null;
    target.mTVTopCancel = null;
    target.mLLEmailSex = null;
    target.mLLEmailArea = null;
    target.mLLEmailType = null;
    target.mTVTopMedium = null;
    target.mLLEmailName = null;
    target.mLLEmailBirthday = null;
  }
}
