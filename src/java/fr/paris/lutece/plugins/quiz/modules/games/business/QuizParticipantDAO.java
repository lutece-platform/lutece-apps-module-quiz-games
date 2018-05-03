/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
package fr.paris.lutece.plugins.quiz.modules.games.business;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * This class provides Data Access methods for QuizParticipant objects
 */
public class QuizParticipantDAO implements IQuizParticipantDAO
{
    private static final String SQL_QUERY_NEW_PK = " SELECT max( id_participant ) FROM quiz_games_participant ";
    private static final String SQL_QUERY_INSERT_PARTICIPANT = "INSERT INTO quiz_games_participant ( id_participant, id_quiz, identifiant, email, first_name, name, is_valid ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_SELECT_PARTICIPANTS = " SELECT id_participant, id_quiz, identifiant, email, first_name, name, is_valid FROM quiz_games_participant WHERE id_quiz = ? AND is_valid = true";
    private static final String SQL_QUERY_SELECT_PARTICIPANTS_BY_IDENTIFIANT_AND_QUIZ_ID = " SELECT id_participant, id_quiz, identifiant, email, first_name, name, is_valid FROM quiz_games_participant WHERE id_quiz = ? AND identifiant = ?";
    private static final String SQL_QUERY_DELETE_PARTICIPANTS_BY_QUIZ = "DELETE FROM quiz_games_participant WHERE id_quiz = ?";

    /**
     * Find the new primary key in the table.
     *
     * @param plugin the plugin
     * @return the new primary key
     */
    int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * Insert a new record in the table.
     * 
     * @param quizParticipant The Instance of the object QuizParticipant
     * @param plugin the plugin
     */
    public void insert( QuizParticipant quizParticipant, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_PARTICIPANT, plugin );
        quizParticipant.setIdParticipant( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, quizParticipant.getIdParticipant( ) );
        daoUtil.setInt( 2, quizParticipant.getIdQuiz( ) );
        daoUtil.setString( 3, quizParticipant.getIdentifiant( ) );
        daoUtil.setString( 4, quizParticipant.getEmail( ) );
        daoUtil.setString( 5, quizParticipant.getFirstName( ) );
        daoUtil.setString( 6, quizParticipant.getName( ) );
        daoUtil.setBoolean( 7, quizParticipant.getIsValid( ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Delete a record from the table
     * 
     * @param nIdQuiz The indentifier of the object QuizParticipant
     * @param plugin the plugin
     */
    public void deleteParticipantsByQuiz( int nIdQuiz, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_PARTICIPANTS_BY_QUIZ, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Returns the list of the participants of a quiz
     * @param nIdQuiz The Quiz identifier
     * @param plugin the plugin
     * @return list The Collection of Participants
     */
    public List<QuizParticipant> selectParticipantsList( int nIdQuiz, Plugin plugin )
    {
        List<QuizParticipant> participantList = new ArrayList<QuizParticipant>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PARTICIPANTS, plugin );
        daoUtil.setInt( 1, nIdQuiz );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            QuizParticipant quizParticipant = new QuizParticipant( );
            quizParticipant.setIdParticipant( daoUtil.getInt( 1 ) );
            quizParticipant.setIdQuiz( daoUtil.getInt( 2 ) );
            quizParticipant.setIdentifiant( daoUtil.getString( 3 ) );
            quizParticipant.setEmail( daoUtil.getString( 4 ) );
            quizParticipant.setFirstName( daoUtil.getString( 5 ) );
            quizParticipant.setName( daoUtil.getString( 6 ) );
            quizParticipant.setIsValid( daoUtil.getBoolean( 7 ) );

            participantList.add( quizParticipant );
        }

        daoUtil.free(  );

        return participantList;
    }

    /**
     * {@inheritDoc}
     */
    public QuizParticipant findByIdentifiantAndQuizId( String identifiant, int idQuiz, Plugin plugin )
    {
        QuizParticipant participant = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_PARTICIPANTS_BY_IDENTIFIANT_AND_QUIZ_ID, plugin );
        daoUtil.setInt( 1, idQuiz );
        daoUtil.setString( 2, identifiant );
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            participant = new QuizParticipant( );
            participant.setIdParticipant( daoUtil.getInt( 1 ) );
            participant.setIdQuiz( daoUtil.getInt( 2 ) );
            participant.setIdentifiant( daoUtil.getString( 3 ) );
            participant.setEmail( daoUtil.getString( 4 ) );
            participant.setFirstName( daoUtil.getString( 5 ) );
            participant.setName( daoUtil.getString( 6 ) );
            participant.setIsValid( daoUtil.getBoolean( 7 ) );
        }

        daoUtil.free( );

        return participant;
    }
}
