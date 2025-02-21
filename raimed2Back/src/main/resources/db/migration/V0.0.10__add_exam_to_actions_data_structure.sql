ALTER TABLE raimed_action
    ADD COLUMN exam_id BIGINT;

ALTER TABLE raimed_action
    ADD CONSTRAINT FK_RAIMED_ACTION_ON_EXAM FOREIGN KEY (exam_id) REFERENCES raimed_exam (id);