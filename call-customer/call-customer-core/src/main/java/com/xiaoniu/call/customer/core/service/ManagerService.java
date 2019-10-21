package com.xiaoniu.call.customer.core.service;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.call.customer.api.dto.AccountManageDTO;
import com.xiaoniu.call.customer.api.dto.ManagerDTO;
import com.xiaoniu.call.customer.api.dto.UserLoginDTO;
import com.xiaoniu.call.customer.api.vo.AccountManagerVO;
import com.xiaoniu.call.customer.api.vo.ManagerVO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ManagerService {

    void saveManager(Long uid);

    void enableManager(Long id, Boolean status);

    void deleteManager(Long id);
}
