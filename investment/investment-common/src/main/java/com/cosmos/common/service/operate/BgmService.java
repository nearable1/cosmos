package com.cosmos.common.service.operate;


import com.cosmos.common.entity.pojo.Bgm;

import java.util.List;

public interface BgmService {

    List<Bgm> quweryBgmList();

    Bgm queryBgmById(String bgmId);
}
