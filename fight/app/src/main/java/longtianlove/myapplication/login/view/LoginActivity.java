package longtianlove.myapplication.login.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import longtianlove.bottomlib.base.BaseActivity;
import longtianlove.myapplication.R;
import longtianlove.myapplication.login.presenter.LoginPresenter;
import longtianlove.myapplication.util.widget.TextAnimationLayout;

/**
 * Created by long on 2016/12/11.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginView{
    private RelativeLayout login_root;
    private TextView login_edit;
    private TextView login_switch_role;
    private TextView login_mobile_tv;
    private TextView login_account_tv;
    private View login_mobileselect_view;
    private View login_accountselect_view;
    /*****
     * 普通登录
     ******/
    private View login_accountlogin_container;
    private EditText login_username;
    private EditText login_password;
    private CheckBox login_show_password;
    /*****
     * 快捷登录
     ******/
    private View login_quicklogin_container;
    private EditText login_quickmobile_ed;
    private EditText login_quickpassword_ed;
    private TextView login_quickmsg_tv;
    /**********
     * 第三方登录container
     ******/
    private View thirdContainerView;


//    private MsgCodeTimer msgCodeTimer;

    private boolean isMsgCodeTimer;
    private Button login_login;
    private TextView login_forget_pwd;

    private boolean isStartAnimation;//动画是否进行中，避免连续动画的出现


//    private NewCommonLoginPresenter newCommonLoginPresenter;
//    private NewQuickLoginPresenter newQuickLoginPresenter;
    LoginPresenter loginPresenter=new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chr_login);
        initView();
        initListener();
        initData(savedInstanceState);
    }


    private void initView() {
        login_root = (RelativeLayout) findViewById(R.id.login_root);
        login_edit = (TextView) findViewById(R.id.login_edit);
        login_switch_role = (TextView) findViewById(R.id.login_switch_role);
        ((TextAnimationLayout) findViewById(R.id.login_username_layout)).setDelView(R.id.login_username_del);
        findViewById(R.id.login_username_del).setOnClickListener(this);
        login_username = (EditText) findViewById(R.id.login_username);
        ((TextAnimationLayout) findViewById(R.id.login_password_layout)).setDelView(R.id.login_password_del);
        findViewById(R.id.login_password_del).setOnClickListener(this);
        login_password = (EditText) findViewById(R.id.login_password);
        login_show_password = (CheckBox) findViewById(R.id.login_show_password);
        login_login = (Button) findViewById(R.id.login_login);
        login_forget_pwd = (TextView) findViewById(R.id.login_forget_pwd);
        login_mobile_tv = (TextView) findViewById(R.id.login_mobile_tv);
        login_account_tv = (TextView) findViewById(R.id.login_account_tv);
        login_mobileselect_view = findViewById(R.id.login_mobileselect_view);
        login_accountselect_view = findViewById(R.id.login_accountselect_view);
        login_accountlogin_container = findViewById(R.id.login_input_container);
        login_quicklogin_container = findViewById(R.id.login_quicklogin_container);
        ((TextAnimationLayout) findViewById(R.id.login_quickmobile_layout)).setDelView(R.id.login_quickmobiledel_btn);
        findViewById(R.id.login_quickmobiledel_btn).setOnClickListener(this);
        login_quickmobile_ed = (EditText) findViewById(R.id.login_quickmobile_ed);
        ((TextAnimationLayout) findViewById(R.id.login_quickpassword_layout)).setDelView(R.id.login_quickmsgcodedel_btn);
        findViewById(R.id.login_quickmsgcodedel_btn).setOnClickListener(this);
        login_quickpassword_ed = (EditText) findViewById(R.id.login_quickpassword_ed);
        login_quickmsg_tv = (TextView) findViewById(R.id.login_quickmsg_tv);


        thirdContainerView = findViewById(R.id.login_extra_container);
    }
    private void initListener() {
        login_root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int height;

            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                login_root.getWindowVisibleDisplayFrame(rect);
                if (height == 0) {
                    height = rect.height();
                }
                if (height - rect.height() > 300) {
                    onSoftInputVisible();
                } else if (height - rect.height() < -300) {
                    onSoftInputHiddle();
                }
                height = rect.height();
            }
        });
        login_edit.setOnClickListener(this);
        login_switch_role.setOnClickListener(this);
        login_mobile_tv.setOnClickListener(this);
        login_account_tv.setOnClickListener(this);
        login_show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
