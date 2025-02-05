export enum Zones {
  SKIN = 'SKIN',
  FACE = 'FACE',
  MOUSE = 'MOUSE',
  EYES = 'EYES',
  EARS = 'EARS',
  SHOULDERS = 'SHOULDERS',
  ARMS = 'ARMS',
  CHEST = 'CHEST',
  ABDOMEN = 'ABDOMEN',
  GENITALS = 'GENITALS',
  PERINEUM = 'PERINEUM',
  HANDS = 'HANDS',
  LEGS = 'LEGS',
  HAIRS = 'HAIRS',
  NECK = 'NECK',
  BACK = 'BACK',
  ELBOWS = 'ELBOWS',
  FEET = 'FEET',
  MEMBERS = 'MEMBERS',

}

export const ZoneDisplayNames: Record<Zones, string> = {
  [Zones.SKIN]: 'Peau',
  [Zones.FACE]: 'Visage',
  [Zones.MOUSE]: 'Bouche',
  [Zones.EYES]: 'Yeux',
  [Zones.EARS]: 'Conduits auditifs',
  [Zones.SHOULDERS]: 'Épaules',
  [Zones.ARMS]: 'Bras',
  [Zones.CHEST]: 'Thorax',
  [Zones.ABDOMEN]: 'Abdomen',
  [Zones.GENITALS]: 'Organes génitaux',
  [Zones.PERINEUM]: 'Périné',
  [Zones.HANDS]: 'Mains',
  [Zones.LEGS]: 'Jambes',
  [Zones.HAIRS]: 'Cheveux',
  [Zones.NECK]: 'Cou',
  [Zones.BACK]: 'Dos',
  [Zones.ELBOWS]: 'Coudes',
  [Zones.FEET]: 'Pieds',
  [Zones.MEMBERS]: 'Membres',
};


