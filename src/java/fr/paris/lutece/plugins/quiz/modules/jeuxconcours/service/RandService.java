/*
 * Copyright (c) 2002-2012, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.quiz.modules.jeuxconcours.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import fr.paris.lutece.plugins.quiz.modules.jeuxconcours.business.IQuizParticipantDAO;
import fr.paris.lutece.plugins.quiz.modules.jeuxconcours.business.QuizParticipant;
import fr.paris.lutece.portal.service.plugin.Plugin;


/**
 * Rand Service
 * 
 */
public class RandService
{
    @Autowired
    private IQuizParticipantDAO _dao;

    /**
     * Creation of an instance of an article QuizParticipant
     * 
     * @param quizParticipant An instance of the QuizParticipant which contains
     *            the informations to store
     * @param plugin the plugin
     * @return The instance of the QuizParticipant which has been created
     */
    public QuizParticipant create( QuizParticipant quizParticipant, Plugin plugin )
    {
        _dao.insert( quizParticipant, plugin );

        return quizParticipant;
    }

    /**
     * Deletes the QuizParticipant instance whose identifier is specified in
     * parameter
     * 
     * @param nQuiz The identifier of the article QuizParticipant to delete in
     *            the database
     * @param plugin the plugin
     */
    public void removeParticipantsByQuiz( int nQuiz, Plugin plugin )
    {
        _dao.deleteParticipantsByQuiz( nQuiz, plugin );
    }

    /**
     * Returns QuizParticipant list
     * 
     * @param plugin the plugin
     * @param nIdQuiz The identifier of the article Quiz to find it in the
     *            database
     * @return the list of the QuizParticipant of the database in form of a
     *         QuizParticipant Collection object
     */
    public List<QuizParticipant> findAllValidByQuizId( int nIdQuiz, Plugin plugin )
    {
        return _dao.selectParticipantsList( nIdQuiz, plugin );
    }

    /**
     * Return a participant by this identifiant and by quiz
     * @param identifiant the participant identifiant
     * @param idQuiz the quiz id
     * @param plugin the plugin
     * @return participant
     */
    public QuizParticipant findByIdentifiantAndQuizId( String identifiant, int idQuiz, Plugin plugin )
    {
        return _dao.findByIdentifiantAndQuizId( identifiant, idQuiz, plugin );
    }
}
