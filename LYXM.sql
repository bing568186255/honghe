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

Date: 2018-08-09 14:51:41
*/


-- ----------------------------
-- Table structure for LYXM
-- ----------------------------
DROP TABLE "SYSTEM"."LYXM";
CREATE TABLE "SYSTEM"."LYXM" (
"xmid" VARCHAR2(255 BYTE) NULL ,
"xmm" VARCHAR2(255 BYTE) NULL ,
"xmdz" VARCHAR2(255 BYTE) NULL ,
"qxbm" VARCHAR2(255 BYTE) NULL ,
"xmgk" VARCHAR2(255 BYTE) NULL ,
"szfw" VARCHAR2(255 BYTE) NULL ,
"xmzb" VARCHAR2(255 BYTE) NULL ,
"xmfw" VARCHAR2(255 BYTE) NULL ,
"xmsx" VARCHAR2(255 BYTE) NULL ,
"zsjh" VARCHAR2(255 BYTE) NULL ,
"ghyd" NUMBER(30) NULL ,
"jsyd" NUMBER(30) NULL ,
"syyd" NUMBER(30) NULL ,
"zzyd" NUMBER(30) NULL ,
"gyyd" NUMBER(30) NULL ,
"zhyd" NUMBER(30) NULL ,
"qtyd" NUMBER(30) NULL ,
"nyd" NUMBER(30) NULL ,
"wlyd" NUMBER(30) NULL ,
"sjljtz" NUMBER(30) NULL ,
"ztz" NUMBER(30) NULL ,
"xmfzrdh" NUMBER(30) NULL ,
"sjtbylxdh" NUMBER(30) NULL ,
"jszt" VARCHAR2(255 BYTE) NULL ,
"xmlx" VARCHAR2(255 BYTE) NULL ,
"xmxz" VARCHAR2(255 BYTE) NULL ,
"xmyt" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "SYSTEM"."LYXM"."jszt" IS '建设状态';

-- ----------------------------
-- Records of LYXM
-- ----------------------------
INSERT INTO "SYSTEM"."LYXM" VALUES ('1', '红河县他竜旅游特色村建设项目
', '红河县迤萨镇
', '2528', '道路建设、寨门、游客咨询点、他竜小筑休憩点、文庙、武庙修缮、文庙广场、生态停车场、民宿体验中心、现有建筑修缮和改造。
', '无', '103.3743467908,23.3637259517', '[[23.3715042035,103.3762107527],[23.3719966516,103.3779059088],[23.3709526594,103.3789358771],[23.3698901402,103.3776312709],[23.3703038018,103.3749919772]]', '旅游', '建议加大扶持力度
建议加大扶持力度
', '1', '1', '1', '1', '1', '1', '1', '0', '0', '11', '13', '13720009586', '123562323', '已完成', '旅游', '政府', '无');
INSERT INTO "SYSTEM"."LYXM" VALUES ('2', '河口珠宝玉石加工城
', '红河县XXXX', '2528', '占地7.2亩，建筑面积4903.65平方米
', '无', '103.3753502369,23.3636104958', '[[23.3642200000,103.3756000000],[23.3640438574,103.3732366562],[23.3632165295,103.3719491959],[23.3698901402,103.3776312709],[23.3619164324,103.3731722832]]', '商业', '无', '1', '1', '1', '1', '1', '1', '1', '0', '0', '13', '14', '12330009586', '2222', '建设中', '商业', '民事', '无');