//                PBIManager.updatePoint(new PBIPointer(PBIConstant.B_LOGIN).putPBI(PBIConstant.B_LOGIN_02));
            }
        });
        login_login.setOnClickListener(this);
        login_forget_pwd.setOnClickListener(this);
        login_quickmsg_tv.setOnClickListener(this);
        login_quickmobile_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isMsgCodeTimer) {
                    return;
                }
                if (s.toString().trim().length() > 10) {
                    setMsgClick(true);
                } else {
                    setMsgClick(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    accountLogin();
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
        login_quickpassword_ed.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    quickLogin();
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }
    private void initData(Bundle savedInstanceState) {
        login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        showLoginView(false, false);
        setMsgClick(false);
//        scaleInAnimation(login_mobileselect_view);
        login_username.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        login_password.setImeOptions(EditorInfo.IME_ACTION_GO);
//        msgCodeTimer = new MsgCodeTimer(60000, 1000);/
//        //todo im相互踢，设置名字
//        if (savedInstanceState == null) {
//            if (getIntent().hasExtra(IntentConst.EXTRA_KEY_KICKEDOFF) && getIntent().getBooleanExtra(IntentConst.EXTRA_KEY_KICKEDOFF, false)) {
//                DialogUtil.showLogoutDialog(this);
//            }
//        }
//        setCuserName();
//
//        newCommonLoginPresenter = new NewCommonLoginPresenter(this);
//        newQuickLoginPresenter = new NewQuickLoginPresenter(this);


    }
    /**
     * 设置获取验证码的是否可点击
     *
     * @param isClick true可点击
     */
    private void setMsgClick(boolean isClick) {
        login_quickmsg_tv.setClickable(isClick);
        if (isClick) {
            login_quickmsg_tv.setTextColor(Color.parseColor("#FFFF5A5A"));
        } else {
            login_quickmsg_tv.setTextColor(Color.parseColor("#FFB3B3B3"));
        }
    }

    /**
     * 键盘收起时显示的布局
     */
    private void onSoftInputHiddle() {
        thirdContainerView.setVisibility(View.VISIBLE);
    }
    /**
     * 键盘弹出时隐藏的布局
     */
    private void onSoftInputVisible() {
        thirdContainerView.setVisibility(View.GONE);
    }


    void quickLogin(){
        String phone = login_quickmobile_ed.getText().toString().trim();
        String msgCode = login_quickpassword_ed.getText().toString().trim();
        loginPresenter.userLogin(phone,msgCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_edit://跳转注册页面
//                LegoUtil.writeClientLog("clogin","cregist");
//                performEdit();
//                PBIManager.updatePoint(new PBIPointer(PBIConstant.B_LOGIN).putPBI(PBIConstant.B_LOGIN_05));
                //todo 跳转到注册页面
//                LegoUtil.writeClientLog("login", "regist");
//                PBIManager.updatePoint(new PBIPointer(PBIConstant.C_LOGIN_PAGE).putPBI(PBIConstant.C_LOGIN_BLOCK01));
//                startActivity(new Intent(this, NewRegisterActivity.class));
                break;
            case R.id.login_switch_role:
//                LegoUtil.writeClientLog("clogin","change");
//                performSwitchRole();
//                PBIManager.updatePoint(new PBIPointer(PBIConstant.B_LOGIN).putPBI(PBIConstant.B_LOGIN_01));
//                //todo 切换账号
//                DialogUtil.showTwoButtonDialog(this, "确认切换身份？", "取 消", "确 认", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                }, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SelectClientInstance.getSelectClientInstance().setSelectClient(SelectClientInstance.selectClient.COMPANYCLIENT);
//                        startActivity(new Intent(NewLoginActivity.this, LoginActivity.class));
//                        finish();
//                    }
//                });
                break;
            case R.id.login_username_del: {
                login_username.setText("");
            }
            break;
            case R.id.login_password_del: {
                login_password.setText("");
            }
            break;
            case R.id.login_quickmobiledel_btn:
                login_quickmobile_ed.setText("");
                break;
            case R.id.login_quickmsgcodedel_btn:
                login_quickpassword_ed.setText("");
                break;
            case R.id.login_login:
//                //todo 点击登录，包括快捷登录和普通登陆
//                if (!DoubleClickUtil.isFastMiniDoubleClick()) {// 防止暴力点击
//                    PBIManager.updatePoint(new PBIPointer(PBIConstant.C_LOGIN_PAGE).putPBI(PBIConstant.C_LOGIN_BLOCK04));
//                    if (login_accountlogin_container.isShown()) {
//                        LegoUtil.writeClientLog("normlogin", "normallogin_login");
//                        accountLogin();
//                    } else {
//                        LegoUtil.writeClientLog("phonecode", "msglogin_login");
                        quickLogin();
//                    }
//                }

                break;
            case R.id.login_forget_pwd:
           //     //todo 跳转到忘记密码
//                LegoUtil.writeClientLog("clogin","pwforgetclick");
//                performForgetPwd();
//                PBIManager.updatePoint(new PBIPointer(PBIConstant.B_LOGIN).putPBI(PBIConstant.B_LOGIN_04));
//                LegoUtil.writeClientLog("normlogin", "normallogin_snforget");
//                PBIManager.updatePoint(new PBIPointer(PBIConstant.C_LOGIN_PAGE).putPBI(PBIConstant.C_LOGIN_BLOCK05));
//                startActivity(new Intent(this, NewForgetPasswordActivity.class));

                break;
            case R.id.login_mobile_tv:
//                if (!DoubleClickUtil.isFastMiniDoubleClick()) {// 防止暴力点击
//                    if (login_accountlogin_container.isShown()) {
//                        showLoginView(true, true);
//                    } else {
//                        showLoginView(true, false);
//                    }
//                }
                break;
            case R.id.login_account_tv:
//                if (!DoubleClickUtil.isFastMiniDoubleClick()) {// 防止暴力点击
//                    if (login_accountlogin_container.isShown()) {
//                        showLoginView(false, false);
//                    } else {
//                        showLoginView(false, true);
//                    }
//                }
                break;
            case R.id.login_quickmsg_tv:
//                loginPersenter.companySendMsg(login_quickmobile_ed.getText().toString().trim());
//                //todo 发送验证码
//                LegoUtil.writeClientLog("phonecode", "msglogin_getmsg");
//                newQuickLoginPresenter.phoneFromatCheck(login_quickmobile_ed.getText().toString().trim());
//                PBIManager.updatePoint(new PBIPointer(PBIConstant.C_LOGIN_PAGE).putPBI(PBIConstant.C_LOGIN_BLOCK08));
                break;
        }
    }

    @Override
    public void NetworkOK() {

    }

    @Override
    public void NetworkError() {

    }
}
