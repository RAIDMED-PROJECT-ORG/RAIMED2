CREATE TABLE raimed_event
(
    id            UUID                        NOT NULL,
    date          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    diagnostic_id BIGINT                      NOT NULL,
    action_id     UUID                        NOT NULL,
    CONSTRAINT pk_raimed_event PRIMARY KEY (id)
);

ALTER TABLE raimed_event
    ADD CONSTRAINT FK_RAIMED_EVENT_ON_ACTION FOREIGN KEY (action_id) REFERENCES raimed_action (id);

ALTER TABLE raimed_event
    ADD CONSTRAINT FK_RAIMED_EVENT_ON_DIAGNOSTIC FOREIGN KEY (diagnostic_id) REFERENCES raimed_diagnostic (id);