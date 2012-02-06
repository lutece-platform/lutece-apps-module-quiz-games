/*Table structure for table `quiz_jeuxconcours_participant` */

DROP TABLE IF EXISTS `quiz_jeuxconcours_participant`;

CREATE TABLE `quiz_jeuxconcours_participant` (
  `id_participant` int(11) NOT NULL,
  `id_quiz` int(11) NOT NULL,
  `identifiant` varchar(25) collate utf8_unicode_ci NOT NULL,
  `email` varchar(25) collate utf8_unicode_ci NOT NULL,
  `first_name` varchar(25) collate utf8_unicode_ci NOT NULL,
  `name` varchar(25) collate utf8_unicode_ci NOT NULL,
  `is_valid` int(11) default NULL,
  PRIMARY KEY  (`id_participant`)
);

--
-- Structure de la table `quiz_portlet`
--

CREATE TABLE IF NOT EXISTS `quiz_portlet` (
  `id_portlet` int(11) NOT NULL DEFAULT '0',
  `quiz` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_portlet`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;