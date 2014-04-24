/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.quiz.modules.games.web.portlet;

import fr.paris.lutece.plugins.quiz.business.QuizHome;
import fr.paris.lutece.plugins.quiz.modules.games.business.portlet.QuizPortlet;
import fr.paris.lutece.plugins.quiz.modules.games.business.portlet.QuizPortletHome;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.business.portlet.PortletType;
import fr.paris.lutece.portal.business.portlet.PortletTypeHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.web.portlet.PortletJspBean;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage Quiz Portlet features
 */
public class QuizPortletJspBean extends PortletJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants

    // Parameter
    private static final String PARAMETER_QUIZ_ID = "id_quiz";
    private static final String MARK_QUIZ_LIST = "quiz_list";
    private static final String MESSAGE_YOU_MUST_CHOOSE_A_QUIZ = "module.quiz.games.message.create_portlet.quiz.mandatory";

    /**
     * Returns the properties prefix used for quiz portlet and defined in
     * lutece.properties file
     * 
     * @return the value of the property prefix
     */
    public String getPropertiesPrefix(  )
    {
        return "portlet.quiz";
    }

    /**
     * Returns the Quiz Portlet form of creation
     * 
     * @param request The Http rquest
     * @return the quiz code of the quiz portlet form
     */
    public String getCreate( HttpServletRequest request )
    {
        String strPageId = request.getParameter( PARAMETER_PAGE_ID );
        String strPortletTypeId = request.getParameter( PARAMETER_PORTLET_TYPE_ID );
        Map<String, Object> model = new HashMap<String, Object>(  );
        PortletType portletType = PortletTypeHome.findByPrimaryKey( strPortletTypeId );
        Plugin plugin = PluginService.getPlugin( portletType.getPluginName( ) );
        ReferenceList quizList = ReferenceList.convert( QuizHome.findAll( plugin ), "idQuiz", "name", true );

        model.put( MARK_QUIZ_LIST, quizList );

        HtmlTemplate template = getCreateTemplate( strPageId, strPortletTypeId, model );

        return template.getHtml( );
    }

    /**
     * Returns the Quiz Portlet form for update
     * 
     * @param request The Http request
     * @return the quiz code of the quiz portlet form
     */
    public String getModify( HttpServletRequest request )
    {
        String strPortletId = request.getParameter( PARAMETER_PORTLET_ID );
        int nPortletId = Integer.parseInt( strPortletId );
        QuizPortlet portlet = (QuizPortlet) PortletHome.findByPrimaryKey( nPortletId );
        Map<String, Object> model = new HashMap<String, Object>(  );
        Plugin plugin = PluginService.getPlugin( portlet.getPluginName( ) );
        ReferenceList quizList = ReferenceList.convert( QuizHome.findAll( plugin ), "idQuiz", "name", true );

        model.put( MARK_QUIZ_LIST, quizList );

        HtmlTemplate template = getModifyTemplate( portlet, model );

        return template.getHtml( );
    }

    /**
     * Processes the creation form of a new quiz portlet
     * 
     * @param request The Http request
     * @return The jsp URL which displays the view of the created quiz portlet
     */
    public String doCreate( HttpServletRequest request )
    {
        QuizPortlet quizPortlet = new QuizPortlet( );

        // get portlet common attributes
        String strErrorUrl = setPortletCommonData( request, quizPortlet );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        // get portlet specific attributes
        String strPageId = request.getParameter( PARAMETER_PAGE_ID );
        int nPageId = Integer.parseInt( strPageId );
        quizPortlet.setPageId( nPageId );

        // Clean and Insert the content
        int nQuizId = 0;
        try
        {
            nQuizId = Integer.valueOf( request.getParameter( PARAMETER_QUIZ_ID ) );
        }
        catch ( NumberFormatException e )
        {
            strErrorUrl = AdminMessageService.getMessageUrl( request, MESSAGE_YOU_MUST_CHOOSE_A_QUIZ,
                    AdminMessage.TYPE_STOP );
        }

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        quizPortlet.setQuiz( nQuizId );

        // creates the portlet
        QuizPortletHome.getInstance( ).create( quizPortlet );

        // displays the page with the new portlet
        return "../../" + getPageUrl( quizPortlet.getPageId( ) );
    }

    /**
     * Processes the update form of the quiz portlet whose identifier is in the
     * http request
     * 
     * @param request The Http request
     * @return The jsp URL which displays the view of the updated portlet
     */
    public String doModify( HttpServletRequest request )
    {
        // recovers portlet attributes
        String strPortletId = request.getParameter( PARAMETER_PORTLET_ID );
        int nPortletId = Integer.parseInt( strPortletId );
        QuizPortlet quizPortlet = (QuizPortlet) PortletHome.findByPrimaryKey( nPortletId );

        // get portlet common attributes
        String strErrorUrl = setPortletCommonData( request, quizPortlet );

        if ( strErrorUrl != null )
        {
            return strErrorUrl;
        }

        // Clean and Insert the content
        int nQuizId = 0;
        try
        {
            nQuizId = Integer.valueOf( request.getParameter( PARAMETER_QUIZ_ID ) );
        }
        catch ( NumberFormatException e )
        {
            strErrorUrl = AdminMessageService.getMessageUrl( request, MESSAGE_YOU_MUST_CHOOSE_A_QUIZ,
                    AdminMessage.TYPE_STOP );
        }
        quizPortlet.setQuiz( nQuizId );

        // updates the portlet
        quizPortlet.update( );

        // displays the page with the portlet updated
        return "../../" + getPageUrl( quizPortlet.getPageId( ) );
    }
}
