package com.zavakid.springtide.example.todolist.controller.user

import com.zavakid.springtide.example.todolist.model.User
import com.zavakid.springtide.example.todolist.service.UserService
import com.zavakid.springtide.transaction.TransactionHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
 *
 * @author zebin.xuzb 2014-09-27
 */
@RestController
@RequestMapping(Array("/user"))
class UserController {

  @Autowired
  var userService: UserService = _

  @Autowired
  implicit var transaction: TransactionTemplate = _


  @RequestMapping(Array("new"))
  def newUser(model: ModelMap): String = {
    val user = new User()
    model.addAttribute("user", user)
    "/user/new"
  }


  @RequestMapping(Array("list"))
  def list(): String = {
    //    val user = userRepository.findUserById(1)
    //    val user = userRepository.findOne(1L)
    //    val user = userRepository.findById(1L)
    //val user = userRepository.findOnlyId(1L)
    TransactionHelper.doInTransaction {
      userService.findUser(1)
    }.get.getTaskEntities.toString
  }

}
