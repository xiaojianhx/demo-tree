##### 第一部分：问题抛出
常见的设计，采用标准的树形结构，每个结点记录父ID（pid）。利用pid查询子集时，一次只能查询出一层，查询多层时，逻辑代码将会非常繁琐，而且无法一次查询出子集的数量等等，另外多次查询效率不高。

推荐一种数据结构，可以支持无限层级，大部分情况两次查询，可找出所有需要的数据，减少查询次数，提升性能，本质也是树。可应用于评论、用户裂变、地域、分类等
##### 第二部分：原理介绍
###### 基本结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201005140043591.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hpYW9qaWFuaHg=,size_16,color_FFFFFF,t_70#pic_center)
每个节点有三个属性，节点名称、左属性、右属性。其中**左属性**、**右属性**为关键属性。
```java
public class Node {
    private String name;
    private int lft;
    private int rght;
}
```
###### 规律
 1. 祖先节点的左 < 后代节点的左；祖先节点的右 > 后代节点的右；
 2. 同一节点右左之差，除以2并向下取整，即为后代节点数量；
###### 验证
- 验证规律1：
  - 节点（I）左右分别是6、7，其父节点（E）的左右分别是5、8；
  - 节点（C）左右分别是12、17，其子节点（G）左右分别是13、14，子节点（H）是15、16；
  - 节点（B）左右为2、11，其后代节点（D、E、F、I）中最小的左是3，最大的右是10；
  - 节点（A）左右为1、18，其后代节点（B、C、D、E、F、G、H、I）中最小的左是2，最大的右是17；
- 验证规律2：
  - 节点（I）的后代节点数量 = ( 7 - 6 ) / 2 = 0；
  - 节点（E）的后代节点数量 = ( 8 - 5 ) / 2 = 1；
  - 节点（B）的后代节点数量 = ( 11 - 2 ) / 2 = 4；
  - 节点（A）的后代节点数量 = ( 18 - 1 ) / 2 = 8；

###### 找后代节点
- 如找节点（E）的后代节点，条件：左 > 5 AND 右 < 8，结果是节点（I）；
- 如找节点（B）的后代节点，条件：左 > 2 AND 右 < 11；结果是结点（D、E、F、I）；
- 如找节点（A）的后代节点，条件：左 > 1 AND 右 < 18；结果是结点（B、C、D、E、F、G、H、I）；
*如果查询的结果也想包含自己，则上述条件换成 >= 和 <=*
###### 找祖先节点
- 如找节点（B）的祖先节点，条件：左 < 2 AND 右 > 11，结果是节点（A）；
- 如找节点（E）的祖先节点，条件：左 < 5 AND 右 > 8；结果是（A、B）；
- 如找节点（I）的祖先节点，条件：左  < 6 AND 右 > 7；结果是（A、B、E）；
*如果查询的结果也想包含自己，则上述条件换成 <= 和 >=*
###### 增加节点
假设在节点（E）下增加子节点（J），这里只演示在子节点的最后添加，后面专门介绍直接位置插入节点，保证同级节点的顺序。
1. 为新增节点（J）设置左右属性，左 = 父节点的右；右 = 左 + 1；
2. 修改祖先节点的右属性，右 = 右 + 2 WHERE 左 < J（左） && 右 >= J（左），这里涉及到节点（A、B、E）；
3. 修改右侧节点的左右属性，SET 左 = 左 + 2， 右 = 右 + 2 WHERE 左 >= J（右），这里涉及节点（F、G、H、C）；
变化如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201005142211628.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hpYW9qaWFuaHg=,size_16,color_FFFFFF,t_70#pic_center)

