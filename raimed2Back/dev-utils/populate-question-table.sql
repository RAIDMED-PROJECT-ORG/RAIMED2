-- Sample of opened questions
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Quels sont vos antécédents familiaux ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Quels sont vos antécédents gynécologiques ?', 'FEMALE') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Que prenez vous comme traitement habituel ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'De quelles allergies souffrez-vous ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Quel est votre métier ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Quels sont vos antécédents médicaux personnels ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Quels sont vos antécédents chirurgicaux personnels ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Quel moyen de contraception utilisez-vous ?', 'FEMALE') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Consommez-vous de l''alcool ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Quels sont vos loisirs ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('OPENED', 'Comment vous vous sentez ?', 'MIXED') ON CONFLICT DO NOTHING;

-- Sample of closed questions
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous de la fièvre ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous du sang rouge dans les selles ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des selles blanches ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous les selles noires ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des glaires dans les selles ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous du sang sur le papier toilette ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des selles dans vos urines ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des contractures douloureuses de l''anus ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des douleurs rectales ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous du mal à retenir vos selles ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des douleurs ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous voyagé récemment ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des maux de tête le matin ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des règles douloureuses ?', 'FEMALE') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des idées noires ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'Avez-vous des difficultés à vous endormir ?', 'MIXED') ON CONFLICT DO NOTHING;
INSERT INTO raimed_question(type, content, filter) VALUES ('CLOSED', 'La douleur irradie-t-elle vers les reins ?', 'MIXED') ON CONFLICT DO NOTHING;