create user 'auto_user'@'localhost' identified by 'focusztw';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP on kbook.*  to 'auto_user'@'localhost';