插入已完成，可以尝试着把示例图完整画一遍。注意，除第一个节点（A）的左右手动设置外，其余的节点的都是动态算出来的。示例中左是从1开始，可以使用任何一个自然数，如：-1、3678、7758、10000等等；
###### 物理删除节点（不带子节点的节点）
如上例中，删除结点（D）
- 步骤如下：
1. 修改祖先节点的右属性，SET 右 = 右 - 2 WHERE 左 < D（左） AND 右 > D（右），这里涉及到结点（A、B）；
2. 修改右侧节点左右属性，SET 左 = 左 - 2， 右 = 右 - 2 WHERE 左 > D（右），这里涉及节点（E、I、J、F、G、H、C）；
变化如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201005143453275.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hpYW9qaWFuaHg=,size_16,color_FFFFFF,t_70#pic_center)
###### 物理删除节点（带子节点的节点）
利用递归，先删除子节点，再删除自己。（*真实的使用场景中，建议使用逻辑删除，不做物理删除*）
伪代码如下：
```java
public void del(node) {
	List childs = node.childs()
	if(childs != empty) {
	    for;;
	        del(childs[i]);
	    return;
	}
	// 物理删除结点
}
```
##### 第三部分：扩展和示例
上述介绍了基本原理，在实际使用中，扩展几个属性，使用起来会更方便。以对书的评论为例，建立三张表，书籍表、评论表、评论关系表。这里给出个关系BEAN。
```java
public class Rlat {
    private long id;// 这里仅仅是关系表的主键，没有任何业务意义，与pid没有任何关系
    private long rid;// 根级ID（主体ID），这里关联书籍表ID
    private long pid;// 父级ID（业务ID），这里关联评论表ID
    private long bid;// 业务ID，这里关联评论表ID
    private int lft;// 左
    private int rght;// 右
    private int level; // 层级，当只要N级子集时，方便查询；添加结点是维护该属性；
    private int son; // 儿子的数量，需要时直接带出，无需额外查询，修改子节点时，维护该属性；
    private int child; // 后代的数量，需要时直接带出，无需额外查询，修改子节点时，维护该属性；
    private int seq; // 排序（同级有效）
    private long created;// 创建时间
    private long updated;// 修改时间
    private int del_flg; // 逻辑表示，0-正常；1-删除
    // get/set 略
}
```
书籍表数据如下：
|ID | 书名 |
|--|--|
|  1|飞狐外传  |
|  2|雪山飞狐  |
|  3|连城诀  |
|  4|天龙八部  |
|  5|射雕英雄传  |
评论表数据如下：
|ID | 评论内容|
|--|--|
|  1|飞狐外传不错  |
|  2|飞狐外传真不错  |
|  3|飞狐外传真的不错  |
|  4|天龙八部真好  |
|  5|天龙八部太好了  |
评论关系表数据如下：
|ID | 根级ID|父级ID|业务ID|左|右|层级|儿子数量|后代数量|
|--|--|--|--|--|--|--|--|--|--|--|--|
|  1|书籍表ID1|----|----|1|8|0|1|3
|  2|书籍表ID1|评论表ID1|评论表ID1|2|7|1|1|2
|  3|书籍表ID1|评论表ID1|评论表ID2|3|6|2|1|1
|  4|书籍表ID1|评论表ID2|评论表ID3|4|5|3|0|0
|  5|书籍表ID4|----|----|1|6|0|2|2
|  6|书籍表ID4|评论表ID4|评论表ID4|2|3|1|0|0
|  7|书籍表ID4|评论表ID5|评论表ID5|4|5|1|0|0

其中，关系表中ID（1、5）为根数据，即根据书籍表中书籍书籍的数据，每本书应该有且只有一条根数据。新增评论时，应该先判断根数据是否存在，没有则创建。根数据的作用：
- 方便查询；
- 保证数据结构的完整；

