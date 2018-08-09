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

Date: 2018-08-09 14:52:21
*/


-- ----------------------------
-- Table structure for XXDY
-- ----------------------------
DROP TABLE "SYSTEM"."XXDY";
CREATE TABLE "SYSTEM"."XXDY" (
"id" VARCHAR2(255 BYTE) NOT NULL ,
"bm" VARCHAR2(255 BYTE) NULL ,
"mc" VARCHAR2(255 BYTE) NULL ,
"sjid" VARCHAR2(255 BYTE) NULL ,
"lx" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of XXDY
-- ----------------------------
INSERT INTO "SYSTEM"."XXDY" VALUES ('1', '2500', '红河州', null, '州');
INSERT INTO "SYSTEM"."XXDY" VALUES ('2', '2501', '个旧市', '2500', '市');
INSERT INTO "SYSTEM"."XXDY" VALUES ('3', '2522', '蒙自县', '2500', '县');
INSERT INTO "SYSTEM"."XXDY" VALUES ('4', '2523', '屏边县', '2500', '县');
INSERT INTO "SYSTEM"."XXDY" VALUES ('5', '2524', '建水县', '2500', '县');
INSERT INTO "SYSTEM"."XXDY" VALUES ('6', '2525', '石屏县', '2500', '县');
INSERT INTO "SYSTEM"."XXDY" VALUES ('7', '2526', '弥勒县', '2500', '县');
INSERT INTO "SYSTEM"."XXDY" VALUES ('8', '2527', '沪西县', '2500', '县');
INSERT INTO "SYSTEM"."XXDY" VALUES ('9', '2528', '红河县', '2500', '县');

-- ----------------------------
-- Indexes structure for table XXDY
-- ----------------------------

-- ----------------------------
-- Checks structure for table XXDY
-- ----------------------------
ALTER TABLE "SYSTEM"."XXDY" ADD CHECK ("id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table XXDY
-- ----------------------------
ALTER TABLE "SYSTEM"."XXDY" ADD PRIMARY KEY ("id");
