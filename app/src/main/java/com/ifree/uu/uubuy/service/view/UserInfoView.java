package com.ifree.uu.uubuy.service.view;

import com.ifree.uu.uubuy.service.entity.UserInfoEntity;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/9/6.
 * Description:
 */
public interface UserInfoView extends View {
    void onSuccess(UserInfoEntity mUserInfoEntity);
    void onError(String result);
}
