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

Date: 2018-08-09 14:53:30
*/


-- ----------------------------
-- Table structure for XMTCQKTB
-- ----------------------------
DROP TABLE "SYSTEM"."XMTCQKTB";
CREATE TABLE "SYSTEM"."XMTCQKTB" (
"xmmc" VARCHAR2(255 BYTE) NULL ,
"xmid" VARCHAR2(255 BYTE) NOT NULL ,
"tzdwmc" VARCHAR2(255 BYTE) NULL ,
"tzdwid" VARCHAR2(255 BYTE) NULL ,
"tzxz" VARCHAR2(255 BYTE) NULL ,
"tzbl" NUMBER(11) NULL ,
"bz" VARCHAR2(255 BYTE) NULL ,
"ztdwqk" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "SYSTEM"."XMTCQKTB"."xmmc" IS '项目名称';
COMMENT ON COLUMN "SYSTEM"."XMTCQKTB"."xmid" IS '项目id';
COMMENT ON COLUMN "SYSTEM"."XMTCQKTB"."tzdwmc" IS '投资单位名称 ';
COMMENT ON COLUMN "SYSTEM"."XMTCQKTB"."tzdwid" IS '投资单位id';
COMMENT ON COLUMN "SYSTEM"."XMTCQKTB"."tzxz" IS '投资性质 ';
COMMENT ON COLUMN "SYSTEM"."XMTCQKTB"."tzbl" IS '投资比例 ';
COMMENT ON COLUMN "SYSTEM"."XMTCQKTB"."bz" IS '备注';
COMMENT ON COLUMN "SYSTEM"."XMTCQKTB"."ztdwqk" IS '投资到位情况';

-- ----------------------------
-- Records of XMTCQKTB
-- ----------------------------

-- ----------------------------
-- Indexes structure for table XMTCQKTB
-- ----------------------------

-- ----------------------------
-- Checks structure for table XMTCQKTB
-- ----------------------------
ALTER TABLE "SYSTEM"."XMTCQKTB" ADD CHECK ("xmid" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table XMTCQKTB
-- ----------------------------
ALTER TABLE "SYSTEM"."XMTCQKTB" ADD PRIMARY KEY ("xmid");
