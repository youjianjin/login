/*
MySQL Data Transfer
Source Host: localhost
Source Database: edaizao
Target Host: localhost
Target Database: edaizao
Date: 2018/1/17 14:57:36
*/

-- ----------------------------
-- Table structure for t_company
-- ----------------------------
CREATE TABLE `t_company` (
  `oid` varchar(32) NOT NULL COMMENT '主键',
  `gcompany` varchar(32) NOT NULL COMMENT '公司名',
  `glev1` varchar(2) DEFAULT '00' COMMENT '权限1',
  `glev2` varchar(2) DEFAULT '00' COMMENT '权限2',
  `glev3` varchar(2) DEFAULT '00' COMMENT '权限3',
  `ts` varchar(20) DEFAULT NULL COMMENT '时间yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司表';

-- ----------------------------
-- Table structure for t_companyinfo
-- ----------------------------
CREATE TABLE `t_companyinfo` (
  `oid` varchar(32) NOT NULL COMMENT '主键',
  `gcontact` varchar(32) DEFAULT NULL COMMENT '联系人',
  `gcity` varchar(32) DEFAULT NULL COMMENT '省市区1000,1000,1000隔开',
  `gaddress` varchar(50) DEFAULT NULL COMMENT '详细地址',
  `ginfo` varchar(200) DEFAULT NULL COMMENT '公司介绍',
  `gmould` varchar(50) DEFAULT NULL COMMENT '公司业务1001,1002,1003隔开',
  `cid` varchar(32) NOT NULL COMMENT '关联公司id',
  `ts` varchar(20) DEFAULT NULL COMMENT '时间yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司信息表';

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
CREATE TABLE `t_dept` (
  `oid` varchar(32) NOT NULL COMMENT '主键',
  `cid` varchar(32) DEFAULT NULL,
  `gdept` varchar(20) DEFAULT NULL,
  `ts` varchar(20) DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
CREATE TABLE `t_file` (
  `oid` varchar(32) NOT NULL COMMENT '主键',
  `filename` varchar(20) DEFAULT NULL COMMENT '文件名',
  `filetype` varchar(10) DEFAULT NULL COMMENT '文件类型',
  `fileurl` varchar(50) DEFAULT NULL COMMENT '文件url',
  `sid` varchar(32) DEFAULT NULL COMMENT '员工id',
  `ts` varchar(20) DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='上传文件表';

-- ----------------------------
-- Table structure for t_staff
-- ----------------------------
CREATE TABLE `t_staff` (
  `oid` varchar(32) NOT NULL COMMENT '主键',
  `gusername` varchar(32) DEFAULT NULL COMMENT '用户名',
  `gmobile` varchar(11) NOT NULL COMMENT '手机号',
  `gemail` varchar(32) NOT NULL COMMENT '邮箱',
  `gpassword` varchar(50) NOT NULL COMMENT '密码MD5',
  `cid` varchar(32) NOT NULL COMMENT '关联公司信息',
  `glev1` varchar(2) DEFAULT '00' COMMENT '权限:1超级2管理3员工',
  `glev2` varchar(2) DEFAULT '00' COMMENT '权限',
  `glev3` varchar(2) DEFAULT '00' COMMENT '权限',
  `ts` varchar(20) DEFAULT NULL COMMENT '产生时间yyyy-MM-dd HH:mm:ss',
  `did` varchar(32) DEFAULT NULL,
  `siid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
CREATE TABLE `t_user` (
  `oid` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `gusername` varchar(32) DEFAULT NULL COMMENT '用户名',
  `gmobile` varchar(11) NOT NULL COMMENT '手机号',
  `gemail` varchar(32) NOT NULL COMMENT '邮箱',
  `gpassword` varchar(50) NOT NULL COMMENT '密码MD5',
  `glev1` varchar(2) DEFAULT '00' COMMENT '权限:1超级',
  `glev2` varchar(2) DEFAULT '00' COMMENT '权限',
  `glev3` varchar(2) DEFAULT '00' COMMENT '权限',
  `ts` varchar(20) DEFAULT NULL COMMENT '时间yyyy-MM-dd HH:mm:ss',
  PRIMARY KEY (`oid`),
  KEY `gusername` (`gusername`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records 
-- ----------------------------
