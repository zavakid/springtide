package com.zavakid.springtide.example.todolist.controller.task

import com.zavakid.springtide.example.todolist.repository.{TaskRepository, UserRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
  *
  * @author zebin.xuzb 2014-09-27
  */
@RestController
 @RequestMapping(Array("/task"))
class TaskController {

   @Autowired
   var userRepository: UserRepository = _

   @Autowired
   var taskRepository: TaskRepository = _

   @RequestMapping(Array("list"))
   def list(): String = {
     val task = taskRepository.findOne(1L)
     println(task)
     "success"
   }

 }
