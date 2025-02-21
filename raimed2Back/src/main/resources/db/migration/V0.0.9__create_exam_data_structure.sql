CREATE TABLE raimed_exam
(
    id   BIGSERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    zone VARCHAR(255) NOT NULL
);

CREATE TABLE raimed_exam_signs
(
    exam_id BIGINT       NOT NULL,
    sign    VARCHAR(255) NOT NULL,
    FOREIGN KEY (exam_id) REFERENCES raimed_exam (id) ON DELETE CASCADE
);
