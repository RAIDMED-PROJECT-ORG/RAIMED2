CREATE TABLE raimed_question
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    type    VARCHAR(255)                            NOT NULL,
    content VARCHAR(255)                            NOT NULL,
    answer  VARCHAR(255)                            ,
    is_mutual BOOLEAN,
    teacher_id UUID,
    filter  VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_raimed_question PRIMARY KEY (id)
);

ALTER TABLE raimed_question
    ADD CONSTRAINT uc_raimed_question_content UNIQUE (content);

-- Set automatique de is_mutual si teacher_id != null
CREATE OR REPLACE FUNCTION set_is_mutual()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.teacher_id IS NULL THEN
        NEW.is_mutual := TRUE;
ELSE
        NEW.is_mutual := FALSE;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER trg_set_is_mutual
    BEFORE INSERT OR UPDATE ON raimed_question
                         FOR EACH ROW
                         EXECUTE FUNCTION set_is_mutual();