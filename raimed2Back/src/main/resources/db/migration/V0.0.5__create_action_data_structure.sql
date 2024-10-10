CREATE TABLE raimed_action
(
    id                 UUID         NOT NULL,
    type               VARCHAR(255) NOT NULL,
    primary_element    VARCHAR(255) NOT NULL,
    virtual_patient_id BIGINT       NOT NULL,
    closed_answer      VARCHAR(255),
    opened_answer      VARCHAR(255),
    question_id        BIGINT,
    speech             VARCHAR(255),
    CONSTRAINT pk_raimed_action PRIMARY KEY (id)
);

ALTER TABLE raimed_action
    ADD CONSTRAINT FK_RAIMED_ACTION_ON_VIRTUAL_PATIENT FOREIGN KEY (virtual_patient_id) REFERENCES raimed_virtual_patient (id);

ALTER TABLE raimed_action
    ADD CONSTRAINT FK_RAIMED_ACTION_ON_QUESTION FOREIGN KEY (question_id) REFERENCES raimed_question (id);