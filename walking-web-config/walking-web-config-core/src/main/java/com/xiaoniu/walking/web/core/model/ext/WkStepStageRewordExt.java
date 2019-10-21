package com.xiaoniu.walking.web.core.model.ext;

import com.xiaoniu.architecture.page.api.bean.DefaultPageParameter;

import java.io.Serializable;
import java.util.Date;

public class WkStepStageRewordExt extends DefaultPageParameter implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.stage_name
     *
     * @mbggenerated
     */
    private String stageName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.step_lower_limit
     *
     * @mbggenerated
     */
    private Integer stepLowerLimit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.step_upper_limit
     *
     * @mbggenerated
     */
    private Integer stepUpperLimit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.reward_amount
     *
     * @mbggenerated
     */
    private Integer rewardAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.state
     *
     * @mbggenerated
     */
    private Byte state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.create_man
     *
     * @mbggenerated
     */
    private String createMan;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.modify_time
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wk_step_stage_reword.modify_man
     *
     * @mbggenerated
     */
    private String modifyMan;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.id
     *
     * @return the value of wk_step_stage_reword.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.id
     *
     * @param id the value for wk_step_stage_reword.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.stage_name
     *
     * @return the value of wk_step_stage_reword.stage_name
     *
     * @mbggenerated
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.stage_name
     *
     * @param stageName the value for wk_step_stage_reword.stage_name
     *
     * @mbggenerated
     */
    public void setStageName(String stageName) {
        this.stageName = stageName == null ? null : stageName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.step_lower_limit
     *
     * @return the value of wk_step_stage_reword.step_lower_limit
     *
     * @mbggenerated
     */
    public Integer getStepLowerLimit() {
        return stepLowerLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.step_lower_limit
     *
     * @param stepLowerLimit the value for wk_step_stage_reword.step_lower_limit
     *
     * @mbggenerated
     */
    public void setStepLowerLimit(Integer stepLowerLimit) {
        this.stepLowerLimit = stepLowerLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.step_upper_limit
     *
     * @return the value of wk_step_stage_reword.step_upper_limit
     *
     * @mbggenerated
     */
    public Integer getStepUpperLimit() {
        return stepUpperLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.step_upper_limit
     *
     * @param stepUpperLimit the value for wk_step_stage_reword.step_upper_limit
     *
     * @mbggenerated
     */
    public void setStepUpperLimit(Integer stepUpperLimit) {
        this.stepUpperLimit = stepUpperLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.reward_amount
     *
     * @return the value of wk_step_stage_reword.reward_amount
     *
     * @mbggenerated
     */
    public Integer getRewardAmount() {
        return rewardAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.reward_amount
     *
     * @param rewardAmount the value for wk_step_stage_reword.reward_amount
     *
     * @mbggenerated
     */
    public void setRewardAmount(Integer rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.state
     *
     * @return the value of wk_step_stage_reword.state
     *
     * @mbggenerated
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.state
     *
     * @param state the value for wk_step_stage_reword.state
     *
     * @mbggenerated
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.create_time
     *
     * @return the value of wk_step_stage_reword.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.create_time
     *
     * @param createTime the value for wk_step_stage_reword.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.create_man
     *
     * @return the value of wk_step_stage_reword.create_man
     *
     * @mbggenerated
     */
    public String getCreateMan() {
        return createMan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.create_man
     *
     * @param createMan the value for wk_step_stage_reword.create_man
     *
     * @mbggenerated
     */
    public void setCreateMan(String createMan) {
        this.createMan = createMan == null ? null : createMan.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.modify_time
     *
     * @return the value of wk_step_stage_reword.modify_time
     *
     * @mbggenerated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.modify_time
     *
     * @param modifyTime the value for wk_step_stage_reword.modify_time
     *
     * @mbggenerated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wk_step_stage_reword.modify_man
     *
     * @return the value of wk_step_stage_reword.modify_man
     *
     * @mbggenerated
     */
    public String getModifyMan() {
        return modifyMan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wk_step_stage_reword.modify_man
     *
     * @param modifyMan the value for wk_step_stage_reword.modify_man
     *
     * @mbggenerated
     */
    public void setModifyMan(String modifyMan) {
        this.modifyMan = modifyMan == null ? null : modifyMan.trim();
    }
}