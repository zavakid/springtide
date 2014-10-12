package com.zavakid.springtide.example.todolist.service.impl

import com.zavakid.springtide.example.todolist.model.User
import com.zavakid.springtide.example.todolist.repository.UserRepository
import com.zavakid.springtide.example.todolist.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 * @author zebin.xuzb 2014-10-09
 */
@Service
class DefaultUserService extends UserService {

  @Autowired
  var userRepository: UserRepository = _

  override def findUser(id: Long): Option[User] =
    Option(userRepository.getOne(id))

  override def save(user: User): User =
    userRepository.save(user)

  override def findUserByName(name: String): Option[User] =
    Option(userRepository.queryUserByName(name))

}
