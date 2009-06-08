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

package com.googlecode.sarasvati.hib;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

import com.googlecode.sarasvati.ArcToken;
import com.googlecode.sarasvati.Engine;
import com.googlecode.sarasvati.Env;
import com.googlecode.sarasvati.NodeToken;
import com.googlecode.sarasvati.TokenSet;
import com.googlecode.sarasvati.TokenSetMemberEnv;
import com.googlecode.sarasvati.impl.MapEnv;

@Entity
@Table(name="wf_token_set")
public class HibTokenSet implements TokenSet
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  protected Long    id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "process_id", nullable=false)
  protected HibGraphProcess process;

  @Column(name="name", nullable=false)
  protected String name;

  @Column(name="max_member_index", nullable=false)
  protected int maxMemberIndex;

  @Type (type="yes_no")
  @Column( name="complete", nullable=false)
  protected boolean complete;

  @OneToMany (fetch=FetchType.LAZY, mappedBy="tokenSet", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE } )
  @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
  protected Set<HibTokenSetMemberAttributes>     properties;

  @ForeignKey(name="FK_token_set_attr")
  @CollectionOfElements
  @JoinTable( name="wf_token_set_attr", joinColumns={@JoinColumn( name="token_set_id")})
  @org.hibernate.annotations.MapKey( columns={@Column(name="name")})
  @Column( name="value")
  @Cascade( org.hibernate.annotations.CascadeType.DELETE )
  protected Map<String, String> attrMap;

  @Transient
  protected Env env;

  @Transient
  protected TokenSetMemberEnv memberEnv;

  @Transient
  protected List<ArcToken> activeArcTokens;

  @Transient
  protected List<NodeToken> activeNodeTokens;

  protected HibTokenSet ()
  {
    /* default constructor for hibernate */
  }

  public HibTokenSet (final HibGraphProcess process,
                      final String name,
                      final int maxMemberIndex)
  {
    this.process = process;
    this.name = name;
    this.maxMemberIndex = maxMemberIndex;
    this.activeArcTokens = new LinkedList<ArcToken>();
    this.activeNodeTokens = new LinkedList<NodeToken>();
  }

  public Long getId ()
  {
    return id;
  }

  public void setId (Long id)
  {
    this.id = id;
  }

  @Override
  public String getName ()
  {
    return name;
  }

  public void setName (final String name)
  {
    this.name = name;
  }

  @Override
  public HibGraphProcess getProcess ()
  {
    return process;
  }

  public void setProcess (final HibGraphProcess process)
  {
    this.process = process;
  }

  public int getMaxMemberIndex ()
  {
    return maxMemberIndex;
  }

  public void setMaxMemberIndex (int maxMemberIndex)
  {
    this.maxMemberIndex = maxMemberIndex;
  }

  public Map<String, String> getAttrMap ()
  {
    return attrMap;
  }

  public void setAttrMap (Map<String, String> attrMap)
  {
    this.attrMap = attrMap;
  }

  @Override
  public Env getEnv ()
  {
    if (env == null)
    {
      env = new MapEnv( getAttrMap() );
    }
    return env;
  }

  @Override
  public TokenSetMemberEnv getMemberEnv ()
  {
    if (memberEnv == null)
    {
      memberEnv = new HibTokenSetMemberEnv( this );
    }
    return memberEnv;
  }

  public Set<HibTokenSetMemberAttributes> getProperties ()
  {
    return properties;
  }

  public void setProperties (Set<HibTokenSetMemberAttributes> properties)
  {
    this.properties = properties;
  }

  @Override
  public List<ArcToken> getActiveArcTokens (Engine engine)
  {
    if ( activeArcTokens == null )
    {
      activeArcTokens = ((HibEngine)engine).getActiveArcTokens( this );
    }
    return activeArcTokens;
  }

  @Override
  public List<NodeToken> getActiveNodeTokens (Engine engine)
  {
    if ( activeNodeTokens == null )
    {
      activeNodeTokens = ((HibEngine)engine).getActiveNodeTokens( this );
    }
    return activeNodeTokens;
  }

  @Override
  public boolean isComplete ()
  {
    return complete;
  }

  public void setComplete (boolean complete)
  {
    this.complete = complete;
  }

  @Override
  public void markComplete (Engine engine)
  {
    complete = true;
  }

  @Override
  public int hashCode ()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals (Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof HibTokenSet))
      return false;
    HibTokenSet other = (HibTokenSet) obj;
    if (id == null)
    {
      if (other.getId() != null)
        return false;
    } else if (!id.equals( other.getId() ))
      return false;
    return true;
  }
}