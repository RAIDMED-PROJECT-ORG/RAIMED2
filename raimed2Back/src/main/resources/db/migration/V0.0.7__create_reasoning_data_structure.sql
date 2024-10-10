CREATE TABLE raimed_reasoning
(
    id            UUID                        NOT NULL,
    type          VARCHAR(31),
    date          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    degree_of_certainty INTEGER,
    diagnostic_id BIGINT                      NOT NULL,
    value         VARCHAR(255),
    action_id     UUID,
    hypothesis_id UUID,
    CONSTRAINT pk_raimed_reasoning PRIMARY KEY (id)
);

CREATE TABLE reasoning_linked_actions
(
    action_id    UUID NOT NULL,
    reasoning_id UUID NOT NULL,
    CONSTRAINT pk_reasoning_linked_actions PRIMARY KEY (action_id, reasoning_id)
);

CREATE TABLE reasoning_linked_interpretations
(
    interpretation_id UUID NOT NULL,
    reasoning_id      UUID NOT NULL,
    CONSTRAINT pk_reasoning_linked_interpretations PRIMARY KEY (interpretation_id, reasoning_id)
);

CREATE TABLE reasoning_linked_syndroms
(
    reasoning_id UUID NOT NULL,
    syndrom_id   UUID NOT NULL,
    CONSTRAINT pk_reasoning_linked_syndroms PRIMARY KEY (reasoning_id, syndrom_id)
);

ALTER TABLE raimed_reasoning
    ADD CONSTRAINT uc_raimed_reasoning_hypothesis UNIQUE (hypothesis_id);

ALTER TABLE raimed_reasoning
    ADD CONSTRAINT FK_RAIMED_REASONING_ON_ACTION FOREIGN KEY (action_id) REFERENCES raimed_action (id);

ALTER TABLE raimed_reasoning
    ADD CONSTRAINT FK_RAIMED_REASONING_ON_DIAGNOSTIC FOREIGN KEY (diagnostic_id) REFERENCES raimed_diagnostic (id);

ALTER TABLE raimed_reasoning
    ADD CONSTRAINT FK_RAIMED_REASONING_ON_HYPOTHESIS FOREIGN KEY (hypothesis_id) REFERENCES raimed_reasoning (id);

ALTER TABLE reasoning_linked_actions
    ADD CONSTRAINT fk_realinact_on_action FOREIGN KEY (action_id) REFERENCES raimed_action (id);

ALTER TABLE reasoning_linked_actions
    ADD CONSTRAINT fk_realinact_on_syndrom FOREIGN KEY (reasoning_id) REFERENCES raimed_reasoning (id);

ALTER TABLE reasoning_linked_interpretations
    ADD CONSTRAINT fk_realinint_on_interpretation FOREIGN KEY (interpretation_id) REFERENCES raimed_reasoning (id);

ALTER TABLE reasoning_linked_interpretations
    ADD CONSTRAINT fk_realinint_on_syndrom FOREIGN KEY (reasoning_id) REFERENCES raimed_reasoning (id);

ALTER TABLE reasoning_linked_syndroms
    ADD CONSTRAINT fk_realinsyn_on_hypothesis FOREIGN KEY (reasoning_id) REFERENCES raimed_reasoning (id);

ALTER TABLE reasoning_linked_syndroms
    ADD CONSTRAINT fk_realinsyn_on_syndrom FOREIGN KEY (syndrom_id) REFERENCES raimed_reasoning (id);