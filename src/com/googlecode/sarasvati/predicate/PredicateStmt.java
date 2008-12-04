package com.googlecode.sarasvati.predicate;

import com.googlecode.sarasvati.predicate.visitor.PredicateVisitor;

public interface PredicateStmt
{
  Object eval (PredicateEnv env);

  void traverse (PredicateVisitor visitor);
}