export const InspectionSigns = {
  [Zones.SKIN]: ['Acanthosis nigricans', 'Angiome plan', 'Angiome rubis', 'Angiomes stellaires', 'Anhydrose', 'Aucune anomalie', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Cyanose', 'Décollement épidermique', 'Dépilation', 'Echymoses', 'Ephélides', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Erythrose faciale', 'Escarre', 'Folliculite', 'Furoncle', 'Hématome', 'Hirsutisme', 'Hyperhydrose', 'Hypertrichose', 'Ictère', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Livedo', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Marbrures', 'Mélanodermie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Nouure cutanée', 'Paleur cutanée', 'Papule érythémateuse', 'Phototype 1', 'Plaque cutanée', 'Poliose', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Sécheresse cutanée', 'Tache café au lait', 'Télangiectasies', 'Tumeur cutanée', 'Ulcération', 'Ulcère creusant', 'Ulcère superficiel', 'Urticaire', 'Verrue', 'Vésicules', 'Vitiligo', 'Xanthélasma', 'Xanthome'],
  [Zones.HAIRS]: ['Alopécie diffuse', 'Alopécie localisée', 'Angiome plan', 'Angiome rubis', 'Aucune anomalie', 'Canitie', 'Erosion', 'Erythème', 'Erythrodermie', 'Folliculite', 'Furoncle', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Naevus', 'Nodule cutané', 'Papule érythémateuse', 'Plaque cutanée', 'Pustules', 'Tache café au lait', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Vésicules'],
  [Zones.FACE]: ['Angiome plan', 'Anhydrose', 'Asymétrie du visage', 'Aucune anomalie', 'Bulles', 'Cicatrice chéloïde', 'Dépilation', 'Déviation de la cloison nasale', 'Dysmorphie du visage', 'Ensellure nasale', 'Ephélides', 'Epicanthus', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Erythrose faciale', 'Exophtalmie bilatérale', 'Exophtalmie unilatérale', 'Faciès lunaire', 'Folliculite', 'Furoncle', 'Goitre', 'Hématome', 'Hirsutisme', 'Hyperhydrose', 'Hypertrichose', 'Hypertrophie glandes lacrymales', 'Hypertrophie glandes sous-mandibulaires', 'Hypertrophie parotidienne bilatérale', 'Hypertrophie parotidienne unilatérale', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Mélanodermie', 'Naevus', 'Nodule cutané', 'Papule érythémateuse', 'Perforation de la cloison nasale', 'Perlèche', 'Plaque cutanée', 'Plis radiés péri-buccaux', 'Polype nasal', 'Ptosis', 'Purpura pétéchial', 'Pustules', 'Rhinorrhée', 'Sclérose cutanée', 'Tache café au lait', 'Télangiectasies', 'Tirage', 'Tuméfaction', 'Tumeur cutanée', 'Turgescence jugulaire', 'Ulcération', 'Urticaire', 'Vésicules', 'Vitiligo', 'Xanthélasma'],
  [Zones.MOUSE]: ['Aphte', 'Aucune anomalie', 'Bulles', 'Bulles hémorragiques intra-buccales', 'Chancre', 'Chéilite', 'Erosion', 'Erythème', 'Erythème pharyngé', 'Fente palatine', 'Glossite', 'Langue dépapillée', 'Leucoplasie', 'Macroglossie', 'Macule érythémateuse', 'Mélanodermie', 'Papule érythémateuse', 'Perlèche', 'Plis radiés péri-buccaux', 'Purpura du voile du palais', 'Purpura nécrotique', 'Purpura pétéchial', 'Sillon lingual médian', 'Stomatite', 'Taches de Koplik', 'Télangiectasies', 'Tuméfaction', 'Ulcération', 'Ulcération buccale', 'Vésicules'],
  [Zones.EARS]: ['Aucune anomalie', 'Cérumen', 'Corps étranger dans le conduit auditif externe', 'Erosion', 'Erythème', 'Hématome', 'Otorragie', 'Otorrhée', 'Tuméfaction', 'Tympan bombant jaunatre', 'Ulcération', 'Vésicules'],
  [Zones.EYES]: ['Anisocorie', 'Arc cornéen', 'Aucune anomalie', 'Epicanthus', 'Erythème conjonctival', 'Exophtalmie bilatérale', 'Exophtalmie unilatérale', 'Hémorragie sous-conjonctivale', 'Hypertrophie glandes lacrymales', 'Mydriase', 'Myosis', 'Paleur conjonctivale', 'Ptosis', 'Subictère', 'Ulcération', 'Xanthélasma'],
  [Zones.HANDS]: ['Aucune anomalie', 'Bulles', 'Cor au pied', 'Coup de vent ulnaire', 'Cyanose', 'Décollement épidermique', 'Déformation d\'un membre', 'Déformation articulaire', 'Dermite ocre', 'Doigts en boutonnière', 'Doigts en col de cygne', 'Dysmorphie des extrémitées', 'Ephélides', 'Erosion', 'Erythème', 'Erythème palmaire', 'Erythrodermie', 'Escarre', 'Gonflement articulaire', 'Hallux valgux', 'Hématome', 'Hemorragies sous-unguéales', 'Hippocratisme digital', 'Hirsutisme', 'Hypertrichose', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Koïlonychie', 'Leuconychie', 'Lignes de Beau', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Mélanodermie', 'Mélanonychie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Ongle incarné', 'Ongles cassants', 'Ongles en dés à coudre', 'Onycholyse', 'Papule érythémateuse', 'Périonyxis', 'Pied creux', 'Pied plat', 'Plaque cutanée', 'Polydactylie', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Sécheresse cutanée', 'Syndactylie', 'Télangiectasies', 'Tremblement d\'action', 'Tremblement de repos', 'Tremblement d\'intention', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Verrue', 'Vésicules', 'Vitiligo', 'Xanthome'],
  [Zones.FEET]: ['Aucune anomalie', 'Bulles', 'Cor au pied', 'Coup de vent ulnaire', 'Cyanose', 'Décollement épidermique', 'Déformation d\'un membre', 'Déformation articulaire', 'Dermite ocre', 'Doigts en boutonnière', 'Doigts en col de cygne', 'Dysmorphie des extrémitées', 'Ephélides', 'Erosion', 'Erythème', 'Erythème palmaire', 'Erythrodermie', 'Escarre', 'Gonflement articulaire', 'Hallux valgux', 'Hématome', 'Hemorragies sous-unguéales', 'Hippocratisme digital', 'Hirsutisme', 'Hypertrichose', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Koïlonychie', 'Leuconychie', 'Lignes de Beau', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Mélanodermie', 'Mélanonychie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Ongle incarné', 'Ongles cassants', 'Ongles en dés à coudre', 'Onycholyse', 'Papule érythémateuse', 'Périonyxis', 'Pied creux', 'Pied plat', 'Plaque cutanée', 'Polydactylie', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Sécheresse cutanée', 'Syndactylie', 'Télangiectasies', 'Tremblement d\'action', 'Tremblement de repos', 'Tremblement d\'intention', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Verrue', 'Vésicules', 'Vitiligo', 'Xanthome'],
  [Zones.ARMS]: ['Amyotrophie', 'Angiome plan', 'Angiome rubis', 'Atrophie blanche', 'Aucune anomalie', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Décollement épidermique', 'Déformation d\'un membre', 'Déformation articulaire', 'Dépilation', 'Dermite ocre', 'Echymoses', 'Ephélides', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Fasciculations musculaires', 'Flessum articulaire', 'Folliculite', 'Furoncle', 'Gonflement articulaire', 'Hématome', 'Hirsutisme', 'Hypertrichose', 'Hypertrophie musculaire', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Livedo', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Marbrures', 'Mélanodermie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Nouure cutanée', 'Œdème du membre inférieur droit', 'Œdème du membre inférieur gauche', 'Oedèmes bilatéraux des membres inférieurs', 'Oedèmes bilatéraux des membres supérieurs', 'Papule érythémateuse', 'Plaque cutanée', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Sécheresse cutanée', 'Tache café au lait', 'Télangiectasies', 'Tremblement d\'action', 'Tremblement de repos', 'Tremblement d\'intention', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Ulcère creusant', 'Ulcère superficiel', 'Urticaire', 'Varices', 'Varicosités', 'Vésicules', 'Vitiligo', 'Xanthome'],
  [Zones.LEGS]: ['Amyotrophie', 'Angiome plan', 'Angiome rubis', 'Atrophie blanche', 'Aucune anomalie', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Décollement épidermique', 'Déformation d\'un membre', 'Déformation articulaire', 'Dépilation', 'Dermite ocre', 'Echymoses', 'Ephélides', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Fasciculations musculaires', 'Flessum articulaire', 'Folliculite', 'Furoncle', 'Gonflement articulaire', 'Hématome', 'Hirsutisme', 'Hypertrichose', 'Hypertrophie musculaire', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Livedo', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Marbrures', 'Mélanodermie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Nouure cutanée', 'Œdème du membre inférieur droit', 'Œdème du membre inférieur gauche', 'Oedèmes bilatéraux des membres inférieurs', 'Oedèmes bilatéraux des membres supérieurs', 'Papule érythémateuse', 'Plaque cutanée', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Sécheresse cutanée', 'Tache café au lait', 'Télangiectasies', 'Tremblement d\'action', 'Tremblement de repos', 'Tremblement d\'intention', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Ulcère creusant', 'Ulcère superficiel', 'Urticaire', 'Varices', 'Varicosités', 'Vésicules', 'Vitiligo', 'Xanthome'],
  [Zones.ELBOWS]: ['Amyotrophie', 'Angiome plan', 'Angiome rubis', 'Atrophie blanche', 'Aucune anomalie', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Décollement épidermique', 'Déformation d\'un membre', 'Déformation articulaire', 'Dépilation', 'Dermite ocre', 'Echymoses', 'Ephélides', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Fasciculations musculaires', 'Flessum articulaire', 'Folliculite', 'Furoncle', 'Gonflement articulaire', 'Hématome', 'Hirsutisme', 'Hypertrichose', 'Hypertrophie musculaire', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Livedo', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Marbrures', 'Mélanodermie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Nouure cutanée', 'Œdème du membre inférieur droit', 'Œdème du membre inférieur gauche', 'Oedèmes bilatéraux des membres inférieurs', 'Oedèmes bilatéraux des membres supérieurs', 'Papule érythémateuse', 'Plaque cutanée', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Sécheresse cutanée', 'Tache café au lait', 'Télangiectasies', 'Tremblement d\'action', 'Tremblement de repos', 'Tremblement d\'intention', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Ulcère creusant', 'Ulcère superficiel', 'Urticaire', 'Varices', 'Varicosités', 'Vésicules', 'Vitiligo', 'Xanthome'],
  [Zones.MEMBERS]: ['Amyotrophie', 'Angiome plan', 'Angiome rubis', 'Atrophie blanche', 'Aucune anomalie', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Décollement épidermique', 'Déformation d\'un membre', 'Déformation articulaire', 'Dépilation', 'Dermite ocre', 'Echymoses', 'Ephélides', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Fasciculations musculaires', 'Flessum articulaire', 'Folliculite', 'Furoncle', 'Gonflement articulaire', 'Hématome', 'Hirsutisme', 'Hypertrichose', 'Hypertrophie musculaire', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Livedo', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Marbrures', 'Mélanodermie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Nouure cutanée', 'Œdème du membre inférieur droit', 'Œdème du membre inférieur gauche', 'Oedèmes bilatéraux des membres inférieurs', 'Oedèmes bilatéraux des membres supérieurs', 'Papule érythémateuse', 'Plaque cutanée', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Sécheresse cutanée', 'Tache café au lait', 'Télangiectasies', 'Tremblement d\'action', 'Tremblement de repos', 'Tremblement d\'intention', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Ulcère creusant', 'Ulcère superficiel', 'Urticaire', 'Varices', 'Varicosités', 'Vésicules', 'Vitiligo', 'Xanthome'],
  [Zones.SHOULDERS]: ['Amyotrophie', 'Angiome plan', 'Angiome rubis', 'Atrophie blanche', 'Aucune anomalie', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Décollement épidermique', 'Déformation d\'un membre', 'Déformation articulaire', 'Dépilation', 'Dermite ocre', 'Echymoses', 'Ephélides', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Fasciculations musculaires', 'Flessum articulaire', 'Folliculite', 'Furoncle', 'Gonflement articulaire', 'Hématome', 'Hirsutisme', 'Hypertrichose', 'Hypertrophie musculaire', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Livedo', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Marbrures', 'Mélanodermie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Nouure cutanée', 'Œdème du membre inférieur droit', 'Œdème du membre inférieur gauche', 'Oedèmes bilatéraux des membres inférieurs', 'Oedèmes bilatéraux des membres supérieurs', 'Papule érythémateuse', 'Plaque cutanée', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Sécheresse cutanée', 'Tache café au lait', 'Télangiectasies', 'Tremblement d\'action', 'Tremblement de repos', 'Tremblement d\'intention', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Ulcère creusant', 'Ulcère superficiel', 'Urticaire', 'Varices', 'Varicosités', 'Vésicules', 'Vitiligo', 'Xanthome'],
  [Zones.CHEST]: ['Angiome plan', 'Angiome rubis', 'Angiomes stellaires', 'Aucune anomalie', 'Balancement thoraco-abdominal', 'Bradypnée', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Circulation veineuse collatérale thoracique', 'Décollement épidermique', 'Echymoses', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Folliculite', 'Furoncle', 'Goitre', 'Gynecomastie', 'Hématome', 'Hirsutisme', 'Hyperhydrose', 'Hypertrichose', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Naevus', 'Nodule cutané', 'Orthopnée', 'Papule érythémateuse', 'Pectus carinatum', 'Pectus excavatum', 'Plaque cutanée', 'Polypnée', 'Purpura ecchymotique', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Respiration de Cheynes-Stokes', 'Respiration de Kussmaul', 'Rétraction mamelonnaire', 'Sclérose cutanée', 'Tache café au lait', 'Télangiectasies', 'Tirage', 'Tuméfaction', 'Tumeur cutanée', 'Turgescence jugulaire', 'Ulcération', 'Urticaire', 'Vésicules', 'Vitiligo'],
  [Zones.ABDOMEN]: ['Abdomen distendu', 'Angiome plan', 'Angiome rubis', 'Ascite visible', 'Aucune anomalie', 'Balancement thoraco-abdominal', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Circulation veineuse collatérale abdominale', 'Décollement épidermique', 'Echymoses', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Eventration', 'Folliculite', 'Furoncle', 'Hématome', 'Hernie inguinale', 'Hernie ombilicale', 'Hirsutisme', 'Hypertrichose', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Livedo', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Naevus', 'Nodule cutané', 'Papule érythémateuse', 'Plaque cutanée', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Tache café au lait', 'Télangiectasies', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Urines décolorées', 'Urines foncées', 'Urines rouges', 'Urines troubles', 'Urticaire', 'Vésicules', 'Vitiligo'],
  [Zones.BACK]: ['Angiome plan', 'Angiome rubis', 'Angiomes stellaires', 'Aucune anomalie', 'Bosse de bison', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Cyphose dorsale', 'Décollement épidermique', 'Echymoses', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Escarre', 'Folliculite', 'Furoncle', 'Goitre', 'Hématome', 'Hirsutisme', 'Hyperhydrose', 'Hyperlordose lombaire', 'Hypertrichose', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Livedo', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Naevus', 'Nodule cutané', 'Papule érythémateuse', 'Plaque cutanée', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Scoliose', 'Tache café au lait', 'Télangiectasies', 'Tirage', 'Tuméfaction', 'Tumeur cutanée', 'Turgescence jugulaire', 'Ulcération', 'Urticaire', 'Vésicules', 'Vitiligo', 'Xanthome'],
  [Zones.NECK]: ['Angiome plan', 'Angiome rubis', 'Angiomes stellaires', 'Aucune anomalie', 'Bosse de bison', 'Bulles', 'Cicatrice chéloïde', 'Cicatrices chirurgicales', 'Cyphose dorsale', 'Décollement épidermique', 'Echymoses', 'Erosion', 'Erythème', 'Erythème morbiliforme', 'Erythème roséoliforme', 'Erythème scarlatiniforme', 'Erythrodermie', 'Escarre', 'Folliculite', 'Furoncle', 'Goitre', 'Hématome', 'Hirsutisme', 'Hyperhydrose', 'Hyperlordose lombaire', 'Hypertrichose', 'Kératose', 'Kératose actinique', 'Kératose séborhéique', 'Livedo', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Naevus', 'Nodule cutané', 'Papule érythémateuse', 'Plaque cutanée', 'Purpura ecchymotique', 'Purpura en vibices', 'Purpura nécrotique', 'Purpura pétéchial', 'Pustules', 'Sclérose cutanée', 'Scoliose', 'Tache café au lait', 'Télangiectasies', 'Tirage', 'Tuméfaction', 'Tumeur cutanée', 'Turgescence jugulaire', 'Ulcération', 'Urticaire', 'Vésicules', 'Vitiligo', 'Xanthome'],
  [Zones.GENITALS]: ['Aucune anomalie', 'Chancre', 'Cryptorchidie', 'Dépilation', 'Erosion', 'Erythème', 'Erythrodermie', 'Escarre', 'Folliculite', 'Furoncle', 'Hernie inguinale', 'Hirsutisme', 'Hypertrichose', 'Kératose', 'Macroorchidie', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Microorchidie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Papule érythémateuse', 'Plaque cutanée', 'Prolapsus rectal', 'Prolapsus utérin', 'Pustules', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Vésicules', 'Vitiligo'],
  [Zones.PERINEUM]: ['Aucune anomalie', 'Chancre', 'Cryptorchidie', 'Dépilation', 'Erosion', 'Erythème', 'Erythrodermie', 'Escarre', 'Folliculite', 'Furoncle', 'Hernie inguinale', 'Hirsutisme', 'Hypertrichose', 'Kératose', 'Macroorchidie', 'Macule achromique', 'Macule érythémateuse', 'Macule pigmentée', 'Microorchidie', 'Naevus', 'Nécrose', 'Nodule cutané', 'Papule érythémateuse', 'Plaque cutanée', 'Prolapsus rectal', 'Prolapsus utérin', 'Pustules', 'Tuméfaction', 'Tumeur cutanée', 'Ulcération', 'Vésicules', 'Vitiligo'],
};

// TODO: Change when we have the real data
export const PalpationSigns = InspectionSigns;
export const PercussionSigns = InspectionSigns;
export const AuscultationSigns = InspectionSigns;

export interface ExamResults {
  zone: Zones;
  signs: string[];
}
