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
package fr.paris.lutece.plugins.quiz.modules.games.web;

import fr.paris.lutece.plugins.quiz.business.Quiz;
import fr.paris.lutece.plugins.quiz.modules.games.business.QuizParticipant;
import fr.paris.lutece.plugins.quiz.modules.games.service.RandService;
import fr.paris.lutece.plugins.quiz.service.QuizService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class manages Rand page.
 */
public class RandApp implements XPageApplication
{
    // TEMPLATES
    private static final String TEMPLATE_QUIZ_RESULTS = "skin/plugins/quiz/modules/games/quiz_results.html";
    private static final String TEMPLATE_QUIZ_ERROR = "skin/plugins/quiz/modules/games/quiz_error.html";
    private static final String TEMPLATE_QUESTIONS_LIST = "skin/plugins/quiz/modules/games/quiz.html";
    // PROPERTIES
    private static final String PROPERTY_QUIZ_RESULTS_PAGE_PATH = "quiz.xpage.pageQuizResultsPath";
    private static final String PROPERTY_QUIZ_RESULTS_PAGE_TITLE = "quiz.xpage.pageQuizResultsTitle";
    private static final String PROPERTY_QUIZ_ERROR_PAGE_PATH = "quiz.xpage.pageQuizErrorPath";
    private static final String PROPERTY_QUIZ_ERROR_PAGE_TITLE = "quiz.xpage.pageQuizErrorTitle";
    private static final String PROPERTY_QUIZ_PAGE_PATH = "quiz.xpage.pageQuizPath";
    private static final String PROPERTY_QUIZ_PAGE_TITLE = "quiz.xpage.pageQuizTitle";
    // PARAMETERS
    private static final String PARAMETER_ID_QUIZ = "quiz_id";
    private static final String PARAMETER_RESULTS = "results";
    private static final String PARAMETER_ACTION = "action";
    private static final String BEAN_QUIZ_SERVICE = "quiz.quizService";
    // MARKERS
    private static final String MARK_ERROR = "error";
    private static final String MARK_QUESTIONS = "questions_count";
    private static final String MARK_SCORE = "score";
    // MESSAGES
    private static final String MESSAGE_ERROR_ONE_PARTICIPATION_MAX = "module.quiz.games.message.error.one_participant.max";

    private RandService _randService = (RandService) SpringContextService.getContext( ).getBean( RandService.class );

    /**
     * Returns the Quiz XPage content depending on the request parameters and
     * the current mode.
     * 
     * @param request The HTTP request.
     * @param nMode The current mode.
     * @param plugin The Plugin
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException
     *             Message displayed if an exception occures
     * @return The page content.
     */
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin ) throws SiteMessageException,
            UserNotSignedException
    {
        String strIdQuiz = request.getParameter( PARAMETER_ID_QUIZ );
        XPage page = new XPage( );

        int nIdQuiz = Integer.parseInt( strIdQuiz );

        page = getQuizResults( nIdQuiz, request, plugin );


        return page;
    }

    /**
     * Return The Answers list
     * @param nQuizId The quiz id
     * @param locale The current locale
     * @param mapParameters request parameters as a map
     * @return The XPage
     * @throws UserNotSignedException
     */
    private XPage getQuizResults( int nQuizId, HttpServletRequest request, Plugin plugin )
            throws UserNotSignedException
    {
        XPage page = new XPage( );

        QuizService serviceQuiz = (QuizService) SpringContextService.getBean( BEAN_QUIZ_SERVICE );
        Map model = serviceQuiz.getResults( nQuizId, request.getParameterMap( ), request.getLocale( ) );

        String strError = (String) model.get( QuizService.KEY_ERROR );

        if ( strError != null )
        {
            return getErrorPage( strError, request.getLocale( ) );
        }


        // Save the participant
        QuizParticipant participant = new QuizParticipant( );
        // Set user informations
        LuteceUser user;
        user = this.getUser( request );

        if ( user == null )
        {
            throw new UserNotSignedException( );
        }

        // Only one participation per person
        QuizParticipant participantExist = this._randService.findByIdentifiantAndQuizId( user.getName( ), nQuizId,
                plugin );
        if ( participantExist != null )
        {
            return getErrorPage(
                    I18nService.getLocalizedString( MESSAGE_ERROR_ONE_PARTICIPATION_MAX, request.getLocale( ) ),
                    request.getLocale( ) );
        }

        participant.setEmail( user.getUserInfo( LuteceUser.HOME_INFO_ONLINE_EMAIL ) );
        participant.setFirstName( user.getUserInfo( LuteceUser.NAME_GIVEN ) );
        participant.setName( user.getUserInfo( LuteceUser.NAME_FAMILY ) );
        participant.setIdentifiant( user.getName( ) );
        participant.setIdQuiz( nQuizId );
        boolean isValid = false;
        if ( model.get( MARK_QUESTIONS ).equals( model.get( MARK_SCORE ) ) )
        {
            isValid = true;
        }
        participant.setIsValid( isValid );
        this._randService.create( participant, plugin );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUIZ_RESULTS, request.getLocale( ), model );

        page.setContent( template.getHtml( ) );

        String strPath = I18nService.getLocalizedString( PROPERTY_QUIZ_RESULTS_PAGE_PATH, request.getLocale( ) );
        String strTitle = I18nService.getLocalizedString( PROPERTY_QUIZ_RESULTS_PAGE_TITLE, request.getLocale( ) );
        Quiz quiz = (Quiz) model.get( QuizService.KEY_QUIZ );
        Object[] args = { quiz.getName( ) };
        page.setPathLabel( MessageFormat.format( strPath, args ) );
        page.setTitle( MessageFormat.format( strTitle, args ) );

        return page;
    }

    /**
     * Return authified user and throw technical exception if no user auth found
     * @param request http request
     * @return user lutece
     */
    protected LuteceUser getUser( HttpServletRequest request ) throws UserNotSignedException
    {
        try
        {
            return SecurityService.getInstance( ).getRemoteUser( request );
        }
        catch ( UserNotSignedException e )
        {
            throw new UserNotSignedException( );
        }
    }

    /**
     * Returns an error page
     * @param strError The error message
     * @param locale The current locale
     * @return The XPage
     */
    private XPage getErrorPage( String strError, Locale locale )
    {
        XPage page = new XPage( );
        Map model = new HashMap( );
        model.put( MARK_ERROR, strError );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_QUIZ_ERROR, locale, model );

        page.setContent( template.getHtml( ) );

        String strPath = I18nService.getLocalizedString( PROPERTY_QUIZ_ERROR_PAGE_PATH, locale );
        String strTitle = I18nService.getLocalizedString( PROPERTY_QUIZ_ERROR_PAGE_TITLE, locale );
        page.setPathLabel( strPath );
        page.setTitle( strTitle );

        return page;
    }
}
