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

Date: 2018-08-09 14:52:52
*/


-- ----------------------------
-- Table structure for XMWJ
-- ----------------------------
DROP TABLE "SYSTEM"."XMWJ";
CREATE TABLE "SYSTEM"."XMWJ" (
"xmid" VARCHAR2(255 BYTE) NOT NULL ,
"kywjmc" VARCHAR2(255 BYTE) NULL ,
"kywjid" VARCHAR2(255 BYTE) NULL ,
"kywjdz" VARCHAR2(255 BYTE) NULL ,
"gtwjmc" VARCHAR2(255 BYTE) NULL ,
"gtwjid" VARCHAR2(255 BYTE) NULL ,
"gtwjdz" VARCHAR2(255 BYTE) NULL ,
"hpwjmc" VARCHAR2(255 BYTE) NULL ,
"hpwjid" VARCHAR2(255 BYTE) NULL ,
"hpwjdz" VARCHAR2(255 BYTE) NULL ,
"sbwjmc" VARCHAR2(255 BYTE) NULL ,
"sbwjid" VARCHAR2(255 BYTE) NULL ,
"sbwjdz" VARCHAR2(255 BYTE) NULL ,
"ghwjmc" VARCHAR2(255 BYTE) NULL ,
"ghwjid" VARCHAR2(255 BYTE) NULL ,
"ghwjdz" VARCHAR2(255 BYTE) NULL ,
"sjpfmc" VARCHAR2(255 BYTE) NULL ,
"sjpfid" VARCHAR2(255 BYTE) NULL ,
"sjpfdz" VARCHAR2(255 BYTE) NULL ,
"kgxkmc" VARCHAR2(255 BYTE) NULL ,
"kgxkid" VARCHAR2(255 BYTE) NULL ,
"kgxkdz" VARCHAR2(255 BYTE) NULL ,
"gdzctzqkmc" VARCHAR2(255 BYTE) NULL ,
"gdzctzqkid" VARCHAR2(255 BYTE) NULL ,
"gdzctzqkdz" VARCHAR2(255 BYTE) NULL ,
"jghgzmc" VARCHAR2(255 BYTE) NULL ,
"jghgzid" VARCHAR2(255 BYTE) NULL ,
"jghgzdz" VARCHAR2(255 BYTE) NULL ,
"sjwjmc" VARCHAR2(255 BYTE) NULL ,
"sjwjid" VARCHAR2(255 BYTE) NULL ,
"sjwjdz" VARCHAR2(255 BYTE) NULL ,
"jswj" VARCHAR2(255 BYTE) NULL ,
"jswjid" VARCHAR2(255 BYTE) NULL ,
"jswjdz" VARCHAR2(255 BYTE) NULL ,
"jyzt" VARCHAR2(255 BYTE) NULL ,
"jyztid" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "SYSTEM"."XMWJ"."xmid" IS '项目ID';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."kywjmc" IS '可研文件名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."kywjid" IS '可研文件id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."kywjdz" IS '可研文件地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."gtwjmc" IS '国土文件名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."gtwjid" IS '国土文件id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."gtwjdz" IS '国土文件地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."hpwjmc" IS '环评文件名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."hpwjid" IS '环评文件id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."hpwjdz" IS '环评文件地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."sbwjmc" IS '水保文件名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."sbwjid" IS '水保文件id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."sbwjdz" IS '水保文件地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."ghwjmc" IS '规划文件名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."ghwjid" IS '规划文件id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."ghwjdz" IS '规划文件地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."sjpfmc" IS '设计批复名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."sjpfid" IS '设计批复id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."sjpfdz" IS '设计批复地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."kgxkmc" IS '开工许可名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."kgxkid" IS '开工许可id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."kgxkdz" IS '开工许可地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."gdzctzqkmc" IS '固定资产投资情况名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."gdzctzqkid" IS '固定资产投资情况id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."gdzctzqkdz" IS '固定资产投资情况地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."jghgzmc" IS '竣工合格证名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."jghgzid" IS '竣工合格证id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."jghgzdz" IS '竣工合格证地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."sjwjmc" IS '审计文件名称';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."sjwjid" IS '审计文件id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."sjwjdz" IS '审计文件地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."jswj" IS '结算文件';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."jswjid" IS '结算文件id';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."jswjdz" IS '结算文件地址';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."jyzt" IS '经营主体 ';
COMMENT ON COLUMN "SYSTEM"."XMWJ"."jyztid" IS '经营主体id';

-- ----------------------------
-- Records of XMWJ
-- ----------------------------

-- ----------------------------
-- Indexes structure for table XMWJ
-- ----------------------------

-- ----------------------------
-- Checks structure for table XMWJ
-- ----------------------------
ALTER TABLE "SYSTEM"."XMWJ" ADD CHECK ("xmid" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table XMWJ
-- ----------------------------
ALTER TABLE "SYSTEM"."XMWJ" ADD PRIMARY KEY ("xmid");
