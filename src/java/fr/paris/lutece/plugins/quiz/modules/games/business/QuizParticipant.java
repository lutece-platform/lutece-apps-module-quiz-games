/*
 * Copyright (c) 2002-2013, Mairie de Paris
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



/**
 * This class represents business object QuizParticipant
 */
public class QuizParticipant
{
    private int _nIdParticipant;
    private int _nIdQuiz;
    private String _strIdentifiant;
    private String _strEmail;
    private String _strFirstName;
    private String _strName;
    private boolean _isValid;

    /**
     * Creates a new QuizParticipant object.
     */
    public QuizParticipant(  )
    {
    }

    /**
     * Returns the identifier of this QuizParticipant.
     * @return _nIdParticipant The Participant identifier
     */
    public int getIdParticipant( )
    {
        return _nIdParticipant;
    }

    /**
     * Sets the identifier of the participant to the specified integer.
     * @param _nIdParticipant The Participant identifier
     */
    public void setIdParticipant( int nIdParticipant )
    {
        _nIdParticipant = nIdParticipant;
    }

    /**
     * Return the identifier of the quiz
     * @return _nIdQuiz The Quiz identifier
     */
    public int getIdQuiz(  )
    {
        return _nIdQuiz;
    }

    /**
     * Sets the identifier of the quiz to the specifies integer
     * @param nIdQuiz The Quiz identifier
     */
    public void setIdQuiz( int nIdQuiz )
    {
        _nIdQuiz = nIdQuiz;
    }

    /**
     * @param email the email to set
     */
    public void setEmail( String email )
    {
        this._strEmail = email;
    }

    /**
     * @return the email
     */
    public String getEmail( )
    {
        return _strEmail;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName( String firstName )
    {
        this._strFirstName = firstName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName( )
    {
        return _strFirstName;
    }

    /**
     * @param name the name to set
     */
    public void setName( String name )
    {
        this._strName = name;
    }

    /**
     * @return the name
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setIsValid( boolean isValid )
    {
        this._isValid = isValid;
    }

    /**
     * @return the isValid
     */
    public boolean getIsValid( )
    {
        return _isValid;
    }

    /**
     * @param identifiant the identifiant to set
     */
    public void setIdentifiant( String identifiant )
    {
        this._strIdentifiant = identifiant;
    }

    /**
     * @return the identifiant
     */
    public String getIdentifiant( )
    {
        return _strIdentifiant;
    }
}
