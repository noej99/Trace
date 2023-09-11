SELECT * FROM MEMBER;

DELETE FROM MEMBER WHERE m_id = 'dlwmsgk21@gmail.com';
DELETE FROM MEMBER_WRITER  WHERE mw_m_id = 'dlwmsgk21@gmail.com';

INSERT INTO MEMBER values('admin', '$2a$10$mD7/vqg/vWNBCQC6kcG90OlvJ40w3mUP.Fdxre.KP.rO35MAVdtym', 'admin', 'admin', sysdate, '04320!서울 용산구 한강대로 405!5번출구', 'icon/doge.png', 30, 0);
INSERT INTO MEMBER_WRITER values('admin', 'admin', 'icon/doge.png');

CREATE SEQUENCE 
SELECT *FROM REPORT;

UPDATE FROM MEMBER

SELECT * FROM freeboard;

DELETE FROM FREEBOARD WHERE FB_No <44;