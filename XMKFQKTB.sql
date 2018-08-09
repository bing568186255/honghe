/*
Navicat Oracle Data Transfer
Oracle Client Version : 10.2.0.5.0

Source Server         : 23.83.226.203
Source Server Version : 110200
Source Host           : 23.83.226.203:49161
Source Schema         : SYSTEM

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2018-08-09 14:54:14
*/


-- ----------------------------
-- Table structure for XMKFQKTB
-- ----------------------------
DROP TABLE "SYSTEM"."XMKFQKTB";
CREATE TABLE "SYSTEM"."XMKFQKTB" (
"rq" TIMESTAMP(0)  NULL ,
"xmmc" VARCHAR2(255 BYTE) NULL ,
"xmid" VARCHAR2(255 BYTE) NOT NULL ,
"jsjd" VARCHAR2(255 BYTE) NULL ,
"xct" BLOB NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "SYSTEM"."XMKFQKTB"."rq" IS '日期';
COMMENT ON COLUMN "SYSTEM"."XMKFQKTB"."xmmc" IS '项目名称';
COMMENT ON COLUMN "SYSTEM"."XMKFQKTB"."xmid" IS '项目id';
COMMENT ON COLUMN "SYSTEM"."XMKFQKTB"."jsjd" IS '建设阶段';
COMMENT ON COLUMN "SYSTEM"."XMKFQKTB"."xct" IS '现场图';

-- ----------------------------
-- Records of XMKFQKTB
-- ----------------------------

-- ----------------------------
-- Indexes structure for table XMKFQKTB
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table XMKFQKTB
-- ----------------------------
ALTER TABLE "SYSTEM"."XMKFQKTB" ADD PRIMARY KEY ("xmid");
