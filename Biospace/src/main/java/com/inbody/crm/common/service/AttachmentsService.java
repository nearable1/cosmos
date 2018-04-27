package com.inbody.crm.common.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.CoAttachmentsDao;
import com.inbody.crm.common.utils.FileUtils;

@Service
@Transactional(readOnly = true)
public class AttachmentsService extends BaseService {

	@Value("${attRootPath}")
	private String rootPath;

	@Autowired
	CoAttachmentsDao coAttachmentsDao;

	/**
	 * 根据文件id获取附件信息
	 * 
	 * @param fileId
	 *            文件ID（附件表记录id）
	 * @return 附件信息
	 */
	@Transactional(readOnly = true)
	public CoAttachments getAttachmentInfo(String fileId) {
		// 关联id check
		if (StringUtils.isBlank(fileId)) {
			throw new ServiceException("取得附件信息失败：文件ID为空！");
		}
		// 根据fileid获取文件信息
		CoAttachments coAttachments = coAttachmentsDao.get(fileId);
		return coAttachments;
	}

	/**
	 * 根据文件id获取完整文件名
	 * 
	 * @param fileId
	 *            文件ID（附件表记录id）
	 * @return 文件完整路径（含文件名）
	 */
	@Transactional(readOnly = true)
	public String getFileFullPathById(String fileId) {
		// 根据文件id取得附件信息
		CoAttachments att = coAttachmentsDao.get(fileId);
		return getFileFullPathByAtt(att);
	}

	/**
	 * 根据附件信息获取完整文件名
	 * 
	 * @param att
	 *            附件信息
	 * @return 文件完整路径（含文件名）
	 */
	@Transactional(readOnly = true)
	public String getFileFullPathByAtt(CoAttachments att) {
		// 附件信息不存
		if (att == null) {
			throw new ServiceException("取得文件名失败：附件信息不存在！");
		}

		StringBuilder fileName = new StringBuilder(rootPath);
		fileName.append(File.separator);
		fileName.append(att.getAddress());
		fileName.append(File.separator);
		fileName.append(att.getAssId());
		fileName.append(File.separator);
		fileName.append(att.getId());
//		fileName.append(".");
//		fileName.append(att.getFileType());

		return fileName.toString();
	}

    /**
     * 批量上传文件
     * 
     * @param assId
     *            关联id
     * @param type
     *            文件分类（客户/代理商、出入库、检测、报价单）
     * @param files
     *            上传文件（复数）
     */
    @Transactional(readOnly = false)
    public void uploadFileList(String assId, String type, MultipartFile[] files) {
        if (files == null) {
            return;
        }
        int lineNo = 0;
        for (MultipartFile file : files) {
            lineNo = lineNo + 1;
            uploadFile(assId, type, String.valueOf(lineNo), file);
        }
    }

	/**
	 * 上传单个文件
	 * 
	 * @param assId
	 *            关联id
	 * @param type
	 *            文件分类（客户/代理商、出入库、检测、报价单）
	 * @param lineNo
	 *            文件行号
	 * @param file
	 *            上传的文件
	 */
	@Transactional(readOnly = false)
	public void uploadFile(String assId, String type, String lineNo,
			MultipartFile file) {
		// 关联id check
		if (StringUtils.isBlank(assId)) {
			throw new ServiceException("文件上传失败：关联ID为空！");
		}
		// 文件分类check
		if (StringUtils.isBlank(type)) {
			throw new ServiceException("文件上传失败：文件分类为空！");
		}
		// 上传文件check
		if (file == null) {
			throw new ServiceException("文件上传失败：没有上传的文件！");
		}

		if (StringUtils.isBlank(lineNo)) {
			lineNo = "1";
		}

		CoAttachments att = new CoAttachments();
		att.setAssId(assId);
		att.setAddress(type);
		att.setFileName(FileUtils.getFileNameWithoutExtension(file.getOriginalFilename()));
		att.setFileType(FileUtils.getFileExtension(file.getOriginalFilename()));
		att.setLineNo(String.valueOf(lineNo));
		att.preInsert();
		coAttachmentsDao.insert(att);

		try {
			FileUtils.writeByteArrayToFile(new File(getFileFullPathByAtt(att)),
					file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("文件：" + file.getOriginalFilename() + "，上传失败！");
		}
	}

	/**
	 * 根据关联id获取附件信息一览
	 * 
	 * @param assId
	 *            关联id（报价单id、检测id等）
	 * @return 附件信息一览
	 */
	@Transactional(readOnly = true)
	public List<CoAttachments> getAttachmentList(String assId) {
		return coAttachmentsDao.findListByAssId(assId);
	}

	/**
	 * 根据文件id删除附件文件及附件信息
	 * 
	 * @param fileId
	 *            文件id
	 * @return 被删除文件文件名
	 */
	@Transactional(readOnly = false)
    public String deleteFile(String fileId) {
        // 附件信息取得
        CoAttachments att = getAttachmentInfo(fileId);
        // 完整文件路径取得
        String fullFileName = getFileFullPathByAtt(att);
        // 附件信息删
        coAttachmentsDao.deleteById(att.getId());
        // 文件删除
        boolean isSuccess = FileUtils.deleteFile(fullFileName);

        if (!isSuccess) {
            throw new ServiceException(
                    "文件：" + att.getFileName() + "." + att.getFileType() + "，删除失败！");
        }

        return att.getFileName() + "." + att.getFileType();
    }
	
	/**
	 * 删除关联id所对应的所有附件文件及附件信息
	 * 
	 * @param assId
	 *            关联id
	 * @param type
	 *            文件分类（客户/代理商、出入库、检测、报价单）
	 */
	@Transactional(readOnly = false)
	public void deleteAllFileByAssId(String assId, String type) {
		// 关联id所对应的所有附件信息删除
		coAttachmentsDao.deleteByAssId(assId);
		// 删除所有附件文件
		StringBuilder dirName = new StringBuilder(rootPath);
		dirName.append(File.separator);
		dirName.append(type);
		dirName.append(File.separator);
		dirName.append(assId);

		boolean isSuccess = FileUtils.deleteDirectory(dirName.toString());

		if (!isSuccess) {
			throw new ServiceException("文件删除失败！");
		}
	}

	/**
	 * 根据文件id下载附件文件
	 * 
	 * @param fileId
	 *            文件id
	 * @return error 错误信息
	 */
	@Transactional(readOnly = true)
	public String downloadFile(String fileId, HttpServletRequest request,
			HttpServletResponse response) {
		// 附件信息取得
		CoAttachments att = getAttachmentInfo(fileId);
		// 完整文件路径取得
		String fullFileName = getFileFullPathByAtt(att);

		// 文件下载
		return FileUtils.downFile(new File(fullFileName), request, response,
				att.getFileName() + "." + att.getFileType());
	}

    /**
     * 保存附件信息
     * 
     * @param assId
     *            关联id
     * @param fileName
     *            文件名（不包含路径）
     * @param type
     *            附件类型
     * 
     * @return 附件信息
     */
    @Transactional(readOnly = false)
    public CoAttachments saveAttFileInfo(String assId, String fileName, String type) {
        CoAttachments att = new CoAttachments();
        att.setAssId(assId);
        att.setAddress(type);
        att.setFileName(FileUtils.getFileNameWithoutExtension(fileName));
        att.setFileType(FileUtils.getFileExtension(fileName));
        att.setLineNo("1");
        att.preInsert();
        coAttachmentsDao.insert(att);

        return att;
    }

}
