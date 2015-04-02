alter table t_menu add column visit_key_ varchar(20);
update t_menu set visit_key_ = menu_id_ where parent_id_ is not null;