##### 第四部分：示例
基于上面书籍评论的场景，写了个例子，实现基本的增删查
```base
JDK:jdk-14.0.1
DB:MySQL8.0.21
```
```sql
CREATE TABLE `books` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NULL DEFAULT '' COLLATE 'utf8mb4_general_ci',
	`created` BIGINT(19) NULL DEFAULT '0' COMMENT '创建时间',
	`updated` BIGINT(19) NULL DEFAULT '0' COMMENT '修改时间',
	`del_flg` TINYINT(1) NULL DEFAULT '0' COMMENT '逻辑标识，0-正常；1-删除；',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='书籍表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `comments` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`bid` INT(10) NOT NULL DEFAULT '0' COMMENT '书籍ID',
	`content` VARCHAR(200) NULL DEFAULT '' COMMENT '评论内容' COLLATE 'utf8mb4_general_ci',
	`created` BIGINT(19) NULL DEFAULT '0' COMMENT '创建时间',
	`updated` BIGINT(19) NULL DEFAULT '0' COMMENT '修改时间',
	`del_flg` TINYINT(1) NULL DEFAULT '0' COMMENT '逻辑标识，0-正常；1-删除；',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='评论表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `rlats` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`rid` INT(10) NOT NULL DEFAULT '0',
	`pid` INT(10) NOT NULL DEFAULT '0',
	`bid` INT(10) NOT NULL DEFAULT '0',
	`lft` INT(10) NOT NULL DEFAULT '0',
	`rght` INT(10) NOT NULL DEFAULT '0',
	`son` INT(10) NOT NULL DEFAULT '0',
	`child` INT(10) NOT NULL DEFAULT '0',
	`level` INT(10) NOT NULL DEFAULT '0',
	`seq` INT(10) NOT NULL DEFAULT '0',
	`created` BIGINT(19) NULL DEFAULT '0' COMMENT '创建时间',
	`updated` BIGINT(19) NULL DEFAULT '0' COMMENT '修改时间',
	`del_flg` TINYINT(1) NULL DEFAULT '0' COMMENT '逻辑标识，0-正常；1-删除；',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='评论表'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;
```
核心方法：
```java
    public void save(long bookId, long pid, String content) throws Exception {

        var now = System.currentTimeMillis();

        var dao = new Dao();

        var helper = DBHelper.build(URL, USER, PASSWORD);

        var bid = dao.insertComment(helper, bookId, content);

        var root = dao.getRlatRootByRid(helper, bookId);

        if (root == null) {
            root = dao.getRalt(helper, dao.insertRlatForRoot(helper, bookId));
        }

        var parent = dao.getRlatByBid(helper, pid);

        if (parent == null) {
            parent = root;
        }

        var rlat = new Rlat();
        rlat.setRid(bookId);

        if (pid == 0) {
            rlat.setPid(bid);
        } else {
            rlat.setPid(pid);
        }

        var lft = parent.getRght();
        var rght = lft + 1;

        rlat.setBid(bid);
        rlat.setLft(lft);
        rlat.setRght(rght);
        rlat.setSon(0);
        rlat.setChild(0);
        rlat.setLevel(parent.getLevel() + 1);
        rlat.setCreated(now);
        rlat.setUpdated(now);
        rlat.setDel_flg((byte) 0);

        dao.insertRlat(helper, rlat);

        dao.updateRlatForAncestor(helper, bookId, lft);
        dao.updateRlatForParent(helper, bookId, lft, rght);
        dao.updateRlatForRght(helper, bookId, rght);
    }
    public void del(long id) throws Exception {

        var dao = new Dao();

        var helper = DBHelper.build(URL, USER, PASSWORD);

        dao.deleteComment(helper, id);

        var rlat = dao.getRlatByBid(helper, id);

        dao.updateRlatAncestorForDelNode(helper, rlat.getRid(), rlat.getLft(), rlat.getRght());
        dao.updateRlatParentForDelNode(helper, rlat.getRid(), rlat.getLft(), rlat.getRght(), rlat.getLevel() - 1);
        dao.updateRlatRghtForDelNode(helper, rlat.getRid(), rlat.getRght());

        dao.deleteRalt(helper, rlat.getId());
    }
```
