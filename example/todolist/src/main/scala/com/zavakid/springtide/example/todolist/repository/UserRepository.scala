package com.zavakid.springtide.example.todolist.repository

import com.zavakid.springtide.example.todolist.model.User
import org.springframework.data.jpa.repository.{JpaRepository, JpaSpecificationExecutor, Query}
import org.springframework.stereotype.Repository

/**
 *
 * @author zebin.xuzb 2014-09-27
 */
@Repository
trait UserRepository extends JpaRepository[User, java.lang.Long] with JpaSpecificationExecutor[User] {


  //  @EntityGraph(value = "includeTaskIds", `type` = EntityGraph.EntityGraphType.LOAD)
  //  def findUserById(id: Long): User
  //
  //  @EntityGraph(value = "onlyName", `type` = EntityGraph.EntityGraphType.FETCH)
  //  def findById(id: Long): User

  //def findOnlyId(id: Long): User

  @Query("select u from User u where u.id = ?1")
  def queryUserById(id: Long): User

  def queryUserByName(name: String): User
}
