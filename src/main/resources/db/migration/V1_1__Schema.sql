create table `build_number`
(
    `id`      bigint(20) not null auto_increment,
    `branch`  varchar(255) not null,
    `number`  bigint(20) not null,
    `project` varchar(255) not null,
    primary key (`id`)
) engine = innodb
    default charset = utf8;
create unique index `idx_project_branch` on `build_number` (`project`, `branch`);

create table `build`
(
    `id`           bigint(20) not null auto_increment,
    `branch`       varchar(255) not null,
    `build_number` bigint(20) not null,
    `project`      varchar(255) not null,
    primary key (`id`)
) engine = innodb
    default charset = utf8;
create unique index `idx_project_branch` on `build` (`project`, `branch`, `build_number`);



create table `build_label`
(
    `id`          bigint(20) not null auto_increment,
    `label_key`   varchar(255) not null,
    `label_value` varchar(255) not null,
    `build_id`    bigint(20) not null,
    primary key (`id`),
    key           `fk_build` (`build_id`),
    constraint `fk_build` foreign key (`build_id`) references `build` (`id`)
) engine = innodb
    default charset = utf8;
create index `idx_build_label_key` on build_label (label_key);

create table `build_set_template`
(
    `id`   bigint(20) not null auto_increment,
    `name` varchar(255) not null,
    primary key (`id`)
) engine = innodb
    default charset = utf8;
create unique index `unq_env_name` on build_set_template (`name`);


create table `build_template`
(
    `id`                    bigint(20) not null auto_increment,
    `branch`                varchar(255) default null,
    `project`               varchar(255) default null,
    `build_number`          bigint(20),
    `build_set_template_id` bigint(20) not null,
    primary key (`id`),
    key                     `fk_build_set_template` (`build_set_template_id`),
    constraint `fk_build_set_template` foreign key (`build_set_template_id`) references `build_set_template` (`id`)
) engine = innodb
    default charset = utf8;

create table `build_template_labels`
(
    `build_template_id` bigint(20) not null,
    `labels`            varchar(255)          default null,
    `labels_key`        varchar(255) not null default '',
    primary key (`build_template_id`, `labels_key`),
    constraint `fk_build_template` foreign key (`build_template_id`) references `build_template` (`id`)
) engine = innodb
    default charset = utf8;

create table `project`
(
    `id`     bigint(20) not null auto_increment,
    `name`   varchar(255) not null,
    `active` tinyint(1) not null default 1,
    primary key (`id`)
) engine = innodb
    default charset = utf8;

create table `branch`
(
    `id`         bigint(20) not null auto_increment,
    `name`       varchar(255) not null,
    `project_id` bigint(20) not null,
    `active`     tinyint(1) not null default 1,
    primary key (`id`),
    constraint `fk_project_id` foreign key (`project_id`) references `project` (`id`)
) engine = innodb
    default charset = utf8;