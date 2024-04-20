# 数据库初始化

-- 创建库
create database if not exists llm_refactor;

-- 切换库
use llm_refactor;

-- 实验代码表，存储重构前后的代码内容以及检测报告
create table if not exists code_exp
(
    id                  bigint      auto_increment  comment '代码 id' primary key,
    file_name           varchar(512)    not null    comment '代码文件的名字',
    language_type       varchar(16)     not null    comment '代码的语言类型',
    origin_code         text            null        comment '重构前的代码内容',
    origin_report       text                        comment '原始的测评报告',
    origin_num_problem  int                     comment '原代码的检测报告中报告的问题数',
    prompt              text                        comment '所用的Prompt',
    new_code            text                        comment '重构后的代码内容',
    new_report          text                        comment '重构后的测评报告',
    new_num_problem     int                         comment '重构后的代码中报告的问题数',
    createTime          datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time         datetime default CURRENT_TIMESTAMP not null comment '更新时间'
);
