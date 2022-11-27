/*insert into MEMBER(MEMBER_ID, MEMBER_NAME, DELETE_FLAG)
values (1, '토르', false),
(2, '캡틴아메리카', false),
(3, '아이언맨', false),
(4, '헐크', false),
(5, '블랙펜서', false);*/

insert into MEMBER_POINT(MEMBER_ID,POINT,GET_COUNT,CREATE_DATE,UPDATE_DATE,DELETE_FLAG)
values(1,10,2,TIMESTAMPADD(DAY, -1, NOW()),TIMESTAMPADD(DAY, -1, NOW()),false),
(2,10,4,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(3,10,9,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(4,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(5,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(15,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(6,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(7,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(8,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(9,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(10,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(11,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(12,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(13,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
(14,0,0,'2022-10-10 10:10:10','2022-10-10 10:10:10',false);

insert into REWARD (REWARD_ID,SUBJECT,CONTENT,REWARD_TYPE,REWARD_TYPE_NAME,CREATE_DATE,UPDATE_DATE,DELETE_FLAG)
values (1,'포인트 지급','받기를 누르면 포인트를 지급합니다',1,'포인트',now(),now(),false),
       (2,'test포인트 지급','test받기를 누르면 포인트를 지급합니다',1,'포인트',now(),now(),false);

insert into REWARD_DETAIL(REWARD_DETAIL_ID,REWARD_ID,REWARD_COUNT,QUANTITY,MAX_CONTINUE,CREATE_DATE,UPDATE_DATE,DELETE_FLAG)
values (1,1,10,10,10,'2022-10-10 10:10:10','2022-10-10 10:10:10',false),
    (2,2,10,0,10,now(),now(),false);

insert into REWARD_ADDITIONAL(REWARD_ADDITIONAL_ID,REWARD_ID,CONTINUE_DAY,ADD_REWARD,CREATE_DATE,UPDATE_DATE,DELETE_FLAG)
values (1,1,3,300,now(),now(),false),
       (2,1,5,500,now(),now(),false),
       (3,1,10,1000,now(),now(),false),
       (4,2,3,300,now(),now(),false),
       (5,2,5,500,now(),now(),false),
       (6,2,10,1000,now(),now(),false);