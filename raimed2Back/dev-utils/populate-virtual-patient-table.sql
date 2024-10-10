
INSERT INTO raimed_virtual_patient(age, gender, result, created_at, created_by)
VALUES (20, 'MALE', 'Cancer du pancréas', '2019-01-01 00:00:00', (SELECT id FROM raimed_user WHERE username = 'teacher'))
ON CONFLICT DO NOTHING;

-- Add spontaneous patient speech actions
INSERT INTO raimed_action(id, type, speech, primary_element, virtual_patient_id)
VALUES (
    (SELECT gen_random_uuid()),
    'SPONTANEOUS_PATIENT_SPEECH',
    'Je suis essouflé',
    'essoufflement',
    (SELECT last_value FROM raimed_virtual_patient_id_seq)
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, speech, primary_element, virtual_patient_id)
VALUES (
    (SELECT gen_random_uuid()),
   'SPONTANEOUS_PATIENT_SPEECH',
   'J''ai mal au ventre',
   'mal de ventre',
   (SELECT last_value FROM raimed_virtual_patient_id_seq)
) ON CONFLICT DO NOTHING;


-- Add closed question actions
INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
    (SELECT gen_random_uuid()),
    'CLOSED_QUESTION',
    'Oui',
    'fièvre',
    (SELECT last_value FROM raimed_virtual_patient_id_seq),
    (SELECT id FROM raimed_question WHERE content = 'Avez-vous de la fièvre ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Non',
   '',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous du sang rouge dans les selles ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Oui',
   'selles blanches',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous des selles blanches ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Non',
   '',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous les selles noires ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Non',
   '',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous des glaires dans les selles ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Non',
   '',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous du sang sur le papier toilette ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Oui',
   'selles dans les urines',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous des selles dans vos urines ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Oui',
   'contractures anus',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous des contractures douloureuses de l''anus ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Non',
   '',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous des douleurs rectales ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Non',
   '',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous du mal à retenir vos selles ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Oui',
   'douleurs',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous des douleurs ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Oui',
   'voyage',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous voyagé récemment ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Non',
   '',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous des maux de tête le matin ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Oui',
   'idées noires',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous des idées noires ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Non',
   '',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Avez-vous des difficultés à vous endormir ?')
) ON CONFLICT DO NOTHING;

INSERT INTO raimed_action(id, type, closed_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'CLOSED_QUESTION',
   'Oui',
   'douleur reins',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'La douleur irradie-t-elle vers les reins ?')
) ON CONFLICT DO NOTHING;


-- Add opened question actions
INSERT INTO raimed_action(id, type, opened_answer, primary_element, virtual_patient_id, question_id)
VALUES (
   (SELECT gen_random_uuid()),
   'OPENED_QUESTION',
   'Asthme',
   'asthme',
   (SELECT last_value FROM raimed_virtual_patient_id_seq),
   (SELECT id FROM raimed_question WHERE content = 'Quels sont vos antécédents familiaux ?')
) ON CONFLICT DO NOTHING;