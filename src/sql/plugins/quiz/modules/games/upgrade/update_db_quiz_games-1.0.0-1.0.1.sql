RENAME TABLE `quiz_jeuxconcours_participant` TO `quiz_games_participant`

ALTER TABLE `quiz_games_participant` MODIFY `identifiant` varchar(255) collate utf8_unicode_ci NOT NULL;
ALTER TABLE `quiz_games_participant` MODIFY `email` varchar(255) collate utf8_unicode_ci NOT NULL;
ALTER TABLE `quiz_games_participant` MODIFY `first_name` varchar(255) collate utf8_unicode_ci NOT NULL;
ALTER TABLE `quiz_games_participant` MODIFY `name` varchar(255) collate utf8_unicode_ci NOT NULL;
