package com.zavakid.springtide.example.todolist.service

import com.zavakid.springtide.example.todolist.model.User

/**
 *
 * @author zebin.xuzb 2014-10-09
 */
trait UserService {

  def findUser(id: Long): Option[User]

  def save(user: User): User

  def findUserByName(name: String): Option[User]
}
