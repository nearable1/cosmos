package com.xiaoniu.call.customer.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.AccountManageDTO;
import com.xiaoniu.call.customer.api.dto.ManagerDTO;
import com.xiaoniu.call.customer.api.dto.UserLoginDTO;
import com.xiaoniu.call.customer.api.vo.AccountManagerVO;
import com.xiaoniu.call.customer.api.vo.ManagerVO;

public interface AccountManageService {

    PageResult<AccountManageDTO> accountManageList(AccountManagerVO accountManagerVO);

    PageResult<UserLoginDTO> userLoginLoglist(AccountManagerVO accountManagerVO);

    PageResult<ManagerDTO> managerList(ManagerVO managerVO);
}
