/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.paris.lutece.plugins.quiz.modules.jeuxconcours.business;

import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;


/**
 * 
 * @author nmoitry
 */
public interface IQuizParticipantDAO
{
    /**
     * Delete an instance of a participant by Quiz
     * 
     * @param nQuiz An instance of the Quiz which contains the informations to
     *            store
     * @param plugin the plugin
     */
    void deleteParticipantsByQuiz( int nQuiz, Plugin plugin );

    /**
     * Creation of a participant Quiz
     * 
     * @param quizParticipant contains the informations to store
     * @param plugin the plugin
     */
    void insert( QuizParticipant quizParticipant, Plugin plugin );

    /**
     * Returns the list of participant for a quiz
     * 
     * @param nIdQuiz The primary key of the article to find in the database
     * @param plugin the plugin
     * @return list of participant
     */
    List<QuizParticipant> selectParticipantsList( int nIdQuiz, Plugin plugin );

    /**
     * Return a participant by this identifiant and by quiz
     * @param identifiant the participant identifiant
     * @param idQuiz the quiz id
     * @param plugin the plugin
     * @return participant
     */
    QuizParticipant findByIdentifiantAndQuizId( String identifiant, int idQuiz, Plugin plugin );
}
