-- Optional label definitions (issue #7): a type, allowed values and a display style per label key.
-- The default utf8 collation is case-insensitive, so keys and values are unique ignoring case.
create table `label_definition`
(
    `id`                    bigint(20)    not null auto_increment,
    `label_key`             varchar(255)  not null,
    `display_name`          varchar(255)           default null,
    `description`           varchar(1024)          default null,
    `applies_to_build`      tinyint(1)    not null default 1,
    `applies_to_deployment` tinyint(1)    not null default 1,
    `label_type`            varchar(32)   not null default 'STRING',
    `pattern`               varchar(1024)          default null,
    `style`                 varchar(32)            default null,
    primary key (`id`)
) engine = innodb
  default charset = utf8;
create unique index `unq_label_definition_key` on `label_definition` (`label_key`);

create table `label_definition_allowed_value`
(
    `label_definition_id` bigint(20)   not null,
    `position`            int(11)      not null,
    `label_value`         varchar(255) not null,
    primary key (`label_definition_id`, `position`),
    constraint `fk_allowed_value_definition` foreign key (`label_definition_id`) references `label_definition` (`id`)
) engine = innodb
  default charset = utf8;

create table `label_definition_value_style`
(
    `label_definition_id` bigint(20)   not null,
    `label_value`         varchar(255) not null,
    `style`               varchar(32)  not null,
    primary key (`label_definition_id`, `label_value`),
    constraint `fk_value_style_definition` foreign key (`label_definition_id`) references `label_definition` (`id`)
) engine = innodb
  default charset = utf8;
