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

    Copyright 2009 Paul Lorenz
*/
package com.googlecode.sarasvati.join.lang;


public class NodeRequired extends AbstractJoinRequirement
{
  private final String nodeName;

  public NodeRequired (final String nodeName)
  {
    this.nodeName = nodeName;
  }

  public String getNodeName ()
  {
    return nodeName;
  }

  public JoinRequirementEvaluator newEvaluator (final JoinLangEnv env)
  {
    return new NodeRequiredEvaluator( env, this );
  }

  /**
   * @see com.googlecode.sarasvati.join.lang.AbstractJoinRequirement#isEqualTo(JoinRequirement)
   */
  @Override
  public boolean isEqualTo (final JoinRequirement expr)
  {
    if ( !super.isEqualTo( expr ) )
    {
      return false;
    }

    if ( !( expr instanceof NodeRequired) )
    {
      return false;
    }

    NodeRequired other = (NodeRequired)expr;
    return nodeName.equals( other.nodeName );
  }

  @Override
  public String toString ()
  {
    return "require node " + nodeName + (getWhenExpr() == null ? "" : " when (" + getWhenExpr() +")" );
  }
}