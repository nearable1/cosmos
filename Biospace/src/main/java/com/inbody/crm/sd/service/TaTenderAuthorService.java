/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.act.utils.ActUtils;
import com.inbody.crm.sd.entity.TaTenderAuthor;
import com.inbody.crm.sd.dao.TaTenderAuthorDao;
import com.inbody.crm.sd.entity.TaTenderAuthorDtl;
import com.inbody.crm.sd.dao.TaTenderAuthorDtlDao;

/**
 * 招标授权Service
 * @author qidd
 * @version 2017-10-19
 */
@Service
@Transactional(readOnly = true)
public class TaTenderAuthorService extends CrudService<TaTenderAuthorDao, TaTenderAuthor> {

	@Autowired
	private TaTenderAuthorDao taTenderAuthorDao;
	@Autowired
	private TaTenderAuthorDtlDao taTenderAuthorDtlDao;
    @Autowired
    private CommonService commonService;

    @Autowired
    private AttachmentsService attachmentsService;
	
	public TaTenderAuthor get(String id) {
		TaTenderAuthor taTenderAuthor = super.get(id);
		TaTenderAuthorDtl taTenderAuthorDtl = new TaTenderAuthorDtl();
		taTenderAuthorDtl.setAppId(taTenderAuthor.getId());
		taTenderAuthor.setTaTenderAuthorDtlList(taTenderAuthorDtlDao.findList(taTenderAuthorDtl));
        // 附件信息取得
        List<CoAttachments> attachmentsList = attachmentsService.getAttachmentList(id);
        taTenderAuthor.setAttachmentsList(attachmentsList);
		return taTenderAuthor;
	}
	
	public List<TaTenderAuthor> findList(TaTenderAuthor taTenderAuthor) {
		return super.findList(taTenderAuthor);
	}
	
	public Page<TaTenderAuthor> findPage(Page<TaTenderAuthor> page, TaTenderAuthor taTenderAuthor) {
		return super.findPage(page, taTenderAuthor);
	}
	
	@Transactional(readOnly = false)
	public void save(TaTenderAuthor taTenderAuthor) {
        // 临时保存、申请、或是再申请时执行订单信息保存
        if (StringUtils.isBlank(taTenderAuthor.getAct().getProcInsId())
                || StringUtils.equals(taTenderAuthor.getWorkflowStatus(),
                        CommonConstants.WORKFLOW_STATUS_10)
                || StringUtils.equals(taTenderAuthor.getWorkflowStatus(),
                        CommonConstants.WORKFLOW_STATUS_60)) {

    		super.save(taTenderAuthor);
    		taTenderAuthorDtlDao.deleteByAppId(taTenderAuthor.getId());
    		for (TaTenderAuthorDtl taTenderAuthorDtl : taTenderAuthor.getTaTenderAuthorDtlList()){
    			if (TaTenderAuthorDtl.DEL_FLAG_NORMAL.equals(taTenderAuthorDtl.getDelFlag())){
    				taTenderAuthorDtl.setAppId(taTenderAuthor.getId());
    				taTenderAuthorDtl.preInsert();
    				taTenderAuthorDtlDao.insert(taTenderAuthorDtl);
    			}
    			//    			if (taTenderAuthorDtl.getId() == null){
//    				continue;
//    			}
//    			if (TaTenderAuthorDtl.DEL_FLAG_NORMAL.equals(taTenderAuthorDtl.getDelFlag())){
//    				if (StringUtils.isBlank(taTenderAuthorDtl.getId())){
//    					taTenderAuthorDtl.setAuthorizationNo(taTenderAuthor.getId());
//    					taTenderAuthorDtl.preInsert();
//    					taTenderAuthorDtlDao.insert(taTenderAuthorDtl);
//    				}else{
//    					taTenderAuthorDtl.preUpdate();
//    					taTenderAuthorDtlDao.update(taTenderAuthorDtl);
//    				}
//    			}else{
//    				taTenderAuthorDtlDao.delete(taTenderAuthorDtl);
//    			}
    		}
    		if(StringUtils.isNotBlank(taTenderAuthor.getBusinessOppId())){
    			taTenderAuthorDao.updateBO(taTenderAuthor.getBusinessOppId());
    		}
            // 报价单附件信息保存
            this.saveAttachmentsFile(taTenderAuthor.getId(), taTenderAuthor.getFiles());
        }

		// 流程流转
        taTenderAuthor.getAct().setBusinessId(taTenderAuthor.getId());
		commonService.flowProcess(taTenderAuthor.getAct(), ActUtils.TA_TENDER_AUTHOR,
				CommonConstants.TENDER_AUTHOR,
				taTenderAuthor.getWorkflowStatus());
	}

    /**
     * 附件保存
     * 
     * @param quotationNo
     *            报价单画面输入信息
     * @param files
     *            报价单附件文件
     */
    @Transactional(readOnly = false)
    public void saveAttachmentsFile(String taTenderAuthorId, MultipartFile[] files) {
        // 报价单附件保存
        if (files != null && files.length > 0) {
            attachmentsService.uploadFileList(taTenderAuthorId,
                    CommonConstants.ATTACHMENT_TYPE_TENDER, files);
        }
    }

	@Transactional(readOnly = false)
	public void managerSave(TaTenderAuthor taTenderAuthor) {
		super.save(taTenderAuthor);
		
		taTenderAuthorDtlDao.deleteByAppId(taTenderAuthor.getId());
		for (TaTenderAuthorDtl taTenderAuthorDtl : taTenderAuthor.getTaTenderAuthorDtlList()){
			if (TaTenderAuthorDtl.DEL_FLAG_NORMAL.equals(taTenderAuthorDtl.getDelFlag())){
				taTenderAuthorDtl.setAppId(taTenderAuthor.getId());
				taTenderAuthorDtl.preInsert();
				taTenderAuthorDtlDao.insert(taTenderAuthorDtl);
			}
			//    			if (taTenderAuthorDtl.getId() == null){
//				continue;
//			}
//			if (TaTenderAuthorDtl.DEL_FLAG_NORMAL.equals(taTenderAuthorDtl.getDelFlag())){
//				if (StringUtils.isBlank(taTenderAuthorDtl.getId())){
//					taTenderAuthorDtl.setAuthorizationNo(taTenderAuthor.getId());
//					taTenderAuthorDtl.preInsert();
//					taTenderAuthorDtlDao.insert(taTenderAuthorDtl);
//				}else{
//					taTenderAuthorDtl.preUpdate();
//					taTenderAuthorDtlDao.update(taTenderAuthorDtl);
//				}
//			}else{
//				taTenderAuthorDtlDao.delete(taTenderAuthorDtl);
//			}
		}
        // 报价单附件信息保存
        this.saveAttachmentsFile(taTenderAuthor.getId(), taTenderAuthor.getFiles());
	}
	
	@Transactional(readOnly = false)
	public void delete(TaTenderAuthor taTenderAuthor) {
		super.delete(taTenderAuthor);
		taTenderAuthorDtlDao.delete(new TaTenderAuthorDtl(taTenderAuthor));
	}
	
}