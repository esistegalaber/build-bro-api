-- Instance-wide settings as key/value pairs, e.g. `label.validation` (issue #7).
create table `setting`
(
    `setting_key`   varchar(64)  not null,
    `setting_value` varchar(255) not null,
    primary key (`setting_key`)
) engine = innodb
  default charset = utf8;
