/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sm.tm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.entity.SnInfo;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.sm.tm.dao.TmTestingDao;
import com.inbody.crm.sm.tm.dao.TmTestingDtlDao;
import com.inbody.crm.sm.tm.entity.TmTesting;
import com.inbody.crm.sm.tm.entity.TmTestingDtl;

/**
 * 主子表Service
 * @author yangj
 * @version 2017-09-20
 */
@Service
@Transactional(readOnly = true)
public class TmTestingService extends CrudService<TmTestingDao, TmTesting> {

	@Autowired
	private TmTestingDtlDao tmTestingDtlDao;
	@Autowired
	private TmTestingDao tmTestingDao;
    @Autowired
    private AttachmentsService attachmentsService;
	
	public TmTesting get(String id) {
		TmTesting tmTesting = super.get(id);
		tmTesting.setTmTestingDtlList(tmTestingDtlDao.findList(new TmTestingDtl(tmTesting)));
		// 附件信息取得
        List<CoAttachments> attachmentsList = attachmentsService
                .getAttachmentList(tmTesting.getTestingNo());
        tmTesting.setAttachmentsList(attachmentsList);
        
		return tmTesting;
	}
	
	public List<TmTesting> findList(TmTesting tmTesting) {
		return super.findList(tmTesting);
	}
	
	public List<TmTesting> findListLatest(TmTesting tmTesting) {
		return tmTestingDao.findPageLatest(tmTesting);
	}
	
	public Page<TmTesting> findPage(Page<TmTesting> page, TmTesting tmTesting) {
		return super.findPage(page, tmTesting);
	}
	
	public Page<TmTesting> findPageLatest(Page<TmTesting> page, TmTesting tmTesting) {
		tmTesting.setPage(page);
		page.setList(tmTestingDao.findPageLatest(tmTesting));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(TmTesting tmTesting) {
		super.save(tmTesting);
		
		tmTestingDtlDao.deleteByTestingNo(tmTesting.getTestingNo());
		for (TmTestingDtl tmTestingDtl : tmTesting.getTmTestingDtlList()){
			if (tmTestingDtl.getId() == null){
				continue;
			}
			if (TmTestingDtl.DEL_FLAG_NORMAL.equals(tmTestingDtl.getDelFlag())){
				tmTestingDtl.setTestingNo(tmTesting.getTestingNo());
				tmTestingDtl.preInsert();
				tmTestingDtlDao.insert(tmTestingDtl);
				
				// 更新SN的机器状态
		        TmTestingDtl tmTestingUpdate = new TmTestingDtl();
		        if("0".equals(tmTesting.getTestingResult())){
		        	tmTestingUpdate.setStatus("2");
		        }else if("1".equals(tmTesting.getTestingResult())){
		        	tmTestingUpdate.setStatus("3");
		        }
		        tmTestingUpdate.setSnNo(tmTestingDtl.getSnNo());
		        tmTestingUpdate.setSnVersion(tmTestingDtl.getSnVersion());
		        tmTestingDao.updateSnInfo(tmTestingUpdate);
			}
		}
		/*for (TmTestingDtl tmTestingDtl : tmTesting.getTmTestingDtlList()){
			if (tmTestingDtl.getId() == null){
				continue;
			}
			if (TmTestingDtl.DEL_FLAG_NORMAL.equals(tmTestingDtl.getDelFlag())){
				if (StringUtils.isBlank(tmTestingDtl.getId())){
					tmTestingDtl.setTestingNo(tmTesting.getTestingNo());
					tmTestingDtl.preInsert();
					tmTestingDtlDao.insert(tmTestingDtl);
				}else{
					tmTestingDtl.preUpdate();
					tmTestingDtlDao.update(tmTestingDtl);
				}
				// 更新SN的机器状态
		        TmTestingDtl tmTestingUpdate = new TmTestingDtl();
		        if("0".equals(tmTesting.getTestingResult())){
		        	tmTestingUpdate.setStatus("2");
		        }else if("1".equals(tmTesting.getTestingResult())){
		        	tmTestingUpdate.setStatus("3");
		        }
		        tmTestingUpdate.setSnNo(tmTestingDtl.getSnNo());
		        tmTestingDao.updateSnInfo(tmTestingUpdate);
			}else{
				tmTestingDtlDao.delete(tmTestingDtl);
			}
		}*/
		// 附件信息保存
        this.saveAttachmentsFile(tmTesting.getTestingNo(), tmTesting.getFiles());
	}
	
	@Transactional(readOnly = false)
	public void delete(TmTesting tmTesting) {
		super.delete(tmTesting);
		tmTestingDtlDao.delete(new TmTestingDtl(tmTesting));
	}

	@Transactional(readOnly = false)
	public List<SnInfo> getSnInfo(String snNo,String materialNo) {
		TmTesting bean = new TmTesting();
		bean.setSnNo(snNo);
		bean.setMaterialNo(materialNo);
		List<SnInfo> snInfoList =tmTestingDao.findSnInfoList(bean);
		return snInfoList;
	}

	@Transactional(readOnly = false)
	public int getSnWarehouseInfo(String snNo) {
		TmTesting bean = new TmTesting();
		bean.setSnNo(snNo);
		int count =tmTestingDao.hasSnwarehouseInfo(bean);
		return count;
	}

    /**
     * 附件保存
     * 
     * @param testingNo
     *            画面输入信息
     * @param files
     *            附件文件
     */
    @Transactional(readOnly = false)
    public void saveAttachmentsFile(String testingNo, MultipartFile[] files) {
        // 报价单附件保存
        if (files != null && files.length > 0) {
            attachmentsService.uploadFileList(testingNo,
                    CommonConstants.ATTACHMENT_TYPE_TESTING, files);
        }
    }
}