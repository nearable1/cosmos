update `sys_dict` set label = '保外1-2年' where type = 'DM0026' and `value`='2';
update `sys_dict` set sort = '50' where type = 'DM0026' and `value`='3';
update `sys_dict` set sort = '60' where type = 'DM0026' and `value`='4';
INSERT INTO `sys_dict` VALUES ('d39cd536821d6a6a8d0ddbdae4e27bce', '5', '保外2-3年', 'DM0026', '维修分类', '30', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('bc1ccdbdff2bba70c4becde88b22de66', '6', '保外3年以上', 'DM0026', '维修分类', '40', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');

update `sys_dict` set label = '通用配件' where type = 'DM0058' and `value`='2';
INSERT INTO `sys_dict` VALUES ('d39cd536821d7a6a8d0ddbdae4e27bce', '7', '普通配件', 'DM0058', '物料类型', '30', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
update `sys_dict` set sort = '40' where type = 'DM0058' and `value`='3';
update `sys_dict` set sort = '50' where type = 'DM0058' and `value`='4';
update `sys_dict` set sort = '60' where type = 'DM0058' and `value`='5';
update `sys_dict` set sort = '70' where type = 'DM0058' and `value`='6';

INSERT INTO `sys_dict` VALUES ('d39cd536821d8a6a8d0ddbdae4e27bce', '1', '失败', 'DM0063', '销售计划撤回原因', '10', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('d39cd536821d9a6a8d0ddbdae4e27bce', '2', '延期', 'DM0063', '销售计划撤回原因', '20', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');

INSERT INTO `sys_dict` VALUES ('d40cd536821d8a6a8d0ddbdae4e27bce', '10', '10%', 'DM0064', '折扣率', '90', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('d40cd536822d8a6a8d0ddbdae4e27bce', '20', '20%', 'DM0064', '折扣率', '80', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('d40cd536823d8a6a8d0ddbdae4e27bce', '30', '30%', 'DM0064', '折扣率', '70', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('d40cd536824d8a6a8d0ddbdae4e27bce', '40', '40%', 'DM0064', '折扣率', '60', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('d40cd536825d8a6a8d0ddbdae4e27bce', '50', '50%', 'DM0064', '折扣率', '50', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('d40cd536826d8a6a8d0ddbdae4e27bce', '60', '60%', 'DM0064', '折扣率', '40', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('d40cd536827d8a6a8d0ddbdae4e27bce', '70', '70%', 'DM0064', '折扣率', '30', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('d40cd536828d8a6a8d0ddbdae4e27bce', '80', '80%', 'DM0064', '折扣率', '20', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');
INSERT INTO `sys_dict` VALUES ('d40cd536829d8a6a8d0ddbdae4e27bce', '90', '90%', 'DM0064', '折扣率', '10', '0', '1', '2017-07-19 15:50:22', '1', '2017-07-19 15:50:22', '', '0');

alter table `rm_quotation` add `DEPOSIT_RATE` varchar(5) DEFAULT NULL COMMENT '折扣率 : 代码';