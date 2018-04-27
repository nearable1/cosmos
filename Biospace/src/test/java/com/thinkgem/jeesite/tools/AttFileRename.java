package com.thinkgem.jeesite.tools;

import java.io.File;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.common.utils.FileUtils;
import com.inbody.crm.common.utils.ListUtils;

/**
 * 附件文件名变换
 * 
 * @author yangj
 * @version 2016-11-09
 */
public class AttFileRename {

    /** 待重命名附件文件目录 */
    private static final String inputPath = "C:\\Biospace\\900.数据移行\\02.业务数据\\主数据\\attachment";

    /** 重命名后输出文件目录 */
    private static final String outputPath = "C:\\output\\ATTACHMENTS";

    /**
     * 附件重命名
     */
    public static void main(String[] args) {

        SqlSessionFactory sqlSession = getSqlSession();
        if (sqlSession == null) {
            System.out.println("执行失败");
            System.exit(0);
        }

        // 取得附件数据
        List<CoAttachments> attDataList = sqlSession.openSession().selectList("findAll");

        String iFileName = "";
        String oFileName = "";
        int fCount = 0;
        for (CoAttachments att : attDataList) {
            iFileName = inputPath + File.separator + att.getAddress() + File.separator
                    + att.getFileName() + "." + att.getFileType();
            oFileName = outputPath + File.separator + att.getAddress() + File.separator
                    + att.getAssId() + File.separator + att.getId();
            boolean isSuc = FileUtils.copyFile(iFileName, oFileName);
            if (!isSuc) {
                System.out.println("附件：" + att.getFileName() + "." + att.getFileType()
                        + " 重命名失败！记录ID: " + att.getId());
                fCount = fCount + 1;
            }
        }

        System.out.println("总数：" + ListUtils.size(attDataList));
        System.out.println("成功：" + String.valueOf(ListUtils.size(attDataList) - fCount));
        System.out.println("失败：" + fCount);
    }

    /**
     * sqlSession取得
     * 
     * @return sqlSession
     */
    public static SqlSessionFactory getSqlSession() {

        BasicDataSource pool = new BasicDataSource();
        pool.setUsername("root");
        pool.setPassword("root");
        pool.setDriverClassName("com.mysql.jdbc.Driver");
        pool.setUrl(
                "jdbc:mysql://localhost:3306/biospace?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory,
                pool);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(AttFileRenameDao.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(configuration);

        return sqlSessionFactory;
    }
}

@MyBatisDao
interface AttFileRenameDao {
    @Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "createBy.id", column = "create_by"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "updateBy.id", column = "update_by"),
            @Result(property = "updateDate", column = "update_date"),
            @Result(property = "delFlag", column = "del_flag"),
            @Result(property = "assId", column = "ass_id"),
            @Result(property = "lineNo", column = "line_no"),
            @Result(property = "address", column = "address"),
            @Result(property = "newRemarks", column = "new_remarks"),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "fileType", column = "file_type"),
    })
    @Select("select * from co_attachments")
    List<CoAttachments> findAll();
}