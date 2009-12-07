/*
    This file is part of Sarasvati.

    Sarasvati is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    Sarasvati is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with Sarasvati.  If not, see <http://www.gnu.org/licenses/>.

    Copyright 2008 Paul Lorenz
*/

package com.googlecode.sarasvati.join;

import java.util.Date;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import com.googlecode.sarasvati.join.lang.JoinLangExpr;
import com.googlecode.sarasvati.join.lang.JoinLangLexer;
import com.googlecode.sarasvati.rubric.env.RubricEnv;
import com.googlecode.sarasvati.rubric.lang.RubricStmt;
import com.googlecode.sarasvati.rubric.visitor.RubricVisitor;

/**
 * JoinLang is a simple language for defining join requirements
 *
 * <pre>
 *   REQUIRE = 'require' 'node' <node name> ('when' EXPR)?
 * </pre>
 * 
 * A predicate used in an expression is evaluated with the help of a
 * {@link RubricEnv}. Specifically, the method {@link RubricEnv#evalPredicate(String)}
 * is called to evaluated a predicate.
 *
 * <p>
 *
 * Date functions work much the same way, except they are evaluated used the
 * {@link RubricEnv#evalDateFunction(String)} method which returns a {@link Date}.
 *
 * <p>
 *
 * A Rubric statement used in a guard might look something like
 *
 * <pre>
 * if Order.isExpedite then Accept else Skip
 * </pre>
 *
 * A Rubric statement used in generating a task due date might look like
 *
 * <pre>
 *   if Order.isExpedite then (2 days before standardInterval) else (standardInterval)
 * </pre>
 *
 * @author Paul Lorenz
 */
public class JoinLangInterpreter
{
  /**
   * Takes a JoinLang program and returns a compiled version in the form of
   * an Abstract Syntax Tree (AST). The AST can be executed/evaluated with
   * a call to {@link RubricStmt#eval(RubricEnv)} or the AST can be traversed
   * using a {@link RubricVisitor} with call to
   * {@link RubricStmt#traverse(RubricVisitor)}.
   *
   * @param rubricStatement A String containing a Rubric program
   * @throws JoinLangException Thrown if the program is not valid.
   * @return A RubricStmt, which is the root of the compiled AST.
   */
  public static JoinLangExpr compile (final String joinSpecStmt) throws JoinLangException
  {
    JoinLangLexer lexer = new JoinLangLexer( new ANTLRStringStream( joinSpecStmt ) );

    CommonTokenStream stream = new CommonTokenStream( lexer );
    ErrorReportingJoinLangParser parser = new ErrorReportingJoinLangParser( stream );

    try
    {
      JoinLangExpr expr = parser.joinExpr().value;
      JoinLangException e = parser.getError();
      if ( e != null )
      {
        throw e;
      }

      return expr;
    }
    catch ( RecognitionException re )
    {
      // this should never happen, as the parser is configured to not throw exceptions.
      System.err.println( "Received unexpected recognition exception" );
      re.printStackTrace();
      return null;
    }
  }
}