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

package com.googlecode.sarasvati.mem;

import com.googlecode.sarasvati.impl.BaseEngine;
import com.googlecode.sarasvati.load.GraphLoader;
import com.googlecode.sarasvati.load.GraphLoaderImpl;
import com.googlecode.sarasvati.load.GraphValidator;

public class MemEngine extends BaseEngine
{
  protected MemGraphRepository repository;

  /**
   * Creates a new MemEngine with the default application context.
   * Each application context can have it's own set of global
   * listeners. MemEngine instances constructed via this constructor
   * will use the global graph cache.
   */
  public MemEngine ()
  {
    this( DEFAULT_APPLICATION_CONTEXT );
  }

  /**
   * Creates a new MemEngine with the default application context.
   * Each application context can have it's own set of global
   * listeners.
   *
   * @param useGlobalGraphCache Specifies whether this engine should
   *                            use the global graph cache, or a
   *                            cache specific to this instance.
   */
  public MemEngine (final boolean useGlobalGraphCache)
  {
    this( DEFAULT_APPLICATION_CONTEXT, useGlobalGraphCache );
  }

  /**
   * Creates a new MemEngine with the given application context.
   * Each application context has it's own set of global listeners.
   *
   * This allows different applications running the same JVM to
   * have different sets of listeners without having to add
   * them at the process level.
   *
   * MemEngine instances constructed via this constructor
   * will use the global graph cache.
   *
   * @param applicationContext The application context
   */
  public MemEngine (final String applicationContext)
  {
    this( applicationContext, true );
  }

  /**
   * Creates a new MemEngine with the given application context.
   * Each application context has it's own set of global listeners.
   *
   * This allows different applications running the same JVM to
   * have different sets of listeners without having to add
   * them at the process level.
   *
   * @param applicationContext The application context
   * @param useGlobalGraphCache Specifies whether this engine should
   *                            use the global graph cache, or a
   *                            cache specific to this instance.
   */
  public MemEngine (final String applicationContext, final boolean useGlobalGraphCache)
  {
    super( applicationContext );
    this.repository = useGlobalGraphCache ? MemGraphRepository.INSTANCE : new MemGraphRepository(false);
  }

  @Override
  public MemGraphFactory getFactory ()
  {
    return MemGraphFactory.INSTANCE;
  }

  @Override
  public MemGraphRepository getRepository ()
  {
    return repository;
  }

  @Override
  public GraphLoader<MemGraph> getLoader()
  {
    return getLoader( null );
  }

  @Override
  public GraphLoader<MemGraph> getLoader (final GraphValidator validator)
  {
    return new GraphLoaderImpl<MemGraph>( getFactory(), getRepository(), validator );
  }

  @Override
  public MemEngine newEngine ()
  {
    return new MemEngine ();
  }
}