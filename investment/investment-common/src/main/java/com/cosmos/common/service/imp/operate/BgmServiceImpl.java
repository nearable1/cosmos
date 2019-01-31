package com.cosmos.common.service.imp.operate;

import com.cosmos.common.dao.mapper.BgmMapper;
import com.cosmos.common.entity.pojo.Bgm;
import com.cosmos.common.idworker.Sid;
import com.cosmos.common.service.operate.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 1819014975
 * @Title: UserServiceImpl
 * @ProjectName imooc-videos-dev
 * @Description: TODO
 * @date 2018/10/10 9:52
 */
@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private Sid sid;

    /**
     * 查询所有背景音乐
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Bgm> quweryBgmList() {
        return bgmMapper.selectAll();
    }

    /**
     * 根据id查询背景音乐信息
     * @param bgmId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Bgm queryBgmById(String bgmId){
        return bgmMapper.selectByPrimaryKey(bgmId);
    }
}
