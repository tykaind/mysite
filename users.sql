drop table users;

drop sequence seq_user_no;

create table users(
    no	number,
    id varchar2(20) unique not null,
    password varchar2(20) not null,
    name varchar2(20),
    gender varchar2(10),
    PRIMARY KEY(no));
    
create SEQUENCE seq_user_no
INCREMENT by 1
start with 1
nocache;

insert into users values(SEQ_USER_NO.nextval, 'kjm0221', '1234', '¹Ú¼öÇö', 'male');

commit;
rollback;

select *
from users;

