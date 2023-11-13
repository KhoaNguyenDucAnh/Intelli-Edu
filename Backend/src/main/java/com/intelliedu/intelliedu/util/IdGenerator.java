package com.intelliedu.intelliedu.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * FileIdGenerator
 */
public class IdGenerator extends SequenceStyleGenerator {

  @Override
  public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
    return HashUtil.UUID(); 
  } 
}
