/*
 Navicat Premium Dump SQL

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 50743 (5.7.43-log)
 Source Host           : localhost:3306
 Source Schema         : online-sys-api

 Target Server Type    : MySQL
 Target Server Version : 50743 (5.7.43-log)
 File Encoding         : 65001

 Date: 23/04/2025 16:30:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for evaluations
-- ----------------------------
DROP TABLE IF EXISTS `evaluations`;
CREATE TABLE `evaluations`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级评论ID',
  `commenter_id` int(11) NULL DEFAULT NULL COMMENT '评论者ID',
  `replier_id` int(11) NULL DEFAULT NULL COMMENT '回复者ID',
  `content_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '内容类型',
  `content_id` int(11) NULL DEFAULT NULL COMMENT '内容ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of evaluations
-- ----------------------------
INSERT INTO `evaluations` VALUES (1, NULL, 8, NULL, 'SCENIC', 11, '我仿佛听见了雪山的心跳，低沉而有力', '2025-04-23 13:39:05');
INSERT INTO `evaluations` VALUES (2, 1, 8, NULL, 'SCENIC', 11, '静静地凝视着这片雪白的世', '2025-04-23 13:39:21');
INSERT INTO `evaluations` VALUES (3, NULL, 14, NULL, 'SCENIC', 9, '还是可以的', '2025-04-23 14:54:26');
INSERT INTO `evaluations` VALUES (4, 3, 14, NULL, 'SCENIC', 9, '说说', '2025-04-23 15:38:51');

-- ----------------------------
-- Table structure for evaluations_upvote
-- ----------------------------
DROP TABLE IF EXISTS `evaluations_upvote`;
CREATE TABLE `evaluations_upvote`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论点赞表ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `evaluations_id` int(11) NULL DEFAULT NULL COMMENT '点赞ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of evaluations_upvote
-- ----------------------------
INSERT INTO `evaluations_upvote` VALUES (16, 8, 2);
INSERT INTO `evaluations_upvote` VALUES (28, 8, 1);
INSERT INTO `evaluations_upvote` VALUES (36, 8, 3);
INSERT INTO `evaluations_upvote` VALUES (44, 14, 3);
INSERT INTO `evaluations_upvote` VALUES (51, 14, 1);
INSERT INTO `evaluations_upvote` VALUES (53, 14, 4);
INSERT INTO `evaluations_upvote` VALUES (54, 14, 2);

-- ----------------------------
-- Table structure for hotel
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '酒店的主键ID，自增',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '酒店名称',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '酒店封面',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '酒店地址',
  `concat_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '酒店联系电话',
  `vendor_id` int(11) NULL DEFAULT NULL COMMENT '供应商/服务商ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` VALUES (1, '云顶轩酒店', '/api/online-travel-sys/v1.0/file/getFile?fileName=d1306095.jpg', '12321321', '321321321', 1, '2024-10-31 17:55:40');
INSERT INTO `hotel` VALUES (2, '静谧花园酒店', '/api/online-travel-sys/v1.0/file/getFile?fileName=d1306095.jpg', '青湖路312号', '0772-7878', 5, '2024-10-23 17:55:40');
INSERT INTO `hotel` VALUES (3, '星河假日酒店', '/api/online-travel-sys/v1.0/file/getFile?fileName=d1306095.jpg', '12321321', '12321321', 1, '2024-10-31 17:55:40');
INSERT INTO `hotel` VALUES (4, '翠湖轩酒店', '/api/online-travel-sys/v1.0/file/getFile?fileName=3b4cbf212.png', '步行街尽头200米', '0772-9090', 5, '2024-10-31 17:55:40');
INSERT INTO `hotel` VALUES (5, '天籁酒店', '/api/online-travel-sys/v1.0/file/getFile?fileName=9217f1811.png', '观音山左侧100米', '0772-8989', 1, '2024-10-31 17:55:40');
INSERT INTO `hotel` VALUES (6, '福来酒店', '/api/online-travel-sys/v1.0/file/getFile?fileName=a22045213.png', '镇魂街32号', '0772-676767', 5, '2024-11-11 21:51:57');
INSERT INTO `hotel` VALUES (7, '四季春酒店', '/api/online-travel-sys/v1.0/file/getFile?fileName=935b592Snipaste_2024-12-26_16-50-27.png', '大王街121号', '13456550911', 4, '2025-02-22 15:01:09');

-- ----------------------------
-- Table structure for hotel_order_info
-- ----------------------------
DROP TABLE IF EXISTS `hotel_order_info`;
CREATE TABLE `hotel_order_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '酒店预约订单主键ID，自增',
  `room_id` int(11) NULL DEFAULT NULL COMMENT '房间ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `concat_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系人',
  `concat_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系电话',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '总金额',
  `pay_status` tinyint(1) NULL DEFAULT NULL COMMENT '预约订单的状态',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 901 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hotel_order_info
-- ----------------------------
INSERT INTO `hotel_order_info` VALUES (899, 9, 8, '4234', '432432', 111.00, 1, '2025-04-14 23:24:41', '2025-04-14 23:23:53');
INSERT INTO `hotel_order_info` VALUES (900, 5, 8, '张三', '15464523567', 322.00, 0, NULL, '2025-04-23 12:54:20');

-- ----------------------------
-- Table structure for hotel_room
-- ----------------------------
DROP TABLE IF EXISTS `hotel_room`;
CREATE TABLE `hotel_room`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '酒店房间主键ID，自增',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '房间号',
  `hotel_id` int(11) NULL DEFAULT NULL COMMENT '酒店ID',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `discount` double(10, 0) NULL DEFAULT NULL COMMENT '折扣',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '房间描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hotel_room
-- ----------------------------
INSERT INTO `hotel_room` VALUES (3, '经济房型', 6, '/api/online-travel-sys/v1.0/file/getFile?fileName=a3c9be113.png', 99.00, NULL, '房间内配备了超大床铺，奢华床品确保您享有无与伦比的睡眠体验。独立的浴室则配有按摩浴缸和高端洗浴用品，让您在繁忙的一天后尽情放松。此外，房间内还提供高速无线网络、迷你吧和智能电视，满足您所有的娱乐需求。', '2024-11-06 13:03:47');
INSERT INTO `hotel_room` VALUES (4, '豪华型1号', 6, '/api/online-travel-sys/v1.0/file/getFile?fileName=ffd1ea913.png', 4999.00, 9, '我们的豪华房型，专为追求极致舒适与奢华体验的客人设计。每间房间都拥有宽敞的布局，配备高档家具和现代化设施，营造出温馨而优雅的氛围。大窗户让自然光洒满整个空间，您可以在阳台上欣赏到迷人的城市景观或宁静的海景。', '2024-11-12 13:07:05');
INSERT INTO `hotel_room` VALUES (5, '测试111', 5, '/api/online-travel-sys/v1.0/file/getFile?fileName=06d1806Snipaste_2024-12-26_16-50-16.png', 322.00, 10, '2121', '2025-02-21 18:01:04');
INSERT INTO `hotel_room` VALUES (6, '测试房间', 2, '/api/online-travel-sys/v1.0/file/getFile?fileName=c956441Snipaste_2024-12-26_16-50-27.png', 123.00, 10, '测试1222', '2025-02-21 18:17:13');
INSERT INTO `hotel_room` VALUES (9, '舒适性住房', 7, '/api/online-travel-sys/v1.0/file/getFile?fileName=1c6a75aSnipaste_2024-12-26_16-50-16.png', 111.00, 10, '舒适', '2025-02-22 15:01:36');

-- ----------------------------
-- Table structure for hotel_room_bed
-- ----------------------------
DROP TABLE IF EXISTS `hotel_room_bed`;
CREATE TABLE `hotel_room_bed`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '酒店房间床位主键ID，自增',
  `number` int(11) NULL DEFAULT NULL COMMENT '床位号',
  `room_id` int(11) NULL DEFAULT NULL COMMENT '房间ID',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '床位是否可用状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hotel_room_bed
-- ----------------------------
INSERT INTO `hotel_room_bed` VALUES (13, 11, 4, 1, '2024-11-12 13:14:01');
INSERT INTO `hotel_room_bed` VALUES (14, 121, 4, 1, '2024-11-12 13:14:06');
INSERT INTO `hotel_room_bed` VALUES (17, 1, 3, 1, '2025-01-08 20:40:35');
INSERT INTO `hotel_room_bed` VALUES (19, 3, 3, 1, '2025-01-08 20:44:24');
INSERT INTO `hotel_room_bed` VALUES (20, 4, 3, 1, '2025-01-08 20:45:14');
INSERT INTO `hotel_room_bed` VALUES (21, 2, 3, NULL, '2025-01-08 20:45:27');
INSERT INTO `hotel_room_bed` VALUES (22, 101, NULL, 1, '2025-01-08 20:53:37');
INSERT INTO `hotel_room_bed` VALUES (23, 103, NULL, 1, '2025-01-08 20:54:18');
INSERT INTO `hotel_room_bed` VALUES (24, 190, NULL, 1, '2025-01-08 20:55:33');
INSERT INTO `hotel_room_bed` VALUES (25, 102, NULL, 1, '2025-01-08 20:58:57');
INSERT INTO `hotel_room_bed` VALUES (26, 102, 4, 1, '2025-01-08 21:00:21');
INSERT INTO `hotel_room_bed` VALUES (28, 101, 9, 1, '2025-02-22 15:01:48');
INSERT INTO `hotel_room_bed` VALUES (29, 102, 9, 1, '2025-02-22 15:01:52');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告信息主键ID，自增',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '2024年度第三季度运营财报', '<p style=\"text-align: start;\">在2023年第三季度，XYZ科技有限公司继续保持稳健增长，实现了多项关键财务指标的显著提升。以下是本季度的主要财务数据：</p><ul><li style=\"text-align: start;\"><strong>总收入：</strong> 5000万元，同比增长20%。</li><li style=\"text-align: start;\"><strong>净利润：</strong> 800万元，同比增长15%。</li><li style=\"text-align: start;\"><strong>毛利率：</strong> 45%，同比提高2个百分点。</li><li style=\"text-align: start;\"><strong>运营费用：</strong> 1800万元，同比增长10%。</li><li style=\"text-align: start;\"><strong>研发投入：</strong> 1200万元，同比增长25%。</li></ul><h4 style=\"text-align: start;\">业务亮点</h4><ol><li style=\"text-align: start;\">产品创新：本季度，我们成功推出了新一代智能穿戴设备，市场反响热烈，销售额环比增长30%。新产品的推出不仅提升了公司的品牌形象，还为未来的增长奠定了坚实的基础。</li><li style=\"text-align: start;\">市场拓展：我们继续扩大国际市场布局，特别是在东南亚和欧洲市场的表现尤为突出。国际市场的收入占比达到了25%，同比增长10个百分点。</li><li style=\"text-align: start;\">客户关系管理：通过优化客户关系管理系统，我们的客户满意度显著提升，客户留存率提高了5个百分点。新客户的获取成本降低了10%，进一步提高了整体运营效率。</li></ol><h4 style=\"text-align: start;\">财务分析</h4><ul><li style=\"text-align: start;\"><strong>收入增长：</strong> 主要得益于新产品推出和国际市场的扩展，销售收入持续增长。</li><li style=\"text-align: start;\"><strong>成本控制：</strong> 通过优化供应链管理和提高生产效率，运营成本得到有效控制。</li><li style=\"text-align: start;\"><strong>研发投入：</strong> 公司持续加大研发投入，以保持技术领先优势，未来将继续在人工智能和物联网领域进行深入探索。</li></ul>', '2024-11-02 22:08:58');
INSERT INTO `notice` VALUES (2, '网站维护通知', '<blockquote style=\"text-align: start;\">今天辰星带大家深入探讨一番，关于 MySQL 中 TEXT 和 LONGTEXT 数据类型的区别与具体使用场景问题。最近有粉丝私信我说：“猫哥，TEXT 和 LONGTEXT 到底应该怎么选择啊？”</blockquote><p style=\"text-align: start;\">我相信很多开发者在面对 <strong>MySQL</strong> 中的这两个数据类型时，都有些困惑：它们看起来差不多，但存储容量差异很大，具体使用中怎么选择才最合适呢？今天我们就来彻底搞清楚！</p><h4 style=\"text-align: start;\">1. 引言 📝</h4><p style=\"text-align: start;\">在开发<a href=\"https://cloud.tencent.com/solution/database?from_column=20065&amp;from=20065\" target=\"_blank\">数据库</a>应用时，存储大文本内容是不可避免的，而选择合适的数据类型不仅能帮助优化性能，还能节省存储空间。 TEXT 和 LONGTEXT 在 <strong>MySQL</strong> 中是非常重要的存储类型，用来存储大量的文本信息。本篇文章将详细分析这两个数据类型，从 <strong>存储长度、应用场景、性能差异</strong> 等方面全面解答大家的疑惑。</p><h4 style=\"text-align: start;\">2. TEXT 和 LONGTEXT 的概述 📚</h4><p style=\"text-align: start;\"><span style=\"color: rgb(10, 191, 91); background-color: rgb(243, 245, 249);\"><code>TEXT</code></span> 和 <span style=\"color: rgb(10, 191, 91); background-color: rgb(243, 245, 249);\"><code>LONGTEXT</code></span> 是 <strong>MySQL</strong> 中专门用来存储大文本的字段类型。虽然它们的用途很相似，但各自的 <strong>存储容量</strong> 却有很大的不同。</p><ul><li style=\"text-align: start;\"><strong>TEXT</strong>：最大存储容量为 <strong>65,535 字节</strong>（约 64 KB）。通常适合存储中等长度的文本内容。</li><li style=\"text-align: start;\"><strong>LONGTEXT</strong>：最大存储容量为 <strong>4,294,967,295 字节</strong>（约 4 GB），可以存储超长文本，如完整的文章或书籍。</li></ul><blockquote style=\"text-align: start;\">NOTE: 尽管 `LONGTEXT` 可以存储非常大的文本，但如果不必要地选择它，可能会导致存储资源浪费。</blockquote><h4 style=\"text-align: start;\">3. 数据长度与存储特性 🔍</h4><h5 style=\"text-align: start;\">3.1 数据长度限制 🌐</h5><p style=\"text-align: start;\"><span style=\"color: rgb(10, 191, 91); background-color: rgb(243, 245, 249);\"><code>TEXT</code></span> 和 <span style=\"color: rgb(10, 191, 91); background-color: rgb(243, 245, 249);\"><code>LONGTEXT</code></span> 数据类型的一个最主要区别就是 <strong>存储容量</strong>。</p><ul><li style=\"text-align: start;\"><span style=\"color: rgb(10, 191, 91); background-color: rgb(243, 245, 249);\"><code>TEXT</code></span> 最大长度为 <strong>64 KB</strong>，对于大多数的博客文章、评论等文本信息来说是足够的。</li><li style=\"text-align: start;\"><span style=\"color: rgb(10, 191, 91); background-color: rgb(243, 245, 249);\"><code>LONGTEXT</code></span> 最大长度为 <strong>4 GB</strong>，适合需要存储超大文本的场景，比如电子书全文、大型数据导入的场景。</li></ul><h5 style=\"text-align: start;\">3.2 数据存储细节 🗄️</h5><p style=\"text-align: start;\">MySQL 会将 <code><strong>TEXT</strong></code> 和 <code><strong>LONGTEXT</strong></code> 字段的<a href=\"https://cloud.tencent.com/product/cdcs?from_column=20065&amp;from=20065\" target=\"_blank\">数据存储</a>在表的外部，其字段只存储指向数据的指针。这就意味着对于大量的文本内容，读取这些字段会产生额外的 <strong>I/O 开销</strong>。</p><h4 style=\"text-align: start;\">4. 使用场景分析 🛠️</h4><h5 style=\"text-align: start;\">4.1 TEXT 使用场景 🌟</h5><p style=\"text-align: start;\"><span style=\"color: rgb(10, 191, 91); background-color: rgb(243, 245, 249);\"><code>TEXT</code></span> 类型适合 <strong>中等长度文本</strong> 的存储，常见场景包括：</p><ul><li style=\"text-align: start;\">博客文章内容（不超过 64 KB）</li><li style=\"text-align: start;\">用户评论和反馈</li><li style=\"text-align: start;\">产品描述等</li></ul>', '2024-11-07 22:10:36');

-- ----------------------------
-- Table structure for scenic
-- ----------------------------
DROP TABLE IF EXISTS `scenic`;
CREATE TABLE `scenic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景点主键ID、自增的',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '景点名称',
  `category_id` int(11) NULL DEFAULT NULL COMMENT '景点分类ID',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '封面',
  `detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '景点详情',
  `vendor_id` int(11) NULL DEFAULT NULL COMMENT '供应商ID',
  `save_ids` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '景点收藏者用户ID列表，字符串，用,分割',
  `view_ids` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '景点浏览者用户ID列表，字符串，用,分割',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '景点所在地',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '景点状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scenic
-- ----------------------------
INSERT INTO `scenic` VALUES (1, '大山坳风景区', 5, '/api/online-travel-sys/v1.0/file/getFile?fileName=7019ec810.jpg', '<p>4324324句处理成富文本格式，主要是为了使其在文档或报告中更加美观易读。我们可以使用 Markdown 格式来呈现 SQL 查询语句，并加上适当的注释和格式化。以下是将你提供的 MyBatis 映射文件中的 SQL 查询语句处理成富文本格式的示45454</p>', 1, NULL, '8', 'xx省xxx镇', 1, '2024-10-29 17:50:15');
INSERT INTO `scenic` VALUES (2, '生态农庄', 9, '/api/online-travel-sys/v1.0/file/getFile?fileName=d1306095.jpg', '句处理成富文本格式，主要是为了使其在文档或报告中更加美观易读。我们可以使用 Markdown 格式来呈现 SQL 查询语句，并加上适当的注释和格式化。以下是将你提供的 MyBatis 映射文件中的 SQL 查询语句处理成富文本格式的示', 5, NULL, '8,3', '北京市海淀区中关村大街', 1, '2024-10-29 18:00:27');
INSERT INTO `scenic` VALUES (3, '生态狩猎基地', 8, '/api/online-travel-sys/v1.0/file/getFile?fileName=c8e749e1.jpg', '<h3 style=\"text-align: start; line-height: 1.5;\"><strong>12问题分析</strong></h3><ol><li style=\"text-align: start;\">监听器的名称问题：你在 watch 中定义的监听器是 receiveContent，但在 &lt;Editor&gt; 组件中传递的属性是 reveiveContent（拼写错误，少了一个 c）。这会导致 receiveContent 监听器无法正确绑定到 detail 的变化。</li><li style=\"text-align: start;\">detail 的赋值方式：如果 detail 是一个对象或数组，直接赋值（如 this.detail = row.detail）可能不会触发监听器，因为 Vue 的响应式系统无法检测到对象或数组内部的变化。</li><li style=\"text-align: start;\">Editor 组件的实现：确保 Editor 组件内部正确使用了 reveiveContent 属性，并且在内容变化时触发了 @on-receive 事件。</li></ol><h3 style=\"text-align: start; line-height: 1.5;\"><strong>解决方案</strong></h3><h4 style=\"text-align: start;\">1. <strong>修正属性名称</strong></h4><p style=\"text-align: start;\">将 <span style=\"background-color: var(--ds-md-inline-code-color,#ececec);\"><code>reveiveContent</code></span> 改为 <span style=\"background-color: var(--ds-md-inline-code-color,#ececec);\"><code>receiveContent</code></span>，确保属性名称一致：</p><p style=\"text-align: start;\">vue</p><p style=\"text-align: start;\">复制</p><pre><code >&lt;Editor height=\"300px\" :receiveContent=\"detail\" @on-receive=\"onReceive\" /&gt;</code></pre><h4 style=\"text-align: start;\">2. <code><strong>确保 detail 是响应式的</strong></code></h4><p style=\"text-align: start;\">如果 <span style=\"background-color: var(--ds-md-inline-code-color,#ececec);\"><code>detail</code></span> 是一个对象或数组，使用 <span style=\"background-color: var(--ds-md-inline-code-color,#ececec);\"><code>Vue.set</code></span> 或直接替换整个对象，以确保 Vue 能检测到变化：</p><p style=\"text-align: start;\">javascript</p><p style=\"text-align: start;\">复制</p><pre><code >handleEdit(row) {\n    console.log(\"编辑的景点信息：\" + JSON.stringify(row));\n    this.cover = row.cover;\n    this.dialogOperation = true;\n    this.isOperation = true;\n    this.data = { ...row };\n    this.detail = JSON.parse(JSON.stringify(row.detail)); // 确保 detail 是全新的对象\n}</code></pre><h4 style=\"text-align: start;\">3. <code><strong>检查 Editor 组件的实现</strong></code></h4><p style=\"text-align: start;\">确保 <span style=\"background-color: var(--ds-md-inline-code-color,#ececec);\"><code>Editor</code></span> 组件内部正确使用了 <span style=\"background-color: var(--ds-md-inline-code-color,#ececec);\"><code>receiveContent</code></span> 属性，并在内容变化时触发 <span style=\"background-color: var(--ds-md-inline-code-color,#ececec);\"><code>@on-receive</code></span> 事件。例如：</p><p style=\"text-align: start;\">vue</p><p style=\"text-align: start;\">复制</p><pre><code >&lt;template&gt;\n  &lt;div&gt;\n    &lt;textarea v-model=\"internalContent\"&gt;&lt;/textarea&gt;\n  &lt;/div&gt;\n&lt;/template&gt;\n\n&lt;script&gt;\nexport default {\n  props: {\n    receiveContent: {\n      type: String,\n      default: \'\'\n    }\n  },\n  data() {\n    return {\n      internalContent: this.receiveContent\n    };\n  },\n  watch: {\n    receiveContent(newVal) {\n      this.internalContent = newVal; // 同步外部传入的内容\n    },\n    internalContent(newVal) {\n      this.$emit(\'on-receive\', newVal); // 内容变化时触发事件\n    }\n  }\n};\n&lt;/script&gt;</code></pre><h4 style=\"text-align: start;\">4. <strong>监听器的修正</strong></h4><p style=\"text-align: start;\">确保 <span style=\"background-color: var(--ds-md-inline-code-color,#ececec);\"><code>receiveContent</code></span> 监听器正确绑定到 <span style=\"background-color: var(--ds-md-inline-code-color,#ececec);\"><code>detail</code></span>：</p><p style=\"text-align: start;\">javascript</p><p style=\"text-align: start;\"><img src=\"/api/online-travel-sys/v1.0/file/getFile?fileName=d025cdeSnipaste_2024-12-26_16-50-16.png\" alt=\"/api/online-travel-sys/v1.0/file/getFile?fileName=d025cdeSnipaste_2024-12-26_16-50-16.png\" data-href=\"/api/online-travel-sys/v1.0/file/getFile?fileName=d025cdeSnipaste_2024-12-26_16-50-16.png\" style=\"width: 50%;\"></p><p style=\"text-align: start;\">复制</p><pre><code >watch: {\n    detail: {\n        handler(newVal, oldVal) {\n            console.log(\"接收内容：\", newVal);\n            this.content = newVal;\n        },\n        deep: true, // 启用深度监听\n        immediate: true // 立即触发\n    }\n}</code></pre><h3 style=\"text-align: start; line-height: 1.5;\"></h3>', 1, '8,11', '8,11', '上海市徐汇区漕溪北路', 1, '2024-09-21 18:01:23');
INSERT INTO `scenic` VALUES (4, '滑索基地测试', 3, '/api/online-travel-sys/v1.0/file/getFile?fileName=a0d86ee7.jpg', '<h4 style=\"text-align: start;\">务亮点</h4><ol><li style=\"text-align: start;\">产品创新：本季度，我们成功推出了新一代智能穿戴设备，市场反响热烈，销售额环比增长30%。新产品的推出不仅提升了公司的品牌形象，还为未来的增长奠定了坚实的基础。</li><li style=\"text-align: start;\">市场拓展：我们继续扩大国际市场布局，特别是在东南亚和欧洲市场的表现尤为突出。国际市场的收入占比达到了25%，同比增长10个百分点。</li><li style=\"text-align: start;\">客户关系管理：通过优化客户关系管理系统，我们的客户满意度显著提升，客户留存率提高了5个百分点。新客户的获取成本降低了10%，进一步提高了整体运营效率。</li></ol><h4 style=\"text-align: start;\">财务分析</h4><ul><li style=\"text-align: start;\"><strong>收入增长：</strong> 主要得益于新产品推出和国际市场的扩展，销售收入持续增长。</li><li style=\"text-align: start;\"><strong>成本控制：</strong> 通过优化供应链管理和提高生产效率，运营成本得到有效控制。</li><li style=\"text-align: start;\"><strong>研发投入：</strong> 公司持续加大研发投入，以保持技术领先优势，未来将继续在人工智能和物联网领域进行深入探索。</li></ul>', 5, '3,8', '3,8', '广州市天河区珠江新城花城大道111', 1, '2024-10-29 18:02:08');
INSERT INTO `scenic` VALUES (5, '夕阳桥', 3, '/api/online-travel-sys/v1.0/file/getFile?fileName=b5f7b9e5.png', '句处理成富文本格式，主要是为了使其在文档或报告中更加美观易读。我们可以使用 Markdown 格式来呈现 SQL 查询语句，并加上适当的注释和格式化。以下是将你提供的 MyBatis 映射文件中的 SQL 查询语句处理成富文本格式的示', 5, '3,8', '3,8,14', 'xxx镇夕阳街道121', 1, '2024-11-11 17:14:46');
INSERT INTO `scenic` VALUES (6, '北海银滩', 9, '/api/online-travel-sys/v1.0/file/getFile?fileName=cd17facSnipaste_2024-11-16_18-48-39.png', '<p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">感谢网友🙏🏻在北海住到了宝藏民宿！😭 🌷独栋小楼 🌷近银滩侨港 🌷独立泳池 躺着就能看到海不要太city鸭！ 近期打算来北海的姐妹真的可以安排鸭👭 . </span></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">💙位置真的不错，想去北海银滩就2公里，去侨港风情街3公里🚗自驾来的友友也蛮方便哒，附近就有停车场，主打就是一个便利！ . 💙房间空间也大，我觉得住四个人也没问题的，还有大落地窗，直面大海，视野绝了🌊躺着就能看海上日出日落了！ . 💙还有厨房，有厨具和餐具，可以做饭，卫生间干湿分离，洁癖友友放心入，提供早餐，吃得饱也吃的好🍞 . 📍位置：北海银海区广东路</span></p>', 5, '10,11,9', '8,3,10,11,9,14', '北海银滩处', 1, '2024-11-16 18:49:22');
INSERT INTO `scenic` VALUES (8, '黑独山：水墨画', 4, '/api/online-travel-sys/v1.0/file/getFile?fileName=2e82a7aSnipaste_2024-11-16_18-47-39.png', '<p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">中秋国庆假期快到了，相信很多宝子都在计划出游， 在西北自由的旷野找到了我的精神保护区， 穿行4000公里戈壁高原， 祖国的大好河山真的一点不输国外❗️ 看看青海绚烂多彩的盐湖， 听听西北旷野自由的风， 走走戈壁荒漠的极致孤独， 感受下王维诗中“大漠孤烟直，长河落日圆”的意境。 1️⃣📍U型公路：直通天际无比震撼 2️⃣📍茶卡盐湖：天空之境 3️⃣📍大柴旦翡翠湖拖尾 4️⃣📍张掖七彩丹霞 5️⃣📍黑独山：水墨画 6️⃣📍水上雅丹 7️⃣📍大地之子雕塑群 8️⃣📍大柴旦翡翠湖黄色湖 9️⃣📍艾肯泉：大地之眼 1️⃣0️⃣📍盲崖翡翠湖 1️⃣1️⃣📍丝路遗产城 1️⃣2️⃣📍鸣沙山月牙泉 1️⃣3️⃣📍东台吉乃尔湖 1️⃣4️⃣📍敦煌莫高窟 🚶‍➡️推荐大家反走青甘环线： day1:西宁-门源-祁连山-张掖七彩丹霞-住张掖 day2:张掖-嘉峪关-瓜州雕塑群-住敦煌（沙洲夜市） day3:敦煌莫高窟-敦煌博物馆-鸣沙山月牙泉-住敦煌 day4:敦煌-丝路遗产城-石油小镇-黑独山-住大柴旦 day5:大茶蛋-u型公路-东台吉乃尔湖-水上雅丹-大柴旦翡翠湖-住大柴旦 day6:大柴旦-察尔汗盐湖-茶卡盐湖-住乌兰县 day7:乌兰县-橡皮山-青海湖黑马河-塔尔寺-西宁 💡注意： 1:青海海西海拔在3000+，避免剧烈活动谨防高反，建议反走青甘环线，从甘肃到青海海拔过度，更容易适应高反，此次行程带了角鲨烯乳剂，预防应对高反很管用，相比胶囊类的吸收更快，极力推荐。 2:紫外线比较强注意防晒 3:青海昼夜温差大注意保暖</span></p>', 5, '11', '8,11', '茶卡盐湖', 1, '2024-11-16 18:53:48');
INSERT INTO `scenic` VALUES (9, '黑独山：水墨画', 3, '/api/online-travel-sys/v1.0/file/getFile?fileName=37d8ee3Snipaste_2024-11-16_18-47-27.png', '句处理成富文本格式，主要是为了使其在文档或报告中更加美观易读。我们可以使用 Markdown 格式来呈现 SQL 查询语句，并加上适当的注释和格式化。以下是将你提供的 MyBatis 映射文件中的 SQL 查询语句处理成富文本格式的示', 5, NULL, '8,10,14', '我的精神保护区', 1, '2024-11-16 18:54:03');
INSERT INTO `scenic` VALUES (10, '黑独山：水墨画', 3, '/api/online-travel-sys/v1.0/file/getFile?fileName=d1583f3Snipaste_2024-11-16_18-47-18.png', '<p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">中秋国庆假期快到了，相信很多宝子都在计划出游， 在西北自由的旷野找到了我的精神保护区， 穿行4000公里戈壁高原， 祖国的大好河山真的一点不输国外❗️ 看看青海绚烂多彩的盐湖， 听听西北旷野自由的风， 走走戈壁荒漠的极致孤独， 感受下王维诗中“大漠孤烟直，长河落日圆”的意境。 1️⃣📍U型公路：直通天际无比震撼 2️⃣📍茶卡盐湖：天空之境 3️⃣📍大柴旦翡翠湖拖尾 4️⃣📍张掖七彩丹霞 5️⃣📍黑独山：水墨画 6️⃣📍水上雅丹 7️⃣📍大地之子雕塑群 8️⃣📍大柴旦翡翠湖黄色湖 9️⃣📍艾肯泉：大地之眼 1️⃣0️⃣📍盲崖翡翠湖 1️⃣1️⃣📍丝路遗产城 1️⃣2️⃣📍鸣沙山月牙泉 1️⃣3️⃣📍东台吉乃尔湖 1️⃣4️⃣📍敦煌莫高窟 🚶‍➡️推荐大家反走青甘环线： day1:西宁-门源-祁连山-张掖七彩丹霞-住张掖 day2:张掖-嘉峪关-瓜州雕塑群-住敦煌（沙洲夜市） day3:敦煌莫高窟-敦煌博物馆-鸣沙山月牙泉-住敦煌 day4:敦煌-丝路遗产城-石油小镇-黑独山-住大柴旦 day5:大茶蛋-u型公路-东台吉乃尔湖-水上雅丹-大柴旦翡翠湖-住大柴旦 day6:大柴旦-察尔汗盐湖-茶卡盐湖-住乌兰县 day7:乌兰县-橡皮山-青海湖黑马河-塔尔寺-西宁 💡注意： 1:青海海西海拔在3000+，避免剧烈活动谨防高反，建议反走青甘环线，从甘肃到青海海拔过度，更容易适应高反，此次行程带了角鲨烯乳剂，预防应对高反很管用，相比胶囊类的吸收更快，极力推荐。 2:紫外线比较强注意防晒 3:青海昼夜温差大注意保暖</span></p>', 5, '14', '8,14', '茶卡盐湖', 1, '2024-11-16 18:54:46');
INSERT INTO `scenic` VALUES (11, '相思湖|冬景依然', 5, '/api/online-travel-sys/v1.0/file/getFile?fileName=44694f45.png', '<h2 style=\"text-align: start; line-height: 1.5;\">《雪山之巅》</h2><p style=\"text-align: start;\">清晨，我踏上了通往雪山的路。远处的山峰在晨曦中若隐若现，仿佛一位披着白纱的仙子，静静地伫立在天地之间。寒风凛冽，却带着一种纯净的气息，像是大自然的呼吸，轻轻拂过我的脸庞。</p><p style=\"text-align: start;\">脚下的积雪发出“咯吱咯吱”的声响，仿佛在诉说着这片土地的古老故事。每一步都深深陷入雪中，却又被一种无形的力量托起，仿佛雪山在默默引导着我，走向它的怀抱。</p><p style=\"text-align: start;\">抬头望去，雪山的轮廓在阳光下熠熠生辉，像是被镀上了一层银边。峰顶的积雪在阳光的照射下，闪烁着钻石般的光芒，耀眼而神秘。偶尔有几片雪花从空中飘落，轻盈地舞动着，仿佛在为我引路。</p><p style=\"text-align: start;\">我停下脚步，静静地凝视着这片雪白的世界。雪山是如此宁静，仿佛时间在这里停滞。风停了，雪停了，连呼吸都变得轻柔，生怕打破了这份神圣的寂静。我仿佛听见了雪山的心跳，低沉而有力，与我的脉搏渐渐同步。</p><p style=\"text-align: start;\">忽然，一阵风从山间掠过，卷起一片雪雾，像是雪山在向我低语。它告诉我，它的故事从未结束，它的灵魂永远在这片土地上徘徊。每一片雪花，都是它的记忆；每一阵风，都是它的叹息。</p><p style=\"text-align: start;\">我继续向前，脚下的路越来越陡峭，但心中的敬畏却越来越深。雪山不再是遥不可及的风景，而是一位古老的朋友，用它冰冷的外表，包裹着一颗炽热的心。它的孤独，它的坚韧，它的沉默，都在这一刻与我共鸣。</p><p style=\"text-align: start;\">终于，我登上了山顶。站在这里，仿佛站在了世界的尽头。脚下是茫茫的云海，头顶是湛蓝的天空，而雪山，依旧静静地伫立在那里，像一位永恒的守护者。</p><p style=\"text-align: start;\">我闭上眼睛，感受着雪山的呼吸。风从耳边掠过，带着雪的味道，带着山的气息。这一刻，我仿佛与雪山融为一体，成为了它的一部分。它的孤独，它的宁静，它的力量，都在我的心中回荡。</p><p style=\"text-align: start;\">下山的路，比上山更加艰难。但我知道，雪山的故事已经深深地刻在了我的心里。它的美丽，它的神秘，它的孤独，都将成为我记忆中最珍贵的部分。</p><p style=\"text-align: start;\">当我回头望去，雪山依旧静静地伫立在那里，仿佛从未改变。但我知道，它已经在我心中种下了一颗种子，一颗关于宁静、关于力量、关于永恒的种子。</p><p style=\"text-align: start;\">雪山，谢谢你。</p>', 5, NULL, '8,14', '茶卡盐湖', 1, '2024-11-16 18:55:03');

-- ----------------------------
-- Table structure for scenic_category
-- ----------------------------
DROP TABLE IF EXISTS `scenic_category`;
CREATE TABLE `scenic_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景点分类的主键ID，自增',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '景点分类名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scenic_category
-- ----------------------------
INSERT INTO `scenic_category` VALUES (1, '自驾游');
INSERT INTO `scenic_category` VALUES (2, '露营');
INSERT INTO `scenic_category` VALUES (3, '观光型旅游');
INSERT INTO `scenic_category` VALUES (4, '度假型旅游');
INSERT INTO `scenic_category` VALUES (5, '生态旅游');
INSERT INTO `scenic_category` VALUES (6, '登山');
INSERT INTO `scenic_category` VALUES (7, '滑雪');
INSERT INTO `scenic_category` VALUES (8, '狩猎');
INSERT INTO `scenic_category` VALUES (9, '民宿');
INSERT INTO `scenic_category` VALUES (10, '美食');
INSERT INTO `scenic_category` VALUES (11, '医疗保健');

-- ----------------------------
-- Table structure for scenic_line
-- ----------------------------
DROP TABLE IF EXISTS `scenic_line`;
CREATE TABLE `scenic_line`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景点路线主键ID，自增',
  `scenic_id` int(11) NULL DEFAULT NULL COMMENT '景点ID',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路线封面图',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路线描述',
  `level` int(11) NULL DEFAULT NULL COMMENT '层级',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scenic_line
-- ----------------------------
INSERT INTO `scenic_line` VALUES (2, 4, '/api/online-travel-sys/v1.0/file/getFile?fileName=8af5d8b5.png', '这座古桥位于风景秀丽的小镇边缘，历史悠久，始建于明朝，距今已有近500年的历史。古桥采用精美的青石建造，桥身雕刻着精美的图案，展现了古代工匠的高超技艺。桥下清澈的河水缓缓流淌，桥上行人络绎不绝，成为当地重要的交通枢纽和文化地标。历经风雨沧桑，古桥依然坚固稳固，见证了无数历史变迁，是文化遗产的重要组成部分。', 1, '2024-10-30 16:21:59');
INSERT INTO `scenic_line` VALUES (3, 4, '/api/online-travel-sys/v1.0/file/getFile?fileName=3eb3dff6.png', '这座古镇宛如一幅静谧的水墨画卷，静卧在青山绿水之间，已有千年的历史。青石板铺就的小巷蜿蜒曲折，古色古香的建筑错落有致，仿佛诉说着往昔的繁华与沧桑。潺潺流水环绕古镇，映衬着两岸的柳树与古桥，构成了一幅幅动人的画面。漫步其间，仿佛穿越时空，感受到那份宁静与和谐，让人流连忘返。', 2, '2024-10-30 16:25:06');
INSERT INTO `scenic_line` VALUES (4, 4, '/api/online-travel-sys/v1.0/file/getFile?fileName=49ba2813.png', '这片风景区宛如人间仙境，四季分明，景色各异。春天，山花烂漫，鸟语花香；夏日，绿树成荫，清风徐来；秋时，层林尽染，红叶似火；冬日，银装素裹，静谧祥和。湖水碧波荡漾，倒映着群山与蓝天，仿佛一幅流动的山水画，令人陶醉其中，忘却尘世烦恼。', 4, '2024-10-30 16:26:38');
INSERT INTO `scenic_line` VALUES (5, 4, '/api/online-travel-sys/v1.0/file/getFile?fileName=7ee828c1.png', '步入这片自然的怀抱，你会感受到大自然的鬼斧神工。奇峰怪石拔地而起，瀑布飞流直下，清澈的溪流穿梭于林间，带来阵阵清凉。沿途繁花似锦，树木葱郁，每一处都是大自然精心雕琢的艺术品。在这里，你可以尽情呼吸新鲜空气，聆听自然的声音，享受心灵的宁静与自由。', 5, '2024-10-30 16:27:40');
INSERT INTO `scenic_line` VALUES (10, 3, '/api/online-travel-sys/v1.0/file/getFile?fileName=773e3471.png', '红叶似火；冬日，银装素裹，静谧祥和。湖水碧波荡漾，倒映着群山与蓝天，仿佛一幅流动的山水画，令人陶醉其中，忘却尘世烦恼。', 3, '2024-10-30 18:09:19');
INSERT INTO `scenic_line` VALUES (14, 3, '/api/online-travel-sys/v1.0/file/getFile?fileName=4166dda1.png', '43433', 18, '2024-10-30 18:12:22');
INSERT INTO `scenic_line` VALUES (16, 3, '/api/online-travel-sys/v1.0/file/getFile?fileName=70e05a11.png', '沿途繁花似锦，树木葱郁，每一处都是大自然精心雕琢的艺术品。在这里，你可以尽情呼吸新鲜空气，聆听自然的声音，享受心灵的宁静与自由。\n\n', 5, '2024-10-30 18:13:34');
INSERT INTO `scenic_line` VALUES (17, 5, '/api/online-travel-sys/v1.0/file/getFile?fileName=6da40af1.png', '第一站：水城', 1, '2024-11-11 17:15:31');
INSERT INTO `scenic_line` VALUES (18, 5, '/api/online-travel-sys/v1.0/file/getFile?fileName=1ffaa7c2.png', '第二站：大自然的鬼斧神工', 2, '2024-11-11 17:15:53');
INSERT INTO `scenic_line` VALUES (19, 2, '/api/online-travel-sys/v1.0/file/getFile?fileName=93ef1354.png', 'zhenhao ', 1, '2024-11-11 17:16:28');

-- ----------------------------
-- Table structure for scenic_rating
-- ----------------------------
DROP TABLE IF EXISTS `scenic_rating`;
CREATE TABLE `scenic_rating`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景点评分主键ID，自增',
  `scenic_id` int(11) NULL DEFAULT NULL COMMENT '景点ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `score` int(11) NULL DEFAULT NULL COMMENT '评分分数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scenic_rating
-- ----------------------------
INSERT INTO `scenic_rating` VALUES (1, 4, 11, 3, '2024-10-30 19:07:18');
INSERT INTO `scenic_rating` VALUES (2, 4, 3, 5, '2024-10-30 19:07:37');
INSERT INTO `scenic_rating` VALUES (4, 2, 8, 5, '2024-10-30 19:32:31');
INSERT INTO `scenic_rating` VALUES (7, 4, 8, 3, '2024-11-14 16:43:09');
INSERT INTO `scenic_rating` VALUES (8, 6, 8, 4, '2024-11-16 19:03:57');
INSERT INTO `scenic_rating` VALUES (9, 10, 8, 3, '2024-11-16 20:18:02');
INSERT INTO `scenic_rating` VALUES (10, 11, 8, 3, '2025-01-20 21:12:04');
INSERT INTO `scenic_rating` VALUES (11, 3, 8, 4, '2025-01-20 21:19:53');
INSERT INTO `scenic_rating` VALUES (12, 9, 10, 3, '2025-02-22 15:04:58');
INSERT INTO `scenic_rating` VALUES (13, 11, 14, 4, '2025-04-23 14:53:50');
INSERT INTO `scenic_rating` VALUES (14, 9, 14, 5, '2025-04-23 14:54:18');

-- ----------------------------
-- Table structure for scenic_strategy
-- ----------------------------
DROP TABLE IF EXISTS `scenic_strategy`;
CREATE TABLE `scenic_strategy`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景点攻略主键ID，自增',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `scenic_id` int(11) NULL DEFAULT NULL COMMENT '景点ID',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '景点攻略封面',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '景点标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '景点攻略内容',
  `is_audit` tinyint(1) NULL DEFAULT NULL COMMENT '是否已经审核',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scenic_strategy
-- ----------------------------
INSERT INTO `scenic_strategy` VALUES (5, 3, 1, '/api/online-travel-sys/v1.0/file/getFile?fileName=d1306095.jpg', '挑战3块钱过1天', '句处理成富文本格式，主要是为了使其在文档或报告中更加美观易读。我们可以使用 Markdown 格式来呈现 SQL 查询语句，并加上适当的注释和格式化。以下是将你提供的 MyBatis 映射文件中的 SQL 查询语句处理成富文本格式的示', 1, '2024-10-31 16:26:44');
INSERT INTO `scenic_strategy` VALUES (6, 8, 3, '/api/online-travel-sys/v1.0/file/getFile?fileName=1436bd15.png', '2天游玩路线，玩到开心！！！', '<p>你的真知灼见，栩<u><em><strong>栩如生。测试数据。。。。。</strong></em></u></p><p style=\"text-align: start;\">在2023年第三季度，XYZ科技有限公司继续保持稳健增长，实现了多项关键财务指标的显著提升。以下是本季度的主要财务数据：</p><ul><li style=\"text-align: start;\"><strong>总收入：</strong> 5000万元，同比增长20%。</li><li style=\"text-align: start;\"><strong>净利润：</strong> 800万元，同比增长15%。</li><li style=\"text-align: start;\"><strong>毛利率：</strong> 45%，同比提高2个百分点。</li><li style=\"text-align: start;\"><strong>运营费用：</strong> 1800万元，同比增长10%。</li><li style=\"text-align: start;\"><strong>研发投入：</strong> 1200万元，同比增长25%。</li></ul><h4 style=\"text-align: start;\">业务亮点</h4><ol><li style=\"text-align: start;\">产品创新：本季度，我们成功推出了新一代智能穿戴设备，市场反响热烈，销售额环比增长30%。新产品的推出不仅提升了公司的品牌形象，还为未来的增长奠定了坚实的基础。</li><li style=\"text-align: start;\">市场拓展：我们继续扩大国际市场布局，特别是在东南亚和欧洲市场的表现尤为突出。国际市场的收入占比达到了25%，同比增长10个百分点。</li><li style=\"text-align: start;\">客户关系管理：通过优化客户关系管理系统，我们的客户满意度显著提升，客户留存率提高了5个百分点。新客户的获取成本降低了10%，进一步提高了整体运营效率。</li></ol><h4 style=\"text-align: start;\">财务分析</h4><ul><li style=\"text-align: start;\"><strong>收入增长：</strong> 主要得益于新产品推出和国际市场的扩展，销售收入持续增长。</li><li style=\"text-align: start;\"><strong>成本控制：</strong> 通过优化供应链管理和提高生产效率，运营成本得到有效控制。</li><li style=\"text-align: start;\"><strong>研发投入：</strong> 公司持续加大研发投入，以保持技术领先优势，未来将继续在人工智能和物联网领域进行深入探索。</li></ul>', 1, '2024-11-13 18:10:35');
INSERT INTO `scenic_strategy` VALUES (7, 8, 1, '/api/online-travel-sys/v1.0/file/getFile?fileName=04fbda74.png', '穷游攻略！', '<p>你的真知灼见，栩栩如生。</p><p style=\"text-align: start;\">在2023年第三季度，XYZ科技有限公司继续保持稳健增长，实现了多项关键财务指标的显著提升。以下是本季度的主要财务数据：</p><ul><li style=\"text-align: start;\"><strong>总收入：</strong> 5000万元，同比增长20%。</li><li style=\"text-align: start;\"><strong>净利润：</strong> 800万元，同比增长15%。</li><li style=\"text-align: start;\"><strong>毛利率：</strong> 45%，同比提高2个百分点。</li><li style=\"text-align: start;\"><strong>运营费用：</strong> 1800万元，同比增长10%。</li><li style=\"text-align: start;\"><strong>研发投入：</strong> 1200万元，同比增长25%。</li></ul><h4 style=\"text-align: start;\">业务亮点</h4><ol><li style=\"text-align: start;\">产品创新：本季度，我们成功推出了新一代智能穿戴设备，市场反响热烈，销售额环比增长30%。新产品的推出不仅提升了公司的品牌形象，还为未来的增长奠定了坚实的基础。</li><li style=\"text-align: start;\">市场拓展：我们继续扩大国际市场布局，特别是在东南亚和欧洲市场的表现尤为突出。国际市场的收入占比达到了25%，同比增长10个百分点。</li><li style=\"text-align: start;\">客户关系管理：通过优化客户关系管理系统，我们的客户满意度显著提升，客户留存率提高了5个百分点。新客户的获取成本降低了10%，进一步提高了整体运营效率。</li></ol><h4 style=\"text-align: start;\">财务分析</h4><ul><li style=\"text-align: start;\"><strong>收入增长：</strong> 主要得益于新产品推出和国际市场的扩展，销售收入持续增长。</li><li style=\"text-align: start;\"><strong>成本控制：</strong> 通过优化供应链管理和提高生产效率，运营成本得到有效控制。</li><li style=\"text-align: start;\"><strong>研发投入：</strong> 公司持续加大研发投入，以保持技术领先优势，未来将继续在人工智能和物联网领域进行深入探索。</li></ul>', 1, '2024-11-13 20:37:06');
INSERT INTO `scenic_strategy` VALUES (8, 8, 5, '/api/online-travel-sys/v1.0/file/getFile?fileName=47545faSnipaste_2024-11-16_18-44-44.png', '南浔古镇是我期待很久的地方', '<p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">- 南浔古镇是我期待很久的地方.</span></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">满足我对江南🍃水乡的所有幻想 青瓦、小桥、流水、手摇船 - 这里没有乌镇、周庄那么商业 依旧保留着安静又慢条斯理的日常 真的让人放下不少平日里的焦躁~ </span></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">约个周末休息时去这里走走吧 真心觉得南浔更适合游玩放松. - 分享几个免费打卡点 </span></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">❶百间楼:走在两边的古道，遥看对岸的建筑，加上水中的倒影，宛如画中游. </span></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">❷长发桥:很喜欢的一座桥，搭配岸边的绿树小店，好安逸! </span></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">❸通津桥:在十字港码头，可以拍出桥和水的融合. </span></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">❹水上巴士:坐在船上感受江南气息，三元一位，三十分钟一趟。 拍照时间日出日落时效果更美 🎫门票：免费 全天开放【小景点另外收费】 </span></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">🚅交通：自驾导航至“南浔古镇”,高铁（杭州-湖州高铁站） </span></p><p><span style=\"color: rgb(51, 51, 51); background-color: rgb(255, 255, 255); font-size: 16px;\">🍜美食推荐：浔蹄、双交面、三道茶、浔酥饼、定胜糕，这次出行我还带了点当地美食回去，在张石铭旧宅附近浔饼记里买到一盒巧世巧事的巧克力柑橘片，用的当地特色红美人柑橘，外面裹着巧克力，酸甜不腻，口感非常清新，既好看又好吃，雪花酥也是有柑橘和很多很扎实的优质坚果料里面，奶香浓郁，坚果咸香、红美人柑橘融合了棉花糖的甜，香酥松软，奶味十足，好吃到停不下来～</span></p>', 1, '2024-11-16 18:46:24');
INSERT INTO `scenic_strategy` VALUES (9, 12, 11, '/api/online-travel-sys/v1.0/file/getFile?fileName=d28afc2Snipaste_2024-12-26_16-50-27.png', '测试发布', '<h2 style=\"text-align: start;\">公开的服务地址</h2><pre><code class=\"language-nx\">https://api.moonshot.cn\n</code></pre><p style=\"text-align: start;\">Moonshot 提供基于 HTTP 的 API 服务接入，并且对大部分 API，我们兼容了 OpenAI SDK。</p><h1 style=\"text-align: start;\">快速开始</h1><h2 style=\"text-align: start;\">单轮对话</h2><p style=\"text-align: start;\">OpenAI 官方 SDK 支持 <a href=\"https://github.com/openai/openai-python\" target=\"_blank\">Python(opens in a new tab)</a> 和 <a href=\"https://github.com/openai/openai-node\" target=\"_blank\">Node.js(opens in a new tab)</a> 两种语言，使用 OpenAI SDK 和 Curl 与 API 进行交互的代码如下：</p><p style=\"text-align: start;\">pythoncurlnode.js</p><pre><code class=\"language-nx\">curl https://api.moonshot.cn/v1/chat/completions \\    -H \"Content-Type: application/json\" \\    -H \"Authorization: Bearer $MOONSHOT_API_KEY\" \\    -d \'{        \"model\": \"moonshot-v1-8k\",        \"messages\": [            {\"role\": \"system\", \"content\": \"你是 Kimi，由 Moonshot AI 提供的人工智能助手，你更擅长中文和英文的对话。你会为用户提供安全，有帮助，准确的回答。同时，你会拒绝一切涉及恐怖主义，种族歧视，黄色暴力等问题的回答。Moonshot AI 为专有名词，不可翻译成其他语言。\"},            {\"role\": \"user\", \"content\": \"你好，我叫李雷，1+1等于多少？\"}        ],        \"temperature\": 0.3   }\'\n</code></pre><p style=\"text-align: start;\">其中 $MOONSHOT_API_KEY 需要替换为您在平台上创建的 API Key。</p><p style=\"text-align: start;\">使用 OpenAI SDK 时运行文档中的代码时，需要保证 Python 版本至少为 3.7.1，Node.js 版本至少为 18，OpenAI SDK 版本不低于 1.0.0。</p><pre><code class=\"language-nx\">pip install --upgrade \'openai&gt;=1.0\'\n</code></pre><blockquote style=\"text-align: start;\">我们可以这样简单检验下自己库的版本：python -c \'import openai; print(\"version =\",openai.__version__)\'# 输出可能是 version = 1.10.0，表示当前 python 实际使用了 openai 的 v1.10.0 的库</blockquote><h2 style=\"text-align: start;\">多轮对话</h2><p><br></p>', 1, '2025-02-21 18:29:08');

-- ----------------------------
-- Table structure for scenic_ticket
-- ----------------------------
DROP TABLE IF EXISTS `scenic_ticket`;
CREATE TABLE `scenic_ticket`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景区门票主键ID， 自增',
  `scenic_id` int(11) NULL DEFAULT NULL COMMENT '景点ID',
  `detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '门票描述',
  `number` int(11) NULL DEFAULT NULL COMMENT '门票数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '门票价格',
  `discount` double(10, 1) NULL DEFAULT NULL COMMENT '折扣',
  `use_status` tinyint(1) NULL DEFAULT NULL COMMENT '门票的可用状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scenic_ticket
-- ----------------------------
INSERT INTO `scenic_ticket` VALUES (1, 4, '游玩一日游（畅玩）', 95, 19.95, 9.0, 1, '2024-10-28 11:43:36');
INSERT INTO `scenic_ticket` VALUES (2, 4, '音乐节', 879, 199.00, 9.0, 1, '2024-11-03 11:50:32');
INSERT INTO `scenic_ticket` VALUES (3, 5, '演唱会门票|追星体验', 999, 199.00, NULL, 0, '2024-11-11 22:41:47');
INSERT INTO `scenic_ticket` VALUES (4, 5, '介绍', 111, 188.00, NULL, 1, '2024-11-11 22:44:32');
INSERT INTO `scenic_ticket` VALUES (5, 2, '亲子体验|周末两天', 874, 399.00, NULL, 1, '2024-11-11 22:45:06');

-- ----------------------------
-- Table structure for scenic_ticket_order
-- ----------------------------
DROP TABLE IF EXISTS `scenic_ticket_order`;
CREATE TABLE `scenic_ticket_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '景区门票订单信息主键ID，自增',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `ticket_id` int(11) NULL DEFAULT NULL COMMENT '门票ID',
  `concat_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系人',
  `concat_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系电话',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `buy_number` int(11) NULL DEFAULT NULL COMMENT '购买数量',
  `pay_status` tinyint(1) NULL DEFAULT NULL COMMENT '支付状态',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scenic_ticket_order
-- ----------------------------
INSERT INTO `scenic_ticket_order` VALUES (1, 3, 1, '4324', '43243', 232.00, 1, 1, '2024-11-02 13:06:27', '2024-11-01 13:06:31');
INSERT INTO `scenic_ticket_order` VALUES (3, 9, 1, '43243', '43243243', 3232.18, 1, 1, '2024-11-08 13:06:27', '2024-11-07 13:06:27');
INSERT INTO `scenic_ticket_order` VALUES (4, 12, 2, '4324', '43234', 1900.00, 1, 1, '2024-11-12 19:22:01', '2024-11-12 19:22:05');
INSERT INTO `scenic_ticket_order` VALUES (5, 8, 5, '辰星', '1454323232', 2793.00, 7, 1, '2024-11-15 23:09:04', '2024-11-15 17:27:21');
INSERT INTO `scenic_ticket_order` VALUES (6, 8, 5, '张岚', '12212121', 1197.00, 3, 1, '2024-11-15 23:08:56', '2024-11-15 17:32:03');
INSERT INTO `scenic_ticket_order` VALUES (7, 8, 5, '1212', '321321', 1596.00, 4, 1, '2024-11-12 23:01:26', '2024-11-15 17:33:12');
INSERT INTO `scenic_ticket_order` VALUES (8, 8, 1, '1212', '321321', 71.64, 4, 1, '2024-11-15 23:01:25', '2024-11-15 17:34:19');
INSERT INTO `scenic_ticket_order` VALUES (9, 8, 2, '32323', '43434', 716.40, 4, 1, '2024-11-15 23:09:34', '2024-11-15 17:35:20');
INSERT INTO `scenic_ticket_order` VALUES (10, 8, 2, '3232', '432432', 716.40, 4, 1, '2024-11-04 23:01:20', '2024-11-15 17:35:43');
INSERT INTO `scenic_ticket_order` VALUES (11, 8, 2, '3232', '323232', 179.10, 1, 1, '2024-11-16 20:19:07', '2024-11-16 20:18:47');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '用户头像',
  `user_email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `user_role` int(11) NULL DEFAULT NULL COMMENT '用户角色',
  `is_login` tinyint(1) NULL DEFAULT NULL COMMENT '可登录状态(0：可用，1：不可用)',
  `is_word` tinyint(1) NULL DEFAULT NULL COMMENT '禁言状态(0：可用，1：不可用)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '用户注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '程序员辰星', '14e1b600b1fd579f47433b88e8d85291', 'http://127.0.0.1:9000/online-travel/313a0eb6-f025-4c6c-91a7-9d85ccf05aa31.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=wPMAVXH3jloYlgL2QMnC%2F20250415%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250415T114748Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=899a5c91986d815314b27ee453542be90f5dd19e78074088246f40b0d400b263', '1343243@qq.com', 1, 0, 0, '2024-10-25 12:53:05');
INSERT INTO `user` VALUES (8, 'zhouzhiruo', '周芷若', '14e1b600b1fd579f47433b88e8d85291', '/api/online-travel-sys/v1.0/file/getFile?fileName=a2900c83.png', '123@qq.coom', 2, 0, 0, '2024-10-19 12:53:05');
INSERT INTO `user` VALUES (14, 'huihai', '慧海', '14e1b600b1fd579f47433b88e8d85291', '/api/online-travel-sys/v1.0/file/getFile?fileName=be29c704.png', NULL, 2, 0, 0, '2024-11-17 16:26:15');

-- ----------------------------
-- Table structure for vendor
-- ----------------------------
DROP TABLE IF EXISTS `vendor`;
CREATE TABLE `vendor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '供应商表主键ID，自增',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '供应商的名称',
  `concat_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '供应商联系人',
  `concat_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '供应商联系电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电子邮箱',
  `product_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '产品类型',
  `work_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '办公地址',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `is_audit` tinyint(1) NULL DEFAULT NULL COMMENT '是否已经审核',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '供应商的状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vendor
-- ----------------------------
INSERT INTO `vendor` VALUES (1, '大宝宝集团', '大宝', '17865532211', '122121@qqq.com', '民宿', '大宝街道32号楼', 12, 1, 1, '2024-10-29 14:07:24');
INSERT INTO `vendor` VALUES (4, 'dss', '张三', '13434343333', '1244@qq.com', NULL, 'ds街道', 10, 1, 1, '2024-10-31 17:54:47');
INSERT INTO `vendor` VALUES (5, 'DSD生态', '小李1', '0772-1213432', '31232@qq.com', '景点', 'xxx街道', 8, 1, 1, '2024-11-11 14:39:35');
INSERT INTO `vendor` VALUES (9, '墨子房地产', '张贵', '13454545555', '167532546@qq.com', '酒店', '长福路17号', 14, 1, 1, '2025-02-21 18:02:45');
INSERT INTO `vendor` VALUES (10, '栗子科技', '桂林', '12090321111', '122343@qq.com', NULL, '丰收街道12号', 9, 1, 1, '2025-02-22 15:42:50');

SET FOREIGN_KEY_CHECKS = 1;
