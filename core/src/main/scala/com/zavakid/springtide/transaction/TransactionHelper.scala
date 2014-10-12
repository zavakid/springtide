package com.zavakid.springtide.transaction

import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.{TransactionCallback, TransactionTemplate}

/**
 *
 * @author zebin.xuzb 2014-09-27
 */
object TransactionHelper {

  import scala.language.implicitConversions

  def doInTransaction[T](action: => T)(implicit template: TransactionTemplate): T = {
    template.execute(action)
  }

  implicit def actionToTransactionCallback[T](action: => T): TransactionCallback[T] =
    new TransactionCallback[T] {
      override def doInTransaction(status: TransactionStatus): T = action
    }

}
